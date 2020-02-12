package com.book.search.service;

import com.book.search.endpoint.model.response.TopListResponse;

/**
 * 　 4. 인기 키워드 목록
 * 　　  - 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 제공합니다.
 * 　　  - 키워드 별로 검색된 횟수도 함께 표기해 주세요.
 */
public interface TopKeywordService {
    TopListResponse retrieve();
}
