package com.book.search.repository;

import com.book.search.common.data.KeywordData;
import com.book.search.common.data.entity.History;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface HistoryRepository extends PagingAndSortingRepository<History, Long>, JpaSpecificationExecutor<History> {

    @Query(value = "select h.keyword as keyword, count(h.keyword) as count from SEARCH_HISTORY h group by h.keyword order by count desc limit 10",
            nativeQuery = true)
    List<KeywordData> retrieveKeyword();
}
