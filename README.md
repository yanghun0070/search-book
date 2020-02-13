# search-book
이 프로젝트는 Spring boot를 사용하여, 간단한 회원 가입 및 책 검색을 위한 샘플용 프로젝트이다.
회원제로 운영되도록 구현하였다. 
 
## 서비스
서비스는 회원가입/로그인, 책 검색, 히스토리, 인기 키워드로 구성된다. 

### 구축 환경
　 - Java8  
　 - Spring Boot 2.2.4.RELEASE  
　 - Spring Security(로그인)  
　 - nimbus-jose-jwt(로그인 인증 토컨 발급 및 검증)  
　 - H2 DB  


* * *
### API
Method	| Path	| Description	| User authenticated	
------------- | ------------------------- | ------------- |:-------------:|
GET	| /search/books/{keyType}	| 책 검색을 위한 API이다. 	| o
GET	| /search/histories	| 나의 검색 히스토리(키워드, 검색 일시)를 제공한다.	| o
GET	| /search/tops	| 사람들이 가장많이 검색한 10개의 검색 키워드를 제공합니다. | o
POST | /account/login	| 가입한 아이디/패스워드를 통해 로그인 서비스를 제공한다. 	| × 
PUT	| /account/sign-up | 회원 가입 서비스를 제공한다. | × 

#### 공통 에러
>성공 응답(httpstatus code 200)을 제외한 에러 응답의 경우 code, message 필드로 구성된다. 
>아래 메시지 구조와 같다.
>에러메시지 정의는 message/messages.properties에 존재한다.
``` 
{
  "code" : "4004",
  "message" : "The user(qmdfjskldajfdfsdfsd) is not found."
}
```   

>요청에 대한 성공시에는 HttpStatus code 200이다. 이때 code, message 필드는 생략된다.

#### 인증 세션
>로그인 성공시 로그인 인증 token이 발급된다.     
>token은 48시간 유효하며, 48시간이 자나면 expired된다.   
>token 포멧은 JWT token이며, 서버의 RSA-256 key로 signed되어 JWS형태로 제공된다.    
>Key는 RSA-256 2048 bit의 비대칭 키이다. key 파일은 rsa_key/book-rsa-key.pem에 존재한다.    


* * *
### Quick Start 
>아래 절차대로 소스를 다운 및 컴파일 하고, Application을 실행한다.   
> Check out sources    
 `git clone https://github.com/djzend2k/search-book.git`  
 
> Compile and test    
 `cd search-book/`   
 `./gradlew build`

> Run Application 
 `./gradlew bootRun`

>Application이 실행되면 아래 회원가입/로그인/책 검색/내 검색 히스토리/인기검색 키워드 API 테스트가 가능하다.

#### 1. 회원가입
>회원정보는 이름, 아이디, 비밀번호로 구성된다.  
>테스트는 아래 curl을 통해 가능하다.
###### Path
 - /account/sign-up
###### body element
- name, login_id, password로 구성된다.   
``` 

curl -X PUT -d '{ "name" : "이동진", "login_id" : "joinnewuser", "password" : "1234test!" }' -H "Accept: application/json" -H "Content-Type: application/json" http://localhost:8080/account/sign-up -v

``` 

>성공인 경우 http status code 200으로 응답되며, bod 메시지는 따로 존재하지 않는다.  
>에러의 경우 위에서 정의한 공통 에러메시지 구조형태로 제공된다. 


#### 2. 로그인
>회원 가입시 설정한 아이디/패스워드를 통해 로그인을 수행한다.  
로그인 결과 인증 token과 username, authorities정보등을 응답 받는다.  
>인증 token는 JWT형태의 JWS token 이다.  
>발급된 jwt token의 expire time은 48시간이다.   
48시간 이후에는 token이 만료되어 사용할 수 없다
.  
###### Path
 - /account/login
###### body element
- username, password로 구성된다. username은 회원가입시 입력한 login_id 값이다. 

>테스트는 아래 curl을 통해 가능하다.
``` 

curl -X POST -v -H "Accept: application/json" -H "Content-Type: application/json" -d '{"username" : "testuser","password" : "test1234!"}' http://localhost:8080/account/login

```

>응답메시지 구조는 아래와 같다

