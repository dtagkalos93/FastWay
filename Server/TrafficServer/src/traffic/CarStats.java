package traffic;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.JsonArray;

//Path: http://localhost:8080/TrafficServer/car?startpos=38.064136,2023.854369&finalpos=38.054159,2023.842761&ourtime=1
@Path("/car")
public class CarStats {
	
	private String url="https://maps.googleapis.com/maps/api/directions/json?origin=";
	private String url2="&destination=";
	private String key="&key=AIzaSyB8WAublolSKWuJsNe78r7VFImUy8hz80I";
	
	// HTTP Get Method
			@GET  
			// Produces JSON as response
			@Produces(MediaType.APPLICATION_JSON) 
			// Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?username=abc&password=xyz
			public JSONObject doCarStats( @QueryParam("startpos") String startpos, @QueryParam("finalpos") String finalpos,@QueryParam("ourtime") String ourtime,@QueryParam("id") String id){
				
				JSONObject part= duration(startpos, finalpos);
				JSONArray part2;
				JSONObject part3;
				JSONArray part4;
				JSONObject part5;
				JSONObject part6;
				JSONObject distpart1;
				String duration;
				String distance;
				String time;
				String day;
				try {
					part2 = part.getJSONArray("routes");
					part3 = part2.getJSONObject(0);
					part4=part3.getJSONArray("legs");
					part5=part4.getJSONObject(0);
					distpart1 = part5.getJSONObject("distance");
					distance=distpart1.getString("text");
					String [] distanceArray=distance.split(" ");
					distance=distanceArray[0];
					part6=part5.getJSONObject("duration");
					
					duration=part6.getString("text");
					String[]  durations=duration.split(" ");
					duration=durations[0];
					time=getCurrentTime();
					day=getDay();
					return insertData(startpos,finalpos,ourtime,duration,time,day,distance,id);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
				
			}
			
			
			public JSONObject duration(String start,String finapos){
				URLConnectionReader con=new URLConnectionReader();
				
				String finalurl=url+start+url2+finapos+key;
				//String finalurl="https://maps.googleapis.com/maps/api/directions/json?origin=38.064136,%2023.854369&destination=38.054159,%2023.842761&key=AIzaSyB8WAublolSKWuJsNe78r7VFImUy8hz80I";
				System.out.println("Url" + finalurl);
					try {
						return con.sendGet(finalurl);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				return null;
			}
			
			public String getCurrentTime(){
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss	");
				
				Date date = new Date();   // given date
				Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
				calendar.setTime(date);   // assigns calendar to given date 
				if(calendar.get(Calendar.HOUR_OF_DAY)>=0 && calendar.get(Calendar.HOUR_OF_DAY)<=3 ){
					return "Midnight";
				}
				else if(calendar.get(Calendar.HOUR_OF_DAY)>=4 && calendar.get(Calendar.HOUR_OF_DAY)<=7){
					return "Early Morning";
				}
				else if(calendar.get(Calendar.HOUR_OF_DAY)>=8 && calendar.get(Calendar.HOUR_OF_DAY)<=11){
					return "Morning";
				}
				else if(calendar.get(Calendar.HOUR_OF_DAY)>=12 && calendar.get(Calendar.HOUR_OF_DAY)<=15){
					return "Midday";
				}
				else if(calendar.get(Calendar.HOUR_OF_DAY)>=14 && calendar.get(Calendar.HOUR_OF_DAY)<=19){
					return "Aftenoon";
				}
				else{
					return "Nigth";
				}
				
				/*Date date = new Date();
				System.out.println(dateFormat.format(date));
				return dateFormat.format(date).toString();*/
			}
			
			public String getDay(){
				Calendar calendar = Calendar.getInstance();
				int day = calendar.get(Calendar.DAY_OF_WEEK); 
				String dayofweek=null;

				switch (day) {
				    case Calendar.SUNDAY:
				        // Current day is Sunday
				    	dayofweek="Weekend";
				    	break;
				    case Calendar.MONDAY:
				        // Current day is Monday
				    	dayofweek="Daily";
				    	break;

				    case Calendar.TUESDAY:
				    	dayofweek="Daily";
				    	break;
				    case Calendar.WEDNESDAY:
				    	dayofweek="Daily";
				    	break;
				    case Calendar.THURSDAY:
				    	dayofweek="Daily";
				    	break;
				    case Calendar.FRIDAY:
				    	dayofweek="Daily";
				    	break;
				    case Calendar.SATURDAY:
				    	dayofweek="Weekend";
				    	break;
				}
				System.out.println(dayofweek);
				return dayofweek;
				
			}
			
			
			
			public JSONObject insertData(String start,String finalpos,String ourTime, String duration,String time,String dayofweek,String distance,String id){
				JSONObject successInsert=new JSONObject();
				DBActions con=new DBActions();
				boolean existUser=false;
				try {
					existUser=con.insertCarData(start,finalpos,ourTime,duration,time,dayofweek,distance,id);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(existUser){
					try {
						successInsert.put("Car Data", "true");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					try {
						successInsert.put("Car Data", "false");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				return successInsert;
				
			}

}
