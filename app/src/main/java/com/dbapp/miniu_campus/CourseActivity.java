package com.dbapp.miniu_campus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {
	TableLayout tableLayout_entire;
	TableLayout tableLayout_take;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course);
		setTitle(R.string.course);

		Intent intent = getIntent();
		MemberInfo memberInfo = (MemberInfo) intent.getSerializableExtra("MEMBER_INFO");

		myFindView();

		setTableLayout(memberInfo.getMember_id(), memberInfo.getGrade());
	}

	/* 뷰 등록 함수 */
	private void myFindView() {
		tableLayout_entire = (TableLayout) findViewById(R.id.course_table_entire);
		tableLayout_take = (TableLayout) findViewById(R.id.course_table_take);
	}

	/* 테이블 레이아웃 동적 생성 함수 */
	public void setTableLayout(String... params) {
		String member_id = params[0];
		String grade = params[1];

		if (grade.toUpperCase().equals("STUDENT")) {
			((TextView) findViewById(R.id.course_text_take)).setText("수강 강의");
		} else if (grade.toUpperCase().equals("PROFESSOR")) {
			((TextView) findViewById(R.id.course_text_take)).setText("수업 강의");
		}

		// 전체 강의 테이블
		try {
			CourseAsyncTask courseAsyncTask = new CourseAsyncTask();

			// 전체 강의 목록 가져오기
			ArrayList<String[]> courseList = courseAsyncTask.execute("ENTIRE", "").get();

			String[] course_id = courseList.get(0);         // 강의번호 목록
			String[] course_name = courseList.get(1);       // 강의명 목록

			if (course_id[0].toString().equals(""))         // 비어있을 경우 예외 발생
				throw new Exception();

			// TableRow 생성
			for (int i = 0; i < course_id.length; i++) {
				TextView textView_course_id = new TextView(this);
				TextView textView_course_name = new TextView(this);
				Button button_plan = new Button(this);
				Button button_add = new Button(this);

				textView_course_id.setText(course_id[i].toString());
				textView_course_name.setText(course_name[i].toString());

				if (grade.toUpperCase().equals("STUDENT")) {
					button_plan.setText("조회");
					button_add.setText("등록");
				} else if (grade.toUpperCase().equals("PROFESSOR")) {
					button_plan.setText("조회");
					button_add.setText("입력");
				}

				TableRow tableRow = new TableRow(this);
				tableRow.addView(textView_course_id);
				tableRow.addView(textView_course_name);
				tableRow.addView(button_plan);
				tableRow.addView(button_add);

				tableLayout_entire.addView(tableRow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 수강 or 수업 강의 테이블
		try {
			CourseAsyncTask courseAsyncTask = new CourseAsyncTask();

			// 수강 or 수업 강의 목록 가져오기
			ArrayList<String[]> courseList = courseAsyncTask.execute("TAKE", member_id).get();

			String[] course_id = courseList.get(0);         // 강의번호 목록
			String[] course_name = courseList.get(1);       // 강의명 목록

			if (course_id[0].toString().equals(""))         // 비어 있을 경우 예외 발생
				throw new Exception();

			//  TableRow 생성
			for (int i = 0; i < course_id.length; i++) {
				TextView textView_course_id = new TextView(this);
				TextView textView_course_name = new TextView(this);
				Button button_plan = new Button(this);
				Button button_add = new Button(this);

				textView_course_id.setText(course_id[i].toString());
				textView_course_name.setText(course_name[i].toString());

				if (grade.toUpperCase().equals("STUDENT")) {
					button_plan.setText("조회");
					button_add.setText("삭제");
				} else if (grade.toUpperCase().equals("PROFESSOR")) {
					button_plan.setText("조회");
					button_add.setText("입력");
				}

				TableRow tableRow = new TableRow(this);
				tableRow.addView(textView_course_id);
				tableRow.addView(textView_course_name);
				tableRow.addView(button_plan);
				tableRow.addView(button_add);

				tableLayout_take.addView(tableRow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}