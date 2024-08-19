# 스프링 숙련 주차 과제
일정관리 심화버전을 만들어보자.
* **공통 조건**
  - 모든 테이블은 고유 식별자(ID)를 가집니다.
  - `3 Layer Architecture` 에 따라 각 Layer의 목적에 맞게 개발합니다.
  - CRUD 필수 기능은 모두 데이터베이스 연결 및  `JPA`를 사용해서 개발합니다.
  - `JDBC`와 `Spring Security`는 사용하지 않습니다.
  - 인증/인가 절차는 `JWT`를 활용하여 개발합니다.
  - JPA의 연관관계는 **`양방향`**으로 구현합니다.
# API 명세서
| 기능        | Method | URL                         | request  | response | 상태코드     |
|-----------|--------|-----------------------------|----------|----------|----------|
| 일정 정보 등록  | POST   | /api/schedules              | 요청 body  | 등록 정보    | 201:정상등록 |
| 일정 조회     | GET    | /api/schedules/{scheduleId} | 요청 param | 단건 응답 정보 | 200:정상조회 |
| 일정 수정     | PUT    | /api/schedules/{scheduleId}  | 요청 param | 수정 정보    | 200:정상수정 |
# ERD
![](./images/erd.png)