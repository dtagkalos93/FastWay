package traffic;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

//Path:http://localhost:60000/TrafficServer/FastestRoute?time=Afrenoon&day=Weekend&id=2
@Path("/FastestRoute")
public class FastestRoute {
	
	private String url="https://maps.googleapis.com/maps/api/directions/json?origin=";
	private String url2="&destination=";
	private String key="&key=AIzaSyB8WAublolSKWuJsNe78r7VFImUy8hz80I";
	
	// HTTP Get Method
			@GET  
			// Produces JSON as response
			@Produces(MediaType.APPLICATION_JSON) 
			// Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?username=abc&password=xyz
			public JSONObject FastestRoute( @QueryParam("time") String time,@QueryParam("day") String day,@QueryParam("id") String id){
				DBActions conDB=new DBActions();
				
				URLConnectionReader con=new URLConnectionReader();
				try {
					ArrayList<String> coordinates=conDB.findCor(id);
					String startpos=coordinates.get(0);
					String finalpos=coordinates.get(1);
					ArrayList<String> route =conDB.selectRoute(startpos,finalpos,time,day,id);
					String finalUrl=url+startpos+url2+finalpos+"&waypoints=via:"+route.get(0);
					for(int i=1;i<route.size();i++){
						finalUrl=finalUrl+"|via:"+route.get(i);
					}
					finalUrl=finalUrl+key;
					System.out.println(finalUrl);
					return con.sendGet(finalUrl);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
			
}