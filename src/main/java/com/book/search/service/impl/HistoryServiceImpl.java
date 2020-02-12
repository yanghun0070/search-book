package com.book.search.service.impl;

import com.book.search.common.code.ErrorCode;
import com.book.search.common.data.UserData;
import com.book.search.common.data.entity.History;
import com.book.search.component.HistoryComponent;
import com.book.search.endpoint.model.response.HistoryResponse;
import com.book.search.exception.biz.NotFoundException;
import com.book.search.exception.http.ErrorException;
import com.book.search.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.book.search.repository.criteria.HistorySpecification.eqMbrId;

@Slf4j
@Service
public class HistoryServiceImpl implements HistoryService {

    private HistoryComponent historyComponent;
    private Sort sort;

    @Autowired
    public HistoryServiceImpl(HistoryComponent historyComponent) {
        this.historyComponent = historyComponent;
        this.sort = Sort.by("createdTime").descending();
    }

    @Override
    public HistoryResponse select(int size, int page) {
        Page<History> histories;

        try {
            histories = historyComponent.loadHistory(eqMbrId(getMemberId()), getPageable(size, page));
        } catch (NotFoundException e) {
            throw new ErrorException(HttpStatus.NO_CONTENT, ErrorCode.NOT_FOUND_RESOURCE, "history");
        }

        log.debug("[HistoryServiceImpl] historys : {}", histories.toString());

        return new HistoryResponse(histories);
    }

    private Pageable getPageable(int size, int page) {
        return PageRequest.of(page > 0 ? page - 1 : page, size, sort); //jpa page는 0부터 시작한다.
    }

    private long getMemberId() {
        return ((UserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
    }
}
