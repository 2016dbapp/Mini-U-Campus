package com.dbapp.miniu_campus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentsActivity extends AppCompatActivity {
	String course_id;

	TableLayout tableLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_students);
		setTitle(R.string.students);

		Intent intent = getIntent();
		MyData myData = (MyData) intent.getSerializableExtra("MY_DATA");

		this.course_id = myData.getCourse_id();

		this.myFindView();
		this.mySetTableLayout();
	}

	public void myFindView() {
		this.tableLayout = (TableLayout) findViewById(R.id.students_table);
	}

	/* 테이블 레이아웃 동적 생성 함수 */
	public void mySetTableLayout(String... params) {
		// 학생 목록 테이블
		try {
			this.tableLayout.removeAllViews();        // 전체 학생 목록 테이블 내용 삭제

			// 테이블 row 설정 및 추가
			TextView textView_head_member_id = new TextView(this);
			TextView textView_head_department = new TextView(this);
			TextView textView_head_grade = new TextView(this);
			TextView textView_head_member_name = new TextView(this);

			textView_head_member_id.setText("학번");
			textView_head_department.setText("학과");
			textView_head_grade.setText("학년");
			textView_head_member_name.setText("이름");

			TableRow tableRow_head = new TableRow(this);
			tableRow_head.setBackgroundResource(R.color.tableRowDark);
			tableRow_head.addView(textView_head_member_id);
			tableRow_head.addView(textView_head_department);
			tableRow_head.addView(textView_head_grade);
			tableRow_head.addView(textView_head_member_name);

			this.tableLayout.addView(tableRow_head);

			// JSP 접속 후, 학생 목록 가져오기
			StudentsAsyncTask studentsAsyncTask = new StudentsAsyncTask();
			ArrayList<String[]> memberList = studentsAsyncTask.execute(this.course_id).get();

			String[] member_id = memberList.get(0);         // 학번 목록
			String[] department = memberList.get(1);        // 학과 목록
			String[] grade = memberList.get(2);             // 학년 목록
			String[] member_name = memberList.get(3);       // 이름 목록

			if (member_id[0].toString().equals(""))         // 비어있을 경우 예외 발생
				throw new Exception();

			// TableRow 생성
			for (int i = 0; i < member_id.length; i++) {
				TextView textView_member_id = new TextView(this);
				TextView textView_department = new TextView(this);
				TextView textView_grade = new TextView(this);
				TextView textView_member_name = new TextView(this);

				textView_member_id.setText(member_id[i].toString());
				textView_department.setText(department[i].toString());
				textView_grade.setText(grade[i].toString());
				textView_member_name.setText(member_name[i].toString());

				TableRow tableRow = new TableRow(this);
				tableRow.addView(textView_member_id);
				tableRow.addView(textView_department);
				tableRow.addView(textView_grade);
				tableRow.addView(textView_member_name);

				this.tableLayout.addView(tableRow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void myOnClick_Students(View view) {
		switch (view.getId()) {
			case R.id.students_button_ok:
				finish();
				break;
		}
	}
}