``` 
  {
    "username" : "joinuser",
    "authorities" : [ {
      "authority" : "ROLE_MEMBER"
    } ],
    "token" : "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.ewogICJqdGkiIDogImU3N0c3cDhZZXkzWGhOdXlFQ3NJdDlTaktueVBwcVZGZEN4UElpM3VCNDA9IiwKICAiZXhwIiA6IDE1ODE2ODg5MjQsCiAgIm1lbWJlcklkIiA6IDEsCiAgInVzZXJuYW1lIiA6ICJ0ZXN0dXNlciIsCiAgImdyYW50ZWRBdXRob3JpdGllcyIgOiBbICJST0xFX01FTUJFUiIgXSwKICAiYWNjb3VudE5vbkxvY2tlZCIgOiB0cnVlLAogICJjcmVkZW50aWFsc05vbkV4cGlyZWQiIDogdHJ1ZSwKICAiZW5hYmxlZCIgOiB0cnVlLAogICJhY2NvdW50Tm9uRXhwaXJlZCIgOiB0cnVlLAogICJleHBpcmVkIiA6IGZhbHNlCn0.OoG2RW9yNVaHsHLxLfoaISv-JjUESqyAhLt60dq4pjPnAyY2Qws9GbaOZOlooOJBjvRzGHlsCLVVbQZ_Ni0xbdF-7FoyH2Fkp_WKPy7GOD08JgC9FDDasGT9QI3Ryo65btAV-C14eJly9ct_W2GoOw7r0qnWhBxdrlotFqmNqGVPOi4ZyVRtE07FUeybJ2A6EUcnmQ1oS-Y3XMzapwunTgv6IwX21YgXE-zl7Iw1fvYU4hTmxiIUrAuVXFAEpZb3kQ8mV_NrCYBYRgSBBaB_I0MlJ6XOFZ4rvW7O2szyWm2J5xm1UPhsOCMo1DrJ51pGjV-I8OJuDnHpKCOiM--q6A"
  } 
``` 

#### 3. 책 검색
>책 검색은 kakao, naver 이외에 다른 store도 추가가 가능하도록 chain 형태로 구성하였다. 

>__로그인시 발급 받은 jwt token을 Authorization Bearer {token값} 형태로 전송 해야한다.__

>책검색 API는 아래와 같다.
###### Path
 - /search/books/{keyType}
###### keyType
- title, isbn, publisher, person, keyword로 구성된다.
###### query parameter 
- query,  size, page, sort 구성된다. sort는 accuracy, latest로 구성되며, size와 page는 1 부터 시작된다.  

>책 검색 예제는 아래와 같이 수행한다. 
``` 

curl -X GET -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.ewogICJqdGkiIDogImU3N0c3cDhZZXkzWGhOdXlFQ3NJdDlTaktueVBwcVZGZEN4UElpM3VCNDA9IiwKICAiZXhwIiA6IDE1ODE2ODg5MjQsCiAgIm1lbWJlcklkIiA6IDEsCiAgInVzZXJuYW1lIiA6ICJ0ZXN0dXNlciIsCiAgImdyYW50ZWRBdXRob3JpdGllcyIgOiBbICJST0xFX01FTUJFUiIgXSwKICAiYWNjb3VudE5vbkxvY2tlZCIgOiB0cnVlLAogICJjcmVkZW50aWFsc05vbkV4cGlyZWQiIDogdHJ1ZSwKICAiZW5hYmxlZCIgOiB0cnVlLAogICJhY2NvdW50Tm9uRXhwaXJlZCIgOiB0cnVlLAogICJleHBpcmVkIiA6IGZhbHNlCn0.OoG2RW9yNVaHsHLxLfoaISv-JjUESqyAhLt60dq4pjPnAyY2Qws9GbaOZOlooOJBjvRzGHlsCLVVbQZ_Ni0xbdF-7FoyH2Fkp_WKPy7GOD08JgC9FDDasGT9QI3Ryo65btAV-C14eJly9ct_W2GoOw7r0qnWhBxdrlotFqmNqGVPOi4ZyVRtE07FUeybJ2A6EUcnmQ1oS-Y3XMzapwunTgv6IwX21YgXE-zl7Iw1fvYU4hTmxiIUrAuVXFAEpZb3kQ8mV_NrCYBYRgSBBaB_I0MlJ6XOFZ4rvW7O2szyWm2J5xm1UPhsOCMo1DrJ51pGjV-I8OJuDnHpKCOiM--q6A" -H "Accept: application/json" -H "Content-Type: application/json" "http://localhost:8080/search/books/tilte?query=test&page=1&size=2" -v


```

>naver 책 검색의 경우 kakao 책 검색이 장애가 발생하였을때 수행한다.
application.yml 파일의 book.kakao.uri를 변경하고 요청시 테스트가 가능하다.

>응답 메시지는 구조는 아래와 같다.
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


#### 4. 내 검색 히스토리
>내 검색 키워드 히스토리 검색 키워드 역순으로 제공된다.  
>페이징 처리를 위해 size와 page 파라미터를 Optional로 제공된다(default size = 10, page = 1).  
>인증세션이 존재하여야하며 책 검색과 동일하게 로그인시 발급 받은 jwt token을 Authorization Bearer 형태로 전송 해야한다.  

###### Path
 /search/histories
###### query parmeter
- size, page로 구성된다. 

