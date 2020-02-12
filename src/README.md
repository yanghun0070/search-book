# search-book
이 프로젝트는 Spring boot를 사용하여, 간단한 회원 가입 및 책 검색을 위한 샘플용 프로젝트이다.
회원제로 운영되며 로그인은 Spring security을 활용하여 제공된다. 
서비스는 회원 및 검색 API로 구성된다. 

## 서비스
서비스는 회원가입/로그인, 책 검색, 히스토리, 인기 키워드로 구성된다. 

### API
Method	| Path	| Description	| User authenticated	
------------- | ------------------------- | ------------- |:-------------:|
GET	| /search/books/{keyType}	| 책 검색을 위한 API이다. keyword 검색만 지원된다.	|
GET	| /search/histories/{keyType}	| 나의 검색 히스토리(키워드, 검색 일시)를 제공한다.	|
GET	| /search/tops/{keyType}	| 사람들이 검색한 10개의 검색 키워드를 제공합니다. | 	
POST | /accounts/login	| 가입한 아이디/패스워드를 통해 로그인 서비스를 제공한다. 	| × 
PUT	| /accounts/signup | 회원 가입 서비스를 제공한다. | × 


#### 회원가입
회원정보는 이름, 아이디, 비밀번호로 구성된다.
테스트는 아래 curl을 통해 가능하다.
``` 
curl -X PUT -d '{ "name" : "이동진", "login_id" : "joinnewuser", "password" : "1234test!" }' -H "Accept: application/json" -H "Content-Type: application/json" http://localhost:8080/account/sign-up -v
``` 

#### 로그인
회원 가입시 설정한 아이디/패스우드를 통해 로그인을 한다. 
로그인 결과 private key로 sign된 jwt 형태의 token과 username, authorities정보를 응답 받는다.

테스트는 아래 curl을 통해 가능하다.
``` 
curl -X POST -v -H "Accept: application/json" -H "Content-Type: application/json" -d '{"username" : "testuser","password" : "test1234!"}' http://localhost:8080/account/login
```

응답메시지 구조는 아래와 같다

``` 
  {
    "username" : "joinuser",
    "authorities" : [ {
      "authority" : "ROLE_MEMBER"
    } ],
    "token" : "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.ewogICJtZW1iZXJJZCIgOiAzLAogICJ1c2VybmFtZSIgOiAiam9pbnVzZXIiLAogICJncmFudGVkQXV0aG9yaXRpZXMiIDogWyAiUk9MRV9NRU1CRVIiIF0sCiAgImFjY291bnROb25FeHBpcmVkIiA6IHRydWUsCiAgImFjY291bnROb25Mb2NrZWQiIDogdHJ1ZSwKICAiY3JlZGVudGlhbHNOb25FeHBpcmVkIiA6IHRydWUsCiAgImVuYWJsZWQiIDogdHJ1ZQp9.oFMZRzJu-gJU6LmVLLxYmhxQY2NaqPmx4YQY--Jg91uJHM04UtjkLkQc53iaEuULGbitDPCM0LHXppDLVyT6ZC7FDqMSlRDauICHh7RR3Un7ABHsAoz_dzqtqwSD886pYNhsHPPp3uMfwEZe1GlYHzayWmgno6pqvZIgRUaol3CDmhImCr_IiFXAPuETH4ieMCEuoe65IjHCEVxrf5ACGXK1dW0icy2dpwQ-5R6-XfjQgZmD8JFwBAjwLzPn2YUskxcgS5l7nwo9oz7a-kPwevlRjie_ye0TsCjxdCJikjlE0kOT_j0ybuPA4ujxj2cSTtCEDja65-sIfihPBBTcog"
  } 
``` 

#### 책 검색
책 검색 기능은 키워드를 통한 검색만 지원한다. 키워드는 values parameter를 통해 URL encode하여 전달한다.

로그인시 발급 받은 jwt token을 Authorization Bearer 형태로 전송 해야한다.
token 검증은 signed key verify 를 통해 검증한다.

책검색 API는 아래와 같다.
###### Path
 - /search/books/{keyType}
###### keyType
- title, isbn, publisher, person, keyword로 구성된다.
###### 파라미터는 
- query,  size, page, sort 구성된다. sort는 accuracy, latest로 구성되며, size와 page는 1 부터 시작된다.  

