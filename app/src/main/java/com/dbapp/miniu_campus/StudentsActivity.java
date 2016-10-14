package com.dbapp.miniu_campus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class StudentsActivity extends AppCompatActivity {
	String course_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_students);
		setTitle(R.string.students);

		Intent intent = getIntent();
		MyData myData = (MyData) intent.getSerializableExtra("MY_DATA");

		this.course_id = myData.getCourse_id();
	}
}