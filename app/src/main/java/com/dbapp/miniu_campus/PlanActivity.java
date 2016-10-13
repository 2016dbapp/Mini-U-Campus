package com.dbapp.miniu_campus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PlanActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan);
		setTitle(R.string.plan_student);
	}

	public void onClickSave(View view) {
		switch (view.getId()) {
			case R.id.plan_button_save:
				//
				break;
		}
	}
}