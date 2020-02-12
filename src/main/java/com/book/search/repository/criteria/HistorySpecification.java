package com.book.search.repository.criteria;

import com.book.search.common.data.entity.History;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

@Slf4j
public class HistorySpecification {

    public static Specification<History> eqMbrId(final long memberId) {
        return (root, query, criteriaBuilder) -> {
            log.debug("[HistorySpecification] eqMbrId {}, root.member.getAlias() : {}", root.getAlias(), root.get("member").getAlias());
            return criteriaBuilder.equal(root.get("member").get("memberId"), memberId);
        };
    }
}
