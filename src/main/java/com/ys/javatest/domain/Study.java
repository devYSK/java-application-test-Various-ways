package com.ys.javatest.domain;

public class Study {

    private Member owner;

    public Member getOwner() {

        return this.owner;
    }

    public Study(int i, String java) {

    }

    public void setOwner(Member member) {

        this.owner = member;

    }
}
