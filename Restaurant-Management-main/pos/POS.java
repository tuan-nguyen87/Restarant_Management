package pos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import common.Bill;
import common.MenuItem;
import common.Order;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class POS {

    private ArrayList<Bill> bills;
    static ArrayList<MenuItem> Menu = new ArrayList<MenuItem>();
    static int itemNumber = 1;
    public POS() {
        bills = new ArrayList<Bill>();
    }

    public Bill generateBill(Order order) {
        Bill bill = new Bill(order.getDishesInArrayList());
        bills.add(bill);
        return bill;
    }

    // Prints the main menu of the POS
    public boolean mainMenu_POS(Scanner scan){
        String menu="1) Display Total Revenue\n2) Display Table Traffic\n3) Manage Products\n4) Go Back\n5) Exit";
        while(true){
            System.out.println("-----------------------------------------------------");
            System.out.println(menu);
            System.out.print("Input: ");
            int input=scan.nextInt();
            switch(input){
                case 1:
                    // Display Total Revenue
                    displayTotalRevenueSubMenu(scan);
                    break;
                case 2:
                    tableTrafficSubMenu(scan);
                    break;
                case 3:
                    manageProductsSubMenu(scan);
                    break;
                case 4:
                    // Go Back
                    return false;
                case 5:
                    // Exit
                    return true;
            }
        }
    }

    // Prints the menu of 'Display Total Revenue'
    protected void displayTotalRevenueSubMenu(Scanner scan){
        String menu="1) Daily Revenue\n2) Weekly Revenue\n3) Monthly Revenue\n4) Yearly Revenue\n5) Go Back";
        while(true){
            System.out.println("-----------------------------------------------------");
            System.out.println(menu);
            System.out.print("Input: ");
            int input=scan.nextInt();
            switch(input){
                case 1:
                    // Daily Revenue
                    break;
                case 2:
                    // Weekly Revenue
                    break;
                case 3:
                    // Monthly Revenue
                    break;
                case 4:
                    // Yearly Revenue
                    break;
                case 5:
                    // Go Back
                    return;
            }
        }
    }

    // Prints the sub menu of Table Traffic from the POS View
    protected void tableTrafficSubMenu(Scanner scan){
        boolean finished = false;
        String tableTrafficSubMenu = "1) Daily Traffic" +
                "\n2) Weekly Traffic" +
                "\n3) Monthly Traffic" +
                "\n4) Go Back";
        while(!finished) {
            System.out.println("-----------------------------------------------------");
            System.out.println(tableTrafficSubMenu);
            System.out.print("Input: ");
            int input = scan.nextInt();
            switch (input) {
                case 1:
                    // TODO
                    break;
                case 2:
                    // TODO
                    break;
                case 3:
                    // TODO
                    break;
                case 4:
                    // Goes back to the previous menu
                    finished = true;
                    break;
            }
        }
    }

    // Prints the sub menu of Manage Products from the POS View
    protected void manageProductsSubMenu(Scanner scan){
        boolean finished = false;
        String name;
        String manageProductsSubMenu = "1) Add a new product" +
                "\n2) Edit a product" +
                "\n3) Remove a product" +
                "\n4) Go Back";
        while(!finished) {
            System.out.println("-----------------------------------------------------");
            System.out.println(manageProductsSubMenu);
            System.out.print("Input: ");
            int input = scan.nextInt();
            switch (input) {
                case 1:
                    System.out.print("Enter the item Name: ");
                    scan.nextLine();
                    name = scan.nextLine();
                    System.out.print("Enter the price: $");
                    input = (int)(scan.nextFloat() * 100);
                    addToMenu(name, input);
                    break;
                case 2:
                    displayMenu();
                    System.out.print("Enter the name of the item to edit: ");
                    scan.nextLine();
                    name = scan.nextLine();
                    var item = findMenuItem(name);
                    if (item == null) {
                        return;
                    } else {
                        int itemIndex = Menu.indexOf(item);
                        System.out.print("New Menu Name: ");
                        name = scan.nextLine();
                        item.setName(name);
                        System.out.print("New Price: $");
                        input = (int)(scan.nextFloat() * 100);
                        item.setPrice(input);

                        // Update MenuItem in Menu Array
                        Menu.set(itemIndex, item);
                        writeMenu();
                    }
                    break;
                case 3:
                    displayMenu();
                    System.out.print("Enter the Name of the item to delete: ");
                    scan.nextLine();
                    name = scan.nextLine();
                    boolean removed = removeItem(name);
                    if (removed) {
                        System.out.println(name + " has been removed from the Menu");
                    }
                    writeMenu();
                    break;
                case 4:
                    // Goes back to the previous menu
                    finished = true;
                    break;
            }
        }
    }

    //----------------------------[MenuItem Functions]---------------------------------
    public static void addToMenu(String name, int price){
        //MenuItem item = new MenuItem(name, price);
        MenuItem item = MenuItem.addItem(name, price);
        //Menu.put(itemNumber, item);
        Menu.add(item);
        itemNumber++;
        writeMenu();
    }

    public static void writeMenu(){
        try {
            //Creates directory if it doesn't exist
            File directory = new File("./JSON");
            if (!directory.exists()) directory.mkdirs();
            File jsonPath = new File(directory + "/Menu.json");
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.writeValue(jsonPath, Menu);
        }
        catch (IOException e) {
            System.out.println("ERROR: Menu cannot be saved.");
            e.printStackTrace();
        }
    }

    public static void readMenu()  {
        // Relative file path
        File jsonPath = new File("./JSON/Menu.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            Menu = mapper.readValue(jsonPath,
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, MenuItem.class));
        }
        catch (IOException e) {
            System.out.println("ERROR: Unable to read the Menu file. It may not have been created or it may be in the wrong format.");
            //e.printStackTrace();
        }
    }

    public static void displayMenu() {
        for (MenuItem i : Menu){
            i.Display();
        }
    }

    public static MenuItem findMenuItem(String name) {
        for (MenuItem i : Menu) {
            if (i.getName().compareToIgnoreCase(name) == 0) {
                return i;
            }
        }
        System.out.println("No Menu Item found");
        return null;
    }

    public static boolean removeItem(String name){
        return Menu.remove(findMenuItem(name));
    }

}
