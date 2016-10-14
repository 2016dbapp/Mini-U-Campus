package com.dbapp.miniu_campus;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class PlanWriteAyncTask extends AsyncTask<String, Void, Boolean> {
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(String... params) {
		String course_id = params[0];       // 첫번째 인자 : course_id
		String detail = params[1];          // 두번째 인자 : detail

		String page = "http://hwyncho.dlinkddns.com:8080/plan_write.jsp";        // 강의계획서 입력 JSP 페이지
		String url = page + "?course=" + course_id
				+ "&detail=" + detail;

		try {
			Document document = Jsoup.connect(url).get();                   // 페이지 html 소스 가져오기
			Element element = document.select("label#result").first();      // 페이지에서 id가 result인 label 값 가져오기

			if (element.text().toUpperCase().equals("SUCCESS")) {                        // Success인 경우
				return true;
			} else if (element.text().toUpperCase().equals("FAIL")) {                    // Fail인 경우
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