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

성공인 경우 응답 메시지는 존재하지 않고 http status code 200 이다.


#### 로그인
회원 가입시 설정한 아이디/패스우드를 통해 로그인을 한다. 
로그인 결과 private key로 sign된 jwt 형태의 token과 username, authorities정보를 응답 받는다.

발급한 token의 expire time은 48시간이다. 
48시간 이후에는 token이 만료되어 사용할 수 없다.


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
curl -X GET -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.ewogICJqdGkiIDogIk9MdTY4REVocmF6RlNqZlJtU20zU3J4S0pJMXV3UXN0K3psOGpVUkZySDg9IiwKICAiZXhwIiA6IDE1ODE2ODUxMDksCiAgIm1lbWJlcklkIiA6IDEsCiAgInVzZXJuYW1lIiA6ICJ0ZXN0dXNlciIsCiAgImdyYW50ZWRBdXRob3JpdGllcyIgOiBbICJST0xFX01FTUJFUiIgXSwKICAiYWNjb3VudE5vbkxvY2tlZCIgOiB0cnVlLAogICJjcmVkZW50aWFsc05vbkV4cGlyZWQiIDogdHJ1ZSwKICAiZW5hYmxlZCIgOiB0cnVlLAogICJhY2NvdW50Tm9uRXhwaXJlZCIgOiB0cnVlLAogICJleHBpcmVkIiA6IGZhbHNlCn0.BjDspJAJc3jsPhfYSw2Xk4EcNUUxbuswjoNpcE11g_vt2oegdg8ZfS0XECJ4GZ9uvF-RUH7D27eAJMe7oxvHQn10b6IsUxS4SRKNGa2DmAcFaR7zSEk9dmHWqkzYKxOOhkZB92CTiwm45AIQJzWEHB5lnI0AHxTDtWGbK1b557IrhmqDoDD8Tx0lnHwAvU3o2xi4KhOmPH3mGDyTaRjqkpUc6-uNphPMgh7M8ojCT_MgMjj0ksRWdhGmTHTq1g_98ObWsofrhTvfkZKCkxFJH4xEMj7I-KebI3Clyyd9zW073k0-wCy03etcnk6pn41GyrgXJvD057yRHcFCHDIurA" -H "Accept: application/json" -H "Content-Type: application/json" http://localhost:8080/search/books/tilte?query=test&page=1&size=10 -v

```

naver 책 검색의 경우 kakao 책 검색이 장애가 발생하였을때 수행한다.
application.yml 파일의 book.kakao.uri를 변경하고 요청시 테스트가 가능하다.

응답 메시지는 구조는 아래와 같다.
``` 
 {
  "books" : [ {
    "authors" : [ "Kaplan Test Prep" ],
    "title" : "Official Guide to Oet",
    "contents" : "",
    "url" : "https://search.daum.net/search?w=bookpage&bookId=5224866&q=Official+Guide+to+Oet",
    "isbn" : "1506263224 9781506263229",
    "datetime" : "2020-02-04T00:00:00.000+09:00",
    "publisher" : "Kaplan Publishing",
    "translators" : [ ],
    "price" : 57270,
    "thumbnail" : "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F5224866",
    "status" : "",
    "sale_price" : -1
  }, {
    "authors" : [ "Michael A. Putlack", "Stephen Poirier", "Maximilian Tolochko" ],
    "title" : "Decoding the TOEFL iBT Actual Test Listening. 1(New TOEFL Edition)",
    "contents" : "Decoding the TOEFL iBT Actual Test (New TOEFL Edition) 시리즈는 토플 실전 모의고사인 Decoding the TOEFL iBT Actual Test 시리즈의 개정판으로, 2019년 8월부터 시행된 토플의 변화를 충실히 반영하여 만들어졌다. ETS에서 공식적으로 발표한 문제 수, 유형과 같은 외형적 변화뿐 아니라, 내용적인 면에서도 매우 세심하게 토플 4개 영역의 변화를 반영했다. 『Decoding the TOEFL",
    "url" : "https://search.daum.net/search?w=bookpage&bookId=5237567&q=Decoding+the+TOEFL+iBT+Actual+Test+Listening.+1%28New+TOEFL+Edition%29",
    "isbn" : "8927708644 9788927708643",
    "datetime" : "2020-02-03T00:00:00.000+09:00",
    "publisher" : "다락원",
    "translators" : [ ],
    "price" : 16000,
    "thumbnail" : "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F5237567%3Ftimestamp%3D20200212135215",
    "status" : "정상판매",
    "sale_price" : 14400
  } ],
  "total_count" : 7488,
  "page_count" : 1000
}
``` 


#### 내 검색 히스토리
내 검색 키워드 히스토리 검색 키워드 역순으로 제공된다.

페이징 처리를 위해 size와 page 파라미터를 Optional로 제공된다(default size = 0, page = 1).

인증세션이 존재하여야하며 책 검색과 동일하게 로그인시 발급 받은 jwt token을 Authorization Bearer 형태로 전송 해야한다.

``` 
curl -X GET -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.ewogICJqdGkiIDogIk9MdTY4REVocmF6RlNqZlJtU20zU3J4S0pJMXV3UXN0K3psOGpVUkZySDg9IiwKICAiZXhwIiA6IDE1ODE2ODUxMDksCiAgIm1lbWJlcklkIiA6IDEsCiAgInVzZXJuYW1lIiA6ICJ0ZXN0dXNlciIsCiAgImdyYW50ZWRBdXRob3JpdGllcyIgOiBbICJST0xFX01FTUJFUiIgXSwKICAiYWNjb3VudE5vbkxvY2tlZCIgOiB0cnVlLAogICJjcmVkZW50aWFsc05vbkV4cGlyZWQiIDogdHJ1ZSwKICAiZW5hYmxlZCIgOiB0cnVlLAogICJhY2NvdW50Tm9uRXhwaXJlZCIgOiB0cnVlLAogICJleHBpcmVkIiA6IGZhbHNlCn0.BjDspJAJc3jsPhfYSw2Xk4EcNUUxbuswjoNpcE11g_vt2oegdg8ZfS0XECJ4GZ9uvF-RUH7D27eAJMe7oxvHQn10b6IsUxS4SRKNGa2DmAcFaR7zSEk9dmHWqkzYKxOOhkZB92CTiwm45AIQJzWEHB5lnI0AHxTDtWGbK1b557IrhmqDoDD8Tx0lnHwAvU3o2xi4KhOmPH3mGDyTaRjqkpUc6-uNphPMgh7M8ojCT_MgMjj0ksRWdhGmTHTq1g_98ObWsofrhTvfkZKCkxFJH4xEMj7I-KebI3Clyyd9zW073k0-wCy03etcnk6pn41GyrgXJvD057yRHcFCHDIurA"  http://localhost:8080/search/histories?size=11&page=1

