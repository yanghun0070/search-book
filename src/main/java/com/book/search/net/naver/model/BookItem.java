package com.book.search.net.naver.model;

import com.book.search.common.data.BookData;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class BookItem implements BookData {
    private String author;
    private String title;                    //도서 제목	String
    private String description;                //도서 소개	String
    private String link;                        //도서 상세 URL	String
    private String isbn;                    //국제 표준 도서번호(ISBN10 ISBN13) (ISBN10,ISBN13 중 하나 이상 존재하며, ' '(공백)을 구분자로 출력됩니다)	String
    private String pubdate;                //도서 출판날짜. ISO 8601. [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]	String
    private String publisher;                //도서 출판사	String
    private List<String> translators;                //도서 번역자 리스트	Array of String
    private int price;                    //도서 정가	Integer
    private int discount;                //도서 판매가	Integer
    private String image;                //도서 표지 썸네일 URL	String
    private String status;                    //도서 판매 상태 정보(정상, 품절, 절판 등), 상황에 따라 변동 가능성이 있으므로 문자열 처리 지양, 단순 노출요소로 활용을 권장합니다.

    @Override
    public List<String> getAuthors() {
        return Arrays.asList(this.author.split("|"));
    }

    @Override
    public String getContents() {
        return this.description;
    }

    @Override
    public String getUrl() {
        return this.link;
    }

    @Override
    public String getDatetime() {
        return this.pubdate;
    }

    @Override
    public int getSalePrice() {
        return this.discount;
    }

    @Override
    public String getThumbnail() {
        return this.image;
    }
}
