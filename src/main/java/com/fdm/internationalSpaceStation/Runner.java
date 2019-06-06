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
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	  }
	  
	  public static void main(String[] args) throws IOException, JSONException {
		    JSONObject json = readJsonFromUrl("http://api.open-notify.org/iss-now.json");
		    System.out.println(json.get("timestamp")); //UNIX timestamp: the number of seconds that have passed since January 1, 1970
		    System.out.println(json.get("iss_position"));
		    Double latitude = json.getJSONObject("iss_position").getDouble("latitude");
		    Double longitude = json.getJSONObject("iss_position").getDouble("longitude");
		    Double currLatitude = 22.2784; //WeWork
		    Double currLongitude = 114.1702;
		    Double altitude_to_ISS = 408.0;
		
		  }
}

