package com.dbapp.miniu_campus;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class PlanAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected ArrayList<String> doInBackground(String... params) {
		String course_id = params[0];        // 첫번째 인자 : course_id

		String page = "http://hwyncho.dlinkddns.com:8080/plan.jsp";        // 강의계획서 JSP 페이지
		String url = page + "?course_id=" + course_id;

		try {
			Document document = Jsoup.connect(url).get();

			String course_name = null;
			String time = null;
			String prof_id = null;
			String seats = null;
			String detail = null;

			ArrayList<String> planList = new ArrayList<String>();

			Elements elements = document.select("table#plan tbody");

			for (Element e : elements) {
				course_name = e.select("tr td#course_name").text();
				time = e.select("tr td#time").text();
				prof_id = e.select("tr td#prof_id").text();
				seats = e.select("tr td#seats").text();
				detail = e.select("tr td#detail input").attr("value").toString();
			}

			planList.add(course_name);
			planList.add(time);
			planList.add(prof_id);
			planList.add(seats);
			planList.add(detail);

			return planList;

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
	protected void onPostExecute(ArrayList<String> strings) {
		super.onPostExecute(strings);
	}
}