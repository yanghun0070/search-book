package com.book.search.common.data.entity;

import com.book.search.common.data.auditor.AuditableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "SEARCH_HISTORY",
        indexes = {
                @Index(name = "SEARCH_HISTORY_IDX01", columnList = "MBR_ID, KEYWORD")
        })
public class History extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HIST_ID", length = 20)
    private Long id;

    @Column(name = "KEYWORD", length = 32, nullable = false)
    private String keyword;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MBR_ID")
    private Member member;

    public History(String keyword, Member member) {
        this.keyword = keyword;
        this.member = member;
    }

    @Override
    public void setCreatedTime(LocalDateTime createdTime) {
        super.setCreatedTime(createdTime);
    }
}
