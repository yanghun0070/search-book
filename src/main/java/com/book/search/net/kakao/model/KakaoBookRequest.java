package com.book.search.net.kakao.model;

import com.book.search.common.code.KeyTypeCode;
import com.book.search.common.code.SortCode;
import com.book.search.net.data.StoreRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * kakao 책검색 요청 데이터 셋 정의
 */
@AllArgsConstructor
@Builder
@Data
public class KakaoBookRequest implements StoreRequest {
    private String query;    //검색을 원하는 질의어	O	String
    private SortCode sortCode; //결과 문서 정렬 방식	X (accuracy)	accuracy (정확도순) or latest (최신순)
    private int page;    // 결과 페이지 번호	X(기본 1)	1-100 사이 Integer
    private int size;    //한 페이지에 보여질 문서의 개수	X(기본 10)	1-50 사이 Integer
    private KeyTypeCode keyTypeCode;

    @Override
    public int getStart() {
        return ((page - 1) * size) + 1;
    }
}
