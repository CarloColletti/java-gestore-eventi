package org.lessons.java.events;

public class Main {
    public static void main(String[] args) {
        EventManager NewEvent  = new EventManager();
        //scanner
        NewEvent.openScanner();
        //nuovo evento
        NewEvent.createNewEvent();
        //parte prenotazioni
        NewEvent.bookingMenu();
        //parte disdette
        NewEvent.cancelMenu();
        //chiudo scanner
        NewEvent.closeScanner();


    }
}