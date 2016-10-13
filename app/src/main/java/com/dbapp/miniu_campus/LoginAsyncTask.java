package com.dbapp.miniu_campus;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class LoginAsyncTask extends AsyncTask<String, Void, Boolean> {
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(String... params) {
		String member_id = params[0];       // 첫번째 인자 : member_id
		String password = params[1];        // 두번째 인자 : password
		String grade = params[2];           // 세번째 인자 : grade(Student 또는 Professor)

		String page = "http://hwyncho.dlinkddns.com:8080/login.jsp";        // 로그인 JSP 페이지

		String url = page + "?member_id=" + member_id
				+ "&password=" + password
				+ "&grade=" + grade;

		try {
			Document document = Jsoup.connect(url).get();                   // 페이지 html 소스 가져오기
			Element element = document.select("label#result").first();      // 페이지에서 id가 result인 label 값 가져오기

			if (element.text().equals("Success!")) {                        // Success인 경우
				return true;
			} else if (element.text().equals("Fail!")) {                    // Fail인 경우
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Boolean aBoolean) {
		super.onPostExecute(aBoolean);
	}
}