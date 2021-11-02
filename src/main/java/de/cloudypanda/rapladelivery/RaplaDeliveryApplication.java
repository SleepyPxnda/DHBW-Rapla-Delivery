package de.cloudypanda.rapladelivery;

import net.fortuna.ical4j.model.Calendar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RaplaDeliveryApplication {

    private static Calendar calendar;

    public static void main(String[] args) {

        SpringApplication.run(RaplaDeliveryApplication.class, args);
    }

    public static Calendar getCalendar() {
        return calendar;
    }

    public static void setCalendar(Calendar calendar) {
        RaplaDeliveryApplication.calendar = calendar;
    }
}