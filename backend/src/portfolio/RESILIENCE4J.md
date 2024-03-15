# Resilience4j & 모니터링

태그: PORTFOLIO
날짜: 2024년 3월 15일
참여자: 김승진, 진주원

## 문제상황

---

> 사용자 경로 제공에 있어 Kakao, Naver, Tmap Open API를 사용하였고 Open API에 장애가 발생한 경우 처리에 있어 유연한 처리가 필요함을 느낌
>

### 접근

---

- Resilience4j를 통해 Open API에 장애가 발생한 경우 close - open - half open 단계에 걸쳐 유연한 처리

### 설명

---

- Close
    - Open API에 장애가 발생하지 않은 경우 → 기존과 동일하게 로직 수행
- Open
    - Open API에 장애가 발생 ( Open API의 실패율이 30%에 도달한 경우)
    - 이후 10초간 Open API 요청을 수행하지 않고 Default Failure 처리
- Half Open
    - Open API 처리가 정상화된 경우 → Close 상태 변경
    - 정상화되지 않은 경우 → Open 상태 변경

## 분석

---

## Resilience4j 모니터링

- 서킷브레이커 1개가 OpenAPI 장애로 Open 돼있고 3개가 Close 돼있는 것을 볼 수 있다.

![image](https://github.com/HongDam-org/TWTW/assets/84346055/b6ab3b74-f99a-4b14-8b51-74311484b91d)

## SpringBoot 서버 모니터링 (JVM 관련 지표)

![image](https://github.com/HongDam-org/TWTW/assets/84346055/eddf8eca-c91f-428d-aed1-e4af9a7d30cf)

## SpringBoot 서버 모니터링 (요청, 로그 관련 지표)

![image](https://github.com/HongDam-org/TWTW/assets/84346055/8f225ef4-bf0a-4986-a1c9-fc79e32768b8)
