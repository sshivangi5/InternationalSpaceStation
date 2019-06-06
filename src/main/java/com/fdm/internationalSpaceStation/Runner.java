package com.fdm.internationalSpaceStation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class Runner {
	
	// method taken from
	// https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		String jsonText = readAll(rd);
		JSONObject json = new JSONObject(jsonText);
		is.close();
		return json;
	}

	public static double chordDistance(double lat1, double long1, double lat2, double long2) {

		double x1, y1, z1;
		double x2, y2, z2;
		double chordDist;

		x1 = Math.cos(lat1) * Math.cos(long1);
		y1 = Math.cos(lat1) * Math.sin(long1);
		z1 = Math.cos(lat1);

		x2 = Math.cos(lat2) * Math.sin(long2);
		y2 = Math.cos(lat2) * Math.sin(long2);
		z2 = Math.cos(lat2);

		chordDist = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2) + Math.pow((z2 - z1), 2));
		return chordDist;

	}

	public static void main(String[] args) throws IOException, JSONException {
		JSONObject json = readJsonFromUrl("http://api.open-notify.org/iss-now.json");

		System.out.println(json.get("iss_position"));

		// ISS location
		Double latitude = json.getJSONObject("iss_position").getDouble("latitude");
		Double longitude = json.getJSONObject("iss_position").getDouble("longitude");
		Double issAltitude = 408.0;

		// WeWork location
		Double currLatitude = 22.2784;
		Double currLongitude = 114.1702;

		Double earthRadius = 6371.0;
		Double chordDist = chordDistance(Math.toRadians(latitude), Math.toRadians(longitude),
				Math.toRadians(currLatitude), Math.toRadians(currLongitude));
		Double angle = Math.acos(((Math.pow(earthRadius, 2) + Math.pow(earthRadius, 2) - Math.pow(chordDist, 2))
				/ (2 * earthRadius * earthRadius)));
		System.out.println(Math.toDegrees(angle));
		Double distance = Math.sqrt((Math.pow(earthRadius, 2) + Math.pow((earthRadius + issAltitude), 2)
				- (2 * earthRadius * (earthRadius + issAltitude) * Math.cos(Math.PI- angle))));
		System.out.println(distance);
	}
}
