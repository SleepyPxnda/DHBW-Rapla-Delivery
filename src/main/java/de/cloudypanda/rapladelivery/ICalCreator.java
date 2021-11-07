package de.cloudypanda.rapladelivery;

import de.cloudypanda.rapladelivery.models.IcalEventProperties;
import de.cloudypanda.rapladelivery.models.Lesson;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Factory for methods used to generate ical-file
 */
public class ICalCreator {

    private final UidGenerator uidGenerator;

    public ICalCreator() {
        uidGenerator = new RandomUidGenerator();
    }

    /**
     * Generates an event
     * @param eventProperties properties used for the event generation
     * @return an event
     */
    public VEvent createEvent(IcalEventProperties eventProperties) {
        VEvent event = new VEvent(eventProperties.getStartTime(), eventProperties.getEndTime(), eventProperties.getName());
        event.add(uidGenerator.generateUid());
        event.add(new Description(eventProperties.getDescription()));

        return event;
    }

    public List<VEvent> convertLessonsToEvents(List<Lesson> lessons){
        List<VEvent> events = new ArrayList<>();

        lessons.forEach(lesson -> {
            LocalDateTime start = LocalDateTime.ofInstant(Instant.ofEpochMilli(lesson.getStartTime()), ZoneId.systemDefault());
            LocalDateTime end = LocalDateTime.ofInstant(Instant.ofEpochMilli(lesson.getEndTime()), ZoneId.systemDefault());
            IcalEventProperties props = new IcalEventProperties(uidGenerator.generateUid(), start, end, lesson.getTitle(), lesson.getProfessor());

            VEvent event = createEvent(props);
            events.add(event);
        });
        return events;
    }

    public Calendar createCalendar(List<VEvent> events){
        Calendar calendar = new Calendar();
        calendar.add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        calendar.add(Version.VERSION_2_0);
        calendar.add(CalScale.GREGORIAN);

        RaplaDeliveryApplication.LOGGER.info("Adding events to calendar ...");
        events.forEach(calendar::add);
        RaplaDeliveryApplication.LOGGER.info("Events added: ");
        events.forEach(event -> {
            PropertyList properties = event.getProperties();
            RaplaDeliveryApplication.LOGGER.info(properties.getFirst("SUMMARY").get()
                    + " " + properties.getFirst("DTSTART").get()
                    + " " + properties.getFirst("DTEND").get());
        });
        return calendar;
    }

    public static Calendar UpdateCalendar(java.util.Calendar cal) {
        RaplaMapper mapper = new RaplaMapper();
        List<Lesson> allLessons = new ArrayList<>();

        for (int i = 0; i <= 6; i++) {

            int dayOfMonth = cal.get(java.util.Calendar.DAY_OF_MONTH);
            int month = cal.get(java.util.Calendar.MONTH) + 1;
            int year = cal.get(java.util.Calendar.YEAR);

            String raplaUrl = String.format("https://rapla.dhbw-stuttgart.de/rapla?key=txB1FOi5xd1wUJBWuX8lJhGDUgtMSFmnKLgAG_NVMhBcTT6puuu_njyaHFsjO78f&day=%s&month=%s&year=%s",
                    dayOfMonth, month, year);

            RaplaDeliveryApplication.LOGGER.info("Mapping Events for Week: " + cal.get(java.util.Calendar.WEEK_OF_YEAR));
            List<Lesson> lessons = mapper.GetClassesForKW(raplaUrl, cal.get(java.util.Calendar.WEEK_OF_YEAR), cal);

            if(lessons == null){
                RaplaDeliveryApplication.LOGGER.error("Cannot update Calendar - Retrying next cycle");
                return null;
            }


            allLessons.addAll(lessons);
            cal.add(java.util.Calendar.WEEK_OF_YEAR, 1);
        }

        ICalCreator factory = new ICalCreator();
        List<VEvent> events = factory.convertLessonsToEvents(allLessons);

        RaplaDeliveryApplication.LOGGER.info("Creating Calendar ...");
        return factory.createCalendar(events);
    }
}
