package traffic;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

//Path: http://localhost:8080/TrafficServer/destination?startpos=38.064213,23.854284&finalpos=38.024066,23.833596
@Path("/destination")
public class Destination {
	
	private String url="https://maps.googleapis.com/maps/api/directions/json?origin=";
	private String url2="&destination=";
	private String key="&key=AIzaSyB8WAublolSKWuJsNe78r7VFImUy8hz80I";
	
	// HTTP Get Method
			@GET  
			// Produces JSON as response
			@Produces(MediaType.APPLICATION_JSON) 
			// Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?username=abc&password=xyz
			public JSONObject Destination( @QueryParam("startpos") String startpos, @QueryParam("finalpos") String finalpos){
				DBActions con=new DBActions();
				JSONObject destinationID=new JSONObject();
				try {
					int id=con.insertDestination(startpos, finalpos);
					destinationID.put("Destination", id);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			
				return destinationID;
			}
			
			
}