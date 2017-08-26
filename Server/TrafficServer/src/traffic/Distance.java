package traffic;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
@Path("/distance")
public class Distance {
	
	private String url="https://maps.googleapis.com/maps/api/directions/json?origin=";
	private String url2="&destination=";
	private String key="&key=AIzaSyB8WAublolSKWuJsNe78r7VFImUy8hz80I";
	//http://localhost:8080/TrafficServer/distance?startpos=38.064136,23.854369&finalpos=38.054159,23.842761
	@GET  
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	public JSONObject Distance( @QueryParam("startpos") String startpos, @QueryParam("finalpos") String finalpos){
		URLConnectionReader con=new URLConnectionReader();
		JSONArray part2;
		JSONObject part3;
		JSONArray part4;
		JSONObject part5;
		JSONObject part6;
		JSONObject distpart1;
		String duration;
		String distance;
		JSONObject dis=new JSONObject();
		
		String finalurl=url+startpos+url2+finalpos+key;
		//String finalurl="https://maps.googleapis.com/maps/api/directions/json?origin=38.064136,%2023.854369&destination=38.054159,%2023.842761&key=AIzaSyB8WAublolSKWuJsNe78r7VFImUy8hz80I";
		System.out.println("Url" + finalurl);
			try {
				JSONObject part=con.sendGet(finalurl);
				part2 = part.getJSONArray("routes");
				part3 = part2.getJSONObject(0);
				part4=part3.getJSONArray("legs");
				part5=part4.getJSONObject(0);
				distpart1 = part5.getJSONObject("distance");
				distance=distpart1.getString("text");
				String [] distanceArray=distance.split(" ");
				distance=distanceArray[0];
				if(distanceArray[1].equals("m")){
					distance="0.0"+distance;
				}
				dis.put("distance",distance);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return dis;
	}

}
