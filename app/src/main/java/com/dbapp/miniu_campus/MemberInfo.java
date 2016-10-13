package com.dbapp.miniu_campus;

import java.io.Serializable;

public class MemberInfo implements Serializable {
	private static final long serialVersionUID = 1000000L;

	private String member_id;
	private String grade;

	public MemberInfo() {
		member_id = "";
		grade = "";
	}

	public String getMember_id() {
		return this.member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
}
