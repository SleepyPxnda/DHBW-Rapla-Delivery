package de.cloudypanda.rapladelivery.controller;

import de.cloudypanda.rapladelivery.RaplaDeliveryApplication;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarController {


    @GetMapping("calendars/TINF19DRapla.ics")
    @ResponseBody
    public ResponseEntity GetLessonsToday() {

        byte[] calendarByte = RaplaDeliveryApplication.getCalendar().toString().getBytes();
        Resource resource = new ByteArrayResource(calendarByte);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=TINF19DRapla.ics");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok().headers(header).contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}


