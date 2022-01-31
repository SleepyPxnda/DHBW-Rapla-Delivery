package de.cloudypanda.rapladelivery;

import de.cloudypanda.rapladelivery.tasks.RaplaUpdater;
import net.fortuna.ical4j.model.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class RaplaDeliveryApplication {

    private static Calendar calendar;
    public static String Rapla_URL;
    public static final Logger LOGGER = LoggerFactory.getLogger(RaplaDeliveryApplication.class);

    public static void main(String[] args) {
        Rapla_URL = System.getenv("RAPLA_URL");

        LOGGER.info("Started Delivery with URL: '" + Rapla_URL + "'");
        SpringApplication.run(RaplaDeliveryApplication.class, args);
    }
}