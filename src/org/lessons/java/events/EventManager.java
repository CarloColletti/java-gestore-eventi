package org.lessons.java.events;

//IMPORT
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class EventManager {

    //attribute
    Event event;

    Scanner scan;
    //getter

    public Event getEvent() {
        return event;
    }

    //method
    public void createNewEvent() {
        //testo accoglienza
        System.out.println("Benvenuto, nel programma per la creaione e gestione degli eventi");
        String titleEvent;
        do {
            System.out.println("inserisci il nome dell'evento. ");
            titleEvent = scan.nextLine();
            //se il valore è errato stampo il relativo errore
            if(!Event.isTitleEventValid(titleEvent)) {
                System.out.println("Il titolo è un campo obbligatorio.");
            }
            //continua a chiedere finché non viene inserito un valore valido
        } while (!Event.isTitleEventValid(titleEvent));
        //get data
        boolean isValid = false;
        LocalDate date = null;
        do {
            System.out.println("Quando ci sarà l'evento? ");
            //inserisco il giorno
            int day = 0;
            do {
                System.out.print("Giorno: ");
                try {
                    day = Integer.parseInt(scan.nextLine());
                    //se si inserisce un valore che non sia tra 1-31 stampa l'errore e richiede il valore
                    if(day < 1 || day > 31 ) {
                        System.out.println("Il giorno deve essere un numero positivo compreso fra 1 e 31");
                    }
                } catch (NumberFormatException e){
                    System.out.println("Inserisci un numero");
                }
            } while (day < 1 || day > 31);
            //prendo il mese
            int month = 0;
            do {
                System.out.print("Mese: ");
                try {
                    month = Integer.parseInt(scan.nextLine());
                    //se non inserisce un mese tra il 1-12 richiede
                    if(month < 1 || month > 12 ) {
                        System.out.println("Il mese deve essere un numero positivo compreso fra 1 e 12");
                    }
                } catch (NumberFormatException e){
                    System.out.println("Inserisci un numero");
                }
            } while (month < 1 || month > 12);
            //prendo l'anno
            int year = 0;
            do {
                System.out.print("Anno: ");
                try {
                    year = Integer.parseInt(scan.nextLine());
                    //se l'anno è negativo o scritto errato richiedo
                    if(year < 0) {
                        System.out.println("L'anno deve essere un numero positivo");
                    }
                } catch (NumberFormatException e){
                    System.out.println("Inserisci un numero");
                }
            } while (year < 0);
            //creo la data
            try {
                date = LocalDate.of(year, month, day);
                //se la data non è valida stampo un messaggio
                if(!Event.isDateValid(date)) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    System.out.println("La data " + date.format(formatter) + " non è valida. Deve essere successiva alla data odierna.");
                } else {
                    isValid = true;
                }
            } catch (DateTimeException e) {
                System.out.println(e.getMessage());
            }
            //ripeto finché non ho una data valida
        } while (!isValid);
        //richiesta numeri posti
        int seatCapacity = 0;
        do {
            System.out.println("Quanti posti disponibili ci sono?");
            try {
                seatCapacity = Integer.parseInt(scan.nextLine());
                //se il valore non è valido
                if(!Event.isSeatCapacityValid(seatCapacity)) {
                    System.out.println("Il numero di posti " + seatCapacity + " non è valido. Deve essere maggiore di 0.");
                }
            } catch (NumberFormatException e){
                System.out.println("Inserisci un numero");
            }
            //ripeto finché non ho un numero valido
        } while (!Event.isSeatCapacityValid(seatCapacity));
        //creo il nuovo evento
        try {
            this.event = new Event(titleEvent,date,seatCapacity);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        //stampo l'evento
        System.out.println(event);
    }

    public void openScanner() {
        scan = new Scanner(System.in);
    }
    public void closeScanner() {
        if(scan != null) {
            scan.close();
        }
    }


    //parte delle prenotazioni
    public void bookingMenu() {
        System.out.println("Benvenuto nella sezione delle prenotazioni");
        String choice;
        do {
            System.out.println("Vuoi effettuare una prenotazione? y/n");
            choice = scan.nextLine();
            if(!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n")) {
                System.out.println("L'input " + choice + " non è valido. Inserire 'y' o 'n'.");
            }
        } while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n"));
        boolean letsBooking = choice.equalsIgnoreCase("y");
        //se l'utente vuole procedere
        if(letsBooking) {
            //chiedo il numnero delle prenotazioni
            int bookingNum = 0;
            do {
                System.out.println("Inserisca il numero di posti da prenotare");
                try {
                    bookingNum = Integer.parseInt(scan.nextLine());
                    if(bookingNum <= 0) {
                        System.out.println("Il numero deve essere maggiore di 0");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Inserisci un numero");
                }
            } while(bookingNum <= 0);

            //effetuo la prenotazione n volte rispetto a quello digitato dell'utente
            try {
                for (int i = 0; i < bookingNum; i++) {
                    event.bookSeat();
                }
                System.out.println("La prenotazione per: " + bookingNum +" è avvenuta con successo");
                System.out.println("Posti prenotati: " + event.getBookedSeats());
                System.out.println("Posti disponibili: " + event.getAvailableSeats());
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }


    }


    // parte per la cancellazione
    public void cancelMenu() {
        System.out.println("Benvenuto nella sezione delle cancellazioni");
        String choice;
        do {
            System.out.println("Vuoi effettuare una disdetta? y/n");
            choice = scan.nextLine();
            if(!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n")) {
                System.out.println("L'input " + choice + " non è valido. Inserire 'y' o 'n'.");
            }
        } while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n"));

        boolean letsCancel = choice.equalsIgnoreCase("y");

        //se l'utente vuole procedere
        if(letsCancel) {
            //raccolgo il numero delle disdette
            int cancelNum = 0;
            do {
                System.out.println("Quanti posti vuoi disdire?");
                try {
                    cancelNum = Integer.parseInt(scan.nextLine());
                    if(cancelNum <= 0) {
                        System.out.println("Il numero deve essere maggiore di 0");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Inserisci un numero");
                }
            } while(cancelNum <= 0);
            try {
                for (int i = 0; i < cancelNum; i++) {
                    event.cancelSeat();
                }
                //se va tutto bene (cancelSeat non ha tirato eccezioni)
                System.out.println(cancelNum +" posti sono stati disdetti con successo");
                System.out.println("Posti prenotati rimasti dopo la disdetta: " + event.getBookedSeats());
                System.out.println("Posti rimanenti " + event.getAvailableSeats());
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}