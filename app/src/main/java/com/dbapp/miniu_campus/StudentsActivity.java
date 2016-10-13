package com.dbapp.miniu_campus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class StudentsActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_students);
		setTitle(R.string.students);
	}
}