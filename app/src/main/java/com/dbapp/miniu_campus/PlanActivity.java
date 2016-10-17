package com.dbapp.miniu_campus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PlanActivity extends AppCompatActivity {
	String grade;
	String course_id;

	TableLayout tableLayout;

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

		myFindView();
		mySetTableLayout(this.course_id);
	}

	public void myFindView() {
		this.tableLayout = (TableLayout) findViewById(R.id.table_plan);
	}

	public void mySetTableLayout(String... params) {
		try {
			// JSP 접속 후, 강의계획서 가져오기
			PlanAsyncTask planAsyncTask = new PlanAsyncTask();
			ArrayList<String> planList = planAsyncTask.execute(this.course_id).get();

			String course_name = planList.get(0);
			String time = planList.get(1);
			String prof_id = planList.get(2);
			String seats = planList.get(3);
			String detail = planList.get(4);

			if (course_name.toString().equals(""))         // 비어있을 경우 예외 발생
				throw new Exception();

			TextView textVIew_course_id = (TextView) findViewById(R.id.plan_text_course_id);
			TextView textView_course_name = (TextView) findViewById(R.id.plan_text_course_name);
			TextView textView_time = (TextView) findViewById(R.id.plan_text_time);
			TextView textView_prof_id = (TextView) findViewById(R.id.plan_text_prof);
			TextView textView_seats = (TextView) findViewById(R.id.plan_text_seats);
			EditText editText_detail = (EditText) findViewById(R.id.plan_text_detail);

			textVIew_course_id.setText(this.course_id);
			textView_course_name.setText(course_name);
			textView_time.setText(time + "교시");
			textView_prof_id.setText(prof_id);
			textView_seats.setText(seats + "명");
			editText_detail.setText(detail);

			if (this.grade.toUpperCase().equals("STUDENT")) {
				editText_detail.setEnabled(false);
			} else if (this.grade.toUpperCase().equals("PROFESSOR")) {
				editText_detail.setEnabled(true);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onClickSave(View view) throws ExecutionException, InterruptedException {
		switch (view.getId()) {
			case R.id.plan_button_save:
				if (this.grade.toUpperCase().equals("STUDENT")) {
					finish();
				} else if (this.grade.toUpperCase().equals("PROFESSOR")) {
					String detail = ((EditText) findViewById(R.id.plan_text_detail)).getText().toString();

					PlanWriteAsyncTask planWriteAsyncTask = new PlanWriteAsyncTask();
					Boolean result = planWriteAsyncTask.execute(this.course_id, detail).get();

					if (result == true) {
						Toast.makeText(this, "성공적으로 입력됐습니다.", Toast.LENGTH_SHORT).show();
						finish();
					} else {
						Toast.makeText(this, "입력에 실패했습니다.", Toast.LENGTH_SHORT).show();
					}
				}
				break;
		}
	}
}