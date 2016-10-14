package com.dbapp.miniu_campus;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
			Document document = Jsoup.connect(url).get();                   // 페이지 html 가져오기

			String[] member_id = null;
			String[] department = null;
			String[] grade = null;
			String[] member_name = null;

			ArrayList<String[]> memberList = new ArrayList<String[]>();

			Elements elements = document.select("table#students tbody");

			for (Element e : elements) {
				member_id = e.select("tr td#member_id").text().split(" ");
				department = e.select("tr td#department").text().split(" ");
				grade = e.select("tr td#grade").text().split(" ");
				member_name = e.select("tr td#member_name").text().split(" ");
			}

			memberList.add(member_id);
			memberList.add(department);
			memberList.add(grade);
			memberList.add(member_name);

			return memberList;
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