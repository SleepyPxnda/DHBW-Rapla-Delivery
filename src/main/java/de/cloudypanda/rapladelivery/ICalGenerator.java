package de.cloudypanda.rapladelivery;

import de.cloudypanda.rapladelivery.models.Lesson;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;

public class ICalGenerator {

    public static Calendar UpdateCalendar() throws IOException {
        RaplaMapper mapper = new RaplaMapper();
        List<Lesson> allLessons = new ArrayList<>();

        LocalDate currentDate = LocalDate.of(2021, 11, 1);
        for (int i = 0; i <= 15; i++) {

            currentDate = currentDate.plusWeeks(i);

            int weekOfYear = currentDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
            LocalDate dayInWeek = currentDate.with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weekOfYear);
            LocalDate start = dayInWeek.with(DayOfWeek.MONDAY);


            String raplaUrl = String.format("https://rapla.dhbw-stuttgart.de/rapla?key=txB1FOi5xd1wUJBWuX8lJhGDUgtMSFmnKLgAG_NVMhBcTT6puuu_njyaHFsjO78f&day=%s&month=%s&year=%s",
                    start.getDayOfMonth(), start.getMonth().getValue(), start.getYear());

            List<Lesson> lessons = mapper.GetClassesForKW(raplaUrl, weekOfYear);
            allLessons.addAll(lessons);
        }

        ICalFactory factory = new ICalFactory();
        List<VEvent> events = factory.convertLessonsToEvents(allLessons);

        return factory.createCalendar(events);
    }

}
