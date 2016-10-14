package com.dbapp.miniu_campus;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class StudentsAsyncTask extends AsyncTask<String, Void, ArrayList<String[]>> {
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected ArrayList<String[]> doInBackground(String... params) {
		String course_id = params[0];       // 첫번째 인자

		String page = "http://hwyncho.dlinkddns.com:8080/students.jsp";     // 학생 조회 JSP 페이지
		String url = page + "?course_id=" + course_id;

		try {
			Document document = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(ArrayList<String[]> strings) {
		super.onPostExecute(strings);
	}
}
