package com.book.search.common.data.auditor;

import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * 로그인 세션를 통한 auditor정보를 설정 및 삭제하는 클래스이다.
 */
public class AuditHelper {
    private final static ThreadLocal<String> threadLocal = ThreadLocal.withInitial(String::new);

    public static Optional<String> getCurrentAuditor() {
        String auditor = threadLocal.get();
        if (StringUtils.isEmpty(auditor)) {
            auditor = "default";
        }

        return Optional.of(auditor);
    }

    public static void setCurrentAuditor(String auditor) {
        threadLocal.set(auditor);
    }

    public static void clear() {
        threadLocal.remove();
    }
}
