package org.lessons.java.events;

//IMPORT
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class EventManager {

    //attribute
    Event event;

    //method
    public void createNewEvent() {
        Scanner scan = new Scanner(System.in);
        //nome dell'evento
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
                    //se il giorno non è compreso fra 1 e 31
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
                    //se l'anno è negativo
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
        //tengo conto del numero massimo dei posti
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

        scan.close();
    }

}