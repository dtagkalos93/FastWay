package traffic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.sun.crypto.provider.RSACipher;

public class DBActions {

	public String dbClass = "com.mysql.jdbc.Driver";
	private String dbName = "trafficapp";
	public String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;
	public String dbUser = "root";
	public String dbPwd = "";

	private String url = "https://maps.googleapis.com/maps/api/directions/json?origin=";
	private String url2 = "&destination=";
	private String key = "&key=AIzaSyB8WAublolSKWuJsNe78r7VFImUy8hz80I";
	private String url3 = "http://maps.google.com/maps/api/geocode/json?address=";

	private ArrayList<String> waypoints;
	private ArrayList<String> duration;
	private ArrayList<String> distance;
	private ArrayList<String> sortedwaypoints;
	private ArrayList<String> sortedduration;
	private ArrayList<String> sorteddistance;
	private ArrayList<String> route;
	private ArrayList<String> coordinates;

	/**
	 * Method to create DB Connection
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection DBConnention() throws Exception {
		Connection con = null;
		try {
			Class.forName(dbClass);
			con = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
		} catch (Exception e) {
			// TODO
		}
		return con;
	}

	/**
	 * Method to check whether uname and pwd combination are correct
	 * 
	 * @param uname
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public boolean checkLogin(String uname, String pwd) throws Exception {
		boolean isUserAvailable = false;
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnention();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "SELECT * FROM account WHERE username = '" + uname + "' AND password=" + "'" + pwd + "'";
			// System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				// System.out.println(rs.getString(1) + rs.getString(2) +
				// rs.getString(3));
				isUserAvailable = true;
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return isUserAvailable;
	}

	/**
	 * Method to insert uname and pwd in DB
	 * 
	 * @param name
	 * @param uname
	 * @param pwd
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean newUser(String uname, String pwd) throws SQLException, Exception {
		boolean insertStatus = false;
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnention();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "SELECT * FROM account WHERE username = '" + uname + "'";
			// System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				insertStatus = false;
				System.out.println("hreeee iffffff");
			} else {

				System.out.println("here elseeee");
				// System.out.println(rs.getString(1) + rs.getString(2) +
				// rs.getString(3));
				query = "INSERT into account(username, password) values('" + uname + "','" + pwd + "')";
				// System.out.println(query);
				int records = stmt.executeUpdate(query);
				// System.out.println(records);
				// When record is successfully inserted
				if (records > 0) {
					insertStatus = true;
				}
			}

		} catch (SQLException sqle) {
			// sqle.printStackTrace();
			throw sqle;
		} catch (Exception e) {
			// e.printStackTrace();
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return insertStatus;
	}

	public boolean insertCarData(String start, String finalpos, String ourTime, String duration, String time,
			String dayofweek, String distance, String id) throws SQLException, Exception {
		boolean insertStatus = false;
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnention();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "INSERT into carstats(startpos,finalpos,idealtime,ourtime,daytime,day,distance,id) values('"
					+ start + "'," + "'" + finalpos + "','" + duration + "','" + ourTime + "','" + time + "','"
					+ dayofweek + "','" + distance + "','" + id + "')";
			System.out.println(query);
			int records = stmt.executeUpdate(query);
			// System.out.println(records);
			// When record is successfully inserted
			if (records > 0) {
				insertStatus = true;
			}
		} catch (SQLException sqle) {
			// sqle.printStackTrace();
			throw sqle;
		} catch (Exception e) {
			// e.printStackTrace();
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return insertStatus;
	}

	public JSONObject returnDestinations() throws SQLException, Exception {
		boolean insertStatus = false;
		Connection dbConn = null;
		JSONObject destinationObject = new JSONObject();
		int i = 1;
		try {
			try {
				dbConn = DBConnention();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "SELECT * FROM destinations";
			// System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				JSONObject dest = new JSONObject();
				dest.put("from", namePlace(rs.getString("startpos")));
				dest.put("to", namePlace(rs.getString("finalpos")));

				JSONArray destArray = new JSONArray();
				destArray.put(dest);

				destinationObject.put(rs.getString("id"), destArray);

			}

		} catch (SQLException sqle) {
			// sqle.printStackTrace();
			throw sqle;
		} catch (Exception e) {
			// e.printStackTrace();
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return destinationObject;
	}

	public int insertDestination(String start, String finalpos) throws SQLException, Exception {
		boolean destinationexist = true;
		int i = 1;
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnention();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery("SELECT id FROM destinations");
			// System.out.println(rs.next());

			while (rs.next()) {

				i++;
				System.out.println("HEREEEE" + i);
			}
			rs = stmt.executeQuery("SELECT * FROM destinations WHERE startpos ='" + start + "'");
			// System.out.println(rs.next());
			while (rs.next()) {

				if (rs.getString("finalpos").equals(finalpos)) {
					System.out.println(rs.getInt("id"));
					i = rs.getInt("id");
					destinationexist = false;
					break;
				}

			}
			if (destinationexist) {
				int records = stmt.executeUpdate("INSERT into destinations(startpos, finalpos,id) values('" + start
						+ "','" + finalpos + "','" + i + "')");
			}

		} catch (SQLException sqle) {
			// sqle.printStackTrace();
			throw sqle;
		} catch (Exception e) {
			// e.printStackTrace();
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return i;
	}

	public boolean finishDistance(String startpoint, String finishpoint) {
		URLConnectionReader con = new URLConnectionReader();
		JSONArray part2;
		JSONObject part3;
		JSONArray part4;
		JSONObject part5;
		JSONObject distpart1;
		String distance;
		int dist;
		String finalurl = url + startpoint + url2 + finishpoint + key;
		System.out.println("Url" + finalurl);
		try {
			JSONObject part = con.sendGet(finalurl);
			part2 = part.getJSONArray("routes");
			part3 = part2.getJSONObject(0);
			part4 = part3.getJSONArray("legs");
			part5 = part4.getJSONObject(0);
			distpart1 = part5.getJSONObject("distance");
			distance = distpart1.getString("text");
			String[] distanceArray = distance.split(" ");
			distance = distanceArray[0];
			dist = Integer.parseInt(distance);
			if (dist <= 0.5) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public String namePlace(String point) {
		URLConnectionReader con = new URLConnectionReader();
		JSONArray part2;
		JSONObject part3;
		JSONArray part4;
		JSONObject part5;
		String distpart1 = null;
		String distance;
		int dist;
		String finalurl = url3 + point;
		System.out.println("Url" + finalurl);
		try {
			JSONObject part = con.sendGet(finalurl);
			part2 = part.getJSONArray("results");
			part3 = part2.getJSONObject(0);
			distpart1 = part3.getString("formatted_address");

			System.out.println("Address" + distpart1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return distpart1;
	}

	public ArrayList<String> selectRoute(String startPos, String finalPos, String time, String day, String id)
			throws SQLException, Exception {
		
		route = new ArrayList<>();
		Random rand = new Random();
		int max=2;
		int min=0;
		int randomNum = rand.nextInt((max - min) + 1) + min;
		
		System.out.println("FAST ROOUTE!!!");
		
		String point = startPos;
		Connection dbConn = null;
		int i = 0;
		dbConn = DBConnention();
		Statement stmt = dbConn.createStatement();
		while (true) {
			try {
				
				waypoints = new ArrayList<>();
				duration = new ArrayList<>();
				distance = new ArrayList<>();
				sortedwaypoints = new ArrayList<>();
				sortedduration = new ArrayList<>();
				sorteddistance = new ArrayList<>();
				String query = "SELECT * FROM carstats WHERE id='" + id + "'AND daytime='" + time + "' AND day='" + day
						+ "' AND startpos='" + point + "'";
				// System.out.println(query);
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {
					waypoints.add(rs.getString("finalpos"));
					duration.add(rs.getString("ourtime"));
					distance.add(rs.getString("distance"));
				}
				if(waypoints.size()==0){
					break;
				}

				

				findMax(waypoints.size());
				if(sortedwaypoints.size()>randomNum){
					route.add(sortedwaypoints.get(randomNum));
					point=sortedwaypoints.get(randomNum).toString();
				}
				else if(sortedwaypoints.size()>randomNum-1){
					route.add(sortedwaypoints.get(randomNum-1));
					point=sortedwaypoints.get(randomNum-1).toString();
				}
				else{
					route.add(sortedwaypoints.get(0));
					point=sortedwaypoints.get(0).toString();
				}
				System.out.println(point+ " randNumber =" + randomNum);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

			if (dbConn != null) {
				dbConn.close();
			}
		

		
		return route;

	}

	public void findMax(int no) {
		int k = 1;
		while (k <= no) {
			double max = Double.parseDouble(distance.get(0).toString());
			int pos = 0;

			for (int i = 1; i < waypoints.size(); i++) {
				if ( Double.parseDouble(distance.get(i).toString()) > max) {

					max = Double.parseDouble(distance.get(i).toString());
					pos = i;
				}
			}
			sortedwaypoints.add(waypoints.get(pos));

			waypoints.remove(pos);
			sortedduration.add(duration.get(pos));
			duration.remove(pos);
			sorteddistance.add(distance.get(pos));
			distance.remove(pos);

			k++;
		}
	}
	
	
	public ArrayList<String> findCor(String id) throws Exception {
		coordinates=new ArrayList<>();
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnention();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "SELECT * FROM destinations WHERE id = '" + id + "'";
			// System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				// System.out.println(rs.getString(1) + rs.getString(2) +
				// rs.getString(3));
				System.out.println("findCor");
				coordinates.add(rs.getString("startpos"));
				coordinates.add(rs.getString("finalpos"));
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return coordinates;
	}

}
