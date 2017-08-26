package traffic;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

//Path: http://localhost/<appln-folder-name>/register
@Path("/register")
public class Register {
	
	// HTTP Get Method
		@GET 
		// Path: http://localhost/<appln-folder-name>/register/doregister
		@Path("/doregister")  
		// Produces JSON as response
		@Produces(MediaType.APPLICATION_JSON) 
		// Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?username=abc&password=xyz
		public JSONObject doRegister( @QueryParam("username") String uname, @QueryParam("password") String pwd){
			
			return insertUser(uname, pwd);
			
		}
		
		
		public JSONObject insertUser(String username,String password){
			JSONObject successInsert=new JSONObject();
			DBActions con=new DBActions();
			boolean existUser=false;
			try {
				existUser=con.newUser(username, password);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(existUser){
				try {
					successInsert.put("register", "true");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				try {
					successInsert.put("register", "false");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return successInsert;
			
		}

}
