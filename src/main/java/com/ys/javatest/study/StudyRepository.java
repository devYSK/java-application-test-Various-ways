package com.ys.javatest.study;

import com.ys.javatest.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {

    Study save(Study study);
}
