package com.book.search.common.data.auditor;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class BookSearchAuditor implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return AuditHelper.getCurrentAuditor();
    }
}