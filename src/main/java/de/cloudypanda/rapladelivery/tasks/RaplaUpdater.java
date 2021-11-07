package de.cloudypanda.rapladelivery.tasks;

import de.cloudypanda.rapladelivery.ICalCreator;
import de.cloudypanda.rapladelivery.RaplaDeliveryApplication;
import de.cloudypanda.rapladelivery.RaplaMapper;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

@Component
public class RaplaUpdater {

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void UpdateRapla() {

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.setFirstDayOfWeek(java.util.Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));

        //System.out.println(calendar);

        RaplaDeliveryApplication.LOGGER.info("Updating Calendar - " + new Date() + "-  Starting Week: " + calendar.get(java.util.Calendar.WEEK_OF_YEAR));

        Calendar cal = ICalCreator.UpdateCalendar(calendar);
        RaplaDeliveryApplication.LOGGER.info("Updated Calendar - Found " + cal.getComponents().getAll().size() + " Entries");
        RaplaDeliveryApplication.setCalendar(cal);
    }

}
