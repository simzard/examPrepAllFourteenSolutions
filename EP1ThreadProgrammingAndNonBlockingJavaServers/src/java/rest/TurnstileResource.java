/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import client.SpectatorClient;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import server.TurnStileServer;

/**
 * REST Web Service
 *
 * @author simon
 */
@Path("turnstile")
public class TurnstileResource {

    SpectatorClient client;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TurnstileResource
     */
    public TurnstileResource() throws IOException {
        Socket link = new Socket("localhost", 9090);
        client = new SpectatorClient(link);
    }

    /**
     * Retrieves representation of an instance of rest.TurnstileResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    public String getText() throws IOException {
        
        return client.getData();
    }

    /**
     * PUT method for updating or creating an instance of TurnstileResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/plain")
    public void putText(String content) {
    }
}
