# 💡 자바웹프로그래밍2\_스프링부트\_강민국

    

-----

## 📚 목차 (Weekly Curriculum)

1.  **1주차 - 오리엔테이션**
      * 강의 소개 및 전체적인 로드맵 확인
2.  **2주차 - 개발환경 세팅**
      * 자바(JDK) 설치 및 환경 변수 설정
      * VS Code 플러그인(Extension Pack for Java) 설치 및 최적화
      * Git/GitHub 연동 (Repository 생성, Clone, Pull, Push Graph 확인)
      * Spring Boot 프로젝트 생성 및 'Hello World' 출력 (hello2 실습)
3.  **3주차 - 포트폴리오 & 프론트엔드 설계**
      * HTML/CSS를 활용한 개인 포트폴리오 사이트 레이아웃 구축
      * JavaScript 이벤트 처리를 통한 동적 기능 구현 (상세보기 팝업, 뒤로가기 `close()` 함수 등)
4.  **4주차 - 데이터베이스 연동 및 테스트**
      * MySQL 설치 및 스키마 생성
      * `application.properties` 설정을 통한 Spring Boot와 MySQL 연동
      * Connection Test를 위한 `testdb` 페이지 구현 및 데이터 호출 실습
5.  **5주차 - 게시판 만들기 (기초) & MVC 패턴**
      * 게시판 CRUD 기초 설계
      * `@RestController`와 `@Controller`의 차이점 이해 및 코드 리팩토링 (JSON 반환 vs View 반환)
      * 데이터베이스 연동을 통한 게시글 리스트 출력
6.  **7주차 - 게시판 만들기 (심화)**
      * 게시글 작성(Create), 상세 조회(Read), 수정(Update), 삭제(Delete) 로직 완성
      * Thymeleaf 템플릿 엔진을 활용한 데이터 바인딩
7.  **8주차 - 중간고사**
      * 전반기 학습 내용 점검 및 평가
8.  **9주차 - 게시판 검색과 페이징**
      * `JPA Repository`를 활용한 검색 쿼리 메소드 작성 (`findByTitleContaining` 등)
      * `Pageable` 인터페이스를 활용한 페이징 처리 및 UI 페이지네이션 구현
9.  **10주차 - 로그인과 로그아웃 (Session 기초)**
      * HTTP Session을 이용한 로그인 상태 관리
      * 로그인 폼 구현 및 컨트롤러 로직 작성
10. **11주차 - 로그인과 로그아웃 (심화)**
      * 세션 유지, 만료 처리 (`invalidate`)
      * 로그인 여부에 따른 메뉴바 변경 (로그인/로그아웃 버튼 스위칭)
11. **12주차 - 포트폴리오 완성 및 배포 시도**
      * 최종 프로젝트 통합 및 포트폴리오 마무리
      * 웹 호스팅 환경 구축 시도 및 트러블 슈팅 (현재 호스팅 이슈 분석 중)

-----

## 🚀 1. 프로젝트 개요 (Project Overview)

  * **프로젝트 목적**

      * 본 프로젝트는 **'자바웹프로그래밍2'** 수업을 통해 **Spring Boot 프레임워크**를 활용한 웹 애플리케이션의 전 개발 과정을 체계적으로 학습하고 기록하는 데 목적이 있습니다.
      * 단순한 이론 학습을 넘어, **개발 환경 구축부터 프론트엔드 디자인, 백엔드 로직 구현, 그리고 데이터베이스 연동**까지 풀스택 개발의 기초를 다지고, 실무와 유사한 형태의 게시판 및 포트폴리오 서비스를 직접 구현하며 백엔드 개발자로서의 역량을 강화하고자 합니다.

  * **핵심 구현 기능**

      * **개발 환경 구축:** JDK, IDE(VS Code), Git 형상 관리 등 협업과 개발을 위한 기초 인프라 세팅 (2주차)
      * **포트폴리오 프론트엔드:** HTML5, CSS3, Vanilla JS를 활용한 반응형 정적 페이지 설계 및 이벤트 핸들링 (3주차)
      * **데이터베이스 연동:** MySQL RDBMS와 Spring Boot의 JPA/Hibernate를 연동하여 영속성 데이터 관리 (4주차)
      * **웹 게시판 (CRUD):** MVC 패턴을 엄격히 준수하여 글 쓰기, 읽기, 수정, 삭제 기능을 완벽하게 구현 (5\~7주차)
      * **고급 기능:** 사용자의 편의를 위한 **게시글 검색** 기능과 대량의 데이터를 효율적으로 보여주는 **페이징(Pagination)** 처리 (9주차)
      * **인증/인가:** `HttpSession`을 활용한 로그인/로그아웃 프로세스 및 사용자 인증 상태 관리 (10\~11주차)

-----

## 🏗️ 2. 프로젝트 구조 및 아키텍처

  * **배경**

      * 최신 자바 웹 개발의 표준인 **Spring Boot**의 동작 원리를 이해하고, 클라이언트의 요청이 서버를 거쳐 데이터베이스에 도달하는 전체적인 **Data Flow**를 파악하기 위해 진행되었습니다.

  * **MVC 패턴 적용**

      * **Controller (`com.example.demo.controller`):** 사용자의 HTTP 요청(URL)을 받아 적절한 비즈니스 로직을 호출하고, 결과 View(HTML)를 반환합니다. (5주차 학습을 통해 API 중심의 `@RestController`에서 화면 중심의 `@Controller`로 전환하는 과정을 경험함)
      * **Service (`com.example.demo.service`):** Controller와 Repository 사이에서 복잡한 비즈니스 로직을 수행하며 트랜잭션을 관리합니다.
      * **Repository (`com.example.demo.repository`):** Spring Data JPA를 사용하여 데이터베이스(MySQL)와 직접 통신하며 CRUD 쿼리를 수행합니다.
      * **Domain/Entity (`com.example.demo.domain`):** 데이터베이스 테이블과 매핑되는 자바 객체입니다.
      * **View (`resources/templates`):** **Thymeleaf** 템플릿 엔진을 사용하여 서버 데이터를 동적으로 렌더링 하여 사용자에게 보여줍니다.

