/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.Random;

/**
 *
 * @author simon
 */
public class DataGenerator {
    public static String getData(int amount, String dataString) {
        String[] fnames = {"Simon", "Martin", "Ib", "Soeren", "Hans", "Mads", "Hanne", "Sanne", "Mia", "Pia" };
        String[] lnames = {"Hansen", "Jensen", "Frandsen", "Petersen", "Routhe", "Skou Nielsen", "Karlsen", "Møller", "Kjøller", "Thers"}; 
        String[] streets = {"aStreet", "anotherStreet", "Amagerbrogade", "Gothersgade", "Englandsvej", "Jyllingevej", "Roskildevej", "Japanvej", "enGade", "andenGade"};
        String[] cities = {"aCity", "anotherCity", "Cophenhagen", "Aarhus", "Odense", "Esbjerg", "New York", "London", "Paris", "Berlin"};

        Random random = new Random();
        
        String tokens[] = dataString.split(",");
        
        JsonArray ja = new JsonArray();
        
        
        for (int i = 0; i < amount; i++) {
            
            JsonObject jo = new JsonObject();
            
            for (int t = 0; t < tokens.length; t++) {
                switch(tokens[t].trim()) {
                    case "fname":
                        jo.addProperty("fname", fnames[random.nextInt(fnames.length)]);
                        break;
                    case "lname":
                        jo.addProperty("lname", lnames[random.nextInt(lnames.length)]);
                        break;
                    case "street":
                        jo.addProperty("street", streets[random.nextInt(streets.length)]);
                        break;
                    case "city":
                        jo.addProperty("city", cities[random.nextInt(cities.length)]);
                        break;
                }
            }
            ja.add(jo);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(ja);
    }
    public static void main(String[] args) {
        System.out.println(getData(15,"fname     ,     lname, city, street"));
    }
}
