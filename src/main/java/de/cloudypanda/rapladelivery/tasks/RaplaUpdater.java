package de.cloudypanda.rapladelivery.tasks;

import de.cloudypanda.rapladelivery.Ical.CalendarStorage;
import de.cloudypanda.rapladelivery.Ical.ICalCreator;
import de.cloudypanda.rapladelivery.RaplaDeliveryApplication;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

@Component
public class RaplaUpdater {

    //Scheduled at every 3 Hours
    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)
    public void UpdateRapla() {

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setFirstDayOfWeek(java.util.Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        calendar.setTime(new Date());

        RaplaDeliveryApplication.LOGGER.info("Updating IOS Calendar - " + new Date() + "-  Starting Week: " + calendar.get(java.util.Calendar.WEEK_OF_YEAR));
        Calendar IOSCalCalendar = ICalCreator.UpdateCalendar(calendar);
        CalendarStorage.setIOSCalendar(IOSCalCalendar);
        RaplaDeliveryApplication.LOGGER.info("Updated IOS Calendar - Found " + IOSCalCalendar.getComponents().getAll().size() + " Entries");

        calendar = java.util.Calendar.getInstance();
        calendar.setFirstDayOfWeek(java.util.Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        calendar.setTime(new Date());


        RaplaDeliveryApplication.LOGGER.info("Updating Google Calendar - " + new Date() + "-  Starting Week: " + calendar.get(java.util.Calendar.WEEK_OF_YEAR));
        Calendar googleCalCalendar = ICalCreator.UpdateCalendar(calendar);
        CalendarStorage.setGoogleCalendar(googleCalCalendar);
        RaplaDeliveryApplication.LOGGER.info("Updated Google Calendar - Found " + googleCalCalendar.getComponents().getAll().size() + " Entries");
    }

}
