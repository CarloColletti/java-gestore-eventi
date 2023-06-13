package org.lessons.java.events;

//IMPORT
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event {

    //ATTRIBUTI -----------------------------------------------------------------------------------------
    private String titleEvent;
    private LocalDate date;
    private final int CAPACITY;
    private int bookedSeats;
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    //COSTRUTTORE
    public Event(String titleEvent, LocalDate date, int CAPACITY) throws RuntimeException {
        //titolo
        if(isTitleEventValid(titleEvent)) {
            this.titleEvent = titleEvent;
        } else {
            throw new RuntimeException("Il titolo è un campo obbligatorio.");
        }
        //data
        if(isDateValid(date)) {
            this.date = date;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            throw new RuntimeException("La data " + date.format(formatter) + " non è valida. Deve essere successiva alla data odierna.");
        }
        //numero di posti totali
        if(isSeatCapacityValid(CAPACITY)) {
            this.CAPACITY = CAPACITY;
        } else {
            throw new RuntimeException("Il numero di posti " + CAPACITY + " non è valido. Deve essere maggiore di 0.");
        }
        //posti prenotati
        this.bookedSeats = 0;
    }


    //GETTER
    public String getTitleEvent() {
        return titleEvent;
    }
    public LocalDate getDate() {
        return date;
    }
    public int getCAPACITY() {
        return CAPACITY;
    }
    public int getBookedSeats() {
        return bookedSeats;
    }
    public int getAvailableSeats() {
        return CAPACITY - bookedSeats;
    }
    public String getFormattedDate(){
        return date.format(DATE_FORMATTER);
    }

    //SETTER
    public void setTitleEvent(String titleEvent) throws RuntimeException {
        if(isTitleEventValid(titleEvent)) {
            this.titleEvent = titleEvent;
        } else {
            throw new RuntimeException("Il titolo è un campo obbligatorio.");
        }
    }
    public void setDate(LocalDate date) throws RuntimeException {
        if(isDateValid(date)) {
            this.date = date;
        } else {
            throw new RuntimeException("La data " + date.format(DATE_FORMATTER) + " non è valida. Deve essere successiva alla data odierna.");
        }
    }


    //METODI
    public void bookSeat() throws RuntimeException {
        //se la data di oggi è successiva alla data dell'evento non è possibile prenotare
        if(isEventPast()) {
            throw new RuntimeException("L'evento è trascorso. Non è possibile prenotare");
        }
        //se i posti disponibili sono pari o minori di 0 non è possibile prenotare
        if(getAvailableSeats() <= 0) {
            throw new RuntimeException("Non ci sono posti disponibili. Non è possibile prenotare");
        }

        //aumento di 1 i posti prenotati
        this.bookedSeats ++;
    }
    public void cancelSeat() {
        //se la data di oggi è successiva alla data dell'evento non è possibile disdire
        if(isEventPast()) {
            throw new RuntimeException("L'evento è trascorso. Non è possibile disdire");
        }
        //se i posti prenotati sono pari o minori di 0 non è possibile disdire
        if(this.bookedSeats <= 0) {
            throw new RuntimeException("Non ci sono posti prenotati. Non è possibile disdire");
        }
        //diminuisco di 1 i posti prenotati
        this.bookedSeats --;
    }

    //override
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Titolo: " + titleEvent + ", Data: " + date.format(formatter);
    }

    //validatori
    private boolean isEventPast() {
        //prendo la data di oggi
        LocalDate today = LocalDate.now();
        //la confronto con la data dell'evento
        return today.isAfter(this.date);
    }
    public static boolean isDateValid(LocalDate date) {
        //prendo la data di oggi
        LocalDate today = LocalDate.now();
        //la confronto con la data ricevuta
        return date.isAfter(today);
    }
    public static boolean isSeatCapacityValid(int CAPACITY) {
        //il numero di posti totali deve essere maggiore di 0
        return CAPACITY > 0;
    }
    public static boolean isTitleEventValid(String titleEvent) {
        return titleEvent != null && !titleEvent.isBlank();
    }

}