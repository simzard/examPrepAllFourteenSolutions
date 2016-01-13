/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.util.concurrent.ExecutionException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;
import scraper.Scraper;

/**
 * REST Web Service
 *
 * @author simon
 */
@Path("group")
public class GroupResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GroupsResource
     */
    public GroupResource() {
    }

    /**
     * Retrieves representation of an instance of rest.GroupsResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public Response getJson() throws InterruptedException, ExecutionException {
        
        CacheControl cc = new CacheControl();
        cc.setMaxAge(3600); // one hours cache
        cc.setPrivate(true);
        
        String list = Scraper.beginScrape();
        
        return Response.ok(list)
                .cacheControl(cc)
                .build();
                
                
    }

    /**
     * PUT method for updating or creating an instance of GroupsResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
