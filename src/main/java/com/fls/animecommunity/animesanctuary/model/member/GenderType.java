package com.fls.animecommunity.animesanctuary.model.member;

public enum GenderType {
	MALE("남성"),
	FEMALE("여성");
	
	private String description; //필드 선언
	
	//생성자. enum의 특징: 생성자의 접근지정자가 private
	private GenderType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
