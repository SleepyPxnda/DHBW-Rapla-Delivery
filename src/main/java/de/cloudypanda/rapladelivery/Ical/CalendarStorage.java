package de.cloudypanda.rapladelivery.Ical;

import net.fortuna.ical4j.model.Calendar;

public class CalendarStorage {
    private static Calendar IOSCalendar;
    private static Calendar GoogleCalendar;


    public static Calendar getIOSCalendar() {
        return IOSCalendar;
    }

    public static void setIOSCalendar(Calendar IOSCalendar) {
        CalendarStorage.IOSCalendar = IOSCalendar;
    }

    public static Calendar getGoogleCalendar() {
        return GoogleCalendar;
    }

    public static void setGoogleCalendar(Calendar googleCalendar) {
        GoogleCalendar = googleCalendar;
    }

}