```
응답 메시지는 keyword와 date로 구성된다.
아래 구조와 동일하다.
``` 
 {
  "histories" : [ {
    "keyworld" : "test",
    "date" : "2020-02-12 21:59:45"
  }, {
    "keyworld" : "test",
    "date" : "2020-02-12 21:59:31"
  }, {
    "keyworld" : "test",
    "date" : "2020-02-12 21:59:03"
  }, {
    "keyworld" : "딸",
    "date" : "2020-02-10 20:15:01"
  }, {
    "keyworld" : "엄마",
    "date" : "2020-02-09 20:15:01"
  }, {
    "keyworld" : "아빠",
    "date" : "2020-02-08 20:15:01"
  } ],
  "total_count" : 6,
  "page_count" : 1
}
``` 
####  인기 키워드 목록
인기 키워드 목록은 키워드 검색 횟수 역순으로 제공된다.

인증세션이 존재하여야하며 책 검색과 동일하게 로그인시 발급 받은 jwt token을 Authorization Bearer 형태로 전송 해야한다.
``` 
curl -X GET -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.ewogICJqdGkiIDogIk9MdTY4REVocmF6RlNqZlJtU20zU3J4S0pJMXV3UXN0K3psOGpVUkZySDg9IiwKICAiZXhwIiA6IDE1ODE2ODUxMDksCiAgIm1lbWJlcklkIiA6IDEsCiAgInVzZXJuYW1lIiA6ICJ0ZXN0dXNlciIsCiAgImdyYW50ZWRBdXRob3JpdGllcyIgOiBbICJST0xFX01FTUJFUiIgXSwKICAiYWNjb3VudE5vbkxvY2tlZCIgOiB0cnVlLAogICJjcmVkZW50aWFsc05vbkV4cGlyZWQiIDogdHJ1ZSwKICAiZW5hYmxlZCIgOiB0cnVlLAogICJhY2NvdW50Tm9uRXhwaXJlZCIgOiB0cnVlLAogICJleHBpcmVkIiA6IGZhbHNlCn0.BjDspJAJc3jsPhfYSw2Xk4EcNUUxbuswjoNpcE11g_vt2oegdg8ZfS0XECJ4GZ9uvF-RUH7D27eAJMe7oxvHQn10b6IsUxS4SRKNGa2DmAcFaR7zSEk9dmHWqkzYKxOOhkZB92CTiwm45AIQJzWEHB5lnI0AHxTDtWGbK1b557IrhmqDoDD8Tx0lnHwAvU3o2xi4KhOmPH3mGDyTaRjqkpUc6-uNphPMgh7M8ojCT_MgMjj0ksRWdhGmTHTq1g_98ObWsofrhTvfkZKCkxFJH4xEMj7I-KebI3Clyyd9zW073k0-wCy03etcnk6pn41GyrgXJvD057yRHcFCHDIurA"  http://localhost:8080/search/tops

```

응답 메시지 구조는 아래와 예제와 같다.

```
{
  "keywords" : [ {
    "count" : "3",
    "keyword" : "test"
  }, {
    "count" : "2",
    "keyword" : "아빠"
  }, {
    "count" : "1",
    "keyword" : "할비"
  }, {
    "count" : "1",
    "keyword" : "할미"
  }, {
    "count" : "1",
    "keyword" : "딸"
  }, {
    "count" : "1",
    "keyword" : "엄마"
  } ]
}
```


## Notes
build in spring boot framework version 2.2.4.RELEASE
