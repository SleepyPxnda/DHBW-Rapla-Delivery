package de.cloudypanda.rapladelivery.tasks;

import de.cloudypanda.rapladelivery.Ical.CalendarStorage;
import de.cloudypanda.rapladelivery.Ical.ICalCreator;
import de.cloudypanda.rapladelivery.RaplaDeliveryApplication;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.TimeZone;

@Component
public class RaplaUpdater {

    //Scheduled at every 3 Hours
    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)
    public void UpdateRapla() {

        java.util.Calendar IOSCalendar = java.util.Calendar.getInstance();
        IOSCalendar.setTime(new Date());
        IOSCalendar.setFirstDayOfWeek(java.util.Calendar.MONDAY);
        IOSCalendar.setMinimalDaysInFirstWeek(4);
        IOSCalendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));

        RaplaDeliveryApplication.LOGGER.info("Updating Google Calendar - " + new Date() + "-  Starting Week: " + IOSCalendar.get(java.util.Calendar.WEEK_OF_YEAR));
        Calendar IOSCalCalendar = ICalCreator.UpdateCalendar(IOSCalendar);
        CalendarStorage.setIOSCalendar(IOSCalCalendar);
        RaplaDeliveryApplication.LOGGER.info("Updated Google Calendar - Found " + IOSCalCalendar.getComponents().getAll().size() + " Entries");


        java.util.Calendar googleCalendar = java.util.Calendar.getInstance();
        googleCalendar.setTime(new Date());
        googleCalendar.setFirstDayOfWeek(java.util.Calendar.MONDAY);
        googleCalendar.setMinimalDaysInFirstWeek(4);
        googleCalendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        RaplaDeliveryApplication.LOGGER.info("Updating Google Calendar - " + new Date() + "-  Starting Week: " + googleCalendar.get(java.util.Calendar.WEEK_OF_YEAR));
        Calendar googleCalCalendar = ICalCreator.UpdateCalendar(googleCalendar);
        CalendarStorage.setGoogleCalendar(googleCalCalendar);
        RaplaDeliveryApplication.LOGGER.info("Updated Google Calendar - Found " + googleCalCalendar.getComponents().getAll().size() + " Entries");

    }

}
