package com.dbapp.miniu_campus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PlanActivity extends AppCompatActivity {
	String grade;
	String course_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan);

		Intent intent = getIntent();
		MyData myData = (MyData) intent.getSerializableExtra("MY_DATA");

		this.grade = myData.getGrade();
		this.course_id = myData.getCourse_id();

		if (this.grade.toUpperCase().equals("STUDENT")) {
			setTitle(R.string.plan_student);
		} else if (this.grade.toUpperCase().equals("PROFESSOR")) {
			setTitle(R.string.plan_professor);
		}
	}

	public void onClickSave(View view) {
		switch (view.getId()) {
			case R.id.plan_button_save:
				if (this.grade.toUpperCase().equals("STUDENT")) {
					finish();
				} else if (this.grade.toUpperCase().equals("PROFESSOR")) {
					finish();
				}
				break;
		}
	}
}