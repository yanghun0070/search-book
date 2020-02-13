package com.book.search.net.kakao.model;

import com.book.search.common.data.BookData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 카카오 책 데이터 셋 정의
 */
@Data
public class BookDocument implements BookData {
    private List<String> authors;
    private String title;                    //도서 제목	String
    private String contents;                //도서 소개	String
    private String url;                        //도서 상세 URL	String
    private String isbn;                    //국제 표준 도서번호(ISBN10 ISBN13) (ISBN10,ISBN13 중 하나 이상 존재하며, ' '(공백)을 구분자로 출력됩니다)	String
    private String datetime;                //도서 출판날짜. ISO 8601. [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]	String
    private String publisher;                //도서 출판사	String
    private List<String> translators;                //도서 번역자 리스트	Array of String
    private int price;                    //도서 정가	Integer
    @JsonProperty(value = "sale_price")
    private int salePrice;                //도서 판매가	Integer
    private String thumbnail;                //도서 표지 썸네일 URL	String
    private String status;                    //도서 판매 상태 정보(정상, 품절, 절판 등), 상황에 따라 변동 가능성이 있으므로 문자열 처리 지양, 단순 노출요소로 활용을 권장합니다.
}
