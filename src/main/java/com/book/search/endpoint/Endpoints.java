package com.book.search.endpoint;

/**
 *
 * endpoint URI Path를 정의하고 있는 클래스
 *
 * 1. 회원가입/로그인
 * 　　  - 사용자는 회원가입을 통해 아이디와 비번을 등록합니다.
 * 　　  - 회원가입 후 사용자의 아이디와 비번으로 로그인을 할 수 있어야 합니다.
 * 　　  - 비밀번호는 암호화해서 저장해 주세요.
 * 　 2. 책 검색
 * 　　  - 키워드를 통해 책을 검색할 수 있어야 합니다.
 * 　　  - 검색 결과는 Pagination 형태로 제공해 주세요.
 * 　　  - 검색 소스는 카카오 API의 책 검색(https://developers.kakao.com/docs/restapi/search#%EC%B1%85-%EA%B2%80%EC%83%89)을 활용합니다.
 * 　　  - 카카오 책 검색 API에 장애가 발생한 경우, 네이버 책 검색 API를 통해 데이터가 제공되어야 합니다.
 * <p>
 * 네이버 책 검색 API: https://developers.naver.com/docs/search/book/
 * 　 3. 내 검색 히스토리
 * 　　  - 나의 검색 히스토리(키워드, 검색 일시)를 최신 순으로 보여 주세요.
 * 　 4. 인기 키워드 목록
 * 　　  - 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 제공합니다.
 * 　　  - 키워드 별로 검색된 횟수도 함께 표기해 주세요.
 */
public class Endpoints {
    public static final String SEARCH = "/search/**";
    public static final String ACCOUNT = "/account/**";

    public final static String SEARCH_BOOK_ENDPOINT = "/search/books/{" + PathVariables.KEY_TYPE + "}";
    public final static String SEARCH_HISTORY_ENDPOINT = "/search/histories";
    public final static String SEARCH_TOPS_ENDPOINT = "/search/tops";

    public static final String ACCOUNT_SIGNUP = "/account/sign-up";
    public static final String ACCOUNT_LOGIN = "/account/login";

}