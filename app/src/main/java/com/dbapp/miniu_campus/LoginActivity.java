package com.dbapp.miniu_campus;

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

	/* 뷰 등록 함수 */
	private void myFindView() {
		radioButton_student = (RadioButton) findViewById(R.id.student);
		radioButton_professor = (RadioButton) findViewById(R.id.professor);

		editText_member_id = (EditText) findViewById(R.id.member_id);
		editText_password = (EditText) findViewById(R.id.password);

		button_login = (Button) findViewById(R.id.login);
		button_exit = (Button) findViewById(R.id.exit);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		myFindView();

		mode = Mode.Student;
	}

	/* 버튼 클릭 함수 */
	public void onClick(View view) throws ExecutionException, InterruptedException {
		switch (view.getId()) {
			case R.id.student:
			case R.id.professor:        // 라디오 버튼을 누른 경우
				changeMode(view.getId());
				break;

			case R.id.login:            // 로그인 버튼을 누른 경우
				String member_id = editText_member_id.getText().toString();
				String password = editText_password.getText().toString();
				String grade = mode.toString();

				// JSP 접속 후 결과 가져오기
				LoginAsyncTask loginAsyncTask = new LoginAsyncTask();
				Boolean result = loginAsyncTask.execute(member_id, password, grade).get();

				if (result == true) {
					// 다음 화면 추가
				} else {
					Toast.makeText(this.getApplicationContext(),
							"로그인에 실패했습니다.",
							Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.exit:             // 종료 버튼을 누른 경우
				finish();
		}
	}

	/* 로그인 모드 변경 함수 */
	private void changeMode(int id) {
		switch (id) {
			case R.id.student:
				mode = Mode.Student;
				radioButton_student.setChecked(true);
				radioButton_professor.setChecked(false);
				break;

			case R.id.professor:
				mode = Mode.Professor;
				radioButton_professor.setChecked(true);
				radioButton_student.setChecked(false);
				break;
		}
	}

	private enum Mode {
		Student, Professor
	}
}