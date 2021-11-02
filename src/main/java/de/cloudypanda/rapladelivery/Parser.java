package de.cloudypanda.rapladelivery;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.util.List;

public class Parser {

    public static void main(String[] args) throws IOException {

        RaplaMapper mapper = new RaplaMapper();

        LocalDate currentDate = LocalDate.of(2021,9,27);
        for(int i = 0; i <= 15; i++){

            currentDate = currentDate.plusWeeks(i);

            int weekOfYear = currentDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
            LocalDate dayInWeek = currentDate.with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weekOfYear);
            LocalDate start = dayInWeek.with(DayOfWeek.MONDAY);


            String raplaUrl = String.format("https://rapla.dhbw-stuttgart.de/rapla?key=txB1FOi5xd1wUJBWuX8lJhGDUgtMSFmnKLgAG_NVMhBcTT6puuu_njyaHFsjO78f&day=%s&month=%s&year=%s",
                    start.getDayOfMonth() , start.getMonth().getValue(), start.getYear());

            List<Lesson> lessons = mapper.GetClassesForKW(raplaUrl, weekOfYear);

            lessons.forEach(System.out::println);
        }

    }


}
