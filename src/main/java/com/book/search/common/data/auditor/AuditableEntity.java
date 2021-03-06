package com.book.search.common.data.auditor;


import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 테이블의 공통 필드로써, 생성일 및 생성자로 구성된다.
 * 생성자는 로그인 사용자의 username이 입력된다.
 */
@Data
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public abstract class AuditableEntity implements Serializable {

    private static final long serialVersionUID = -6602252468981538985L;

    @Column(name = "CREUSR", nullable = false, updatable = false)
    @CreatedBy
    private String createUser;

    @Column(name = "CREDT", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

}
