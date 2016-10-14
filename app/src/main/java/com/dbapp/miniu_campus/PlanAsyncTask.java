package com.dbapp.miniu_campus;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class PlanAsyncTask extends AsyncTask<String, Void, String[]> {
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected String[] doInBackground(String... params) {
		String course_id = params[0];       // 첫번째 인자 : course_id

		String page = "http://hwyncho.dlinkddns.com:8080/plan.jsp";     // 강의계획서 JSP 페이지
		String url = page + "?course_id=" + course_id;

		try {
			Document document = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new String[0];
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(String[] strings) {
		super.onPostExecute(strings);
	}
}
