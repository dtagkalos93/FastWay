package traffic;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/alldestination")
public class AllDestinations {
	@GET
	// Path: http://localhost/<appln-folder-name>/alldestination

	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/TrafficServer/alldestination/
	public JSONObject AllDestinations() throws JSONException{
		
		return createJson();
		
	}
	
	
	public JSONObject createJson() throws JSONException{
		JSONObject result=new JSONObject();
		
		//result=null;
		DBActions con=new DBActions();
		try {
			
			return con.returnDestinations();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new JSONObject().put("0", "");
		
	}
}
