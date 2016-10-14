package com.dbapp.miniu_campus;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CourseActivity extends AppCompatActivity implements View.OnClickListener {
	String member_id;
	String grade;

	TableLayout tableLayout_entire;
	TableLayout tableLayout_take;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course);
		setTitle(R.string.course);

		Intent intent = getIntent();
		MyData myData = (MyData) intent.getSerializableExtra("MY_DATA");

		this.member_id = myData.getMember_id();
		this.grade = myData.getGrade();

		this.myFindView();

		this.mySetTableLayout(member_id, grade);
	}

	@Override
	public void onClick(View view) {
		if (((Button) findViewById(view.getId())).getText().toString().equals("조회")) {
			if (this.grade.toUpperCase().equals("STUDENT")) {
				myPlanRead(view.getId());
			} else if (this.grade.toUpperCase().equals("PROFESSOR")) {
				myStudents(view.getId());
			}
		} else if (((Button) findViewById(view.getId())).getText().toString().equals("등록")) {
			this.myCourseAdd(view.getId());
		} else if ((((Button) findViewById(view.getId())).getText().toString().equals("삭제"))) {
			this.myCourseDelete(view.getId());
		} else if ((((Button) findViewById(view.getId())).getText().toString().equals("입력"))) {
			this.myPlanWrite(view.getId());
		}
	}

	/* 뷰 등록 함수 */
	private void myFindView() {
		this.tableLayout_entire = (TableLayout) findViewById(R.id.course_table_entire);
		this.tableLayout_take = (TableLayout) findViewById(R.id.course_table_take);
	}

	/* 테이블 레이아웃 동적 생성 함수 */
	public void mySetTableLayout(String... params) {
		String member_id = params[0];
		String grade = params[1];

		if (grade.toUpperCase().equals("STUDENT")) {
			((TextView) findViewById(R.id.course_text_take)).setText("수강 강의");
		} else if (grade.toUpperCase().equals("PROFESSOR")) {
			((TextView) findViewById(R.id.course_text_take)).setText("담당 강의");
		}

		int myId = 0;

		// 전체 강의 테이블
		try {
			this.tableLayout_entire.removeAllViews();        // 전체 강의 테이블 내용 삭제

			// 테이블 row 설정 및 추가
			TextView textView_head_course_id = new TextView(this);
			TextView textView_head_course_name = new TextView(this);

			textView_head_course_id.setText("강의번호");
			textView_head_course_name.setText("강의명");

			TableRow tableRow_head = new TableRow(this);
			tableRow_head.setBackgroundResource(R.color.tableRowDark);
			tableRow_head.addView(textView_head_course_id);
			tableRow_head.addView(textView_head_course_name);

			this.tableLayout_entire.addView(tableRow_head);

			// JSP 접속 후, 전체 강의 목록 가져오기
			CourseAsyncTask courseAsyncTask = new CourseAsyncTask();
			ArrayList<String[]> courseList = courseAsyncTask.execute("ENTIRE", "").get();

			String[] course_id = courseList.get(0);         // 강의번호 목록
			String[] course_name = courseList.get(1);       // 강의명 목록

			if (course_id[0].toString().equals(""))         // 비어있을 경우 예외 발생
				throw new Exception();

			// TableRow 생성
			for (int i = 0; i < course_id.length; i++) {
				TextView textView_course_id = new TextView(this);
				TextView textView_course_name = new TextView(this);
				Button button_left = new Button(this);
				Button button_right = new Button(this);

				textView_course_id.setId(myId);
				textView_course_name.setId(myId + 1);
				button_left.setId(myId + 2);
				button_right.setId(myId + 3);

				textView_course_id.setText(course_id[i].toString());
				textView_course_name.setText(course_name[i].toString());

				if (grade.toUpperCase().equals("STUDENT")) {
					button_left.setText("조회");
					button_right.setText("등록");
				} else if (grade.toUpperCase().equals("PROFESSOR")) {
					button_left.setText("조회");
					button_right.setText("입력");
				}

				button_left.setOnClickListener(this);
				button_right.setOnClickListener(this);

				TableRow tableRow = new TableRow(this);
				tableRow.addView(textView_course_id);
				tableRow.addView(textView_course_name);
				tableRow.addView(button_left);
				tableRow.addView(button_right);

				this.tableLayout_entire.addView(tableRow);

				myId += 10;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 수강 or 담당 강의 테이블
		try {
			this.tableLayout_take.removeAllViews();        // 전체 강의 테이블 내용 삭제

			// 테이블 row 설정 및 추가
			TextView textView_head_course_id = new TextView(this);
			TextView textView_head_course_name = new TextView(this);

			textView_head_course_id.setText("강의번호");
			textView_head_course_name.setText("강의명");

			TableRow tableRow_head = new TableRow(this);
			tableRow_head.setBackgroundResource(R.color.tableRowDark);
			tableRow_head.addView(textView_head_course_id);
			tableRow_head.addView(textView_head_course_name);

			this.tableLayout_take.addView(tableRow_head);

			// JSP 접속 후, 수강 or 수업 강의 목록 가져오기
			CourseAsyncTask courseAsyncTask = new CourseAsyncTask();
			ArrayList<String[]> courseList = courseAsyncTask.execute("TAKE", member_id).get();

			String[] course_id = courseList.get(0);         // 강의번호 목록
			String[] course_name = courseList.get(1);       // 강의명 목록

			if (course_id[0].toString().equals(""))         // 비어 있을 경우 예외 발생
				throw new Exception();

			//  TableRow 생성
			for (int i = 0; i < course_id.length; i++) {
				TextView textView_course_id = new TextView(this);
				TextView textView_course_name = new TextView(this);
				Button button_left = new Button(this);
				Button button_right = new Button(this);

				textView_course_id.setId(myId);
				textView_course_name.setId(myId + 1);
				button_left.setId(myId + 2);
				button_right.setId(myId + 3);

				textView_course_id.setText(course_id[i].toString());
				textView_course_name.setText(course_name[i].toString());

				if (grade.toUpperCase().equals("STUDENT")) {
					button_left.setText("조회");
					button_right.setText("삭제");
				} else if (grade.toUpperCase().equals("PROFESSOR")) {
					button_left.setText("조회");
					button_right.setText("입력");
				}

				button_left.setOnClickListener(this);
				button_right.setOnClickListener(this);

				TableRow tableRow = new TableRow(this);
				tableRow.addView(textView_course_id);
				tableRow.addView(textView_course_name);
				tableRow.addView(button_left);
				tableRow.addView(button_right);

				this.tableLayout_take.addView(tableRow);

				myId += 10;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void myCourseAdd(int id) {
		String course_id = ((TextView) findViewById(id - 3)).getText().toString();

		try {
			CourseAddAsyncTask courseAddAsyncTask = new CourseAddAsyncTask();
			Boolean result = courseAddAsyncTask.execute(member_id, course_id).get();

			if (result == true) {
				Toast.makeText(this, "성공적으로 등록되었습니다.", Toast.LENGTH_SHORT).show();
				this.mySetTableLayout(member_id, grade);
			} else {
				Toast.makeText(this, "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public void myCourseDelete(int id) {
		String course_id = ((TextView) findViewById(id - 3)).getText().toString();

		try {
			CourseDeleteAsyncTask courseDeleteAsyncTask = new CourseDeleteAsyncTask();
			Boolean result = courseDeleteAsyncTask.execute(member_id, course_id).get();

			if (result == true) {
				Toast.makeText(this, "성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
				this.mySetTableLayout(member_id, grade);
			} else {
				Toast.makeText(this, "삭제에 실패했습니다.", Toast.LENGTH_SHORT).show();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public void myPlanRead(int id) {
		String course_id = ((TextView) findViewById(id - 2)).getText().toString();

		MyData myData = new MyData();
		myData.setGrade(this.grade);
		myData.setCourse_id(course_id);

		ComponentName componentName = new ComponentName(
				"com.dbapp.miniu_campus",
				"com.dbapp.miniu_campus.PlanActivity");

		Intent intent = new Intent();
		intent.setComponent(componentName);
		intent.putExtra("MY_DATA", myData);
		startActivity(intent);
	}

	public void myPlanWrite(int id) {
		String course_id = ((TextView) findViewById(id - 3)).getText().toString();

		MyData myData = new MyData();
		myData.setGrade(this.grade);
		myData.setCourse_id(course_id);

		ComponentName componentName = new ComponentName(
				"com.dbapp.miniu_campus",
				"com.dbapp.miniu_campus.PlanActivity");

		Intent intent = new Intent();
		intent.setComponent(componentName);
		intent.putExtra("MY_DATA", myData);
		startActivity(intent);
	}

	public void myStudents(int id) {
		String course_id = ((TextView) findViewById(id - 2)).getText().toString();

		MyData myData = new MyData();
		myData.setCourse_id(course_id);

		ComponentName componentName = new ComponentName(
				"com.dbapp.miniu_campus",
				"com.dbapp.miniu_campus.StudentsActivity");

		Intent intent = new Intent();
		intent.setComponent(componentName);
		intent.putExtra("MY_DATA", myData);
		startActivity(intent);
	}
}