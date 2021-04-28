package de.dhbw.boggle.value_objects;

import java.time.LocalDate;

public final class VO_Date {
    private final Integer day;
    private final Integer month;
    private final Integer year;

    public VO_Date(int day, int month, int year) {
        isValid(day, month, year);

        this.day = day;
        this.month = month;
        this.year = year;
    }

    public VO_Date(String stringDate) {
        String[] dateParts = stringDate.split("\\.");

        if(dateParts.length != 3) {
            throw new IllegalArgumentException("Given date in string format is not valid! Valid format: dd.MM.YYYY");
        }

        isValid(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));

        this.day = Integer.parseInt(dateParts[0]);
        this.month = Integer.parseInt(dateParts[1]);
        this.year = Integer.parseInt(dateParts[2]);
    }

    public String getDateSting() {
        return day.toString() + "." + month.toString() + "." + year.toString();
    }

    private void isValid(int day, int month, int year) {
        //Throws exception if wrong date
        LocalDate.of(year,month, day);
    }
}
