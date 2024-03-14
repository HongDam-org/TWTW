# Full Text Index 적용기

태그: PORTFOLIO
날짜: 2024년 3월 13일
참여자: 김승진, 진주원

## 문제 상황

---

> ‘이길저길’ 서비스 내에서 친구 검색 시 Member의 **닉네임(문자열)으로 검색을 수행하는 API**가 있고 기존 Like 검색은 데이터의 개수가 많아질수록 **성능에서 부족함**이 있었다.
>

## Like 연산시

---

Member 데이터를 500만 row 저장하고 특정 닉네임을 검색하는 쿼리를 Like 연산을 이용하여 수행하였다.

### 준비 작업 (프로시저)

1. sql로 프로시저 작성 (그대로 실행하면 500만개의 member를 insert한다)
    - 기존 자바 코드로 500만개를 넣으려 시도하니 heap 영역의 공간이 없어 error 발생 !

```sql
DELIMITER $$
DROP PROCEDURE IF EXISTS twtw.insertLoop$$
 
CREATE PROCEDURE twtw.insertLoop()
BEGIN
    DECLARE i INT DEFAULT 1;
    WHILE i <= 5000000 DO
        INSERT INTO twtw.MEMBER(id, created_at, updated_at, auth_type, nickname, profile_image, role, client_id)
				VALUES (UNHEX(REPLACE(uuid(), '-', '')), now(), now(), 'APPLE', concat('n', i), concat('profile_image_', i), 'ROLE_USER', concat('client_id', i));
        SET i = i + 1;
    END WHILE;
END$$
DELIMITER $$

CALL twtw.insertLoop;
$$
```

- 500만 row가 insert 됐다.

