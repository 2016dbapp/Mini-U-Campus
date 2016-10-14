package com.dbapp.miniu_campus;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class CourseAsyncTask extends AsyncTask<String, Void, ArrayList<String[]>> {
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected ArrayList<String[]> doInBackground(String... params) {
		String type = params[0];
		String member_id = params[1];       // 첫번째 인자 : member_id

		String page = "http://hwyncho.dlinkddns.com:8080/course.jsp";       // 강의정보 JSP 페이지
		String url = page + "?member_id=" + member_id;

		try {
			Document document = Jsoup.connect(url).get();                   // 페이지 html 가져오기

			String[] course_id = null;
			String[] course_name = null;

			ArrayList<String[]> courseList = new ArrayList<String[]>();

			if (type.toUpperCase().equals("ENTIRE")) {
				Elements elements_entire = document.select("table#entire tbody");               // id가 entire인 table의 tbody 가져오기 (전체 강의)

				for (Element e : elements_entire) {
					course_id = e.select("tr td#entire_course_id").text().split(" ");          // id가 course_id인 td 값 가져오기
					course_name = e.select("tr td#entire_course_name").text().split(" ");      // id가 course_name인 td 값 가져오기
				}

				courseList.add(course_id);
				courseList.add(course_name);
			} else if (type.toUpperCase().equals("TAKE")) {
				Elements elements_take = document.select("table#take tbody");                // id가 take인 table의 tbody 가져오기 (수강 or 수업 강의)

				for (Element e : elements_take) {
					course_id = e.select("tr td#take_course_id").text().split(" ");          // id가 course_id인 td 값 가져오기
					course_name = e.select("tr td#take_course_name").text().split(" ");     // id가 course_name인 td 값 가져오기
				}

				courseList.add(course_id);
				courseList.add(course_name);
			}

			return courseList;
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