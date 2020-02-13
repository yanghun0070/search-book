package com.book.search.endpoint.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * 　 내 검색 히스토리
 * 　　  - 나의 검색 히스토리(키워드, 검색 일시)를 최신 순으로 보여 주세요.
 */

@AllArgsConstructor
@Data
public class HistoryData {
    private String keyworld;
    private String date;
}
