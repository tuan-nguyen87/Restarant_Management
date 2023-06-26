package common;

//this class keeps track of food that is ordered, the status of its preparation, and any special requests
public class Dish extends MenuItem implements Comparable<Dish>{

    public enum statuses {Queued, Preparing, Complete}
    private statuses status;
    private int priority;
    private String note;

    private static int counter = 1;

    public Dish() {}

    public Dish (String note){
        status = statuses.Queued;
        priority = counter++;
        this.note = note;
    }

    // Overload allowing for food name and price
    public Dish(String foodOrder, int price, String note) {
        super(foodOrder, price);
        this.note = note;
        priority = counter++;
        status = statuses.Queued;
    }

    public static void decrementCounter(){
        counter--;
    }

    //check if string is on the menu, and if the dish is in stock
    public boolean inStock(String foodOrder){
        return true;
    }

    //comparator for the priorityQueue to sort food, return 0 if priorities are equal, 1 if this is greater and -1 if this is less
    @Override
    public int compareTo(Dish anotherDish) {
        return Integer.compare(this.priority, anotherDish.priority);
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public statuses getStatus() {
        return status;
    }

    //change the status of the order from queued to in progress or completed
    public void setStatus(statuses status) {
        this.status = status;
    }

    @Override
    public String toString() {
        int price = getPrice();
        int dollars = price/100;
        int cents = price - (100*(price/100));
        String priceString =  dollars + "." + cents;

        String output = "\n\t-------------------------------------------" +
                "\n\tPriority: " + getPriority() + ", " + getName() +
                "\n\t$" + priceString + "\n\tNote: "
                + getNote() + "\n\tStatus: " + getStatus();
        return output;
    }

}
