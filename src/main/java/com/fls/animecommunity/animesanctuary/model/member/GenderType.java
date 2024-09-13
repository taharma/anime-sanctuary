package com.fls.animecommunity.animesanctuary.model.member;

public enum GenderType {
    MALE("남성"),
    FEMALE("여성"),
    OTHER("기타");

    private final String description;

    // 생성자
    GenderType(String description) {
        this.description = description;
    }

    // 설명을 반환하는 메서드
    public String getDescription() {
        return description;
    }

    // 열거형의 문자열 표현을 오버라이드
    @Override
    public String toString() {
        return description;
    }
}
