package de.cloudypanda.rapladelivery;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RaplaMapper {

    public List<Lesson> GetClassesForKW(String raplaUrl, long kw) throws IOException {
        List<Lesson> classes = new ArrayList<>();

        System.out.println("Request to " + raplaUrl);
        Document doc = Jsoup.connect(raplaUrl).get();
        Elements lessons = doc.select("td.week_block a span.tooltip");

        lessons.forEach((lesson) -> {

            //First Div
            String lastChanged = lesson.select("div:first-of-type").text();

            //Always the Second Div
            String date = lesson.select("div:nth-of-type(2)").text();

            Element additionalInfos = lesson.select("tbody").get(0);

            //2. td in 1. tr in tbody in table
            String name = additionalInfos.select("tr:nth-of-type(1) td:nth-of-type(2)").text();

            //2. td in 3. tr in tbody in table
            String comment = additionalInfos.select("tr:nth-of-type(3) td:nth-of-type(2)").text();

            //2. td in 4. tr in tbody in table
            String info = additionalInfos.select("tr:nth-of-type(4) td:nth-of-type(2)").text();

            String prof = "";
            if(additionalInfos.childrenSize() != 6){
                prof = additionalInfos.select("tr:nth-of-type("+ additionalInfos.childrenSize() +") td:nth-of-type(2)").text();
            }

            String beginn = "";
            String end = date.split("-")[1];
            if(date.split(" ")[2].equalsIgnoreCase("wöchentlich")){
                beginn = date.split(" ")[1].split("-")[0];
                end = end.substring(0, 5);
            } else {
                String tembeginn = date.split("-")[0];
                beginn = tembeginn.substring(tembeginn.length() - 5);
            }

            String day = date.split(" ")[0];
            Lesson newClass = new Lesson(kw, day, lastChanged, beginn, end, name, prof, info, comment);
            classes.add(newClass);
        });


        return classes;
    }
}
