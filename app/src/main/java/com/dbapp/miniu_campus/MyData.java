package com.dbapp.miniu_campus;

import java.io.Serializable;

public class MyData implements Serializable {
	private static final long serialVersionUID = 1000000L;

	private String member_id;
	private String grade;
	private String course_id;

	public MyData() {
		this.member_id = "";
		this.grade = "";
		this.course_id = "";
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

	public String getCourse_id() {
		return this.course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
}