``` 
curl -X GET -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.ewogICJqdGkiIDogIk9MdTY4REVocmF6RlNqZlJtU20zU3J4S0pJMXV3UXN0K3psOGpVUkZySDg9IiwKICAiZXhwIiA6IDE1ODE2ODUxMDksCiAgIm1lbWJlcklkIiA6IDEsCiAgInVzZXJuYW1lIiA6ICJ0ZXN0dXNlciIsCiAgImdyYW50ZWRBdXRob3JpdGllcyIgOiBbICJST0xFX01FTUJFUiIgXSwKICAiYWNjb3VudE5vbkxvY2tlZCIgOiB0cnVlLAogICJjcmVkZW50aWFsc05vbkV4cGlyZWQiIDogdHJ1ZSwKICAiZW5hYmxlZCIgOiB0cnVlLAogICJhY2NvdW50Tm9uRXhwaXJlZCIgOiB0cnVlLAogICJleHBpcmVkIiA6IGZhbHNlCn0.BjDspJAJc3jsPhfYSw2Xk4EcNUUxbuswjoNpcE11g_vt2oegdg8ZfS0XECJ4GZ9uvF-RUH7D27eAJMe7oxvHQn10b6IsUxS4SRKNGa2DmAcFaR7zSEk9dmHWqkzYKxOOhkZB92CTiwm45AIQJzWEHB5lnI0AHxTDtWGbK1b557IrhmqDoDD8Tx0lnHwAvU3o2xi4KhOmPH3mGDyTaRjqkpUc6-uNphPMgh7M8ojCT_MgMjj0ksRWdhGmTHTq1g_98ObWsofrhTvfkZKCkxFJH4xEMj7I-KebI3Clyyd9zW073k0-wCy03etcnk6pn41GyrgXJvD057yRHcFCHDIurA"  "http://localhost:8080/search/histories?size=3&page=1"

```
>응답 메시지는 keyword와 date로 구성된다.
아래 구조와 동일하다.
``` 
{
  "histories" : [ {
    "keyworld" : "love",
    "date" : "2020-02-13 18:07:22"
  }, {
    "keyworld" : "love",
    "date" : "2020-02-13 18:07:18"
  }, {
    "keyworld" : "딸",
    "date" : "2020-02-10 20:15:01"
  } ],
  "total_count" : 5,
  "page_count" : 2
}
``` 
####  5. 인기 키워드 목록
>인기 키워드 목록은 키워드 검색 횟수 역순으로 제공된다. 
>인증세션이 존재하여야하며 책 검색과 동일하게 로그인시 발급 받은 jwt token을 Authorization Bearer 형태로 전송 해야한다.  

###### Path
 /search/tops
``` 
curl -X GET -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.ewogICJqdGkiIDogIk9MdTY4REVocmF6RlNqZlJtU20zU3J4S0pJMXV3UXN0K3psOGpVUkZySDg9IiwKICAiZXhwIiA6IDE1ODE2ODUxMDksCiAgIm1lbWJlcklkIiA6IDEsCiAgInVzZXJuYW1lIiA6ICJ0ZXN0dXNlciIsCiAgImdyYW50ZWRBdXRob3JpdGllcyIgOiBbICJST0xFX01FTUJFUiIgXSwKICAiYWNjb3VudE5vbkxvY2tlZCIgOiB0cnVlLAogICJjcmVkZW50aWFsc05vbkV4cGlyZWQiIDogdHJ1ZSwKICAiZW5hYmxlZCIgOiB0cnVlLAogICJhY2NvdW50Tm9uRXhwaXJlZCIgOiB0cnVlLAogICJleHBpcmVkIiA6IGZhbHNlCn0.BjDspJAJc3jsPhfYSw2Xk4EcNUUxbuswjoNpcE11g_vt2oegdg8ZfS0XECJ4GZ9uvF-RUH7D27eAJMe7oxvHQn10b6IsUxS4SRKNGa2DmAcFaR7zSEk9dmHWqkzYKxOOhkZB92CTiwm45AIQJzWEHB5lnI0AHxTDtWGbK1b557IrhmqDoDD8Tx0lnHwAvU3o2xi4KhOmPH3mGDyTaRjqkpUc6-uNphPMgh7M8ojCT_MgMjj0ksRWdhGmTHTq1g_98ObWsofrhTvfkZKCkxFJH4xEMj7I-KebI3Clyyd9zW073k0-wCy03etcnk6pn41GyrgXJvD057yRHcFCHDIurA"  http://localhost:8080/search/tops

```

>응답 메시지 구조는 아래와 예제와 같다.

```
{
  "keywords" : [ {
    "count" : "2",
    "keyword" : "아빠"
  }, {
    "count" : "2",
    "keyword" : "love"
  }, {
    "count" : "1",
    "keyword" : "할미"
  }, {
    "count" : "1",
    "keyword" : "할비"
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
> build in spring boot framework version 2.2.4.RELEASE
