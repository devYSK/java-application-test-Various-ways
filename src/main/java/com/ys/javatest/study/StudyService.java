package com.ys.javatest.study;

import com.ys.javatest.domain.Member;
import com.ys.javatest.domain.Study;
import com.ys.javatest.member.MemberNotFoundException;
import com.ys.javatest.member.MemberService;

import java.util.Optional;

public class StudyService {

    private final MemberService memberService;
    private final StudyRepository repository;

    public StudyService(MemberService memberService, StudyRepository repository) {
        assert memberService != null;
        assert repository != null;

        this.memberService = memberService;
        this.repository = repository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);

        study.setOwner(member.orElseThrow(
                () -> new IllegalArgumentException("Member doesn`t exist for id: " + memberId)));

        Study newStudy = repository.save(study);
        memberService.notify(newStudy);
        memberService.notify(member.get());
        return newStudy;
    }
}
