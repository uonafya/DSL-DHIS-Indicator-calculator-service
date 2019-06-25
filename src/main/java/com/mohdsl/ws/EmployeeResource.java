/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mohdsl.ws;

import java.util.List;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 
@Path("/employees")
public class EmployeeResource {

 
    @GET @Path("{id}/reports")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public String findByManager(@PathParam("id") String managerId) {
        return "dtd";
    }
}