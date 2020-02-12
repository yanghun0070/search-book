package com.book.search.repository;

import com.book.search.AbstractSearchTest;
import com.book.search.common.data.entity.Member;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class MemberRepositoryTest extends AbstractSearchTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void test_find_loginid() {
        String loginId = "testuser";
        Optional<Member> memberOptional = memberRepository.findByLoginId("testuser");
        assertTrue(memberOptional.isPresent());
        assertEquals(memberOptional.get().getLoginId(), loginId);
    }


}