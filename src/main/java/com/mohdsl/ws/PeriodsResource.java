package com.mohdsl.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Produces("application/json")
@Path("periodsGet")
public class PeriodsResource {
    String dbConnection = "jdbc:postgresql://localhost/mohdsl?user=postgres&password=";
    @GET
    @Path("{id}/{periodid}/{sourceid}") // "{id --- indicator id}/{periodid}/{sourceid}"
    @Produces("application/json")
    public String getPeriods(@PathParam("id") int cId,
            @PathParam("periodid") int cperiodid,
            @PathParam("sourceid") int csourceid) {

        System.out.println("debug reached here");
        String preSQLselect = "SELECT ";
        String preSQLselectReplace = "SELECT value1";
        String sqlselectNum = "";
        String sqlselectDen = "";
        String additionalWhereClauseNum = "";
        String additionalWhereClauseDen = "";
        String whereclausePart1 = " AND periodid =";
        String whereclausePart2 = " AND sourceid = ";
        String stringreplaceNum = ") as numerator";
        String stringreplaceDen = ") as denominator";
        String finalSQLNum = "";
        String finalSQLDen = "";
        String numeratorSQL = "";
        String denominatorSQL = "";
        double numeratorValue = 0;
        double denominatorValue = 0;
        double indicatorValue = 0;
        String dayssql = "[days]";
        Date startdate = null;
        Date enddate = null;

        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

        Connection connP = null;
        PreparedStatement psP = null;
        ResultSet rsP = null;
        try {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            connP = DriverManager.getConnection(dbConnection);
            psP = connP.prepareStatement("SELECT startdate, enddate FROM dim_dhis_period"
                    + " WHERE periodid = " + cperiodid + "");
            rsP = psP.executeQuery();
            while (rsP.next()) {
                startdate = rsP.getDate("startdate");
                enddate = rsP.getDate("enddate");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            // close the resources in reverse order of creation (i.e. ResultSet, PreparedStatement, Connection)
            if (rsP != null) {
                try {
                    rsP.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }

            if (psP != null) {
                try {
                    psP.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }

            if (connP != null) {
                try {
                    connP.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }

        Date sdate;
        Date edate;
        System.out.println("debug reached here 2");
        System.out.println("error " + startdate);
        try {
            sdate = sdf.parse(startdate.toString());
            cal1.setTime(sdate);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            edate = sdf.parse(enddate.toString());
            cal2.setTime(edate);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        int periodDays = daysBetween(cal1.getTime(), cal2.getTime());
        String periodDaysStr = "";
        periodDaysStr = "'" + periodDays + "'::int";

        preSQLselect = preSQLselect + "'" + cId + "'::int as IndicatorID, "
                + "'" + cperiodid + "'::int as PeriodID, "
                + "'" + csourceid + "'::int as OrganisationUnitID, "
                + " value1";

        additionalWhereClauseNum = whereclausePart1 + cperiodid
                + whereclausePart2 + csourceid + stringreplaceNum;
        additionalWhereClauseDen = whereclausePart1 + cperiodid
                + whereclausePart2 + csourceid + stringreplaceDen;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            conn = DriverManager.getConnection(dbConnection);
            ps = conn.prepareStatement("SELECT * FROM dim_dhis_indicator where indicatorid = ?");
            ps.setLong(1, cId);
            rs = ps.executeQuery();
            while (rs.next()) {
                numeratorSQL = rs.getString("numeratorsql");
                denominatorSQL = rs.getString("denominatorsql");
                sqlselectNum = numeratorSQL.replace(stringreplaceNum, additionalWhereClauseNum);
                finalSQLNum = sqlselectNum.replace(preSQLselectReplace, preSQLselect);
                finalSQLNum = finalSQLNum.replace(dayssql, periodDaysStr);
                sqlselectDen = denominatorSQL.replace(stringreplaceDen, additionalWhereClauseDen);
                finalSQLDen = sqlselectDen.replace(preSQLselectReplace, preSQLselect);
                finalSQLDen = finalSQLDen.replace(dayssql, periodDaysStr);
                System.out.println("Final Numerator SQL = " + finalSQLNum);
                System.out.println("Final Denominator SQL = " + finalSQLDen);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
            sqle.printStackTrace();
        } finally {
            // close the resources in reverse order of creation (i.e. ResultSet, PreparedStatement, Connection)
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle);
                    sqle.printStackTrace();
                }
            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle);
                    sqle.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle);
                    sqle.printStackTrace();
                }
            }
        }

        Connection conn2 = null;
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;

        try {
            try {
                Class.forName("org.postgresql.Driver");
                System.out.println("connect to driver");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                System.err.println(e);
                e.printStackTrace();
            }
            conn2 = DriverManager.getConnection(dbConnection);
            System.out.println("connected success");
            System.out.println("final query running ");
            System.out.println(finalSQLNum);
            ps2 = conn2.prepareStatement(finalSQLNum);
            
            rs2 = ps2.executeQuery();
            System.out.println("final query complete ");
            while (rs2.next()) {
                
                numeratorValue = rs2.getInt("numerator");
                System.out.println("The numerator "+numeratorValue);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
            sqle.printStackTrace();
        } finally {
            // close the resources in reverse order of creation (i.e. ResultSet, PreparedStatement, Connection)
            if (rs2 != null) {
                try {
                    rs2.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle);
                    sqle.printStackTrace();
                }
            }

            if (ps2 != null) {
                try {
                    ps2.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle);
                    sqle.printStackTrace();
                }
            }

            if (conn2 != null) {
                try {
                    conn2.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle);
                    sqle.printStackTrace();
                }
            }
        }

        Connection conn3 = null;
        PreparedStatement ps3 = null;
        ResultSet rs3 = null;

        try {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            conn3 = DriverManager.getConnection(dbConnection);
            System.out.println("final query two denominatorValue  "+ finalSQLDen);
            ps3 = conn3.prepareStatement(finalSQLDen);
            rs3 = ps3.executeQuery();
            while (rs3.next()) {
               
                denominatorValue = rs3.getInt("denominator");
                 System.out.println("denominator "+denominatorValue );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            // close the resources in reverse order of creation (i.e. ResultSet, PreparedStatement, Connection)
            if (rs3 != null) {
                try {
                    rs3.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle);  
                    sqle.printStackTrace();
                }
            }

            if (ps3 != null) {
                try {
                    ps3.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle);
                    sqle.printStackTrace();
                }
            }

            if (conn3 != null) {
                try {
                    conn3.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle);
                    sqle.printStackTrace();
                }
            }
        }

        indicatorValue = numeratorValue / denominatorValue;
        Connection conn4 = null;
        PreparedStatement ps4 = null;

        try {
            try {
                System.out.println("connecting driver to insert"  );
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("connecting two"  );
            conn4 = DriverManager.getConnection(dbConnection);
            System.out.println("connected"  );
            String InsertSQL = "";
            String UpdateSQL = "";
            String UpsertSQL = "";
             
            finalSQLNum = finalSQLNum.replace("'", "");
            finalSQLDen = finalSQLDen.replace("'", "");
            
            System.out.println("finale queries here"  );
            System.out.println("finalSQLNum  "+finalSQLNum  );
            System.out.println("  finalSQLDen "+finalSQLDen  );
            System.out.println("  indicatorValue:  "+indicatorValue  );
            if (indicatorValue > 0) {
                
                UpdateSQL = "UPDATE fact_dhis_indicatorcalculatedvalues SET value = " + indicatorValue + ", numeratorsql = " + "'" + finalSQLNum + "', denominatorsql = " + "'" + finalSQLDen + "'"
                        + ", numeratorvalue = " + numeratorValue + " , " + " denominatorvalue = " + denominatorValue
                        + " WHERE indicatorid = " + cId + " AND periodid = " + cperiodid + " AND sourceid = " + csourceid + "";
                
                InsertSQL = "INSERT INTO fact_dhis_indicatorcalculatedvalues("
                        + "indicatorid, periodid, sourceid, value, numeratorsql, denominatorsql, numeratorvalue, denominatorvalue)"
                        + " SELECT " + cId + "," + cperiodid + "," + csourceid + "," + indicatorValue + ",'" + finalSQLNum + "','" + finalSQLDen + "'," + numeratorValue + "," + denominatorValue + "";

                
                UpsertSQL = "WITH upsert AS (" + UpdateSQL + " RETURNING *) " + InsertSQL + " WHERE NOT EXISTS (SELECT * FROM upsert)";
                System.out.println(UpsertSQL);
                ps4 = conn4.prepareStatement(UpsertSQL);
                ps4.executeUpdate();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (ps4 != null) {
                try {
                    ps4.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle);
                    sqle.printStackTrace();
                }
            }

            if (conn4 != null) {
                try {
                    conn4.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle);
                    sqle.printStackTrace();
                }
            }
        }
        return "Success";
    }

    public int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
}
