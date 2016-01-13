/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.City;
import entities.Country;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author simon
 */
@Path("world")
public class WorldResource {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("RESTandRESTapisPU");

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WorldResource
     */
    public WorldResource() {
    }

    /**
     * Retrieves representation of an instance of rest.WorldResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("country")
    @Produces("application/json")
    public String getAllCountries() {
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("select c.code, c.name, c.continent, ci.name"
                + " from Country c, City ci"
                + " where c.capital.id = ci.id");

        List<Object[]> rows = query.getResultList();
        JsonArray ja = new JsonArray();

        for (Object[] row : rows) {
            JsonObject jo = new JsonObject();
            jo.addProperty("code", (String) row[0]);
            jo.addProperty("name", (String) row[1]);
            jo.addProperty("continent", (String) row[2]);
            jo.addProperty("capital", (String) row[3]);
            ja.add(jo);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(ja);

    }

    @GET
    @Path("country/{population}")
    @Produces("application/json")
    public String getAllCountriesGreaterThanPopulation(@PathParam("population") int population) {
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("select c.code, c.name, c.continent, ci.name"
                + " from Country c, City ci"
                + " where c.capital.id = ci.id and c.population > :p");
        query.setParameter("p", population);

        List<Object[]> rows = query.getResultList();
        JsonArray ja = new JsonArray();

        for (Object[] row : rows) {
            JsonObject jo = new JsonObject();
            jo.addProperty("code", (String) row[0]);
            jo.addProperty("name", (String) row[1]);
            jo.addProperty("continent", (String) row[2]);
            jo.addProperty("capital", (String) row[3]);
            ja.add(jo);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(ja);

    }

    @GET
    @Path("country/code/{countryCode}")
    @Produces("application/json")
    public String getAllCitiesInCountryByCountryCode(@PathParam("countryCode") String countryCode) {
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("select ci.name, ci.population"
                + " from Country c, City ci"
                + " where c.code = ci.countryCode.code and c.code = :c");
        query.setParameter("c", countryCode);

        List<Object[]> rows = query.getResultList();
        JsonArray ja = new JsonArray();

        for (Object[] row : rows) {
            JsonObject jo = new JsonObject();
            jo.addProperty("name", (String) row[0]);
            jo.addProperty("population", (Integer) row[1]);
            ja.add(jo);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(ja);

    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("country/{countryCode}")
    public String createCityInCountry(@PathParam("countryCode") String code, String json) {
        EntityManager em = emf.createEntityManager();

        // get the Country with the current code
        Query query = em.createQuery("select c from Country c where c.code = :c");
        query.setParameter("c", code);
        
        Country country = (Country) query.getSingleResult();
        
        JsonObject cityInJSON = new JsonParser().parse(json).getAsJsonObject();
        // {name: xxx, district: xxx, Population: xxx} 

        String name = cityInJSON.get("name").getAsString();
        String district = cityInJSON.get("district").getAsString();
        int population = cityInJSON.get("population").getAsInt();

        City city = new City(name, district, population);
        city.setCountryCode(country);
        
        try {
            em.getTransaction().begin();
            em.persist(city);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        
        
        //TODO return proper representation object
        return "{message: 'city created'}";
    }

    /**
     * PUT method for updating or creating an instance of WorldResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
