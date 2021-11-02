package de.cloudypanda.rapladelivery.tasks;

import de.cloudypanda.rapladelivery.ICalFactory;
import de.cloudypanda.rapladelivery.RaplaDeliveryApplication;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RaplaUpdater {

    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void UpdateRapla() throws IOException {

        Calendar cal = ICalFactory.UpdateCalendar();
        RaplaDeliveryApplication.LOGGER.info("Updated Calendar - Found " + cal.getComponents().getAll().size() + " Entries");
        RaplaDeliveryApplication.setCalendar(cal);
    }

}
