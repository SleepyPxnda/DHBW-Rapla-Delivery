package de.cloudypanda.rapladelivery;

import de.cloudypanda.rapladelivery.models.IcalEventProperties;
import de.cloudypanda.rapladelivery.models.Lesson;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;
import org.apache.tomcat.jni.Local;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Factory for methods used to generate ical-file
 */
public class ICalFactory {

    private final UidGenerator uidGenerator;

    public ICalFactory() {
        uidGenerator = new RandomUidGenerator();
    }

    /**
     * Generates an event
     * @param eventProperties properties used for the event generation
     * @return an event
     */
    public VEvent createEvent(IcalEventProperties eventProperties) {
        VEvent event = new VEvent(eventProperties.getStartTime(), eventProperties.getEndTime(), eventProperties.getName());
        event.add(new Uid());
        event.add(new Description(eventProperties.getDescription()));
        event.validate();

        return event;
    }

    public List<VEvent> convertLessonsToEvents(List<Lesson> lessons){
        List<VEvent> events = new ArrayList<>();

        lessons.forEach(lesson -> {
            LocalDateTime start = LocalDateTime.ofInstant(Instant.ofEpochMilli(lesson.getStartTime()), ZoneId.of("GMT+2"));
            LocalDateTime end = LocalDateTime.ofInstant(Instant.ofEpochMilli(lesson.getEndTime()), ZoneId.of("GMT+2"));
            IcalEventProperties props = new IcalEventProperties(uidGenerator.generateUid(), start, end, lesson.getTitle(), lesson.getProfessor());

            events.add(createEvent(props));
        });
        return events;
    }

    public Calendar createCalendar(List<VEvent> events){
        Calendar calendar = new Calendar();
        calendar.add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        calendar.add(Version.VERSION_2_0);
        calendar.add(CalScale.GREGORIAN);

        events.forEach(calendar::add);
        return  calendar;
    }
}
