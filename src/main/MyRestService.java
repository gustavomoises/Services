//Author: Gustavo Lourenco Moises
//Thread Project PROJ-207
//Group 1
//10/12/2020


package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.Agent;

@Path("/")
public class MyRestService {
	
	//AFFILIATION--------------------------------------------------------------------------------------------------------------------------------------------
	
	// http://localhost:8080/JSPDay3RESTExample/rs/affiliation/postaffiliation/{oldAffiliationId}

	@POST
	@Path("/affiliation/postaffiliation/{affilitationId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public String postAffiliation(String jsonString, @PathParam("affilitationId") String oldAffilitationId)
	{
		JSONParser parser= new JSONParser();
		JSONObject obj; 
		String sql="UPDATE `affiliations` SET `AffilitationId`=?,`AffName`=?,`AffDesc`=? WHERE `AffilitationId`=?";
		String message = null;
		try {
			obj= (JSONObject) parser.parse(jsonString);
			Class.forName("org.mariadb.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
			PreparedStatement stmt =conn.prepareStatement(sql);
			stmt.setString(1, (String) obj.get("AffilitationId"));
			stmt.setString(2, (String) obj.get("AffName"));
			stmt.setString(3, (String) obj.get("AffDesc"));
			stmt.setString(4, (String) oldAffilitationId);

			if(stmt.executeUpdate()>0)
			{
				message="Affiliation updated successfully";
			}
			else
			{
				message="Affiliation Update failed";
			}
			conn.close();
			
			//ResultSet rs=stmt.executeQuery();
		} catch (ClassNotFoundException | SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		
		return "{ 'message':'"+message+"' }";
	}
	

	// http://localhost:8080/JSPDay3RESTExample/rs/affiliation/puttaffiliation
	@PUT
	@Path("/affiliation/puttaffiliation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public String putAffiliation(String jsonString)
	{
		JSONParser parser= new JSONParser();
		JSONObject obj; 
		String sql="INSERT INTO `affiliations`(`AffilitationId`, `AffName`, `AffDesc`) VALUES (?,?,?)";
		String message = null;
		try {
			obj= (JSONObject) parser.parse(jsonString);
			Class.forName("org.mariadb.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
			PreparedStatement stmt =conn.prepareStatement(sql);
			stmt.setString(1, (String) obj.get("AffilitationId"));
			stmt.setString(2, (String) obj.get("AffName"));
			stmt.setString(3, (String) obj.get("AffDesc"));

			if(stmt.executeUpdate()>0)
			{
				message="Affiliation inserted successfully";
			}
			else
			{
				message="Affiliation Insert failed";
			}
			conn.close();
			
			//ResultSet rs=stmt.executeQuery();
		} catch (ClassNotFoundException | SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		
		return "{ 'message':'"+message+"' }";
	}
	
	// http://localhost:8080/JSPDay3RESTExample/rs/affiliation/deleteaffiliation/APEGA
	
	@DELETE
	@Path("/affiliation/deleteaffiliation/{ affilitationId }")
	//@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.TEXT_PLAIN)
	
	public String deleteAffiliation(@PathParam("affilitationId") String affilitationId)
	{
	
		String message = "";
	
	try {

		Class.forName("org.mariadb.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
		String sql="Delete from `affiliations` where AffilitationId=?";
		PreparedStatement stmt =conn.prepareStatement(sql);
		stmt.setString(1, affilitationId);
		if(stmt.executeUpdate()>0)
		{
			message="Affiliation Deleted Successfully";
		}
		else
		{
			message="Affiliation Delete Failed.";
		}
		conn.close();
		
		
	} catch (ClassNotFoundException | SQLException  e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return message;
}
	
	// http://localhost:8080/JSPDay3RESTExample/rs/affiliation/getaffiliations
	
	@GET
	@Path("/affiliation/getaffiliations")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAffiliations()
	{
		String response =null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
			Statement stmt= conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from Affiliations");
			ResultSetMetaData rsmd = rs.getMetaData();
			
			JSONArray jsonArray = new JSONArray();
			
			while (rs.next())
			{
				JSONObject obj = new JSONObject();
				for(int i=1; i<=rsmd.getColumnCount();i++)
				{
					obj.put(rsmd.getColumnName(i), rs.getString(i));
				}
				jsonArray.add(obj);
			}
			
			response = jsonArray.toJSONString();
			conn.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
		
	}
	//AGENCY-----------------------------------------------------------------------------------------------------------------------------------------------
	//Regions
	// http://localhost:8080/JSPDay3RESTExample/rs/agency/getagencies
	
	@GET
	@Path("/agency/getagencies")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAgencies()
	{
		String response =null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
			Statement stmt= conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from Agencies");
			ResultSetMetaData rsmd = rs.getMetaData();
			
			JSONArray jsonArray = new JSONArray();
			
			while (rs.next())
			{
				JSONObject obj = new JSONObject();
				for(int i=1; i<=rsmd.getColumnCount();i++)
				{
					obj.put(rsmd.getColumnName(i), rs.getString(i));
				}
				jsonArray.add(obj);
			}
			
			response = jsonArray.toJSONString();
			conn.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
		
	}
	
	//AGENT -----------------------------------------------------------------------------------------------------------------------------------------------
	
	
	// http://localhost:8080/JSPDay3RESTExample/rs/agent/getagents
	@GET
	@Path("/agent/getagents")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAgents()
	{
		String response =null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
			Statement stmt= conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from Agents");
			ResultSetMetaData rsmd = rs.getMetaData();
			
			JSONArray jsonArray = new JSONArray();
			
			while (rs.next())
			{
				JSONObject obj = new JSONObject();
				for(int i=1; i<=rsmd.getColumnCount();i++)
				{
					obj.put(rsmd.getColumnName(i), rs.getString(i));
				}
				jsonArray.add(obj);
			}
			
			response = jsonArray.toJSONString();
			conn.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
		
	}
	
	// http://localhost:8080/JSPDay3RESTExample/rs/agent/getagent/{agentId}
		@GET
		@Path("/agent/getagent/{ agentId }")
		@Produces(MediaType.APPLICATION_JSON)
		public String getAgent(@PathParam("agentId") int agentId)
		{
			String response =null;
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				String sql="select * from Agents where AgentId=?";
				PreparedStatement stmt =conn.prepareStatement(sql);
				stmt.setInt(1,agentId);
				ResultSet rs=stmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				JSONObject obj = new JSONObject();
				if(rs.next())
				{
					for(int i=1; i<=rsmd.getColumnCount();i++)
					{
						obj.put(rsmd.getColumnName(i), rs.getString(i));
					}
				}
				
				response = obj.toJSONString();
				conn.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
			
		}
		
		// http://localhost:8080/JSPDay3RESTExample/rs/agent/putagent
		@PUT
		@Path("/agent/putagent")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces(MediaType.TEXT_PLAIN)
		
		public String putAgent(String jsonString)
		{
			JSONParser parser= new JSONParser();
			JSONObject obj; 
			String sql="INSERT INTO `agents`(`AgtFirstName`, `AgtMiddleInitial`, `AgtLastName`, `AgtBusPhone`, `AgtEmail`, `AgtPosition`, `AgencyId`, `userid`, `password`) VALUES (?,?,?,?,?,?,?,?,?)";
			String message = null;
			try {
				obj= (JSONObject) parser.parse(jsonString);
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				PreparedStatement stmt =conn.prepareStatement(sql);
				stmt.setString(1, (String) obj.get("AgtFirstName"));
				stmt.setString(2, (String) obj.get("AgtMiddleInitial"));
				stmt.setString(3, (String) obj.get("AgtLastName"));
				stmt.setString(4, (String) obj.get("AgtBusPhone"));
				stmt.setString(5, (String) obj.get("AgtEmail"));
				stmt.setString(6, (String) obj.get("AgtPosition"));
				stmt.setInt(7, Integer.parseInt((String) obj.get("AgencyId")));
				stmt.setString(8, (String) obj.get("userid"));
				stmt.setString(9, (String) obj.get("password"));
				if(stmt.executeUpdate()>0)
				{
					message="Agent inserted successfully";
				}
				else
				{
					message="Insert failed";
				}
				conn.close();
				
				//ResultSet rs=stmt.executeQuery();
			} catch (ClassNotFoundException | SQLException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			
			
			return message;
		}
		
		// http://localhost:8080/JSPDay3RESTExample/rs/updateagent
		@PUT
		@Path("/agent/updateagent")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces(MediaType.TEXT_PLAIN)
		
		public String updateAgent(String jsonString)
		{
			JSONParser parser= new JSONParser();
			JSONObject obj; 
			String sql="UPDATE `agents` SET `AgtFirstName`=?, `AgtMiddleInitial`=?, `AgtLastName`=?, `AgtBusPhone`=?, `AgtEmail`=?, `AgtPosition`=?, `AgencyId`=?, `userid`=?, `password`=? WHERE `AgentId`=?";
			String message = null;
			try {
				obj= (JSONObject) parser.parse(jsonString);
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				PreparedStatement stmt =conn.prepareStatement(sql);
				stmt.setString(1, (String) obj.get("AgtFirstName"));
				stmt.setString(2, (String) obj.get("AgtMiddleInitial"));
				stmt.setString(3, (String) obj.get("AgtLastName"));
				stmt.setString(4, (String) obj.get("AgtBusPhone"));
				stmt.setString(5, (String) obj.get("AgtEmail"));
				stmt.setString(6, (String) obj.get("AgtPosition"));
				stmt.setInt(7, Integer.parseInt((String)obj.get("AgencyId")));
				stmt.setString(8, (String) obj.get("userid"));
				stmt.setString(9, (String) obj.get("password"));
				stmt.setInt(10, Integer.parseInt((String)obj.get("AgentId")));

				if(stmt.executeUpdate()>0)
				{
					message="Agent updated successfully";
				}
				else
				{
					message="Agent Update failed";
				}
				conn.close();
				
				//ResultSet rs=stmt.executeQuery();
			} catch (ClassNotFoundException | SQLException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			
			
			return message;
		}
		
		
		
		// http://localhost:8080/JSPDay3RESTExample/rs/agent/deleteagent/{ agentId }
		@DELETE
		@Path("/agent/deleteagent/{ agentId }")
		//@Consumes({MediaType.APPLICATION_JSON})
		@Produces(MediaType.TEXT_PLAIN)
		
		public String deleteAgent(@PathParam("agentId") int agentId)
		{
		
			String message = "";
		
		try {

			Class.forName("org.mariadb.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
			String sql="Delete from `agents` where AgentId=?";
			PreparedStatement stmt =conn.prepareStatement(sql);
			stmt.setInt(1, agentId);
			if(stmt.executeUpdate()>0)
			{
				message="Agent Deleted Successfully";
			}
			else
			{
				message="Delete failed.";
			}
			conn.close();
			
			
		} catch (ClassNotFoundException | SQLException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return message;
}
		

//BOOKINGS 
		
		//Update Booking in the database
		// http://localhost:8080/JSPDay3RESTExample/rs/booking/postbooking
				
		@POST
		@Path("/booking/postbooking")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		
		public String postBooking(String jsonString)
		
		{
			Double tc;
			Integer cust,pckg;
			JSONParser parser= new JSONParser();
			JSONObject obj; 
			String sql="UPDATE `bookings` SET `BookingDate`=?, `BookingNo`=?, `TripTypeId`=?,`TravelerCount`=?, `CustomerId`=?, `PackageId`=? WHERE `BookingId`=?";
			String message = null;
			try {
				obj= (JSONObject) parser.parse(jsonString);
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				PreparedStatement stmt =conn.prepareStatement(sql);
				stmt.setString(1, (String) obj.get("BookingDate"));
				stmt.setString(2, (String) obj.get("BookingNo"));
				stmt.setString(3, null);
				try {
					Connection conn1 = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
					Statement stmt1= conn1.createStatement();
					ResultSet rs1 = stmt1.executeQuery("Select triptypeid from triptypes");
	
					ArrayList<String> trip = new ArrayList<>();
					int k=0;
					while (rs1.next())
					{
						trip.add(rs1.getString(1));
						k++;
					}
				
					conn1.close();
					String tript=(String) obj.get("TripTypeId");
					
					for (int i=0; i<k; i++)
					{
						if(tript==trip.get(i))
						{
							stmt.setString(3, (String) obj.get("TripTypeId"));
							break;
						}
					}
					
				} catch ( SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if ((String)obj.get("TravelerCount")==null)
					stmt.setString(4, (String)obj.get("TravelerCount"));
				else
					try {
						tc = Double.parseDouble((String)obj.get("TravelerCount"));
						stmt.setDouble(4, tc);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						stmt.setString(4, null);
					}
					
				if((String)obj.get("CustomerId")==null)
					stmt.setString(5, (String)obj.get("CustomerId"));
				else
					try {
						cust = Integer.parseInt((String)obj.get("CustomerId"));
						stmt.setInt(5,cust);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						stmt.setString(5, null);
					}
					
				if((String)obj.get("PackageId")==null)
					stmt.setString(6, (String)obj.get("PackageId"));
				else
					try {
						pckg=Integer.parseInt((String)obj.get("PackageId"));
						stmt.setInt(6,pckg );
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						stmt.setString(6, null);
					}
					
				stmt.setInt(7, Integer.parseInt((String)obj.get("BookingId")));

				if(stmt.executeUpdate()>0)
				{
					message="Booking updated successfully";
				}
				else
				{
					message="Booking Update failed";
				}
				conn.close();
				
				//ResultSet rs=stmt.executeQuery();
			} catch (ClassNotFoundException | SQLException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			
			
			return "{ 'message':'" + message + "' }";
		}

		//Insert Booking in the database
		// http://localhost:8080/JSPDay3RESTExample/rs/booking/putbooking
		@PUT
		@Path("/booking/putbooking")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		
		public String putBooking(String jsonString)
		{
			Double tc;
			Integer cust,pckg;
			JSONParser parser= new JSONParser();
			JSONObject obj; 
			String sql="INSERT INTO `bookings`(`BookingDate`, `BookingNo`, `TripTypeId`,`TravelerCount`, `CustomerId`, `PackageId`) VALUES (?,?,?,?,?,?)";
			String message = null;
			try {
				obj= (JSONObject) parser.parse(jsonString);
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				PreparedStatement stmt =conn.prepareStatement(sql);
				stmt.setString(1, (String) obj.get("BookingDate"));
				stmt.setString(2, (String) obj.get("BookingNo"));
				stmt.setString(3,null);
				try {
					Connection conn1 = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
					Statement stmt1= conn1.createStatement();
					ResultSet rs1 = stmt1.executeQuery("Select triptypeid from triptypes");
	
					ArrayList<String> trip = new ArrayList<>();
					int k=0;
					while (rs1.next())
					{
						trip.add(rs1.getString(1));
						k++;
					}
				
					conn1.close();
					String tript=(String) obj.get("TripTypeId");
					
					for (int i=0; i<k; i++)
					{
						if(tript==trip.get(i))
						{
							stmt.setString(3, (String) obj.get("TripTypeId"));
							break;
						}
					}
					
				} catch ( SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if ((String)obj.get("TravelerCount")==null)
					stmt.setString(4, (String)obj.get("TravelerCount"));
				else
					try {
						tc = Double.parseDouble((String)obj.get("TravelerCount"));
						
						stmt.setDouble(4, tc);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						stmt.setString(4, null);
					}
					
				if((String)obj.get("CustomerId")==null)
					stmt.setString(5, (String)obj.get("CustomerId"));
				else
					try {
						cust = Integer.parseInt((String)obj.get("CustomerId"));
						if(cust==0)
							stmt.setString(5, null);
						else
							stmt.setInt(5,cust);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						stmt.setString(5, null);
					}
					
				if((String)obj.get("PackageId")==null)
					stmt.setString(6, (String)obj.get("PackageId"));
				else
					try {
						pckg=Integer.parseInt((String)obj.get("PackageId"));
						if (pckg==0)
							stmt.setString(6,null );
						else
						stmt.setInt(6,pckg );
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						stmt.setString(6, null);
					}
				if(stmt.executeUpdate()>0)
				{
					message="Booking inserted successfully";
				}
				else
				{
					message="Booking failed";
				}
				conn.close();
				
				//ResultSet rs=stmt.executeQuery();
			} catch (ClassNotFoundException | SQLException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			return "{ 'message':'" + message + "' }";
		}	
	
		//Find BookingId by BookingNo
		// http://localhost:8080/JSPDay3RESTExample/rs/booking/findBookingIdByBookingNo/{ bookingNo }
				@GET
				@Path("/booking/findBookingIdByBookingNo/{ bookingNo }")
				@Produces(MediaType.TEXT_PLAIN)
				public String findBookingIdByBookingNo(@PathParam("bookingNo") String bookingNo)
				{
					String response ="";
					try {
						Class.forName("org.mariadb.jdbc.Driver");
						Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
						String sql="SELECT BookingId FROM Bookings WHERE BookingNo=?";
						PreparedStatement stmt =conn.prepareStatement(sql);
						stmt.setString(1,bookingNo);
						ResultSet rs=stmt.executeQuery();
						if(rs.next())
						{
							response =rs.getString(1);
						}
						conn.close();
						
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
					
					return response;
					
				}		
				
		//Get all Bookings from database
		// http://localhost:8080/JSPDay3RESTExample/rs/booking/getbookings
		
		@GET
		@Path("/booking/getbookings")
		@Produces(MediaType.APPLICATION_JSON)
		public String getBookings()
		{
			String response =null;
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				Statement stmt= conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from Bookings");
				ResultSetMetaData rsmd = rs.getMetaData();
				
				JSONArray jsonArray = new JSONArray();
				
				while (rs.next())
				{
					JSONObject obj = new JSONObject();
					for(int i=1; i<=rsmd.getColumnCount();i++)
					{
						obj.put(rsmd.getColumnName(i), rs.getString(i));
					}
					jsonArray.add(obj);
				}
				
				response = jsonArray.toJSONString();
				conn.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
			
		}
		
//BOOKING DETAIL
		
		//Insert Booking in the database
		// http://localhost:8080/JSPDay3RESTExample/rs/bookingdetail/putbookingdetail
				
								
				@PUT
				@Path("/bookingdetail/putbookingdetail")
				@Consumes(MediaType.APPLICATION_JSON)
				@Produces(MediaType.APPLICATION_JSON)
				
				public String putBookingDetail(String jsonString)
				
				{
					Double tc;
					Integer cust;
					JSONParser parser= new JSONParser();
					JSONObject obj; 
					String sql="INSERT INTO `BookingDetails` (`TripStart`, `TripEnd`, `Description`,`Destination`, `ItineraryNo`,`BasePrice`, `AgencyCommission`,`RegionId`,`ClassId`,`FeeId`,`ProductSupplierId`,`BookingId`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
					String message = null;
					try {
						obj= (JSONObject) parser.parse(jsonString);
						Class.forName("org.mariadb.jdbc.Driver");
						Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
						PreparedStatement stmt =conn.prepareStatement(sql);
						stmt.setString(1, (String) obj.get("TripStart"));
						stmt.setString(2, (String) obj.get("TripEnd"));
						stmt.setString(3, (String) obj.get("Description"));
						stmt.setString(4, (String) obj.get("Destination"));
						
						if ((String)obj.get("ItineraryNo")==null)
							stmt.setString(5, (String)obj.get("ItineraryNo"));
						else
							try {
								tc = Double.parseDouble((String)obj.get("ItineraryNo"));
								stmt.setDouble(5, tc);
							} catch (NumberFormatException e) {
								// TODO Auto-generated catch block
								stmt.setString(5, null);
							}

			
						
						
						if ((String)obj.get("BasePrice")==null)
							stmt.setString(6, (String)obj.get("BasePrice"));
						else
							try {
								tc = Double.parseDouble((String)obj.get("BasePrice"));
								stmt.setDouble(6, tc);
							} catch (NumberFormatException e) {
								// TODO Auto-generated catch block
								stmt.setString(6, null);
							}
						if ((String)obj.get("AgencyCommission")==null)
							stmt.setString(7, (String)obj.get("AgencyCommission"));
						else
							try {
								tc = Double.parseDouble((String)obj.get("AgencyCommission"));
								stmt.setDouble(7, tc);
							} catch (NumberFormatException e) {
								// TODO Auto-generated catch block
								stmt.setString(7, null);
							}
						
						
						String reg =(String) obj.get("RegionId");
						if (reg.equals(""))
							stmt.setString(8, null);
						else
						stmt.setString(8, (String) obj.get("RegionId"));
						
						String cla =(String) obj.get("ClassId");
						if (cla.equals(""))
							stmt.setString(9, null);
						else
						stmt.setString(9, (String) obj.get("ClassId"));
						
						String fee =(String) obj.get("FeeId");
						if (fee.equals(""))
							stmt.setString(10, null);
						else
						stmt.setString(10, (String) obj.get("FeeId"));
						

							
						if((String)obj.get("ProductSupplierId")==null)
							stmt.setString(11, (String)obj.get("ProductSupplierId"));
						else
							try {
								cust = Integer.parseInt((String)obj.get("ProductSupplierId"));
								if (cust==0)
									stmt.setString(11, null);
								else
								stmt.setInt(11,cust);
							} catch (NumberFormatException e) {
								// TODO Auto-generated catch block
								stmt.setString(11, null);
							}
						if((String)obj.get("BookingId")==null)
							stmt.setString(12, (String)obj.get("BookingId"));
						else
							try {
								cust = Integer.parseInt((String)obj.get("BookingId"));
								stmt.setInt(12,cust);
							} catch (NumberFormatException e) {
								// TODO Auto-generated catch block
								stmt.setString(12, null);
							}


						if(stmt.executeUpdate()>0)
						{
							message="Booking Detail inserted successfully";
						}
						else
						{
							message="Booking Detail Insert failed";
						}
						conn.close();
						
						//ResultSet rs=stmt.executeQuery();
					} catch (ClassNotFoundException | SQLException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
							
					
					
					return "{ 'message':'" + message + "' }";
				}		
				
		
		
		
		
		
		
		//Update Booking in the database
		// http://localhost:8080/JSPDay3RESTExample/rs/bookingdetail/postbookingdetail
		
						
		@POST
		@Path("/bookingdetail/postbookingdetail")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		
		public String postBookingDetail(String jsonString)
		
		{
			Double tc;
			Integer cust;
			JSONParser parser= new JSONParser();
			JSONObject obj; 
			String sql="UPDATE `BookingDetails` SET `TripStart`=?, `TripEnd`=?, `Description`=?,`Destination`=?, `ItineraryNo`=?,`BasePrice`=?, `AgencyCommission`=?,`RegionId`=?,`ClassId`=?,`FeeId`=?,`ProductSupplierId`=?, `BookingId`=? WHERE `BookingDetailId`=?";
			String message = null;
			try {
				obj= (JSONObject) parser.parse(jsonString);
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				PreparedStatement stmt =conn.prepareStatement(sql);
				stmt.setString(1, (String) obj.get("TripStart"));
				stmt.setString(2, (String) obj.get("TripEnd"));
				stmt.setString(3, (String) obj.get("Description"));
				stmt.setString(4, (String) obj.get("Destination"));
				
				if ((String)obj.get("ItineraryNo")==null)
					stmt.setString(5, (String)obj.get("ItineraryNo"));
				else
					try {
						tc = Double.parseDouble((String)obj.get("ItineraryNo"));
						stmt.setDouble(5, tc);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						stmt.setString(5, null);
					}

	
				
				
				if ((String)obj.get("BasePrice")==null)
					stmt.setString(6, (String)obj.get("BasePrice"));
				else
					try {
						tc = Double.parseDouble((String)obj.get("BasePrice"));
						stmt.setDouble(6, tc);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						stmt.setString(6, null);
					}
				if ((String)obj.get("AgencyCommission")==null)
					stmt.setString(7, (String)obj.get("AgencyCommission"));
				else
					try {
						tc = Double.parseDouble((String)obj.get("AgencyCommission"));
						stmt.setDouble(7, tc);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						stmt.setString(7, null);
					}
				
				String reg =(String) obj.get("RegionId");
				if (reg.equals(""))
					stmt.setString(8, null);
				else
				stmt.setString(8, (String) obj.get("RegionId"));
				
				String cla =(String) obj.get("ClassId");
				if (cla.equals(""))
					stmt.setString(9, null);
				else
				stmt.setString(9, (String) obj.get("ClassId"));
				
				String fee =(String) obj.get("FeeId");
				if (fee.equals(""))
					stmt.setString(10, null);
				else
				stmt.setString(10, (String) obj.get("FeeId"));
				
				
					
				if((String)obj.get("ProductSupplierId")==null)
					stmt.setString(11, (String)obj.get("ProductSupplierId"));
				else
					try {
						cust = Integer.parseInt((String)obj.get("ProductSupplierId"));
						if (cust==0)
							stmt.setString(11, null);
						else
						stmt.setInt(11,cust);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						stmt.setString(11, null);
					}
				if((String)obj.get("BookingId")==null)
					stmt.setString(12, (String)obj.get("BookingId"));
				else
					try {
						cust = Integer.parseInt((String)obj.get("BookingId"));
						stmt.setInt(12,cust);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						stmt.setString(12, null);
					}

					
				stmt.setInt(13, Integer.parseInt((String)obj.get("BookingDetailId")));

				if(stmt.executeUpdate()>0)
				{
					message="Booking Detail updated successfully";
				}
				else
				{
					message="Booking Detail Update failed";
				}
				conn.close();
				
				//ResultSet rs=stmt.executeQuery();
			} catch (ClassNotFoundException | SQLException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			
			
			return "{ 'message':'" + message + "' }";
		}		
		

		 //Get List of Booking Details by BookingId
		// http://localhost:8080/JSPDay3RESTExample/rs/bookingdetail/getbookingdetailsbybookingid/{bookingId}
				
				@GET
				@Path("/bookingdetail/getbookingdetailsbybookingid/{bookingId}")
				@Produces(MediaType.APPLICATION_JSON)
				public String getBookingDetailByBookingId(@PathParam("bookingId") int bookingId)
				{
					String response ="";
					try {
						Class.forName("org.mariadb.jdbc.Driver");
						Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
						String sql="Select * from BookingDetails where BookingId=? ";
						PreparedStatement stmt =conn.prepareStatement(sql);
						stmt.setInt(1,bookingId);
						ResultSet rs=stmt.executeQuery();
						ResultSetMetaData rsmd = rs.getMetaData();

						JSONArray jsonArray = new JSONArray();
						
						while (rs.next())
						{
							JSONObject obj = new JSONObject();
							for(int i=1; i<=rsmd.getColumnCount();i++)
							{
								obj.put(rsmd.getColumnName(i), rs.getString(i));
							}
							jsonArray.add(obj);
						}
						
						response = jsonArray.toJSONString();
						conn.close();
						
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return response;
					
				}
		
				
				// http://localhost:8080/JSPDay3RESTExample/rs/bookingdetail/deletebookingdetail/{bookingdetailId}
				@DELETE
				@Path("/bookingdetail/deletebookingdetail/{bookingdetailid}")
				//@Consumes({MediaType.APPLICATION_JSON})
				@Produces(MediaType.TEXT_PLAIN)
				
				public String deleteBookingDetail(@PathParam("bookingdetailid") int bookingDetailId)
				{
				
					String message = "";
				
				try {

					Class.forName("org.mariadb.jdbc.Driver");
					Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
					String sql="Delete from `BookingDetails` where `bookingdetailId`=?";
					PreparedStatement stmt =conn.prepareStatement(sql);
					stmt.setInt(1, bookingDetailId);
					if(stmt.executeUpdate()>0)
					{
						message="Booking Detail Deleted Successfully";
					}
					else
					{
						message="Booking Detail Delete failed.";
					}
					conn.close();
					
					
				} catch (ClassNotFoundException | SQLException  e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return message;
		}
		
		
		
//CLASS------------------------------------------------------------------------------------------------------
				
				// http://localhost:8080/JSPDay3RESTExample/rs/class/postclass/{oldBookClassId}

				@POST
				@Path("/class/postclass/{bookClassId}")
				@Consumes(MediaType.APPLICATION_JSON)
				@Produces(MediaType.APPLICATION_JSON)
				
				public String postClass(String jsonString, @PathParam("bookClassId") String oldBookClassId)
				{
					JSONParser parser= new JSONParser();
					JSONObject obj; 
					String sql="UPDATE `bookClasses` SET `ClassId`=?,`ClassName`=?,`ClassDesc`=? WHERE `ClassId`=?";
					String message = null;
					try {
						obj= (JSONObject) parser.parse(jsonString);
						Class.forName("org.mariadb.jdbc.Driver");
						Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
						PreparedStatement stmt =conn.prepareStatement(sql);
						stmt.setString(1, (String) obj.get("ClassId"));
						stmt.setString(2, (String) obj.get("ClassName"));
						stmt.setString(3, (String) obj.get("ClassDesc"));
						stmt.setString(4, (String) oldBookClassId);

						if(stmt.executeUpdate()>0)
						{
							message="Class updated successfully";
						}
						else
						{
							message="Class Update failed";
						}
						conn.close();
					} catch (ClassNotFoundException | SQLException | ParseException e) {
						e.printStackTrace();
					}
					return "{ 'message':'" + message + "' }";
				}
				

				// http://localhost:8080/JSPDay3RESTExample/rs/class/putclass
				@PUT
				@Path("/class/putclass")
				@Consumes(MediaType.APPLICATION_JSON)
				@Produces(MediaType.APPLICATION_JSON)
				
				public String putAClass(String jsonString)
				{
					JSONParser parser= new JSONParser();
					JSONObject obj; 
					String sql="INSERT INTO `bookClasses`(`ClassId`, `ClassName`, `ClassDesc`) VALUES (?,?,?)";
					String message = null;
					try {
						obj= (JSONObject) parser.parse(jsonString);
						Class.forName("org.mariadb.jdbc.Driver");
						Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
						PreparedStatement stmt =conn.prepareStatement(sql);
						stmt.setString(1, (String) obj.get("ClassId"));
						stmt.setString(2, (String) obj.get("ClassName"));
						stmt.setString(3, (String) obj.get("ClassDesc"));

						if(stmt.executeUpdate()>0)
						{
							message="Class inserted successfully";
						}
						else
						{
							message="Class Insert failed";
						}
						conn.close();
					} catch (ClassNotFoundException | SQLException | ParseException e) {
						e.printStackTrace();
					}
					return "{ 'message':'" + message + "' }";
				}
				
				// http://localhost:8080/JSPDay3RESTExample/rs/class/deleteclass/{ bookclassId }
				
				@DELETE
				@Path("/class/deleteclass/{ bookclassId }")
				//@Consumes({MediaType.APPLICATION_JSON})
				@Produces(MediaType.TEXT_PLAIN)
				
				public String deleteClass(@PathParam("bookclassId") String bookClassId)
				{
				
					String message = "";
				
				try {

					Class.forName("org.mariadb.jdbc.Driver");
					Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
					String sql="Delete from `bookClasses` where ClassId=?";
					PreparedStatement stmt =conn.prepareStatement(sql);
					stmt.setString(1, bookClassId);
					if(stmt.executeUpdate()>0)
					{
						message="Class Deleted Successfully";
					}
					else
					{
						message="Class Delete Failed.";
					}
					conn.close();
					
					
				} catch (ClassNotFoundException | SQLException  e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return message;
			}		
				
				
				

		// http://localhost:8080/JSPDay3RESTExample/rs/class/getclasses
		
		@GET
		@Path("/class/getclasses")
		@Produces(MediaType.APPLICATION_JSON)
		public String getClasses()
		{
			String response =null;
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				Statement stmt= conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from BookClasses");
				ResultSetMetaData rsmd = rs.getMetaData();
				
				JSONArray jsonArray = new JSONArray();
				
				while (rs.next())
				{
					JSONObject obj = new JSONObject();
					for(int i=1; i<=rsmd.getColumnCount();i++)
					{
						obj.put(rsmd.getColumnName(i), rs.getString(i));
					}
					jsonArray.add(obj);
				}
				
				response = jsonArray.toJSONString();
				conn.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
			
		}
		
//CUSTOMER
		

		// http://localhost:8080/JSPDay3RESTExample/rs/customer/getcustomers
		
		@GET
		@Path("/customer/getcustomers")
		@Produces(MediaType.APPLICATION_JSON)
		public String getCustomers()
		{
			String response =null;
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				Statement stmt= conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from Customers");
				ResultSetMetaData rsmd = rs.getMetaData();
				
				JSONArray jsonArray = new JSONArray();
				
				while (rs.next())
				{
					JSONObject obj = new JSONObject();
					for(int i=1; i<=rsmd.getColumnCount();i++)
					{
						obj.put(rsmd.getColumnName(i), rs.getString(i));
					}
					jsonArray.add(obj);
				}
				
				response = jsonArray.toJSONString();
				conn.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
			
		}
		
		// http://localhost:8080/JSPDay3RESTExample/rs/customer/getcustomer/{ customerId }
				@GET
				@Path("/customer/getcustomer/{ customerId }")
				@Produces(MediaType.APPLICATION_JSON)
				public String getCustomer(@PathParam("customerId") int customerId)
				{
					String response =null;
					try {
						Class.forName("org.mariadb.jdbc.Driver");
						Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
						String sql="select * from Customers where CustomerId=?";
						PreparedStatement stmt =conn.prepareStatement(sql);
						stmt.setInt(1,customerId);
						ResultSet rs=stmt.executeQuery();
						ResultSetMetaData rsmd = rs.getMetaData();
						JSONObject obj = new JSONObject();
						if(rs.next())
						{
							for(int i=1; i<=rsmd.getColumnCount();i++)
							{
								obj.put(rsmd.getColumnName(i), rs.getString(i));
							}
						}
						
						response = obj.toJSONString();
						conn.close();
						
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return response;
					
				}
		
		
//FEE--------------------------------------------------------------------------------------------------------
		
		
		// http://localhost:8080/JSPDay3RESTExample/rs/fee/getfees
		
		@GET
		@Path("/fee/getfees")
		@Produces(MediaType.APPLICATION_JSON)
		public String getFees()
		{
			String response =null;
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				Statement stmt= conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from Fees");
				ResultSetMetaData rsmd = rs.getMetaData();
				
				JSONArray jsonArray = new JSONArray();
				
				while (rs.next())
				{
					JSONObject obj = new JSONObject();
					for(int i=1; i<=rsmd.getColumnCount();i++)
					{
						obj.put(rsmd.getColumnName(i), rs.getString(i));
					}
					jsonArray.add(obj);
				}
				
				response = jsonArray.toJSONString();
				conn.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
			
		}
// End of FEE section 
//PACKAGE-------------------------------------------------------------------------------------------------------------------
		

		// http://localhost:8080/JSPDay3RESTExample/rs/package/getpackages
		
		@GET
		@Path("/package/getpackages")
		@Produces(MediaType.APPLICATION_JSON)
		public String getPackages()
		{
			String response =null;
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				Statement stmt= conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from ProdPackages");
				ResultSetMetaData rsmd = rs.getMetaData();
				
				JSONArray jsonArray = new JSONArray();
				
				while (rs.next())
				{
					JSONObject obj = new JSONObject();
					for(int i=1; i<=rsmd.getColumnCount();i++)
					{
						obj.put(rsmd.getColumnName(i), rs.getString(i));
					}
					jsonArray.add(obj);
				}
				
				response = jsonArray.toJSONString();
				conn.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
			
		}
		
		
		// http://localhost:8080/JSPDay3RESTExample/rs/package/getpackage/{ packageId }
		@GET
		@Path("/package/getpackage/{ packageId }")
		@Produces(MediaType.APPLICATION_JSON)
		public String getPackage(@PathParam("packageId") int packageId)
		{
			String response =null;
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				String sql="select * from ProdPackages where PackageId=?";
				PreparedStatement stmt =conn.prepareStatement(sql);
				stmt.setInt(1,packageId);
				ResultSet rs=stmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				JSONObject obj = new JSONObject();
				if(rs.next())
				{
					for(int i=1; i<=rsmd.getColumnCount();i++)
					{
						obj.put(rsmd.getColumnName(i), rs.getString(i));
					}
				}
				
				response = obj.toJSONString();
				conn.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
			
		}
		
//PRODUCT
		
		// http://localhost:8080/JSPDay3RESTExample/rs/product/getproduct/{ productId }
				@GET
				@Path("/product/getproduct/{ productId }")
				@Produces(MediaType.APPLICATION_JSON)
				public String getProduct(@PathParam("productId") int productId)
				{
					String response =null;
					try {
						Class.forName("org.mariadb.jdbc.Driver");
						Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
						String sql="select * from Products where ProductId=?";
						PreparedStatement stmt =conn.prepareStatement(sql);
						stmt.setInt(1,productId);
						ResultSet rs=stmt.executeQuery();
						ResultSetMetaData rsmd = rs.getMetaData();
						JSONObject obj = new JSONObject();
						if(rs.next())
						{
							for(int i=1; i<=rsmd.getColumnCount();i++)
							{
								obj.put(rsmd.getColumnName(i), rs.getString(i));
							}
						}
						
						response = obj.toJSONString();
						conn.close();
						
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return response;
					
				}
				
				// http://localhost:8080/JSPDay3RESTExample/rs/product/putproduct
				@PUT
				@Path("/product/putproduct")
				@Consumes(MediaType.APPLICATION_JSON)
				@Produces(MediaType.APPLICATION_JSON)
				
				public String putProduct(String jsonString)
				{
					JSONParser parser= new JSONParser();
					JSONObject obj; 
					String sql="INSERT INTO `products`(`ProdName`) VALUES (?)";
					String message = null;
					try {
						obj= (JSONObject) parser.parse(jsonString);
						Class.forName("org.mariadb.jdbc.Driver");
						Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
						PreparedStatement stmt =conn.prepareStatement(sql);
						stmt.setString(1, (String) obj.get("ProdName"));
						if(stmt.executeUpdate()>0)
						{
							message="Product inserted successfully";
						}
						else
						{
							message=" Product Insert failed";
						}
						conn.close();
					} catch (ClassNotFoundException | SQLException | ParseException e) {
						e.printStackTrace();
					}
					return "{ 'message':'" + message + "' }";
				}
				
				// http://localhost:8080/JSPDay3RESTExample/rs/product/postproduct
				@POST
				@Path("/product/postproduct")
				@Consumes(MediaType.APPLICATION_JSON)
				@Produces(MediaType.APPLICATION_JSON)
				
				public String updateProduct(String jsonString)
				{
					JSONParser parser= new JSONParser();
					JSONObject obj; 
					String sql="UPDATE `products` SET `ProdName`=? WHERE `ProductId`=?";
					String message = null;
					try {
						obj= (JSONObject) parser.parse(jsonString);
						Class.forName("org.mariadb.jdbc.Driver");
						Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
						PreparedStatement stmt =conn.prepareStatement(sql);
						stmt.setString(1, (String) obj.get("ProdName"));
						stmt.setInt(2, Integer.parseInt((String)obj.get("ProductId")));

						if(stmt.executeUpdate()>0)
						{
							message="Product updated successfully";
						}
						else
						{
							message="Product Update failed";
						}
						conn.close();
					} catch (ClassNotFoundException | SQLException | ParseException e) {
						e.printStackTrace();
					}
					return "{ 'message':'" + message + "' }";
				}
				
				
				
				// http://localhost:8080/JSPDay3RESTExample/rs/product/deleteproduct/{ productId }
				@DELETE
				@Path("/product/deleteproduct/{ productId }")
				//@Consumes({MediaType.APPLICATION_JSON})
				@Produces(MediaType.TEXT_PLAIN)
				
				public String deleteProduct(@PathParam("productId") int productId)
				{
				
					String message = "";
				
				try {

					Class.forName("org.mariadb.jdbc.Driver");
					Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
					String sql="Delete from `products` where ProductId=?";
					PreparedStatement stmt =conn.prepareStatement(sql);
					stmt.setInt(1, productId);
					if(stmt.executeUpdate()>0)
					{
						message="Product Deleted Successfully";
					}
					else
					{
						message="Product Delete failed.";
					}
					conn.close();
					
					
				} catch (ClassNotFoundException | SQLException  e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return message;
		}
		
		
				// http://localhost:8080/JSPDay3RESTExample/rs/product/getproductbypsid/{ productSupplierId }
				@GET
				@Path("/product/getproductbypsid/{ productSupplierId }")
				@Produces(MediaType.APPLICATION_JSON)
				public String getProductByPSId(@PathParam("productSupplierId") int productSupplierId)
				{
					String response =null;
					try {
						Class.forName("org.mariadb.jdbc.Driver");
						Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
						String sql="SELECT DISTINCT b.ProductId, b.ProdName FROM Products_suppliers a INNER JOIN Products  b ON a.ProductId=b.ProductId WHERE a.ProductSupplierId=? ORDER BY b.ProdName";
						PreparedStatement stmt =conn.prepareStatement(sql);
						stmt.setInt(1, productSupplierId);
						ResultSet rs=stmt.executeQuery();
						ResultSetMetaData rsmd = rs.getMetaData();
						JSONObject obj = new JSONObject();
						if(rs.next())
						{
							for(int i=1; i<=rsmd.getColumnCount();i++)
							{
								obj.put(rsmd.getColumnName(i), rs.getString(i));
							}
						}
						
						response = obj.toJSONString();
						conn.close();
						
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return response;
					
				}
				
		
		

		// http://localhost:8080/JSPDay3RESTExample/rs/product/getproducts
		
		@GET
		@Path("/product/getproducts")
		@Produces(MediaType.APPLICATION_JSON)
		public String getProducts()
		{
			String response =null;
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				Statement stmt= conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from Products");
				ResultSetMetaData rsmd = rs.getMetaData();
				
				JSONArray jsonArray = new JSONArray();
				
				while (rs.next())
				{
					JSONObject obj = new JSONObject();
					for(int i=1; i<=rsmd.getColumnCount();i++)
					{
						obj.put(rsmd.getColumnName(i), rs.getString(i));
					}
					jsonArray.add(obj);
				}
				
				response = jsonArray.toJSONString();
				conn.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
			
		}
		
		
		
		// http://localhost:8080/JSPDay3RESTExample/rs/product/getproductswithsuppliers
		
		@GET
		@Path("/product/getproductswithsuppliers")
		@Produces(MediaType.APPLICATION_JSON)
		public String getProductsWithSuppliers()
		{
			String response =null;
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				Statement stmt= conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT DISTINCT b.ProductId, b.ProdName FROM Products_suppliers a INNER JOIN Products  b ON a.ProductId=b.ProductId ORDER BY b.ProdName");
				ResultSetMetaData rsmd = rs.getMetaData();
				
				JSONArray jsonArray = new JSONArray();
				
				while (rs.next())
				{
					JSONObject obj = new JSONObject();
					for(int i=1; i<=rsmd.getColumnCount();i++)
					{
						obj.put(rsmd.getColumnName(i), rs.getString(i));
					}
					jsonArray.add(obj);
				}
				
				response = jsonArray.toJSONString();
				conn.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
			
		}
		
		//PRODUCT-SUPPLIER-----------------------------------------------------------------------------------------------------------------
		
		
		// http://localhost:8080/JSPDay3RESTExample/rs/productsupplier/getproductidsupplieridbypsid/{ productSupplierId }
		@GET
		@Path("/productsupplier/getproductidsupplieridbypsid/{ productSupplierId }")
		@Produces(MediaType.APPLICATION_JSON)
		public String getProductIdSupplierIdByPSId(@PathParam("productSupplierId") int productSupplierId)
		{
			String response =null;
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				String sql="SELECT DISTINCT ProductId, SupplierId FROM Products_suppliers WHERE ProductSupplierId=?";
				PreparedStatement stmt =conn.prepareStatement(sql);
				stmt.setInt(1, productSupplierId);
				ResultSet rs=stmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				JSONObject obj = new JSONObject();
				if(rs.next())
				{
					for(int i=1; i<=rsmd.getColumnCount();i++)
					{
						obj.put(rsmd.getColumnName(i), rs.getString(i));
					}
				}
				
				response = obj.toJSONString();
				conn.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
			
		}
		
		
		
		
		// http://localhost:8080/JSPDay3RESTExample/rs/productsupplier/getpkgproductsbypkgId/{ packageId }
		@GET
		@Path("/productsupplier/getpkgproductsbypkgId/{ packageId }")
		@Produces(MediaType.APPLICATION_JSON)
		public String getPkgProductsByPkgId(@PathParam("packageId") int packageId)
		{
			String response =null;
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				String sql="SELECT * FROM packages_products_suppliers WHERE PackageId=?";
				PreparedStatement stmt =conn.prepareStatement(sql);
				stmt.setInt(1, packageId);
				ResultSet rs=stmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				
				JSONArray jsonArray = new JSONArray();
				while (rs.next())
				{
					JSONObject obj = new JSONObject();
					for(int i=1; i<=rsmd.getColumnCount();i++)
					{
						obj.put(rsmd.getColumnName(i), rs.getString(i));
					}
					jsonArray.add(obj);
				}
				
				response = jsonArray.toJSONString();
				conn.close();
				
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
			
		}
		
		
		

		// http://localhost:8080/JSPDay3RESTExample/rs/productsupplier/getProdSupIdByIds/{supplierId}/{productId}
		
		@GET
		@Path("/productsupplier/getProdSupIdByIds/{supplierId}/{productId}")
		@Produces(MediaType.TEXT_PLAIN)
		public String getProdSupIdByIds(@PathParam("supplierId") int supplierId,@PathParam("productId") int productId)
		{
			String response ="";
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				String sql="SELECT DISTINCT a.ProductSupplierId FROM Products_suppliers a INNER JOIN Suppliers  b ON a.SupplierId=b.SupplierId WHERE a.ProductId=? and b.SupplierId=?";
				PreparedStatement stmt =conn.prepareStatement(sql);
				stmt.setInt(1, productId);
				stmt.setInt(2, supplierId);
				ResultSet rs=stmt.executeQuery();
				if(rs.next())
					response = rs.getString(1);
				conn.close();

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
			
		}
		
		//REGIONS------------------------------------------------------------------------------------------------------------------------
		
		// http://localhost:8080/JSPDay3RESTExample/rs/region/postregion/{oldRegionId}

		@POST
		@Path("/region/postregion/{oldRegionId}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		
		public String postRegion(String jsonString, @PathParam("oldRegionId") String oldRegionId)
		{
			JSONParser parser= new JSONParser();
			JSONObject obj; 
			String sql="UPDATE `regions` SET `RegionId`=?,`RegionName`=? WHERE `RegionId`=?";
			String message = null;
			try {
				obj= (JSONObject) parser.parse(jsonString);
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				PreparedStatement stmt =conn.prepareStatement(sql);
				stmt.setString(1, (String) obj.get("RegionId"));
				stmt.setString(2, (String) obj.get("RegionName"));
				stmt.setString(3, (String) oldRegionId);

				if(stmt.executeUpdate()>0)
				{
					message="Region updated successfully";
				}
				else
				{
					message="Region Update failed";
				}
				conn.close();
			} catch (ClassNotFoundException | SQLException | ParseException e) {
				e.printStackTrace();
			}
			return "{ 'message':'" + message + "' }";
		}
		

		// http://localhost:8080/JSPDay3RESTExample/rs/region/putregion
		@PUT
		@Path("/region/putregion")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		
		public String putRegion(String jsonString)
		{
			JSONParser parser= new JSONParser();
			JSONObject obj; 
			String sql="INSERT INTO `regions`(`RegionId`, `RegionName`) VALUES (?,?)";
			String message = null;
			try {
				obj= (JSONObject) parser.parse(jsonString);
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				PreparedStatement stmt =conn.prepareStatement(sql);
				stmt.setString(1, (String) obj.get("RegionId"));
				stmt.setString(2, (String) obj.get("RegionName"));

				if(stmt.executeUpdate()>0)
				{
					message="Region inserted successfully";
				}
				else
				{
					message="Region Insert failed";
				}
				conn.close();
			} catch (ClassNotFoundException | SQLException | ParseException e) {
				e.printStackTrace();
			}
			return "{ 'message':'"+ message + "' }";
		}
		
		// http://localhost:8080/JSPDay3RESTExample/rs/region/deleteregion/{regionId}
		
		@DELETE
		@Path("/region/deleteregion/{ regionId }")
		//@Consumes({MediaType.APPLICATION_JSON})
		@Produces(MediaType.TEXT_PLAIN)
		
		public String deleteRegion(@PathParam("regionId") String regionnId)
		{
		
			String message = "";
		
		try {

			Class.forName("org.mariadb.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
			String sql="Delete from `regions` where RegionId=?";
			PreparedStatement stmt =conn.prepareStatement(sql);
			stmt.setString(1, regionnId);
			if(stmt.executeUpdate()>0)
			{
				message="Region Deleted Successfully";
			}
			else
			{
				message="Region Delete Failed.";
			}
			conn.close();
			
			
		} catch (ClassNotFoundException | SQLException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return message;
	}
		
		
		
		
		// http://localhost:8080/JSPDay3RESTExample/rs/region/getregions
		
		@GET
		@Path("/region/getregions")
		@Produces(MediaType.APPLICATION_JSON)
		public String getRegions()
		{
			String response =null;
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				Statement stmt= conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from Regions");
				ResultSetMetaData rsmd = rs.getMetaData();
				
				JSONArray jsonArray = new JSONArray();
				
				while (rs.next())
				{
					JSONObject obj = new JSONObject();
					for(int i=1; i<=rsmd.getColumnCount();i++)
					{
						obj.put(rsmd.getColumnName(i), rs.getString(i));
					}
					jsonArray.add(obj);
				}
				
				response = jsonArray.toJSONString();
				conn.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
			
		}
		
		//REWARD-------------------------------------------------------------------------------------------------------------------
		 //Find next reward Id from Database
		// http://localhost:8080/JSPDay3RESTExample/rs/reward/newreward
		@GET
		@Path("/reward/newreward")
		@Produces(MediaType.TEXT_PLAIN)
		public String newReward()
		{
			String response ="";
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				Statement stmt= conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select RewardId from Rewards order by RewardId DESC limit 1");
				if(rs.next())
				{
					try {
						int next = Integer.parseInt( rs.getString(1));
						response=Integer.toString(next+1);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
				conn.close();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			return response;
		}
		
		
		
		//Update Reward
		// http://localhost:8080/JSPDay3RESTExample/rs/reward/postreward
		@POST
		@Path("/reward/postreward")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		
		public String postReward(String jsonString)
		{
			JSONParser parser= new JSONParser();
			JSONObject obj; 
			String sql="UPDATE `rewards` SET `RwdName`=?,`RwdDesc`=? WHERE `RewardId`=?";
			String message = null;
			try {
				obj= (JSONObject) parser.parse(jsonString);
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				PreparedStatement stmt =conn.prepareStatement(sql);
				stmt.setString(1, (String) obj.get("RwdName"));
				stmt.setString(2, (String) obj.get("RwdDesc"));
				stmt.setInt(3, Integer.parseInt((String) obj.get("RewardId")));

				if(stmt.executeUpdate()>0)
				{
					message="Reward updated successfully";
				}
				else
				{
					message="reward Update failed";
				}
				conn.close();
			} catch (ClassNotFoundException | SQLException | ParseException e) {
				e.printStackTrace();
			}
			return "{ 'message':'"+ message + "' }";
		}
		
		//Insert Reward
		// http://localhost:8080/JSPDay3RESTExample/rs/reward/putreward
		@PUT
		@Path("/reward/putreward")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		
		public String putReward(String jsonString)
		{
			JSONParser parser= new JSONParser();
			JSONObject obj; 
			String sql="INSERT INTO `rewards`( `RwdName`, `RwdDesc`,`RewardId`) VALUES (?,?,?)";
			String message = null;
			try {
				obj= (JSONObject) parser.parse(jsonString);
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				PreparedStatement stmt =conn.prepareStatement(sql);
				stmt.setString(1, (String) obj.get("RwdName"));
				stmt.setString(2, (String) obj.get("RwdDesc"));
				stmt.setInt(3, Integer.parseInt((String) obj.get("RewardId")));

				if(stmt.executeUpdate()>0)
				{
					message="Reward inserted successfully";
				}
				else
				{
					message="Reward Insert failed";
				}
				conn.close();
			} catch (ClassNotFoundException | SQLException | ParseException e) {
				e.printStackTrace();
			}
			return "{ 'message':'"+message +"' }";
		}
		
		//Delete Reward
		// http://localhost:8080/JSPDay3RESTExample/rs/reward/deletereward/{ rewardId }
		@DELETE
		@Path("reward/deletereward/{ rewardId }")
		//@Consumes({MediaType.APPLICATION_JSON})
		@Produces(MediaType.TEXT_PLAIN)
		
		public String deleteReward(@PathParam("rewardId") String rewardId)
		{
		
			String message = "";
		
		try {

			Class.forName("org.mariadb.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
			String sql="Delete from `rewards` where RewardId=?";
			PreparedStatement stmt =conn.prepareStatement(sql);
			stmt.setString(1, rewardId);
			if(stmt.executeUpdate()>0)
			{
				message="Reward Deleted Successfully";
			}
			else
			{
				message="Reward Delete Failed.";
			}
			conn.close();
			
			
		} catch (ClassNotFoundException | SQLException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return message;
	}
		
		
		
		//Get All Rewards
		// http://localhost:8080/JSPDay3RESTExample/rs/reward/getrewards
		@GET
		@Path("/reward/getrewards")
		@Produces(MediaType.APPLICATION_JSON)
		public String getRewards()
		{
			String response =null;
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				Statement stmt= conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from Rewards");
				ResultSetMetaData rsmd = rs.getMetaData();
				
				JSONArray jsonArray = new JSONArray();
				
				while (rs.next())
				{
					JSONObject obj = new JSONObject();
					for(int i=1; i<=rsmd.getColumnCount();i++)
					{
						obj.put(rsmd.getColumnName(i), rs.getString(i));
					}
					jsonArray.add(obj);
				}
				response = jsonArray.toJSONString();
				conn.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}			
			return response;		
		}
	//SUPPLIER---------------------------------------------------------------------------------------------------------------------------

		 //Find next supplier Id from Database
		// http://localhost:8080/JSPDay3RESTExample/rs/supplier/newsupplier
		@GET
		@Path("/supplier/newsupplier")
		@Produces(MediaType.TEXT_PLAIN)
		public String newSupplier()
		{
			String response ="";
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				Statement stmt= conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select SupplierId from Suppliers order by SupplierId DESC limit 1");
				if(rs.next())
				{
					try {
						int next = Integer.parseInt( rs.getString(1));
						response=Integer.toString(next+1);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
				conn.close();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			return response;
		}
		
		
		
		
		// http://localhost:8080/JSPDay3RESTExample/rs/supplier/getsupplier/{ supplierId }
		@GET
		@Path("/supplier/getsupplier/{ supplierId }")
		@Produces(MediaType.APPLICATION_JSON)
		public String getSupplier(@PathParam("supplierId") int supplierId)
		{
			String response =null;
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				String sql="select * from Suppliers where SupplierId=?";
				PreparedStatement stmt =conn.prepareStatement(sql);
				stmt.setInt(1,supplierId);
				ResultSet rs=stmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				JSONObject obj = new JSONObject();
				if(rs.next())
				{
					for(int i=1; i<=rsmd.getColumnCount();i++)
					{
						obj.put(rsmd.getColumnName(i), rs.getString(i));
					}
				}
				
				response = obj.toJSONString();
				conn.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
			return response;
			
		}
		
		// http://localhost:8080/JSPDay3RESTExample/rs/supplier/putsupplier
		@PUT
		@Path("/supplier/putsupplier")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		
		public String putSupplier(String jsonString)
		{
			JSONParser parser= new JSONParser();
			JSONObject obj; 
			String sql="INSERT INTO `suppliers`(`SupName`,`SupplierId`) VALUES (?,?)";
			String message = null;
			try {
				obj= (JSONObject) parser.parse(jsonString);
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				PreparedStatement stmt =conn.prepareStatement(sql);
				stmt.setString(1, (String) obj.get("SupName"));
				stmt.setInt(2, Integer.parseInt((String)obj.get("SupplierId")));
				if(stmt.executeUpdate()>0)
				{
					message="Supplier inserted successfully";
				}
				else
				{
					message=" Supplier Insert failed";
				}
				conn.close();
			} catch (ClassNotFoundException | SQLException | ParseException e) {
				e.printStackTrace();
			}
			return "{ 'message':'" + message + "' }";
		}
		
		// http://localhost:8080/JSPDay3RESTExample/rs/supplier/postsupplier
		@POST
		@Path("/supplier/postsupplier")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		
		public String updateSupplier(String jsonString)
		{
			JSONParser parser= new JSONParser();
			JSONObject obj; 
			String sql="UPDATE `suppliers` SET `SupName`=? WHERE `SupplierId`=?";
			String message = null;
			try {
				obj= (JSONObject) parser.parse(jsonString);
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				PreparedStatement stmt =conn.prepareStatement(sql);
				stmt.setString(1, (String) obj.get("SupName"));
				stmt.setInt(2, Integer.parseInt((String)obj.get("SupplierId")));

				if(stmt.executeUpdate()>0)
				{
					message="Supplier updated successfully";
				}
				else
				{
					message="Supplier Update failed";
				}
				conn.close();
			} catch (ClassNotFoundException | SQLException | ParseException e) {
				e.printStackTrace();
			}
			return "{ 'message':'"+message +"' }";
		}
		
		
		
		// http://localhost:8080/JSPDay3RESTExample/rs/supplier/deletesupplier/{ supplierId }
		@DELETE
		@Path("/supplier/deletesupplier/{ supplierId }")
		//@Consumes({MediaType.APPLICATION_JSON})
		@Produces(MediaType.TEXT_PLAIN)
		
		public String deleteSupplier(@PathParam("supplierId") int supplierId)
		{
		
			String message = "";
		
		try {

			Class.forName("org.mariadb.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
			String sql="Delete from `suppliers` where SupplierId=?";
			PreparedStatement stmt =conn.prepareStatement(sql);
			stmt.setInt(1, supplierId);
			if(stmt.executeUpdate()>0)
			{
				message="Supplier Deleted Successfully";
			}
			else
			{
				message="Supplier Delete failed.";
			}
			conn.close();
			
			
		} catch (ClassNotFoundException | SQLException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return message;
}


		// http://localhost:8080/JSPDay3RESTExample/rs/supplier/getsupplierbypsid/{ productSupplierId }
		@GET
		@Path("/supplier/getsupplierbypsid/{ productSupplierId }")
		@Produces(MediaType.APPLICATION_JSON)
		public String getSupplierByPSId(@PathParam("productSupplierId") int productSupplierId)
		{
			String response =null;
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				String sql="SELECT DISTINCT b.SupplierId, b.SupName FROM Products_suppliers a INNER JOIN Suppliers  b ON a.SupplierId=b.SupplierId WHERE a.ProductSupplierId=? ORDER BY b.SupName";
				PreparedStatement stmt =conn.prepareStatement(sql);
				stmt.setInt(1, productSupplierId);
				ResultSet rs=stmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				JSONObject obj = new JSONObject();
				if(rs.next())
				{
					for(int i=1; i<=rsmd.getColumnCount();i++)
					{
						obj.put(rsmd.getColumnName(i), rs.getString(i));
					}
				}
				
				response = obj.toJSONString();
				conn.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
			
		}
		

		
// http://localhost:8080/JSPDay3RESTExample/rs/supplier/getsupplierswithproducts/{ productId }

@GET
@Path("/supplier/getsupplierswithproducts/{ productId }")
@Produces(MediaType.APPLICATION_JSON)
public String getSuppliersWithProducts(@PathParam("productId") int productId)
{
	String response =null;
	try {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
		String sql="SELECT DISTINCT b.SupplierId, b.SupName FROM Products_suppliers a INNER JOIN Suppliers  b ON a.SupplierId=b.SupplierId WHERE a.ProductId=? ORDER BY b.SupName";
		PreparedStatement stmt =conn.prepareStatement(sql);
		stmt.setInt(1, productId);
		ResultSet rs=stmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		JSONArray jsonArray = new JSONArray();
		
		while (rs.next())
		{
			JSONObject obj = new JSONObject();
			for(int i=1; i<=rsmd.getColumnCount();i++)
			{
				obj.put(rsmd.getColumnName(i), rs.getString(i));
			}
			jsonArray.add(obj);
		}
		
		response = jsonArray.toJSONString();
		conn.close();
		
	} catch (ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return response;
	
}

		

//http://localhost:8080/JSPDay3RESTExample/rs/supplier/getsupplierswithproducts

@GET
@Path("/supplier/getsupplierswithproducts")
@Produces(MediaType.APPLICATION_JSON)
public String getSupplierByProductId()
{
	String response =null;
	try {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
		Statement stmt= conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT DISTINCT b.SupplierId, b.SupName FROM Products_suppliers a INNER JOIN Suppliers  b ON a.SupplierId=b.SupplierId ORDER BY b.SupName");
		ResultSetMetaData rsmd = rs.getMetaData();
		
		JSONArray jsonArray = new JSONArray();
		
		while (rs.next())
		{
			JSONObject obj = new JSONObject();
			for(int i=1; i<=rsmd.getColumnCount();i++)
			{
				obj.put(rsmd.getColumnName(i), rs.getString(i));
			}
			jsonArray.add(obj);
		}
		
		response = jsonArray.toJSONString();
		conn.close();
		
	} catch (ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return response;
	
}

		
		
		// http://localhost:8080/JSPDay3RESTExample/rs/supplier/getsuppliers
		
		@GET
		@Path("/supplier/getsuppliers")
		@Produces(MediaType.APPLICATION_JSON)
		public String getSuppliers()
		{
			String response =null;
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				Statement stmt= conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from Suppliers");
				ResultSetMetaData rsmd = rs.getMetaData();
				
				JSONArray jsonArray = new JSONArray();
				
				while (rs.next())
				{
					JSONObject obj = new JSONObject();
					for(int i=1; i<=rsmd.getColumnCount();i++)
					{
						obj.put(rsmd.getColumnName(i), rs.getString(i));
					}
					jsonArray.add(obj);
				}
				
				response = jsonArray.toJSONString();
				conn.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
			
		}
		
//TRIP TYPES
		// http://localhost:8080/JSPDay3RESTExample/rs/tritype/posttriptype/{oldTripTypeId}

				@POST
				@Path("/tritype/posttriptype/{oldTripTypeId}")
				@Consumes(MediaType.APPLICATION_JSON)
				@Produces(MediaType.APPLICATION_JSON)
				
				public String updateTripType(String jsonString, @PathParam("oldTripTypeId") String oldTripTypeId)
				{
					JSONParser parser= new JSONParser();
					JSONObject obj; 
					String sql="UPDATE `TripTypes` SET `TRipTypeId`=?,`TTName`=? WHERE `TripTypeId`=?";
					String message = null;
					try {
						obj= (JSONObject) parser.parse(jsonString);
						Class.forName("org.mariadb.jdbc.Driver");
						Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
						PreparedStatement stmt =conn.prepareStatement(sql);
						stmt.setString(1, (String) obj.get("TripTypeId"));
						stmt.setString(2, (String) obj.get("TTName"));
						stmt.setString(3, (String) oldTripTypeId);

						if(stmt.executeUpdate()>0)
						{
							message="Trip Type updated successfully";
						}
						else
						{
							message="Trip Type Update failed";
						}
						conn.close();
					} catch (ClassNotFoundException | SQLException | ParseException e) {
						e.printStackTrace();
					}
					return "{ 'message':'"+message +"' }";
				}
				

				// http://localhost:8080/JSPDay3RESTExample/rs/triptype/puttriptype
				@PUT
				@Path("/triptype/puttriptype")
				@Consumes({MediaType.APPLICATION_JSON})
				@Produces(MediaType.APPLICATION_JSON)
				
				public String putTripType(String jsonString)
				{
					JSONParser parser= new JSONParser();
					JSONObject obj; 
					String sql="INSERT INTO `TripTypes`(`TripTypeId`, `TTName`) VALUES (?,?)";
					String message = null;
					try {
						obj= (JSONObject) parser.parse(jsonString);
						Class.forName("org.mariadb.jdbc.Driver");
						Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
						PreparedStatement stmt =conn.prepareStatement(sql);
						stmt.setString(1, (String) obj.get("TripTypeId"));
						stmt.setString(2, (String) obj.get("TTName"));

						if(stmt.executeUpdate()>0)
						{
							message="Trip Type inserted successfully";
						}
						else
						{
							message="Trip Type Insert failed";
						}
						conn.close();
					} catch (ClassNotFoundException | SQLException | ParseException e) {
						e.printStackTrace();
					}
					return "{ 'message':'"+message +"' }";
				}
				
				// http://localhost:8080/JSPDay3RESTExample/rs/triptype/deletetriptype/{ tripTypeId }
				
				@DELETE
				@Path("/triptype/deletetriptype/{ tripTypeId }")
				//@Consumes({MediaType.APPLICATION_JSON})
				@Produces(MediaType.TEXT_PLAIN)
				
				public String deleteTripType(@PathParam("tripTypeId") String tripTypeId)
				{
				
					String message = "";
				
				try {

					Class.forName("org.mariadb.jdbc.Driver");
					Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
					String sql="Delete from `triptypes` where TripTypeId=?";
					PreparedStatement stmt =conn.prepareStatement(sql);
					stmt.setString(1, tripTypeId);
					if(stmt.executeUpdate()>0)
					{
						message="Trip Type Deleted Successfully";
					}
					else
					{
						message="Trip Type Delete Failed.";
					}
					conn.close();
					
					
				} catch (ClassNotFoundException | SQLException  e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return message;
			}
				
		
		
		

		// http://localhost:8080/JSPDay3RESTExample/rs/triptype/gettriptypes
		
		@GET
		@Path("/triptype/gettriptypes")
		@Produces(MediaType.APPLICATION_JSON)
		public String getTripTypes()
		{
			String response =null;
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TravelExperts", "harv", "password");
				Statement stmt= conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from TripTypes");
				ResultSetMetaData rsmd = rs.getMetaData();
				
				JSONArray jsonArray = new JSONArray();
				
				while (rs.next())
				{
					JSONObject obj = new JSONObject();
					for(int i=1; i<=rsmd.getColumnCount();i++)
					{
						obj.put(rsmd.getColumnName(i), rs.getString(i));
					}
					jsonArray.add(obj);
				}
				
				response = jsonArray.toJSONString();
				conn.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;			
		}
		
	
}