책 검색 예제는 아래와 같이 수행한다. 
``` 
curl -X GET -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.ewogICJtZW1iZXJJZCIgOiAxLAogICJ1c2VybmFtZSIgOiAidGVzdHVzZXIiLAogICJncmFudGVkQXV0aG9yaXRpZXMiIDogWyAiUk9MRV9NRU1CRVIiIF0sCiAgImFjY291bnROb25FeHBpcmVkIiA6IHRydWUsCiAgImFjY291bnROb25Mb2NrZWQiIDogdHJ1ZSwKICAiY3JlZGVudGlhbHNOb25FeHBpcmVkIiA6IHRydWUsCiAgImVuYWJsZWQiIDogdHJ1ZQp9.JOWQB0uiwfYCCybUXLWkssiyT8RU-Fm63vzNKW7NQCwDCYQ9EkOXxoJIojga4LEywFL6Xr6caJAvmlnxpHA8Y1cPTAuHe28rhybdDAsyiJCWuyOtHQwNj5ELYxlyeaM7H4tzl3rHGFnISCIfXwnMoEoSZ-8xVTmCv37aRpbklAt1TqD97rLntZZCoeomyQDt2AJsw8TYE7j2U-yRFdO85KZZhDC0m--579rCewYfthdrd7GmTILPVY-xg8iNjGWh3ugMISk-3NGbyH6ufWLyTfOMtum_NscPQd5XE8CG6T0kGnnYH-ZS4eTIj6LkmhE-36IfVT1u-mC6-W8kzEVT6g" -H "Accept: application/json" -H "Content-Type: application/json" http://localhost:8080/search/books/tilte?query=test -v

```

naver 책 검색의 경우 kakao 책 검색이 장애가 발생하였을때 수행한다.
application.yml 파일의 book.kakao.uri를 변경하고 요청시 테스트가 가능하다.

응답 메시지는 구조는 아래와 같다.
``` 



``` 


#### 내 검색 히스토리
내 검색 키워드 히스토리 검색 키워드 역순으로 제공된다.

페이징 처리를 위해 size와 page 파라미터를 Optional로 제공된다(default size = 0, page = 1).

인증세션이 존재하여야하며 책 검색과 동일하게 로그인시 발급 받은 jwt token을 Authorization Bearer 형태로 전송 해야한다.

``` 
curl -X GET -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.ewogICJtZW1iZXJJZCIgOiAxLAogICJ1c2VybmFtZSIgOiAidGVzdHVzZXIiLAogICJncmFudGVkQXV0aG9yaXRpZXMiIDogWyAiUk9MRV9NRU1CRVIiIF0sCiAgImFjY291bnROb25FeHBpcmVkIiA6IHRydWUsCiAgImFjY291bnROb25Mb2NrZWQiIDogdHJ1ZSwKICAiY3JlZGVudGlhbHNOb25FeHBpcmVkIiA6IHRydWUsCiAgImVuYWJsZWQiIDogdHJ1ZQp9.JOWQB0uiwfYCCybUXLWkssiyT8RU-Fm63vzNKW7NQCwDCYQ9EkOXxoJIojga4LEywFL6Xr6caJAvmlnxpHA8Y1cPTAuHe28rhybdDAsyiJCWuyOtHQwNj5ELYxlyeaM7H4tzl3rHGFnISCIfXwnMoEoSZ-8xVTmCv37aRpbklAt1TqD97rLntZZCoeomyQDt2AJsw8TYE7j2U-yRFdO85KZZhDC0m--579rCewYfthdrd7GmTILPVY-xg8iNjGWh3ugMISk-3NGbyH6ufWLyTfOMtum_NscPQd5XE8CG6T0kGnnYH-ZS4eTIj6LkmhE-36IfVT1u-mC6-W8kzEVT6g"  http://localhost:8080/search/histories?size=11&page=1

```

####  인기 키워드 목록
인기 키워드 목록은 키워드 검색 횟수 역순으로 제공된다.

인증세션이 존재하여야하며 책 검색과 동일하게 로그인시 발급 받은 jwt token을 Authorization Bearer 형태로 전송 해야한다.
``` 
curl -X GET -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.ewogICJtZW1iZXJJZCIgOiAxLAogICJ1c2VybmFtZSIgOiAidGVzdHVzZXIiLAogICJncmFudGVkQXV0aG9yaXRpZXMiIDogWyAiUk9MRV9NRU1CRVIiIF0sCiAgImFjY291bnROb25FeHBpcmVkIiA6IHRydWUsCiAgImFjY291bnROb25Mb2NrZWQiIDogdHJ1ZSwKICAiY3JlZGVudGlhbHNOb25FeHBpcmVkIiA6IHRydWUsCiAgImVuYWJsZWQiIDogdHJ1ZQp9.JOWQB0uiwfYCCybUXLWkssiyT8RU-Fm63vzNKW7NQCwDCYQ9EkOXxoJIojga4LEywFL6Xr6caJAvmlnxpHA8Y1cPTAuHe28rhybdDAsyiJCWuyOtHQwNj5ELYxlyeaM7H4tzl3rHGFnISCIfXwnMoEoSZ-8xVTmCv37aRpbklAt1TqD97rLntZZCoeomyQDt2AJsw8TYE7j2U-yRFdO85KZZhDC0m--579rCewYfthdrd7GmTILPVY-xg8iNjGWh3ugMISk-3NGbyH6ufWLyTfOMtum_NscPQd5XE8CG6T0kGnnYH-ZS4eTIj6LkmhE-36IfVT1u-mC6-W8kzEVT6g"  http://localhost:8080/search/tops

```

성공인 경우 응답 메시지는 존재하지 않고 http status code 200 이다.

## Notes
build in spring boot framework version 2.2.4.RELEASE