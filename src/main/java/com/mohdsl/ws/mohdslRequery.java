package com.mohdsl.ws;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/requery")
public class mohdslRequery {

	@GET
	@Path("{indicatorid}/{periodid}/{orgunitid}/{sqlstring}")
	public Response reQuery(@PathParam("indicatorid") int cindicatorid,
							@PathParam("periodid") int cperiodid, 
							@PathParam("orgunitid") int corgunitid,
							@PathParam("sqlstring") String csqlstring) {
		
		String preSQLselect = "SELECT ";
		String preSQLselectReplace = "SELECT value1";
		String sqlselect = "";
		String additionalWhereClause = "";
		String whereclausePart1 = " AND periodid =";
		String whereclausePart2 = " AND sourceid = ";
		String stringreplace = ") as numerator";
		String finalSQL = "";
		
		preSQLselect = preSQLselect +  "'" + cindicatorid + "'::int as IndicatorID, " + 
										"'" + cperiodid + "'::int as PeriodID, " +
										"'" + corgunitid + "'::int as OrganisationUnitID, " +
										" value1";
		
		additionalWhereClause=whereclausePart1 + cperiodid + 
							  whereclausePart2 + corgunitid + stringreplace;
		
		 sqlselect = csqlstring.replace(stringreplace, additionalWhereClause);
		 finalSQL = sqlselect.replace(preSQLselectReplace, preSQLselect);
		 
		 return Response.status(200)
					.entity(finalSQL)
					.build();
	 }
}



