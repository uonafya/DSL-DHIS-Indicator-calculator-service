package com.mohdsl.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("periodsorgGet")
public class PeriodsOrgResource {
        String dbConnection = "jdbc:postgresql://ip/database?user=&password=";
	@GET
	@Path("{id}/{dataelementid}/{categoryoptioncomboid}") //"{id - indicator id}/{dataelementid}/{categoryoptioncomboid}
	 @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<PeriodOrgObj> getPeriodsOrg(@PathParam("id") int cId,
							 @PathParam("dataelementid") int cdataelementid,
							 @PathParam("categoryoptioncomboid") int ccategoryoptioncomboid) {
		List<PeriodOrgObj> retPeriod = new ArrayList<PeriodOrgObj>();
        int zerocheck = 0;
        
		String innerSQL = "";
		if (ccategoryoptioncomboid == zerocheck) {
			innerSQL = "SELECT fact_dhis_datavalue.periodid, fact_dhis_datavalue.sourceid " +
							"from fact_dhis_datavalue " +
							"where sourceid=23408 and dataelementid =" +  cdataelementid + " group by fact_dhis_datavalue.periodid, fact_dhis_datavalue.sourceid " +
							"order by periodid";
				}
		else {
			innerSQL = "SELECT fact_dhis_datavalue.periodid, fact_dhis_datavalue.sourceid " +
					"from fact_dhis_datavalue " +
					"where sourceid=23408 and dataelementid =" +  cdataelementid + " AND categoryoptioncomboid = " + ccategoryoptioncomboid + " group by fact_dhis_datavalue.periodid, fact_dhis_datavalue.sourceid " +
					"order by periodid";
		}
		Connection conn1 = null;
		PreparedStatement ps1 = null;
		ResultSet rsPeriodOrg = null;
		try {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn1 = DriverManager.getConnection(dbConnection);
		   	ps1 = conn1.prepareStatement(innerSQL);
			System.out.println(innerSQL);
			rsPeriodOrg =  ps1.executeQuery();
			while (rsPeriodOrg.next()) {
				PeriodOrgObj toReturn = new PeriodOrgObj();
                toReturn.setperiodid(rsPeriodOrg.getInt("periodid"));
                toReturn.setsourceid(rsPeriodOrg.getInt("sourceid"));
                retPeriod.add(toReturn);
			}//while (rsPeriodOrg.next()) {
		}
		catch (SQLException sqle) {
	    	   sqle.printStackTrace();	
		}
		finally	{
		   // close the resources in reverse order of creation (i.e. ResultSet, PreparedStatement, Connection)
		   if (rsPeriodOrg != null) {
		       try {
		    	   rsPeriodOrg.close();
		       }
		       catch(SQLException sqle) {
		    	   sqle.printStackTrace();	
		       }
		   }

		   if (ps1 != null){
		       try {
		    	   ps1.close();
		       }
		       catch(SQLException sqle) {
		    	   sqle.printStackTrace();	
		       }
		   }

		   if (conn1 != null) {
		       try {
		    	   conn1.close();
		       }
		       catch(SQLException sqle) {
		    	   sqle.printStackTrace();	
		       }
		   }
		}
    		return retPeriod;
	}

}