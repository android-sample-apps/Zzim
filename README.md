# Zzim

  * [소스 코드 링크 GitHub](https://github.com/meuus90/Zzim)

  * [APK 링크 GitHub](zzim-prod-release-0.0.1.apk)


## 목차

- [개발 환경](#개발-환경)
- [프로젝트 구성](#프로젝트-구성)
    - [1. Dependency Injection](#1-dependency-injection)
    - [2. Design Pattern](#2-design-pattern)
    - [3. Paging](#3-paging)
    - [4. Local DB](#4-local-db)
    - [5. CI](#5-ci)
    - [6. 기타](#6-기타)
- [작업 순서 및 체크리스트](#작업-순서-및-체크리스트)
- [License](#license)


## 개발 환경

  * 기본 환경
    * Kotlin : 1.4.32
    * Gradle : 4.0.1
    * JVM target : Java-1.8

  * AndroidX 라이브러리
    * Core 1.3.2
    * Coroutine 1.4.3
    * Lifecycle 2.3.1
    * Room 2.3.0
    * Paging 3.0.0

  * 기타 라이브러리
    * Dagger 2.29.1
    * Retrofit 2.9.0
    * OkHttp 4.9.0
    * Glide 4.11.0


## 프로젝트 구성

### 1. Dependency Injection

#### Dagger 라이브러리 채택

  * Dagger vs Koin
    * Dagger : 빌드타임에서 DI 주입. 빌드타임 늘어나지만 런타임 비교적 안정적임.
    * Koin : 런타임에서 DI 주입. 빌드타임 짧지만 런타임 크래시 확률 높음.

  * Application 클래스에서 AppInjector를 init()을 실행하여 AppComponent를 injection.
    [Zzim(Application)](app/src/main/java/com/meuus90/zzim/Zzim.kt)
    [AppInjector](app/src/main/java/com/meuus90/zzim/di/helper/AppInjector.kt)

  * AppComponent의 서브모듈에는 사용할 Activity들의 모듈이나 AppModule 등을 등록.
    [AppComponent](app/src/main/java/com/meuus90/zzim/di/component/AppComponent.kt)

  * AppModule에서는 의존성 주입이 필요한 요소를 제공.
    [AppModule](app/src/main/java/com/meuus90/zzim/di/module/AppModule.kt)

  * 각 ActivityModule의 서브모듈에는 해당 activity에서 사용할 fragment들의 모듈을 등록.
    [MainActivityModule](app/src/main/java/com/meuus90/zzim/di/module/activity/MainActivityModule.kt)
    [HomeFragmentModule](app/src/main/java/com/meuus90/zzim/di/module/fragment/HomeFragmentModule.kt)

  * Activity나 Fragment 에서는 사용할 ViewModel을 injection 하여 사용.

  * ViewModel는 AppModule에서 제공하는 API interface나 Room interface 등을 injection 하여 사용.


### 2. Design Pattern

  * 아키텍쳐 디자인 패턴은 MVVM 패턴을 적용.

  * View -> ViewModel -> Model(remote or local repository) 순서로 triggering 하며 자신이 호출할 모듈만 알고 자신을 호출한 모듈은 모름.

  * View에서 ViewModel의 Flow를 collect하여 Model로부터 DataSource를 전달받아 내용 변경애 대한 UI처리.(paging 등)


### 3. Paging

  * AndroidX Paging 3 라이브러리를 적용.

  * ProductPagingSource를 이용하여 PagingData Flow 등록하거나, Room으로부터 전달 받은 PagingSource를 이용하여 PagingData Flow 등록.
    [HomeRepository](app/src/main/java/com/meuus90/zzim/model/source/remote/repository/HomeRepository.kt)
    [FavoriteViewModel](app/src/main/java/com/meuus90/zzim/viewmodel/FavoriteViewModel.kt)

  * View에서 ViewModel에 동록된 Flow를 collect하여 각 PagingDataAdapter에 submit.
    [HomeFragment](app/src/main/java/com/meuus90/zzim/view/fragment/HomeFragment.kt)

  * PagingDataAdapter에 DiffCallback을 등록. DiffCallback로직에 따라서 View에서 PagingData submit 시 변경된 Item에 대해 notify.
    [GoodsAdapter](app/src/main/java/com/meuus90/zzim/view/adapter/GoodsAdapter.kt)


### 4. Local DB

#### Room 라이브러리 채택

  * Room vs Realm
    * Room : 적은 용량. sqlite 기반으로 SQL 문법 사용 가능. 각종 AAC 라이브러리와 호환 가능(LiveData, Flow 등). 안드로이드 공식 지원.
    * Realm : 오랜 시간 검증된 라이브러리. NoSQL 방식. 사용 시 좀 더 직관적임. 용량이 큼.

  * AppModule에서 각 DAO를 등록하고 필요한 부분에서 inject하여 사용.


### 5. CI

  * Github Actions Workflow를 이용해 테스트 자동화를 등록. 
    [Github Actions](https://github.com/meuus90/Zzim/actions)

  * 주요 기능
    * develop branch에서 commit push 완료 시 실행
    * JDK 1.8 테스트 환경 셋업
    * Kotlin linter 체크
    * Android linter 체크
    * Unit test 실시


### 6. 기타

  * Firebase에 앱을 등록하였고 졸아요 리스트가 담긴(Json 형식) Dynamic Link를 인텐트로 전송하는 기능을 추가하였음.
    * 링크 클릭 시 앱 실행(앱 없을 시 플레이스토어에 앱이 등록되어 있지 않아 이동 불가)
    * 링크 클릭 하여 앱 실행 시 좋아요 리스트로 이동
    * 링크 클릭 하여 앱 실행 시 Local DB 업데이트 및 좋아요 리스트에 항목 추가

  * Unit test 추가하였음


## 작업 순서 및 체크리스트
- [X] 프로젝트 세팅
- [X] README 초안 작성
- [X] CI 세팅 (GitHub Action / branch develop)
- [X] gradle 세팅
- [X] DI 세팅
- [X] View 세팅 (UI Layout)
- [X] Model 세팅 (Rspository)
- [X] ViewModel 세팅
- [X] Paging 적용
- [X] Firebase 연동 및 공유 기능 적용
- [X] 에러 대응
- [X] Unit Test 테스트코드 작성
- [X] QA
- [X] Release (to branch master)
- [X] README 수정


## License

Completely free (MIT)! See [LICENSE.md](LICENSE.md) for more.
