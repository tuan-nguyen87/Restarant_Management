package common;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bill {

    private int subtotal;
    private LocalDate purchaseDate;
    private int tax;
    private int total;
    private boolean paid;

    public Bill(ArrayList<Dish> dishes){
        for (Dish dish : dishes) {
            subtotal += dish.getPrice();
        }
        tax = calcTax();
        total = subtotal + tax;
        paid = false;
        purchaseDate = LocalDate.now();
    }

    // Add dish to bill
    public void addItem(Dish item){
    }

    // Refund a specific dish
    public void comp(Dish item) {

    }

    private int calcTax(){
        double taxRate = 0.0875;
        return (int) Math.round(subtotal * taxRate);
    }

    public boolean authorizedTransaction(){
        return false;
    }

    // For logging purposes
    // Prints contents of Bill
    public String toString() {
        return "-----------------------------------------------------\n" +
                "Bill: " + purchaseDate.toString() + "\nSubtotal: " + format(subtotal)
                + "\nTax: " + format(tax) + "\nTotal: " + format(total) + "\nPaid: " + paid;
    }

    // Takes an int and prints as a String
    // Shifts decimal 2 places left to represent currency
    private String format(int num) {
        int decimal = Math.abs(num % 100);
        String strDecimal;
        if (decimal == 0) {
            strDecimal = "00";
        } else {
            strDecimal = String.valueOf(decimal);
        }
        return (num / 100) + "." + strDecimal;
    }

}
