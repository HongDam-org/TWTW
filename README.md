![Group 36334](https://github.com/HongDam-org/TWTW/assets/89020004/4ce8b3d0-711f-4b95-ae4d-c494b0c173a4)

## 길치들을 위한 경로 제공 및 만남 관리 서비스 앱

- 길치들을 위한 목적지 경로 제공으로 안전하게 도착해보세요 !
- 친구들끼리 그룹을 만들고 모임을 효과적으로 관리해보세요 !

## 기능 소개

### 👍 목적지 설정과 함께 약속 생성!

- 목적지를 카테고리 별, 키워드 별 검색으로 손쉬운 설정이 가능합니다.
- 목적지와 함께 약속을 생성해 미리 목적지 정보를 공유할 수 있답니다!

### 🎯 약속장소 주변 핵심 스팟과 그룹원 중간지점 파악!

- 약속장소와 가까운 핵심 스팟 정보를 통해 쉽게 장소를 찾아보세요.
- 그룹원의 중간지점 또한 알 수 있어 쉽게 만날 수 있습니다!

### 🛣️ 실시간 길찾기 정보를 지도로 한눈에!

- 지도를 통해 목적지로 가는 길을 빠르게 알 수 있어요.
- 실시간으로 제공되는 경로를 따라가다 보면 목적지가 보인답니다!

### 🗺️ 그룹원들의 현재 이동 상태를 지도에서!

- 약속에 포함된 친구들의 현재 위치를 한 번에 확인할 수 있어요.
- 길을 잃은 친구를 바로 파악할 수 있답니다!

### 📱 그룹 통화를 통해 빠르게 경로 정보 공유 가능!

- 길을 찾기 어려운 경우 그룹 통화가 가능합니다.
- 다 같이 경로에 대한 정보를 공유해 빠르게 모여보세요!


## 시스템 아키텍처
![image](https://github.com/HongDam-org/TWTW/assets/89020004/78a9647d-dff8-4f85-a368-6bd03f5ffdc6)

## DB ERD
![image](https://github.com/HongDam-org/TWTW/assets/89020004/7fafa2f5-fdaf-4010-a788-d21729ac20d7)

## 사용 기술
|iOS|Backend|Infra/DevOps|Etc|
|:---:|:---:|:---:|:---:|
|<img src="https://img.shields.io/badge/swift-F05138?style=for-the-badge&logo=Swift&logoColor=white"><br><img src="https://img.shields.io/badge/rxswift-F1007E?style=for-the-badge"><br><img src="https://img.shields.io/badge/rxcocoa-F1007E?style=for-the-badge"><br><img src="https://img.shields.io/badge/uikit-2396F3?style=for-the-badge&logo=uikit&logoColor=white"><br><img src="https://img.shields.io/badge/alamofire-F40D12?style=for-the-badge">|<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white"><br><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><br><img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"> <br><img src="https://img.shields.io/badge/hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white"> <br> <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"><br><img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white"><br><img src="https://img.shields.io/badge/stomp-010101?style=for-the-badge">|<img src="https://img.shields.io/badge/amazons3-569A31?style=for-the-badge&logo=amazons3&logoColor=white"><br><img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"><br><img src="https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white"><br><img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white"><br><img src="https://img.shields.io/badge/rabbitmq-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white"><br><img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"><br><img src="https://img.shields.io/badge/githubactions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white"><br><img src="https://img.shields.io/badge/prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white"><br><img src="https://img.shields.io/badge/grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white">|<img src="https://img.shields.io/badge/apple_login-000000?style=for-the-badge&logo=apple&logoColor=white"><br><img src="https://img.shields.io/badge/KAKAO_MAP_SDK_V2-FFCD00?style=for-the-badge&logo=kakao&logoColor=white"><br><img src="https://img.shields.io/badge/RX_KAKAO_OAUTH-FFCD00?style=for-the-badge&logo=kakao&logoColor=white"><br><img src="https://img.shields.io/badge/core_location-68BC71?style=for-the-badge"><br><img src="https://img.shields.io/badge/kakao_api-FFCD00?style=for-the-badge&logo=kakao&logoColor=white"><br><img src="https://img.shields.io/badge/tmap_api-D40E14?style=for-the-badge&logo=tvtime&logoColor=white"><br><img src="https://img.shields.io/badge/naver_api-03C75A?style=for-the-badge&logo=naver&logoColor=white">|

## 기술적 고민
1. [문자열 쿼리 FULL Text Index 적용 (2.63s -> 0.53s 4.96배 개선)](https://github.com/HongDam-org/TWTW/wiki/FULL-Text-Index-%EC%A0%81%EC%9A%A9%EA%B8%B0)
2. [FCM 알림 비동기 처리를 위한 RabbitMQ와 데드레터 처리](https://github.com/HongDam-org/TWTW/wiki/RabbitMQ-%EB%8D%B0%EB%93%9C%EB%A0%88%ED%84%B0-%EC%B2%98%EB%A6%AC-%EC%A0%81%EC%9A%A9%EA%B8%B0)
3. [네트워크 비용절감을 위한 OpenAPI Redis 캐싱 (91.88ms -> 22.26ms 4.12배 개선)](https://github.com/HongDam-org/TWTW/wiki/Redis-Cache-%EC%A0%81%EC%9A%A9%EA%B8%B0)
4. [OpenAPI 장애 대응을 위한 Resilience4j 적용과 서킷브레이커 모니터링](https://github.com/HongDam-org/TWTW/wiki/Resilience4j-%EB%AA%A8%EB%8B%88%ED%84%B0%EB%A7%81)
5. [전략 패턴을 통한 Stub과 단위 테스트](https://github.com/HongDam-org/TWTW/wiki/Stub%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EB%8B%A8%EC%9C%84-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%A0%81%EC%9A%A9%EA%B8%B0)

## 구현적 특징

### Server
1. 그룹원들간의 실시간 위치 공유를 위해 WebSocket과 RabbitMQ pub/sub를 사용했다.
2. Naver와 T-MAP API를 사용하여 목적지에 대한 보행자와 자동차 경로를 제공한다.
3. Kakao MAP API를 사용하여 목적지에 대한 정보를 제공한다.
4. Spring Security를 사용하여 애플과 카카오 사용자에 대한 앱 서비스 인증 로직을 구현하였다.
5. Jwt를 통해 데이터의 위변조를 방지하는 이점을 가져갔다.
6. Naver, Kakao, T-MAP, Redis, RabbitMQ의 각각 다른 config를 환경 변수 Properties Object를 통해 관리하였다.
7. Github Actions를 통해 코드 포맷팅을 자동화하였고 CI/CD 파이프라인을 구축하였다.
8. Spring Actuator와 함께 Prometheus 및 Grafana로 모니터링 시스템을 구축하였다.

## 멤버 소개
|홍성민|정호진|박다미|진주원|김승진|
|:----:|:----:|:----:|:----:|:----:|
|iOS|iOS|iOS|Server, DevOps|Server, DevOps|
|[@KKodiac](https://github.com/KKodiac)|[@HJ39](https://github.com/HJ39)|[@dami0806](https://github.com/dami0806)|[@jinjoo-lab](https://github.com/jinjoo-lab)|[@ohksj77](https://github.com/ohksj77)|

