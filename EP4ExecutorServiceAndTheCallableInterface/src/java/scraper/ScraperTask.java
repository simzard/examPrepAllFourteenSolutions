/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scraper;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pojos.Group;

/**
 *
 * @author simon
 */
public class ScraperTask implements Callable {

    private String url;

    public ScraperTask(String url) {
        this.url = url;
    }

    @Override
    public Object call() throws Exception {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException ex) {
            //return new Group("url not found", "url not found", "url not found");
            return null;//new Group("","","");
            //Logger.getLogger(ScraperTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        Elements authorsElem = doc.select("#authors");
        String authors = authorsElem.text();

        Elements classStrElem = doc.select("#class");
        String classStr = classStrElem.text();

        Elements groupNoElem = doc.select("#group");
        String groupNo = groupNoElem.text();

        //check if there is actual content otherwise return null;
        if (!(authors.equals("") && classStr.equals("") && groupNo.equals("")) )
            return new Group(authors, classStr, groupNo);
        return null;
    }

}
