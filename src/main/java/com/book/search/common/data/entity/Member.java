package com.book.search.common.data.entity;

import com.book.search.common.data.auditor.AuditableEntity;
import com.book.search.common.code.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = "history")
@Entity
@Table(name = "BOOK_MEMBER", uniqueConstraints = @UniqueConstraint(columnNames = {"LOGIN_ID"}))
public class Member extends AuditableEntity {

    private static final long serialVersionUID = 1301196645091281987L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MBR_ID", length = 20)
    private Long memberId;

    @Column(name = "LOGIN_ID", length = 32, nullable = false)
    private String loginId;

    @Column(name = "PWD", length = 128, nullable = false)
    private String password;

    @Column(name = "NAME", length = 32, nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", length = 6)
    private Role role;

    @OneToMany(mappedBy = "member")
    private Set<History> history = new HashSet<>();
}
