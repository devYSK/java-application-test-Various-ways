package com.ys.javatest.member;

import com.ys.javatest.domain.Member;
import com.ys.javatest.domain.Study;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate() throws IllegalArgumentException;

    void notify(Study newStudy);

    void notify(Member member);

    void validate(Member member);
}
