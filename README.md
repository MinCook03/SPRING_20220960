# 💡 자바웹프로그래밍2*스프링부트*강민국

---

## 📚 목차

1.  **1주차- 오리엔테이션**
2.  **2주차 - 개발환경세팅 "자바설치 , VsCode 세팅 및 ReadMe파일 생성 및 깃허브 연동 그래프 풀,푸시 기능 확인,연습문제풀이 hello2만들기**
3.  **3주차 - 포트폴리오\프론트설계 "포트폴리오 사이트 프론트 만들기, 연습문제(상세보기 화면 및 뒤로가기 구현close함수**
4.  **데이터베이스 mysql 연동 및 데이터불러오기 testdb사이트 구현 연습문제완료**
5.  **게시판만들기 및 데이터베이스 연동, restcontroller에서 controller로 코드 변경성공 (연승문제ok~)**

---

### 1. 프로젝트 개요

- **프로젝트 목적:** [스프링 부트(Spring Boot)를 활용한 자바 웹 애플리케이션 개발의 전 과정을 학습합니다. 개발 환경 구축부터 프론트엔드 설계, 데이터베이스 연동(MySQL), 그리고 실제 게시판 기능 구현까지의 실습을 통해 백엔드 개발 역량을 강화하는 것을 목표로 합니다.]
- **주요 기능:**
  - [기능1] 개발 환경 구축: Java, VSCode, Git/GitHub 연동 (2주차)
  - [기능2] 포트폴리오 프론트엔드: HTML/CSS/JS 기반의 정적 페이지 및 이벤트 처리 (3주차)
  - [기능3] 데이터베이스 연동: Spring Boot와 MySQL을 연동하여 데이터 조회 (4주차)
  - [기능4] 웹 게시판 구현: MVC 패턴을 적용한 기본적인 CRUD 기능 (5, 7주차)

---

### 2. 프로젝트 설명

- **배경:** ['자바웹프로그래밍2' 수업의 주차별 학습 내용을 정리하고, 스프링 부트 프레임워크에 대한 이해도를 높이기 위해 프로젝트를 시작하였습니다.]
- **구조:** [Spring Boot의 표준적인 MVC (Model-View-Controller) 패턴을 기반으로 합니다.

Controller: HTTP 요청을 받아 비즈니스 로직을 처리하고 뷰(HTML)를 반환합니다. (5주차: RestController -> Controller 변경)

Service: 비즈니스 로직을 담당합니다.

Repository/DAO: 데이터베이스(MySQL)와의 통신 및 데이터 처리를 담당합니다.

View (Templates): (Thymeleaf 또는 JSP 등) 사용자에게 보여지는 UI를 구성합니다.]

---

### 3. 학습 내용

#### **3.1. [학습 카테고리/주제1]**

- [(2주차) 개발 환경 세팅: JDK 설치, VSCode에 Java 및 Spring Boot Extension Pack 설치.]
- [Git/GitHub 연동: git clone, pull, push, commit 등 기본 명령어 학습 및 ReadMe.md 작성.]
- [포트폴리오 프론트엔드: HTML, CSS를 이용한 정적 웹페이지 레이아웃 설계. JavaScript (close() 함수 등)를 이용한 동적 이벤트 처리 (상세보기/뒤로가기).]

#### **3.2. [학습 카테고리/주제2]**

- [MySQL 연동: application.properties 파일에 DataSource 설정 (URL, username, password) 및 Spring Boot 애플리케이션과의 연동 테스트 (testdb 구현).]
- [Controller vs RestController: @RestController: JSON/XML 등 데이터(API)를 반환. , @Controller: 뷰(HTML 파일명 등)를 반환. , 게시판 구현 시 뷰 템플릿을 띄우기 위해 @Controller로 변경하는 과정을 학습했습니다.]
- [게시판 로직 구현: DB와 연동하여 게시글 목록 조회, 상세 보기 등 핵심 기능을 개발하고 관련 연습문제를 완료했습니다]

---

### 4. 기술 스택

| 분류             | 기술        | 설명                |
| :--------------- | :---------- | :------------------ |
| **백엔드**       | Spring Boot | [사용 이유 및 버전] |
|                  | Java        | [사용 이유 및 버전] |
| **프론트엔드**   | HTML/CSS    | [사용 이유 및 버전] |
|                  | JavaScript  | [사용 이유 및 버전] |
| **데이터베이스** | MySQL       | [사용 이유 및 버전] |
|                  |             |                     |




