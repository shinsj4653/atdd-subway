# ATDD-Subway Study

```text
현장실습 프로젝트에 실제로 적용해본 개념

1. RestControllerAdvice 및 ExceptionHandler를 이용한 예외처리 핸들링
2. Spring 공통 정책 수립을 위한 Filter 기반 XSS 공격 방지 및 CORS 정책 적용
3. MockMVC를 이용한 Controller 테스팅
```

## 1주차
> 1차 과제 미션의 목표는 인수 테스트 및 ATDD에 익숙해지기 및 JPA 기본입니다. 또한, 예외처리 방법 및 여러 스프링의 기본적인 동작에 익숙해질 수 있습니다.

1. `RestAssured API` 로 인수테스트 작성하는 방법을 익혔습니다. 해당 API의 응답 형태를 `ResponseEntity`를 사용하여 정의해줬습니다. 이를 통해, SpringBoot 내의 HTTP 요청 및 응답 처리 방식에 대해 공부할 수 있었습니다.
2. `RestControllerAdvice`를 사용하여 @Controller 어노테이션이 적용된 모든 곳에서의 발생되는 예외에 대해 catch를 한 후, 해당 예외들을 `ExceptionHandler`를 통해 처리해줬습니다.
3. 노선과 지하철 역은 Many to Many 관계이므로, 이를 구간이라는 `일급컬렉션 엔티티`를 활용하여 One To Many, Many To One 관계를 유지할 수 있도록 수정해줬습니다.

## 2주차
> 2차 과제의 목표는 JPA의 활용입니다.
기본적으로 구현했던 비즈니스 로직에 다양한 변화를 주어 이 변화에 대해 개선하고, 새롭게 기능을 구현하며 JPA를 좀 더 잘 쓸 수 있도록 해봅니다. 또한, Mockist와 Classicist 모두 되어보며 테스트를 작성해봅니다. (Service 단의 테스트를 작성해봅니다.)

1. Mock 테스팅을 통해 `Mockito` 라이브러리에 대한 학습을 하였고, `Stubbing`을 통해 Service단 테스팅을 성공적으로 수행하였습니다.
2. `디미터 법칙`에 대해 학습하였고, 객체 간 .(dot)을 최대한 쓰지 않도록 하여 객체 간의 결합도를 낮출 수 있고 변경에 유연하고 유지보수하기 쉬운 코드로 리팩토링을 하였습니다.
3. `JGraphT 라이브러리`를 사용하여 그래프의 최단 경로 조회 기능을 구현하였고, 이를 통해 `JPA의 지연로딩 및 Equals 재정의 방식`에 대해 깊이 이해하였습니다.

## 3주차
> 3차 과제 미션에선 로그인 및 소셜 로그인 기능을 구현해보며 이때는 어떻게 테스트 할 수 있을지 고민해봅니다.

1. `JWT 토큰을 이용한 로그인` 로직을 구현해보면서 SpringBoot 에서 Bearer Token 기반 로그인에 필요한 `JwtTokenProvider와 AuthConfig의 ArgumentResolver 의 역할`을 학습하였습니다.
2. `Github OAuth 로그인 프로세스`를 공부하였고, 이를 프로젝트에서 구현하기 위해 Github 사이트 내 로그인 API의 활용 능력을 길렀습니다. 또한, OAuth를 사용하는 로직의 테스트를 외부 API에 의존하지 않게 하기 위해 `가짜 API Controller 및 Client를 설계`하여 활용하였습니다.
3. AuthConfig 클래스를 구성하면서 스프링부트 내에서 로그인이 아닌 또 다른 Config 설정들을 찾아보면서, `Spring 공통 정책` 에 대해 알게되었고, 그 중 `Filter` 기능을 사용하여 `XSS 공격 방지 및 CORS 설정`을 현장실습 프로젝트에 적용해봤습니다. 
