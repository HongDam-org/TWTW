# Redis Cache 성능 비교

태그: PORTFOLIO
날짜: 2024년 3월 15일
참여자: 진주원, 김승진

## 문제상황

---

> 사용자에게 경로를 제공하기 위해 Kakao, Naver,  Tmap Open API를 사용한다. 위 API 제공에 있어 호출까지의 **시간이 소요**되고 **비용 낭비**가 발생
>
- 이미 반환된 경로를 사용자가 재사용하는 경우가 많기 때문에 경로를 임시 메모리에 저장하는 로직 필요
- ‘경로’라는 데이터 특성 상 한번 참조된 값에 대해 변경이 자주 일어나지 않음

### 접근

- **Redis Cache를 이용**하여 Open API의 반환값을 일부 저장하여 사용자에게 제공

### 적용

- @CacheEvict와 @Cacheable Annotation을 사용하여 API에 레디스 캐시 적용

### 시퀀스 다이어그램

![image](https://github.com/HongDam-org/TWTW/assets/84346055/6cb63e15-767d-4a21-89cc-91724323f0b0)

![image](https://github.com/HongDam-org/TWTW/assets/84346055/3375f917-e88e-480a-a3f1-bbcbd401557d)

### 시나리오

> Redis Cache를 적용하였을 때 성능 분석을 위해 다음과 같은 시나리오를 세우고 테스트 진행
>
1. 회원가입 수행
2. 보행자 경로 API를 요청하여 수행

## 결론

---

## 평균 Latency 4.12배 향상

- 추가로, 매번 OpenAPI를 호출하지 않기 때문에 네트워크 비용을 아끼는 효과도 있음

## 동기 처리 [평균 Latency 91.88ms]

![image](https://github.com/HongDam-org/TWTW/assets/84346055/cf1f5fe9-4aa9-48e2-96e5-0e89a53cbbc7)

## 비동기 처리 [평균 Latency 22.26ms]

![image](https://github.com/HongDam-org/TWTW/assets/84346055/e3d1fb30-0f25-4c79-a4b2-1e1a9a9b45d4)

### 분석

---

1. Redis Cache를 사용할 경우 사용하지 않은 경우에 비해 4.12배의 속도 성능 향상
2. Open API를 중복 요청하지 않아 네트워크 비용 절감
3. CacheEvict를 통해 사용자의 요청 경로가 변경된 경우 기존 Cache값을 삭제하고 변경된 경로를 저장하고 제공하여 서비스상 유연성 향상
