	package traffic;
	
	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
	import java.nio.charset.Charset;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class URLConnectionReader {
	
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }


	// HTTP GET request
	public JSONObject sendGet(String url) throws Exception {

	    URL obj = new URL(url);
	    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

	    // optional default is GET
	    con.setRequestMethod("GET");

	    //add request header

	    con.setRequestProperty("Authorization", "myAuthorizationProp");

	    int responseCode = con.getResponseCode();
	    System.out.println("\nSending 'GET' request to URL : " + url);
	    System.out.println("Response Code : " + responseCode);

	    BufferedReader in = new BufferedReader(
	            new InputStreamReader(con.getInputStream()));
	    
	    String jsonText = readAll(in);
	    
	    JSONObject json = new JSONObject(jsonText);
	    
	    in.close();
	    return json;
	    
	    
	    /*String inputLine;
	    StringBuffer response = new StringBuffer();

	    while ((inputLine = in.readLine()) != null) {
	        response.append(inputLine);
	    }
	    in.close();

	    System.out.println("Response Message: " + response.toString());
	    response.
	    return response;*/
	}
}