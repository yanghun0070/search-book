package com.book.search.common.data.auditor;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * auditor 정보를 제공하는 최종 클래스이다.
 */
public class BookSearchAuditor implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return AuditHelper.getCurrentAuditor();
    }
}