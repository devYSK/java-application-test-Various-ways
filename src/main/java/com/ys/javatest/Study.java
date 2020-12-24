package com.ys.javatest;

import com.ys.javatest.domain.StudyStatus;
import lombok.Getter;

//@SpringBootApplication

@Getter
public class Study {

    private StudyStatus studyStatus = StudyStatus.DRAFT;

    private int limit;
    private String name;

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }

    public Study(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("0보다 커야한다");
        }
        this.limit = limit;
    }

    public StudyStatus getStatus() {
        return this.studyStatus;
    }


    @Override
    public String toString() {
        return "Study{" +
                "studyStatus=" + studyStatus +
                ", limit=" + limit +
                ", name='" + name + '\'' +
                '}';
    }

}
