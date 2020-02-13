package com.book.search.component;

import com.book.search.AbstractSearchTest;
import com.book.search.common.data.KeywordData;
import com.book.search.common.data.entity.History;
import com.book.search.exception.biz.NotFoundException;
import com.book.search.repository.criteria.HistorySpecification;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HistoryComponentTest extends AbstractSearchTest {

    @Autowired
    HistoryComponent historyComponent;

    @Test
    public void test_saveHistory() {
        String keyword = "아빠";
        History history = historyComponent.saveHistory(keyword, 1L);

        assertEquals(history.getKeyword(), keyword);
    }

    @Test
    public void test_loadHistory() throws NotFoundException {
        Pageable pageable = PageRequest.of(1, 2);
        Specification<History> specification = HistorySpecification.eqMbrId(1);
        Page<History> histories = historyComponent.loadHistory(specification, pageable);

        histories.get().forEach(System.out::println);

        assertNotNull(histories);
    }


    @Test(expected = NotFoundException.class)
    public void test_loadHistory_not_foud() throws NotFoundException {
        Pageable pageable = PageRequest.of(1, 2);
        Specification<History> specification = HistorySpecification.eqMbrId(800000);
        historyComponent.loadHistory(specification, pageable);
    }

    @Test
    public void loadKeywordData() throws NotFoundException {
        List<KeywordData> list = historyComponent.loadKeywordData();

        for (int i = 0; i < list.size(); i++)
            System.out.println("keyword:" + list.get(i).getKeyword() + " cnt : " + list.get(i).getCount());

        assertNotNull(list);
    }
}