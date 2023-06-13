package org.lessons.java.events;

//IMPORT
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Concert extends Event{

    //attribute
    private LocalTime time;
    private BigDecimal price;
    private final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    //constructor
    public Concert(String title, LocalDate date, int SEAT_CAPACITY, LocalTime time, BigDecimal price) throws RuntimeException {
        //constructor event
        super(title, date, SEAT_CAPACITY);
        this.time = time;
        if (isPriceValid(price)) {
            this.price = price;
        } else {
            throw new RuntimeException("Prezzo non valido");
        }

    }

    //getter
    public LocalTime getTime() {
        return time;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public String getFormattedTime(){
        return time.format(TIME_FORMATTER);
    }
    public String getFormattedDateTime() {
        return getFormattedDate() + " - " + getFormattedTime();
    }
    public String getFormattedPrice() {
        return new DecimalFormat("###,###.00€").format(price);
    }

    //setter
    public void setTime(LocalTime time) {
        this.time = time;
    }
    public void setPrice(BigDecimal price) {
        if (isPriceValid(price)) {
            this.price = price;
        } else {
            throw new RuntimeException("Prezzo non valido");
        }
    }

    //method
    @Override
    public String toString() {
        return "Data e ora: " + getFormattedDateTime() + ", Titolo: " + getTitleEvent() + ", Prezzo: " + getFormattedPrice();
    }
    private boolean isPriceValid(BigDecimal price) {
        return price.compareTo(new BigDecimal(0)) >= 0;
    }

}
