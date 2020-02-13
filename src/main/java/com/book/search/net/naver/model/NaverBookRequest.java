package com.book.search.net.naver.model;

import com.book.search.common.code.KeyTypeCode;
import com.book.search.common.code.SortCode;
import com.book.search.net.data.StoreRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 네이버 요청 데이터 셋 정의
 *
 * display	integer	N
 * start	integer	N	1(기본값), 1000(최대)	검색 시작 위치로 최대 1000까지 가능	-
 * sort	string	N	sim(기본값), date	정렬 옵션: sim(유사도순), date(출간일순), count(판매량순)
 * <p>
 * d_titl	string	N	-	책 제목 검색	상세 검색만 해당
 * d_auth	string	N	-	저자명 검색	상세 검색만 해당
 * d_cont	string	N	-	목차 검색	상세 검색만 해당
 * d_isbn	string	N	-	isbn 검색	상세 검색만 해당
 * d_publ	string	N	-	출판사 검색	상세 검색만 해당
 * d_dafr	string	N	(ex.20000203)	출간 시작일	상세 검색만 해당
 * d_dato	string	N	(ex.20000203)	출간 종료일	상세 검색만 해당
 * d_catg	string	N	-	책 검색 카테고리(카테고리 목록 다운로드)	상세 검색만 해당
 */
@AllArgsConstructor
@Builder
@Data
public class NaverBookRequest implements StoreRequest {
    private String query;    //검색을 원하는 질의어	O	String
    private SortCode sortCode; //결과 문서 정렬 방식	X (accuracy)	accuracy (정확도순) or latest (최신순)
    private int start;    // page * size
    private int display;    //한 페이지에 보여질 문서의 개수	X(기본 10)	1-50 사이 Integer
    private KeyTypeCode keyTypeCode;

    @Override
    public int getPage() {
        return display / start;
    }

    @Override
    public int getSize() {
        return this.display;
    }
}
