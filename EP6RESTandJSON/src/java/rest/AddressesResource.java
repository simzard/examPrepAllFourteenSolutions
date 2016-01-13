/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.GsonBuilder;
import generators.DataGenerator;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author simon
 */
@Path("addresses")
public class AddressesResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AddressesResource
     */
    public AddressesResource() {
    }

    /**
     * Retrieves representation of an instance of generators.AddressesResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("{amount}/{dataString}")
    @Produces("application/json")
    public Response getJson(@PathParam("amount") int amount, @PathParam("dataString") String dataString) {

        return Response.ok()
                .entity(DataGenerator.getData(amount, dataString))
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET")
                .build();
                
        

    }

    /**
     * PUT method for updating or creating an instance of AddressesResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
