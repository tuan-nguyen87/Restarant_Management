package Server;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ReservationController {
    private static final String COLUMN_FORMAT = "%-12s%-13s%-10s%-7s%-7s%-24s\n";
    private static final String RESERVATION_FORMAT = "%-12s%-13s%-10s%-7d%-7d%-24s\n";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static final LocalTime OPEN_TIME = LocalTime.of(10, 0);
    private static final LocalTime CLOSE_TIME = LocalTime.of(21, 0);
    private static final int  RESERVATION_DURATION_MINUTES = 90;
    private static ArrayList<Reservation> reservations;

    public ReservationController() {
        reservations = new ArrayList<Reservation>();
    }

    public void makeReservation(Scanner scan) {
        scan.nextLine(); // Flush scanner
        ArrayList<LocalTime> timeSlots = generateTimeSlotList();

        if (TableController.hasOpenTables()) {
            String name = "";
            int sizeOfParty = 0;
            Table table = null;
            System.out.println("-----------------------------------------------------");
            System.out.println("Type 'cancel' to return to the previous menu");
            System.out.println("Making a Reservation\n");
            System.out.println("Name:");
            name = scan.nextLine();
            if (name.equalsIgnoreCase("cancel")) {
                return;
            }
            System.out.println("Party Size:");
            sizeOfParty = getIntAsInput(scan, false);
            table = TableController.selectTable(scan);
            if (table != null) {
                LocalDateTime reservationDateTime = selectDateTime(scan, timeSlots,false, true);
                Reservation reservation = new Reservation(name, sizeOfParty, table, reservationDateTime);
                reservations.add(reservation);
                System.out.println("Created a new Reservation");
            } else {
                System.out.println("Failed to create Reservation");
            }
        } else {
            System.out.println("No Table openings found. Add Tables in the Table Menu first");
        }
    }

    // Prompts user to update the name of a Reservation
    public void changeReservationName(Scanner scan) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Changing Party Name");
        Reservation reservation = findReservation(scan);
        if (reservation != null) {
            System.out.println("-----------------------------------------------------");
            System.out.println("Enter a new Name for the Party:");
            String input = scan.nextLine();
            // Update Reservation in list
            int index = reservations.indexOf(reservation);
            reservation.setPartyName(input);
            reservations.set(index, reservation);
            System.out.println("Reservation party name has been changed to: " + input);
        } else {
            System.out.println("Failed to change the Party Name");
        }
    }

    // Prompts user to update party size of a Reservation
    public void changeReservationPartySize(Scanner scan) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Changing Party Size");
        Reservation reservation = findReservation(scan);
        if (reservation != null) {
            System.out.println("-----------------------------------------------------");
            System.out.println("Enter the new party size:");
            int partySize = getIntAsInput(scan, false);
            if (partySize != -1) {
                // Update Reservation in list
                int index = reservations.indexOf(reservation);
                reservation.setSizeOfParty(partySize);
                reservations.set(index, reservation);
                System.out.println("Reservation party name has been changed to: " + partySize);
            }
        } else {
            System.out.println("Failed to change the Party Name");
        }
    }

    // Prompts user to move Reservation from one date and time to another
    public void moveReservation(Scanner scan) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Moving Reservation to new Date");
        Reservation reservation = findReservation(scan);

        // Getting old dates
        if (reservation != null) {
            System.out.println("-----------------------------------------------------");
            System.out.println("Select a new Date and Time");
            LocalDateTime oldDateTime = reservation.getReservedDateTime();
            String oldDate = oldDateTime.toLocalDate().format(DATE_FORMAT);
            String oldTime = oldDateTime.toLocalTime().format(TIME_FORMAT);

            // Getting new dates
            LocalDateTime newDateTime = selectDateTime(scan, generateTimeSlotList(),false, true);
            if (newDateTime != null) {
                String newDate = newDateTime.toLocalDate().format(DATE_FORMAT);
                String newTime = newDateTime.toLocalTime().format(TIME_FORMAT);

                // Update Reservation in list
                int index = reservations.indexOf(reservation);
                reservation.setDateTime(newDateTime);
                reservations.set(index, reservation);
                System.out.println("Reservation has been moved from " + oldDate + " at " + oldTime +
                        " to " + newDate + " at " + newTime);
            } else {
                System.out.println("Failed to move Reservation");
            }
        }
    }

    // Prompts user for a date and time. If a corresponding reservation exists, cancel it.
    public void cancelReservation(Scanner scan) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Cancelling Reservation");
        Reservation reservation = findReservation(scan);
        if (reservation != null) {
            reservations.remove(reservation);
            System.out.println("Reservation has been cancelled");
        }
    }

    // A menu to find a particular Reservation given a date and time.
    // Returns null if nothing is found or the menu is exited
    public Reservation findReservation(Scanner scan) {
        scan.nextLine(); //flush scanner
        LocalDateTime dateTime = selectDateTime(scan, null,true,false);
        if (dateTime != null) {
            ArrayList<Reservation> dateFilteredReservations = findReservationByDate(reservations, dateTime.toLocalDate());
            if (dateFilteredReservations.isEmpty()) {
                System.out.println("No reservations found");
                return null;
            } else {
                ArrayList<Reservation> dateTimeFilteredReservations = findReservationByTime(dateFilteredReservations, dateTime.toLocalTime());
                if (dateTimeFilteredReservations.isEmpty()) {
                    System.out.println("No reservations found");
                    return null;
                } else {
                    return dateTimeFilteredReservations.get(0);
                }
            }
        }
        return null;
    }

    // Displays all reservations and their details.
    // Column spacing depends on character length of Reservation properties
    // Displays all reservations for a given date
    public void displayReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found");
        } else {
            System.out.println("----------------------------------------------------------------");
            System.out.printf(COLUMN_FORMAT, "Date", "Start Time", "End Time", "Table",  "Size", "Party Name");
            System.out.println("----------------------------------------------------------------");
            for (Reservation r : reservations) {
                r.display(RESERVATION_FORMAT, RESERVATION_DURATION_MINUTES);
            }
            System.out.println("----------------------------------------------------------------\n");
        }
    }

    // Displays all reservations in the given ArrayList
    public void displayReservations(ArrayList<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found");
        } else {
            System.out.println("----------------------------------------------------------------");
            System.out.printf(COLUMN_FORMAT, "Date", "Start Time", "End Time", "Table",  "Size", "Party Name");
            System.out.println("----------------------------------------------------------------");
            for (Reservation r : reservations) {
                r.display(RESERVATION_FORMAT, RESERVATION_DURATION_MINUTES);
            }
            System.out.println("----------------------------------------------------------------\n");
        }
    }

    // Create a LocalDateTime from user input via console
    // Returns null if failed or menu is cancelled
    private LocalDateTime selectDateTime(
            Scanner scan,
            ArrayList<LocalTime> timeSlots,
            boolean selectFromReservations,
            boolean showOpenSlots
    ) {
        try {
            System.out.println("-----------------------------------------------------");
            System.out.println("Type 'cancel' to return to the previous menu");
            System.out.println("Enter a Reservation date: (MM/DD/YY)");
            // Collect date from scanner
            String input = scan.nextLine();
            if (input.equalsIgnoreCase("cancel")) {
                return null;
            }
            LocalDate date = LocalDate.parse(input, DATE_FORMAT);
            if (selectFromReservations) {
                ArrayList<Reservation> dateFilteredReservations = findReservationByDate(reservations, date);
                if (dateFilteredReservations.isEmpty()) {
                    System.out.println("No reservations found");
                    return null;
                } else {
                    displayReservations(dateFilteredReservations);
                }
            }
            if (showOpenSlots) {
                if (timeSlots != null) {
                    ArrayList<Reservation> reservationsWithOpenings = fillTimeSlots(date, reservations, timeSlots, true);
                    displayReservations(reservationsWithOpenings);
                }
            }
            try {
                System.out.println("-----------------------------------------------------");
                System.out.println("Type 'cancel' to return to the previous menu");
                System.out.println("Enter the Reservation Time: (HH:MM)");
                input = scan.nextLine();
                if (input.equalsIgnoreCase("cancel")) {
                    return null;
                }
                // Collect time from scanner
                LocalTime time = LocalTime.parse(input, TIME_FORMAT);
                return LocalDateTime.of(date, time);
            } catch (Exception e) {
                System.out.println("Invalid time entered!");
            }
        } catch (Exception e) {
            System.out.println("Invalid date entered!");
        }
        return null;
    }

    // Internal helper function to get ArrayList of reservations equal to date
    private ArrayList<Reservation> findReservationByDate(ArrayList<Reservation> reservationsList, LocalDate date) {
        ArrayList<Reservation> filteredReservations = new ArrayList<Reservation>();
        for (Reservation r : reservationsList) {
            if (date.isEqual(r.getReservedDateTime().toLocalDate())) {
                filteredReservations.add(r);
            }
        }
        return filteredReservations;
    }

    // Internal helper function to get ArrayList of reservations equal to the time (up to minutes)
    private ArrayList<Reservation> findReservationByTime(ArrayList<Reservation> reservationsList, LocalTime time) {
        ArrayList<Reservation> filteredReservations = new ArrayList<Reservation>();
        for (Reservation r : reservationsList) {
            time = time.truncatedTo(ChronoUnit.MINUTES);
            if (time.equals((r.getReservedDateTime().toLocalTime().truncatedTo(ChronoUnit.MINUTES)))) {
                filteredReservations.add(r);
            }
        }
        return filteredReservations;
    }

    // Given the [OPEN_TIME] and [CLOSE_TIME] values, generate available reservation time slots
    // In increments of [RESERVATION_DURATION_MINUTES]. If there is extra time, the last reservation
    // has a reservation until [CLOSE_TIME]
    private ArrayList<LocalTime> generateTimeSlotList() {
        LocalTime timeSlot = OPEN_TIME;
        ArrayList<LocalTime> timeSlots = new ArrayList<LocalTime>();
        while(CLOSE_TIME.isAfter(timeSlot.plusMinutes(RESERVATION_DURATION_MINUTES))) {
            timeSlots.add(timeSlot);
            timeSlot = timeSlot.plusMinutes(RESERVATION_DURATION_MINUTES);
        }
        return timeSlots;
    }

    // Given a LocalDate, ArrayList of Reservations, and the restaurant's available time slots,
    // Fills time slots with empty or available Reservations for displaying
    private ArrayList<Reservation> fillTimeSlots(
            LocalDate date,
            ArrayList<Reservation> reservationsList,
            ArrayList<LocalTime> timeSlots,
            boolean openTimesOnly
    ) {
        ArrayList<Reservation> reservationsWithOpenings = new ArrayList<Reservation>();
        // Create HashMap containing the index of timeslot and existing Reservation
        HashMap<Integer, Reservation> reservationsTimeSlots = new HashMap<Integer, Reservation>();
        for (Reservation r : reservationsList) {
            LocalTime time = r.getReservedDateTime().toLocalTime();
            if (timeSlots.contains(time) && r.getReservedDateTime().toLocalDate().isEqual(date)) {
                reservationsTimeSlots.put(timeSlots.indexOf(time), r);
            }
        }
        // Create ArrayList of empty Reservations
        for (int i = 0; i<timeSlots.size(); i++) {
            Reservation emptyReservation = new Reservation("-", 0, LocalDateTime.of(date, timeSlots.get(i)));
            reservationsWithOpenings.add(emptyReservation);
        }

        // Add existing Reservations at the appropriate time slot
        for (Map.Entry<Integer, Reservation> entry : reservationsTimeSlots.entrySet()) {
            if (openTimesOnly) {
                reservationsWithOpenings.remove((int)entry.getKey());
            } else {
                reservationsWithOpenings.set(entry.getKey(), entry.getValue());
            }
        }

        return reservationsWithOpenings;
    }

    // Return an int from user input
    // Returns -1 if loopUntilCorrect is false and the input is not a number
    // Loops infinitely until user enters int if loopUntilCorrect is true
    private int getIntAsInput(Scanner scan, boolean loopUntilCorrect) {
        do {
            try {
                String input = scan.nextLine();
                if (input.equalsIgnoreCase("cancel")) {
                    return -1;
                }
                return Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Not a number");
            }
        } while(loopUntilCorrect);
        return -1; // Not reached if [loopUntilCorrect] is true
    }
}