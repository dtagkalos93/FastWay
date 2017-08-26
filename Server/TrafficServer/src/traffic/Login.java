package traffic;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/login")
public class Login {
	@GET
	// Path: http://localhost/<appln-folder-name>/login/dologin
	@Path("/dologin")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/TrafficServer/login/dologin?username=test&password=1234
	public JSONObject doLogin(@QueryParam("username") String uname, @QueryParam("password") String pwd){
		
		return createJson(uname,pwd);
		
	}
	
	
	public JSONObject createJson(String username,String password){
		JSONObject result=new JSONObject();
		
		//result=null;
		DBActions checkUser=new DBActions();
		boolean existUser=false;
		try {
			existUser=checkUser.checkLogin(username, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("exist user" + existUser);
		if(existUser){
			try {
				result.put("login", "true");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else{
			try {
				result.put("login", "false");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
