package com.mohdsl.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


@Produces("application/xml")
@Path("numeratordenominator")
public class NumeratorDenominatorResource {
        String dbConnection = "jdbc:postgresql://ip/database?user=&password=";
	@GET
	@Path("{indId}")
	public String getNumeratorDenominator(@PathParam("indId") int indId) {			
			String sqlselect = "";
			String sqlnumerator = "numerator";
			String sqlvalue = "";
			String value = "value";
			String sqlvalue1 = "";
			String innerSQL = "";
			String innerSQL1 = "";
			String outersql = "SELECT ";
			String numeratorSQL = "";
			String avecheck = "average";
			String emptyCheck = "";
			String selectOnlycheck = "SELECT";
			
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try	{
			      try {
					Class.forName("org.postgresql.Driver");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   conn = DriverManager.getConnection(dbConnection);
			   ps = conn.prepareStatement("SELECT * FROM dim_dhis_indicatornumerator where indicatorid = ?");
			   ps.setLong(1, indId);
			   rs = ps.executeQuery();
				int i = 0;
				while(rs.next()) {
					sqlvalue = sqlnumerator + i;
					sqlvalue1 = value + i;
					innerSQL = " (SELECT ";
					if (rs.getString("dataelementid") != null) {
						String computation =rs.getString("dataelementaggregationtype");
							if(rs.getString("dataelementaggregationtype").equals(avecheck)) {
								computation="AVG";
							}else{
								computation="SUM";
							}
						innerSQL = innerSQL +  " COALESCE(" + computation + "(value::numeric), 0) as " + sqlvalue1 + " from test_ward_org_unit where dataelementid = " + rs.getInt("dataelementid");
						}
					if (rs.getString("categoryoptioncomboid") != null) {
							innerSQL = innerSQL + " AND categoryoptioncomboid = " +  rs.getInt("categoryoptioncomboid") + ") as " + sqlvalue;
						}
					else {
						if (innerSQL.trim().equals("(SELECT")) {
							}
						else{
							innerSQL = innerSQL + ") as " + sqlvalue;
							}
						}
					if (i > 0) {
						if (innerSQL.toString().trim() != "")	{
							if (innerSQL1.toString().trim() !="") {
									innerSQL1 = innerSQL1 + "," + innerSQL;
								}
							else{
								innerSQL1 = innerSQL1 +  innerSQL;
								}
							}
							else {
								innerSQL1 = innerSQL;
							}
					}
					else {
						if (rs.getString("dataelementid") != null) 
							innerSQL1 = innerSQL1 + innerSQL;
					}
					if (rs.getString("precedingoperator") != null)
						outersql = outersql + rs.getString("precedingoperator").trim();
					if (rs.getString("dataelementid") != null)
						outersql = outersql + sqlvalue1;
					if (rs.getString("midoperator") != null)
						if (rs.getString("midoperator") != "AND")
							outersql = outersql + rs.getString("midoperator").trim();
					if (rs.getString("endoperator") != null)
					    outersql = outersql + rs.getString("endoperator").trim();
					i++;
				}
				sqlselect = innerSQL1;
			 	if (sqlselect.toString().trim()  != null)	{
			 		if (sqlselect.trim().equals(emptyCheck))
			 			if (sqlselect.trim().equals(selectOnlycheck))
			 				numeratorSQL ="";
			 			else
			 				numeratorSQL =outersql + " as numerator" ;
			 		else
			 			numeratorSQL =outersql + " as numerator FROM "+ sqlselect;
			 	}
			}
			catch (SQLException sqle) {
		    	   sqle.printStackTrace();	
						outersql = "ERROR - " + sqle.toString();
			}
			finally	{
			   // close the resources in reverse order of creation (i.e. ResultSet, PreparedStatement, Connection)
			   if (rs != null) {
			       try {
			           rs.close();
			       }
			       catch(SQLException sqle) {
			    	   sqle.printStackTrace();	
							outersql = "ERROR - " + sqle.toString();
			       }
			   }

			   if (ps != null){
			       try {
			           ps.close();
			       }
			       catch(SQLException sqle) {
			    	   sqle.printStackTrace();	
							outersql = "ERROR - " + sqle.toString();
			       }
			   }

			   if (conn != null) {
			       try {
			           conn.close();
			       }
			       catch(SQLException sqle) {
			    	   sqle.printStackTrace();	
						outersql = "ERROR - " + sqle.toString();
			       }
			   }
			}
			

			sqlselect = "";
			String sqldenominator = "denominator";
			sqlvalue = "";
			value = "value";
			sqlvalue1 = "";
			innerSQL = "";
			innerSQL1 = "";
			outersql = "SELECT ";
			String denominatorSQL = "";
			
			
			try	{
			      try {
						Class.forName("org.postgresql.Driver");
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   conn = DriverManager.getConnection(dbConnection);
				   ps = conn.prepareStatement("SELECT * FROM dim_dhis_indicatordenominator where indicatorid = ?");
				   ps.setLong(1, indId);
				   rs = ps.executeQuery();
					int i = 0;
					while(rs.next()) {
						sqlvalue = sqldenominator + i;
						sqlvalue1 = value + i;
						innerSQL = " (SELECT ";
						if (rs.getString("dataelementid") != null) {
							String computation =rs.getString("dataelementaggregationtype");
							if(rs.getString("dataelementaggregationtype").equals(avecheck)) {
									computation="AVG";
								}else{
									computation="SUM";
								}
							innerSQL = innerSQL +  " COALESCE(" + computation + "(value::numeric), 0) as " + sqlvalue1 + " from test_ward_org_unit where dataelementid = " + rs.getInt("dataelementid");
							}
						if (rs.getString("categoryoptioncomboid") != null) {
								innerSQL = innerSQL + " AND categoryoptioncomboid = " +  rs.getInt("categoryoptioncomboid") + ") as " + sqlvalue;
							}
						else {
							if (innerSQL.trim().equals("(SELECT")) {
								innerSQL = "";
								}
							else{
								innerSQL = innerSQL + ") as " + sqlvalue;
								}
							}
						if (i > 0) {
							if (innerSQL.toString().trim() != "")	{
								if (innerSQL1.toString().trim() !="") {
										innerSQL1 = innerSQL1 + "," + innerSQL;
									}
								else{
									innerSQL1 = innerSQL1 +  innerSQL;
									}
								}
								else {
									innerSQL1 = innerSQL;
								}
						}
						else {
							if (rs.getString("dataelementid") != null) 
								innerSQL1 = innerSQL1 + innerSQL;
						}
						if (rs.getString("precedingoperator") != null)
							outersql = outersql + rs.getString("precedingoperator").trim();
						if (rs.getString("dataelementid") != null)
							outersql = outersql + sqlvalue1;
						if (rs.getString("midoperator") != null)
							if (rs.getString("midoperator") != "AND")
								outersql = outersql + rs.getString("midoperator").trim();
						if (rs.getString("endoperator") != null)
						    outersql = outersql + rs.getString("endoperator").trim();
						i++;
					}
					sqlselect = innerSQL1;
				 	if (sqlselect.toString().trim()  != null)	{
				 		if (sqlselect.trim().equals(emptyCheck))
				 			if (sqlselect.trim().equals(selectOnlycheck))
				 				denominatorSQL ="";
				 			else
				 				denominatorSQL =outersql + " as denominator";
				 		else
				 			denominatorSQL =outersql + " as denominator FROM "+ sqlselect;
				 	}
				}
				catch (SQLException sqle) {
			    	   sqle.printStackTrace();	
							outersql = "ERROR - " + sqle.toString();
				}
				finally	{
				   // close the resources in reverse order of creation (i.e. ResultSet, PreparedStatement, Connection)
				   if (rs != null) {
				       try {
				           rs.close();
				       }
				       catch(SQLException sqle) {
				    	   sqle.printStackTrace();	
								outersql = "ERROR - " + sqle.toString();
				       }
				   }

				   if (ps != null){
				       try {
				           ps.close();
				       }
				       catch(SQLException sqle) {
				    	   sqle.printStackTrace();	
								outersql = "ERROR - " + sqle.toString();
				       }
				   }

				   if (conn != null) {
				       try {
				           conn.close();
				       }
				       catch(SQLException sqle) {
				    	   sqle.printStackTrace();	
							outersql = "ERROR - " + sqle.toString();
				       }
				   }
				}
			
		
	
			try	{
			      try {
						Class.forName("org.postgresql.Driver");
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			      System.out.println("numerator sql = " + numeratorSQL);
				conn = DriverManager.getConnection(dbConnection);
				   //ps = conn.prepareStatement("UPDATE indicator SET numeratorsql = " + "'" + numeratorSQL + "'" + 
							//", denominatorsql = " + "'" + denominatorSQL + "'" + 
							//" WHERE indicatorid = " + indId);
	
				   ps = conn.prepareStatement("UPDATE dim_dhis_indicator SET numeratorsql = " + "'" + numeratorSQL + "'" + 
							" WHERE indicatorid = " + indId);
					ps.executeUpdate();
				   ps = conn.prepareStatement("UPDATE dim_dhis_indicator SET denominatorsql = " + "'" + denominatorSQL + "'" + 
							" WHERE indicatorid = " + indId);
					ps.executeUpdate();
				}
				catch (SQLException sqle) {
			    	   sqle.printStackTrace();	
							outersql = "ERROR - " + sqle.toString();
				}
				finally	{
				   // close the resources in reverse order of creation (i.e. ResultSet, PreparedStatement, Connection)
				   if (ps != null){
				       try {
				           ps.close();
				       }
				       catch(SQLException sqle) {
				    	   sqle.printStackTrace();	
								outersql = "ERROR - " + sqle.toString();
								System.out.println(outersql);
				       }
				   }
				   if (conn != null) {
				       try {
				           conn.close();
				       }
				       catch(SQLException sqle) {
				    	   sqle.printStackTrace();	
							outersql = "ERROR - " + sqle.toString();
							System.out.println(outersql);
				       }
				   }
				}
		return numeratorSQL + " / " + denominatorSQL;
		}
	
}
