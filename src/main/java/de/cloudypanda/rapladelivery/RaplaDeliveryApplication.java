package de.cloudypanda.rapladelivery;

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
    
    public static final Logger LOGGER = LoggerFactory.getLogger(RaplaDeliveryApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(RaplaDeliveryApplication.class, args);
    }
}