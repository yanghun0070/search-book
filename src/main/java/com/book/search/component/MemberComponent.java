package com.book.search.component;

import com.book.search.common.code.ErrorCode;
import com.book.search.common.data.entity.Member;
import com.book.search.exception.biz.DuplicateMemberException;
import com.book.search.exception.http.ErrorException;
import com.book.search.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원정보를 control하는 클래스이다.
 */
@Slf4j
@Component
public class MemberComponent {

    @Autowired
    MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member loadMember(Long mbrId) throws UsernameNotFoundException {
        return memberRepository.findById(mbrId)
                .orElseThrow(() -> new UsernameNotFoundException(mbrId + ""));
    }


    @Transactional(readOnly = true)
    public Member loadMember(String username) throws UsernameNotFoundException {
        return memberRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Transactional
    public Member saveMember(Member member) throws DuplicateMemberException {
        try {
            return memberRepository.save(member);
        } catch (DataIntegrityViolationException e) {
            System.out.println("history already exist");
            throw new ErrorException(HttpStatus.BAD_REQUEST, ErrorCode.ALREADY_EXIST);
        }
    }

}
