package com.book.search.component;

import com.book.search.common.data.KeywordData;
import com.book.search.common.data.entity.History;
import com.book.search.exception.biz.NotFoundException;
import com.book.search.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class HistoryComponent {

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    MemberComponent memberComponent;

    @Transactional
    public History saveHistory(String keyword, long mbrId) {
        try {
            return historyRepository.
                    save(new History(keyword, memberComponent.loadMember(mbrId)));
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            throw e;
        }

    }

    @Transactional(readOnly = true)
    public Page<History> loadHistory(Specification<History> specification, Pageable pageable) throws NotFoundException {
        Page<History> histories = historyRepository.findAll(specification, pageable);
        if (histories.getTotalPages() == 0)
            throw new NotFoundException();

        return histories;
    }

    @Transactional(readOnly = true)
    public List<KeywordData> loadKeywordData() throws NotFoundException {
        List<KeywordData> keywordDataList = historyRepository.retrieveKeyword();
        if (keywordDataList.size() == 0)
            throw new NotFoundException();

        return keywordDataList;
    }
}
