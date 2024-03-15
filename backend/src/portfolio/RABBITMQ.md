# RabbitMQ 비동기 & 데드레터 처리

태그: PORTFOLIO
날짜: 2024년 3월 15일
참여자: 진주원, 김승진

## 문제 상황 & 접근

---

1. FCM 오류 처리

> 그룹 초대 , 친구 요청 서비스를 수행하는데 있어 FCM을 사용했다. FCM 자체의 성능 개선을 위해 RabbitMQ을 사용함. FCM 요청에 있어 오류가 발생할 경우 처리를 위해 데드레터 처리
>
1. 비동기 적용

> FCM 자체는 동기방식이기 때문에 짧은 시간에 많은 요청이 수행되는 경우 성능상  개선 필요, 메시지 큐를 사용하여 비동기 방식으로 처리
>

### 적용

---

![image](https://github.com/HongDam-org/TWTW/assets/84346055/0652aba3-f931-4fab-be8b-72eba57c76de)

- 알림을 위한 RabbitMQ queue에서 발생한 오류는 데드레터로 메시지가 전달되도록 연결하였다.

![image](https://github.com/HongDam-org/TWTW/assets/84346055/5940ff1d-a834-4a6a-aef4-9d7e28f4b027)

- 데드레터가 발생한 경우 상단의 RabbitListener로 전송된다.
- RabbitMQ는 데드레터를 데드레터 큐에 추가한다.
- 이후 로깅을 수행한 이후 개발자의 Slack으로 알림을 전송한다.

### 결론

---

## 데드레터 발생시 슬랙 알림

![image](https://github.com/HongDam-org/TWTW/assets/84346055/8fe48b9c-c74f-4bdf-8203-eef0e9315fc0)

## RabbitMQ Management에서 확인한 데드레터 현황

![image](https://github.com/HongDam-org/TWTW/assets/84346055/e748c37c-2b2a-475a-9727-4c98005471dc)
