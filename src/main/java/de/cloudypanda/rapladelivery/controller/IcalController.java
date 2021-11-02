package de.cloudypanda.rapladelivery.controller;

import de.cloudypanda.rapladelivery.RaplaMapper;
import de.cloudypanda.rapladelivery.models.ICalFactory;
import de.cloudypanda.rapladelivery.models.Lesson;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;

@RestController
public class IcalController {


    @GetMapping("rapla.ics")
    public ResponseEntity GetLessonsToday() throws IOException {

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

        Calendar cal = factory.createCalendar(events);



        byte[] calendarByte = cal.toString().getBytes();
        Resource resource = new ByteArrayResource(calendarByte);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=mycalendar.ics");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok().headers(header).contentType(MediaType.
                        APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}


