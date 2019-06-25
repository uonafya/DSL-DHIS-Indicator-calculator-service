/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 * generate numerator and denominator sql
 */
@Produces("application/json")
@Path("periodsGet")
public class AggregateIndicator {
    String dbConnection = "jdbc:postgresql://ip/database?user=&password=";
    @GET
    @Path("{indicatorId}") // "{id --- indicator id}/{periodid}/{sourceid}"
    @Produces("application/json")
    public String generateIndicatoSql(@PathParam("id") int indicatorId) throws SQLException {

        String numerator = "#{O3qECFGrzeF.ieuodyY5ybC}";
        String sql = "Select numerator, denominator from dim_dhis_indicator where indicatorid=?";
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        con = DriverManager.getConnection(dbConnection);
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs=ps.executeQuery();
        return "Success";
    }
}
