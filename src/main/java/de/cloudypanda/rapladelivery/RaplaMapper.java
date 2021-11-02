package de.cloudypanda.rapladelivery;

import de.cloudypanda.rapladelivery.models.Lesson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class RaplaMapper {

    private final List<String> WeekList = Arrays.asList("Mo", "Di", "Mi" , "Do" , "Fr" , "Sa" , "So");

    public List<Lesson> GetClassesForKW(String raplaUrl, int kw) throws IOException {
        List<Lesson> classes = new ArrayList<>();

        //System.out.println("Request to " + raplaUrl);
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

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.WEEK_OF_YEAR, kw);
            cal.set(Calendar.DAY_OF_WEEK, WeekList.indexOf(day));
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.HOUR, Integer.parseInt(beginn.split(":")[0]));
            cal.set(Calendar.MINUTE, Integer.parseInt(beginn.split(":")[1]));

            long beginTime = cal.getTimeInMillis();

            cal.set(Calendar.HOUR, Integer.parseInt(end.split(":")[0]));
            cal.set(Calendar.MINUTE, Integer.parseInt(end.split(":")[1]));

            long endTime = cal.getTimeInMillis();


            Lesson newClass = new Lesson(beginTime, endTime, lastChanged, name, prof, info, comment);
            classes.add(newClass);
        });


        return classes;
    }
}
