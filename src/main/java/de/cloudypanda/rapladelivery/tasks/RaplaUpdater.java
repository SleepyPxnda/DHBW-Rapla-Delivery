package de.cloudypanda.rapladelivery.tasks;

import de.cloudypanda.rapladelivery.ICalGenerator;
import de.cloudypanda.rapladelivery.RaplaDeliveryApplication;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class RaplaUpdater {

    @Scheduled(fixedRate = 1000 * 60 * 60 * 12)
    public void UpdateRapla() throws IOException {

        Calendar cal = ICalGenerator.UpdateCalendar();
        System.out.println(new Date() + " - Updated Calendar \nEntries:" + cal.getComponents().getAll().size());
        RaplaDeliveryApplication.setCalendar(cal);
    }

}