-----

## 📝 3. 상세 학습 내용 (Detailed Learning)

#### **3.1. 백엔드 개발 환경 및 기초 (Environment & Basics)**

  * **(2주차) 개발 인프라 구축:** Java Development Kit (JDK) 설치부터 VS Code의 Spring Boot Extension Pack 설정까지, 효율적인 코딩을 위한 IDE 환경을 최적화했습니다.
  * **형상 관리 (Git/GitHub):** 프로젝트의 버전 관리를 위해 로컬 저장소와 원격 저장소를 연동했습니다. 코드의 변경 사항을 추적하고 `Push/Pull`을 통해 코드를 동기화하며 협업 기초를 다졌습니다.
  * **Spring Boot 기초:** Spring Initializr를 통해 프로젝트 스캐폴딩을 생성하고, 내장 톰캣 서버를 구동하여 웹 애플리케이션의 라이프사이클을 이해했습니다.

#### **3.2. 프론트엔드 및 데이터베이스 (Frontend & DB)**

  * **(3주차) 포트폴리오 사이트:** 부트스트랩이나 외부 라이브러리 의존도를 낮추고 HTML/CSS/JS 만으로 구조적인 웹 페이지를 설계했습니다. 특히 `window.close()`, `history.back()` 등 자바스크립트 내장 함수를 활용해 UX를 고려한 화면 전환을 구현했습니다.
  * **(4주차) MySQL 연동:** `application.properties`에 JDBC Driver, URL, Username, Password를 설정하여 DB 커넥션 풀을 구성했습니다. `testdb` 엔드포인트를 만들어 실제 데이터베이스 연결 상태를 점검하고 데이터를 조회하는 데 성공했습니다.

#### **3.3. 게시판 및 고급 기능 (Board & Advanced Features)**

  * **(5\~7주차) CRUD 마스터:**
      * 초기에는 `@RestController`를 사용하여 JSON 데이터를 반환하는 REST API 형태를 학습했으나, 이후 `@Controller`와 Thymeleaf를 사용하여 사용자가 볼 수 있는 웹 페이지를 렌더링 하는 방식으로 리팩토링했습니다.
      * DB에 데이터를 저장(`save`), 조회(`findById`, `findAll`), 수정(`update`), 삭제(`delete`)하는 서비스 로직을 완성했습니다.
  * **(9주차) 검색 및 페이징:** 데이터가 많아질 경우를 대비해 `PageRequest` 객체를 활용한 페이징 기법을 적용했습니다. 또한, 제목이나 내용에 특정 키워드가 포함된 글을 찾는 검색 로직을 JPA 메서드로 구현했습니다.
  * **(10\~11주차) 세션 로그인:** HTTP의 Stateless 특성을 극복하기 위해 `HttpSession` 객체를 사용했습니다. 로그인 성공 시 세션에 사용자 정보를 저장(`setAttribute`)하고, 로그아웃 시 세션을 무효화(`invalidate`)하여 보안의 기초인 인증 프로세스를 구현했습니다.

-----

## 🛠️ 4. 기술 스택 (Tech Stack)

| 분류 | 기술 (Technology) | 설명 및 사용 이유 (Description) |
| :--- | :--- | :--- |
| **Language** | **Java 17** | 안정성과 범용성이 뛰어난 백엔드 주력 언어로 사용 |
| **Framework** | **Spring Boot 3.x** | 내장 WAS와 자동 설정(Auto Configuration)을 통해 빠른 웹 개발 지원 |
| **Build Tool** | **Gradle** | 의존성 관리 및 빌드 자동화를 위해 사용 |
| **Database** | **MySQL 8.0** | 관계형 데이터베이스(RDBMS)로 데이터 영구 저장 및 관리 |
| **ORM** | **Spring Data JPA** | SQL을 직접 작성하지 않고 객체 지향적으로 DB를 조작하기 위해 사용 |
| **Template Engine**| **Thymeleaf** | 컨트롤러에서 전달받은 데이터를 HTML에 동적으로 렌더링 |
| **Frontend** | **HTML5 / CSS3** | 웹 표준을 준수한 시멘틱 마크업 및 스타일링 |
| **Frontend** | **JavaScript (ES6+)** | 클라이언트 측의 동적 상호작용 및 유효성 검사 처리 |
| **IDE** | **VS Code** | 가볍고 확장성이 뛰어난 코드 에디터 활용 |
| **VCS** | **Git / GitHub** | 소스 코드 버전 관리 및 포트폴리오 아카이빙 |

-----

### 📢 트러블 슈팅 및 향후 계획

  * **이슈:** 12주차에 Spring Boot 내장 톰캣으로 실행되는 애플리케이션을 외부 웹 호스팅 서버에 배포하는 과정에서 환경 설정 불일치 문제가 발생했습니다.
  * **해결 계획:** `.jar` 파일 빌드 후 배포 방식(Deploy)과 클라우드 서비스(AWS 또는 Heroku 등)를 활용한 배포 방법을 추가로 학습하여 서비스를 라이브 할 예정입니다.
