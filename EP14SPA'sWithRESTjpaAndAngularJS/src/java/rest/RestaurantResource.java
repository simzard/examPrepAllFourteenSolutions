/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Restaurant;
import facade.RestaurantFacade;
import java.util.List;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author simon
 */
@Path("restaurant")
public class RestaurantResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RestaurantResource
     */
    public RestaurantResource() {
    }

    /**
     * Retrieves representation of an instance of rest.RestaurantResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        RestaurantFacade rf = new RestaurantFacade(Persistence.createEntityManagerFactory("examORM_Rest_AngularPU"));
        List<Restaurant> restaurants = rf.getRestaurants();
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        return gson.toJson(restaurants);
    }

    /**
     * PUT method for updating or creating an instance of RestaurantResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
