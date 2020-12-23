package com.ys.javatest.study;

import com.ys.javatest.domain.Member;
import com.ys.javatest.domain.Study;
import com.ys.javatest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Test
    void createNewStudy(@Mock MemberService memberService,
                        @Mock StudyRepository studyRepository) {

        Member member = new Member();
        member.setId(1L);
        member.setEmail("youngsoo@naver.com");

        Optional<Member> byId = memberService.findById(1L);

        when(memberService.findById(any()))
                .thenReturn(Optional.of(member));


        StudyService studyService = new StudyService(memberService,
                studyRepository);

        Study study = new Study(10, "java");

        Optional<Member> findById = memberService.findById(1L);
        assertEquals("youngsoo@naver.com", findById.get().getEmail());


        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());


        studyService.createNewStudy(1L, study);
        assertNotNull(studyService);
    }

    @Test
    void stubbing_연습문제(@Mock MemberService memberService,
                       @Mock StudyRepository studyRepository) {

        // given
        StudyService studyService = new StudyService(memberService,
                studyRepository);

        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);

        Study study = new Study(10, "테스트");

        when(memberService.findById(1L))
                .thenReturn(Optional.of(member));
        when(studyRepository.save(study))
                .thenReturn(study);


        given(memberService.findById(1L))
                .willReturn(Optional.of(member));
        given((studyRepository.save(study)))
                .willReturn(study);


        // when
        studyService.createNewStudy(1L, study);


        //then
        assertEquals(member, study.getOwner());

        verify(memberService, times(1)).notify(study);
        verifyNoMoreInteractions();

        then(memberService).should(times(1)).notify(study);
        then(memberService).shouldHaveNoMoreInteractions();


    }

    @DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다.")
    @Test
    void openStudy() {
        // Given
        StudyService studyService = new StudyService(memberService,
                studyRepository);
        Study study = new Study(10, "더 자바, 테스트");
​        // TODO studyRepository Mock 객체의 save 메소드를호출 시 study를 리턴하도록 만들기.
        given(studyRepository.save(study)).willReturn(study);

        // When
        studyService.openStudy(study);

        // Then
        ​// TODO study의 status가 OPENED로 변경됐는지 확인
        assertEquals(StudyStatus.OPENED, study.getStatus());
        // TODO study의 openedDataTime이 null이 아닌지 확인
        assertNotNull(study.getOpenedDateTime());
        // TODO memberService의 notify(study)가 호출 됐는지 확인.
        then(memberService).should().notify(study);
    }
}