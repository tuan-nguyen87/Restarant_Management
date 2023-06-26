package Server;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Reservation{
    private String partyName;
    private Table table;
    private LocalDateTime reservedDateTime;
    private int sizeOfParty;

    public Reservation(String name, int sizeOfParty, LocalDateTime reservedDateTime){
        partyName = name;
        this.sizeOfParty = sizeOfParty;
        this.reservedDateTime = reservedDateTime;
        table = new Table(0);
    }

    public Reservation(String name, int sizeOfParty, Table table, LocalDateTime reservedDateTime){
        partyName = name;
        this.sizeOfParty = sizeOfParty;
        this.reservedDateTime = reservedDateTime;
        this.table = table;
    }

    public void setPartyName(String name) {
        partyName = name;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setTable(Table table){
        this.table = table;
    }

    public Table getTable() {
        if (table != null){
            return table;
        } else {
            System.out.println("No Table found");
        }
        return null;
    }

    public void setDateTime(LocalDateTime newDateTime){
        reservedDateTime = newDateTime;
    }

    public LocalDateTime getReservedDateTime() {
        return reservedDateTime;
    }

    public int getSizeOfParty() {
        return sizeOfParty;
    }

    public void setSizeOfParty(int sizeOfParty) {
        this.sizeOfParty = sizeOfParty;
    }

    // Display a single reservation's details in the console
    // Format allows evenly-spaced columns
    public void display(String format, int reservationDurationMinutes) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        String date = reservedDateTime.toLocalDate().format(dateFormat);
        String startTime = reservedDateTime.toLocalTime().format(timeFormat);
        String endTime = reservedDateTime.toLocalTime().plusMinutes(reservationDurationMinutes).format(timeFormat);
        System.out.printf(format, date, startTime, endTime, table.getTableNumber(), sizeOfParty, partyName);
    }
}