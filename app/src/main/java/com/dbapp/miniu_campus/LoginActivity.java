package com.dbapp.miniu_campus;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
	Mode mode;                                      // 로그인 모드
	private RadioButton radioButton_student;        // 강의등록관리
	private RadioButton radioButton_professor;      // 강의계획서관리

	private EditText editText_member_id;            // 학번
	private EditText editText_password;             // 비밀번호

	private Button button_login;                    // 로그인
	private Button button_exit;                     // 종료

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setTitle(R.string.login);

		this.myFindView();

		this.mode = Mode.STUDENT;
	}

	/* 뷰 등록 함수 */
	private void myFindView() {
		this.radioButton_student = (RadioButton) findViewById(R.id.login_radio_student);
		this.radioButton_professor = (RadioButton) findViewById(R.id.login_radio_professor);

		this.editText_member_id = (EditText) findViewById(R.id.login_text_member_id);
		this.editText_password = (EditText) findViewById(R.id.login_text_password);

		this.button_login = (Button) findViewById(R.id.login_button_login);
		this.button_exit = (Button) findViewById(R.id.login_button_exit);
	}

	/* 버튼 클릭 함수 */
	public void myOnClick_Login(View view) throws ExecutionException, InterruptedException {
		switch (view.getId()) {
			case R.id.login_radio_student:
			case R.id.login_radio_professor:        // 라디오 버튼을 누른 경우
				this.myChangeMode(view.getId());
				break;

			case R.id.login_button_login:            // 로그인 버튼을 누른 경우
				String member_id = editText_member_id.getText().toString();
				String password = editText_password.getText().toString();
				String grade = mode.toString();

				// JSP 접속 후, 로그인 결과 가져오기
				LoginAsyncTask loginAsyncTask = new LoginAsyncTask();
				Boolean result = loginAsyncTask.execute(member_id, password, grade).get();

				if (result == true) {
					this.myStartCourseActivity();
					finish();
				} else {
					Toast.makeText(this.getApplicationContext(),
							"로그인에 실패했습니다.",
							Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.login_button_exit:             // 종료 버튼을 누른 경우
				finish();
				break;
		}
	}

	/* 로그인 모드 변경 함수 */
	private void myChangeMode(int id) {
		switch (id) {
			case R.id.login_radio_student:
				this.mode = Mode.STUDENT;
				this.radioButton_student.setChecked(true);
				this.radioButton_professor.setChecked(false);
				break;

			case R.id.login_radio_professor:
				this.mode = Mode.PROFESSOR;
				this.radioButton_professor.setChecked(true);
				this.radioButton_student.setChecked(false);
				break;
		}
	}

	/* CourseActivity 실행 함수 */
	private void myStartCourseActivity() {
		MyData myData = new MyData();
		myData.setMember_id(editText_member_id.getText().toString());
		myData.setGrade(mode.toString());

		ComponentName componentName = new ComponentName(
				"com.dbapp.miniu_campus",
				"com.dbapp.miniu_campus.CourseActivity");

		Intent intent = new Intent();
		intent.setComponent(componentName);
		intent.putExtra("MY_DATA", myData);
		startActivity(intent);
	}

	private enum Mode {
		STUDENT, PROFESSOR
	}
}