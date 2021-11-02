package de.cloudypanda.rapladelivery;

import de.cloudypanda.rapladelivery.models.Lesson;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ICalGenerator {

    public static Calendar UpdateCalendar() throws IOException {
        RaplaMapper mapper = new RaplaMapper();
        List<Lesson> allLessons = new ArrayList<>();

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(new Date());

        for (int i = 0; i <= 2; i++) {

            int dayOfMonth = cal.get(java.util.Calendar.DAY_OF_MONTH);
            int month = cal.get(java.util.Calendar.MONTH) + 1;
            int year = cal.get(java.util.Calendar.YEAR);

            String raplaUrl = String.format("https://rapla.dhbw-stuttgart.de/rapla?key=txB1FOi5xd1wUJBWuX8lJhGDUgtMSFmnKLgAG_NVMhBcTT6puuu_njyaHFsjO78f&day=%s&month=%s&year=%s",
                    dayOfMonth, month, year);

            System.out.println("Week:" + cal.get(java.util.Calendar.WEEK_OF_YEAR));
            List<Lesson> lessons = mapper.GetClassesForKW(raplaUrl, cal.get(java.util.Calendar.WEEK_OF_YEAR));
            allLessons.addAll(lessons);

            cal.add(java.util.Calendar.WEEK_OF_YEAR, 1);
        }

        ICalFactory factory = new ICalFactory();
        List<VEvent> events = factory.convertLessonsToEvents(allLessons);

        return factory.createCalendar(events);
    }

}