![image](https://github.com/HongDam-org/TWTW/assets/84346055/a4cc5c7b-39db-4aa3-89cd-91c97faee2cc)

### Like 연산 결과

```sql
SELECT * FROM member WHERE nickname like '%123456%';
```

### like 연산 쿼리 결과 [2.63s]

![image](https://github.com/HongDam-org/TWTW/assets/84346055/57bf2918-b2fe-4614-bd08-8499168b1d9a)

# Full Text Index 적용

---

> Like 쿼리의 성능이 좋지 않아 효과적인 문자열 탐색에 있어 **Full Text Index를 적용**하기로 했다.
>

- FULL TEXT INDEX 인덱스 적용

```sql
CREATE FULLTEXT INDEX idx_member_nickname ON member (nickname) with parser ngram;
```

- FULL TEXT INDEX 쿼리

```sql
SELECT * FROM member WHERE MATCH(nickname) AGAINST('123456' IN BOOLEAN MODE);
```

## 첫번째 시도

### match 쿼리 결과 [error]

![image](https://github.com/HongDam-org/TWTW/assets/84346055/a6f0f6e8-f723-481d-abd9-7874d2cdc0ad)

- **ngram**

> 문자열을 모두 [디폴트 최소 길이: 2] 만큼 다 분할해 저장했기 때문에 500만 데이터를 쿼리하는데 드는 비용이 커져 **쿼리 캐시 공간이 부족**했다.
>
- **캐시 사이즈 2배 늘려봤지만** 에러는 해결이 되지 않음

![image](https://github.com/HongDam-org/TWTW/assets/84346055/0e62fe27-b3ae-4450-95fb-e347efa8a713)

> 캐시 사이즈를 늘린다면 메모리 부담이 발생하므로 **최적의 방법이라 판단하지 않았다 !**
>
- 다른 해결 방법인 [인덱스 분할], [ElasticSearch 도입], [하드웨어 리소스 확장]은 오버 엔지니어링이라 판단하고 다른 해결방법 고민해보았다 !

### 분석

- 테스트시 최대 길이 20의 문자열을 더미데이터로 삽입한 부분이 문제라 판단

> 현재 서비스 요구사항에서 닉네임의 길이 제한이 없다는 것에 의문을 두고 팀원들과 회의를 거쳐 닉네임 길이의 최대치를 정하기로 결정
>
- 최대치 8로 선정 ( 선정 과정에 있어 다른 서비스 모델을 참고 )

## 문자열 길이 최대 8인 경우

### like 연산 결과 [2.37s]

![image](https://github.com/HongDam-org/TWTW/assets/84346055/e300d6d2-ab41-41b9-ba2a-2292b757d6c3)

### match against boolean mode는 다음과 같은 결과 [29.22s]

![image](https://github.com/HongDam-org/TWTW/assets/84346055/5045258d-cc73-43e0-84f3-accf6bc7e014)

- 쿼리가 상당히 느리다.

> 쿼리 Profiling으로 실행 계획 확인
>

![image](https://github.com/HongDam-org/TWTW/assets/84346055/43da9c5b-8b90-4a95-b056-00a943890123)

- FULLTEXT initialization 이라는 전체 텍스트 인덱스를 초기화하고, 매번 검색을 위해 데이터를 메모리로 로딩하는 과정 에서 약 29s 가 걸림
- Optimize table member 커맨드를 사용하여 FULLTEXT initialization의 성능을 높이려는 시도

![image](https://github.com/HongDam-org/TWTW/assets/84346055/ef5cbc78-e8b6-4ac5-99af-24e330ce2126)

- 하지만 이후에도 성능에 큰 변화가 없었다.

## 쿼리를 분석/수정 해보자

### 기존 쿼리의 문제점 분석

> 검색을 하는 문자열의 길이가 기존 N-gram의 길이 2보다 크기 때문에 더 세세한 검색 과정을 거치기 때문이다.
>
- **2씩 나눠진 토큰을 포함**하면서 동시에 **해당 문자열의 순서까지 일치**하는 데이터를 찾아야 한다.
- N-gram의 길이로 나뉘어진 문자열은 즉 자체로 인덱스를 의미하고 해당 인덱스로만의 탐색이 어렵다는 것이다.

### 쿼리 수정

> ngram parser의 최소 단위인 2만큼 문자열을 나눈 후 이 나눈 문자열이 모두 포함된 부분을 검색하면 더 빠른 검색이 가능할거라 예상
>

```sql
SHOW GLOBAL VARIABLES LIKE "ngram_token_size"; ( 2인걸 확인 )
```

```sql
SELECT * FROM member WHERE MATCH(nickname) AGAINST('+12 +23 +34 +45 +56' IN BOOLEAN MODE);
```

### 길이 2씩 나누어 검색 [0.53s]

![image](https://github.com/HongDam-org/TWTW/assets/84346055/7e9b0951-ad90-41de-8ec3-0f9000b91c79)

- 몇개의 결과가 더 나왔으며 이전과 비교해 누락된 row는 없었다.
    - 더 나온 이유는 순서를 보장하지 않고 12, 23, 34, 45, 56 을 포함하는 문자열을 검색하기 때문
- LIKE 연산 결과보다 4.96배 개선

### 혹시 몰라 길이 3씩 나누어 검색 [1m 4.19s]

![image](https://github.com/HongDam-org/TWTW/assets/84346055/af50d00f-c1ae-40b4-ade6-1b8ebd9f5dff)

- 이건 예상대로 훨씬 성능이 좋지 않다.
- 길이 3만큼 나누어 검색하면 기존의 문자열 하나만 조건으로 넣었을 때보다 더 느림
- 길이가 2가 아닌 문자열인데 심지어 여러개를 검사했기 때문이라 파악

### FULL TEXT INDEX 사용 시

---

- N-gram parser를 이용할 경우 우리가 지정한 길이만큼의 토큰으로 검사를 진행해야 좋은 성능을 발휘할 수 있다.
- 길이가 더 긴 문자열로 탐색을 진행할 경우 해당 문자열의 토큰들의 배치(순서)까지 고려하여 탐색을 진행하기 때문에 시간이 더 오래 걸린다.

## 결과: [2.63s → 0.53s 로 4.96배 (496%) 개선]

### before

![image](https://github.com/HongDam-org/TWTW/assets/84346055/78841f74-4dd0-48f6-89e8-7cb6ddb96812)

### after

![image](https://github.com/HongDam-org/TWTW/assets/84346055/be4722b3-7eab-45ae-8160-4138a6e862d8)

- 추후 데이터가 훨씬 많이 쌓이고 서비스가 확장되면 Elastic Search와 같은 다른 기술의 도입도 가능하지만,
  현재의 주어진 인프라 내에서 서비스 요구사항 수정으로 오류를 해결하고 쿼리를 수정하여 성능도 향상할 수 있었음
