package com.book.search.endpoint.model.response;

import com.book.search.common.data.KeywordData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 키워드 별로 검색된 횟수도 함께 표기해 주세요.
 */
@AllArgsConstructor
@Builder
@Data
public class TopListResponse {
    List<KeywordData> keywords;
}
