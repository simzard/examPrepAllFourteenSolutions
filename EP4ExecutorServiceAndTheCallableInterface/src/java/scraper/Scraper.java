/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scraper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import pojos.Group;

/**
 *
 * @author simon
 */
public class Scraper {

    public static int scrapeCount = 0;
    
    public static List<String> urls = new ArrayList<String>() {
        {

            //Class A
            add("http://cphbusinessjb.cloudapp.net/CA2/");
            add("http://ca2-ebski.rhcloud.com/CA2New/");
            add("http://ca2-chrislind.rhcloud.com/CA2Final/");
            add("http://ca2-pernille.rhcloud.com/NYCA2/");
            add("https://ca2-jonasrafn.rhcloud.com:8443/company.jsp");
            add("http://ca2javathehutt-smcphbusiness.rhcloud.com/ca2/index.jsp");

            //Class B
            add("https://ca2-ssteinaa.rhcloud.com/CA2/");
            add("http://tomcat-nharbo.rhcloud.com/CA2/");
            add("https://ca2-cphol24.rhcloud.com/3.semCa.2/");
            add("https://ca2-ksw.rhcloud.com/DeGuleSider/");
            add("http://ca2-ab207.rhcloud.com/CA2/index.html");
            add("http://ca2-sindt.rhcloud.com/CA2/index.jsp");
            add("http://ca2gruppe8-tocvfan.rhcloud.com/");
            add("https://ca-ichti.rhcloud.com/CA2/");

            //Class COS
            add("https://ca2-9fitteen.rhcloud.com:8443/CA2/");
            add("https://cagroup04-coolnerds.rhcloud.com/CA_v1/index.html");
            add("http://catwo-2ndsemester.rhcloud.com/CA2/");
        }
    };

    // return json list
    public static String beginScrape() throws InterruptedException, ExecutionException {
        System.out.println("Scraping ..." + ++scrapeCount);
        // use 4 threads - my cpu has 4 cores
        ExecutorService es = Executors.newFixedThreadPool(4);

        List<Future<Group>> futGroups = new ArrayList();

        // traverse the list make a taks for each url
        for (String url : urls) {
            ScraperTask task = new ScraperTask(url);
            futGroups.add(es.submit(task));
        }

        boolean done = false;
        List<Group> groups = new ArrayList();

        while (!done) {
            // stop when there are no more Futures left - meaning we are done
            if (futGroups.size() == 0) {
                break;
            }

            for (int i = 0; i < futGroups.size(); i++) {
                if (futGroups.get(i).isDone()) {
                    Group g = futGroups.get(i).get();
                    if (g != null) {
                        groups.add(g);
                    }
                    // group is saved on the return list
                    // now remove this Future from the list
                    futGroups.remove(futGroups.get(i));

                }
            }
        }
        es.shutdown();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(groups);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String groups = Scraper.beginScrape();

        System.out.println(groups);

    }

}
