package de.cloudypanda.rapladelivery;

import de.cloudypanda.rapladelivery.models.IcalEventProperties;
import de.cloudypanda.rapladelivery.models.Lesson;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
        event.add(uidGenerator.generateUid());
        event.add(new Description(eventProperties.getDescription()));
        event.validate();

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

        events.forEach(calendar::add);
        RaplaDeliveryApplication.LOGGER.info("Adding events to calendar ...");
        return  calendar;
    }

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


            List<Lesson> lessons = mapper.GetClassesForKW(raplaUrl, cal.get(java.util.Calendar.WEEK_OF_YEAR));
            allLessons.addAll(lessons);

            cal.add(java.util.Calendar.WEEK_OF_YEAR, 1);
        }

        ICalFactory factory = new ICalFactory();
        List<VEvent> events = factory.convertLessonsToEvents(allLessons);
        RaplaDeliveryApplication.LOGGER.info("Creating Calendar ...");
        return factory.createCalendar(events);
    }
}
