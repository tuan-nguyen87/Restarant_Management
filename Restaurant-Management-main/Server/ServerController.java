package Server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import common.Dish;
import common.MenuItem;
import common.Order;
import pos.POS;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerController {
    private static ArrayList<Order> allTableOrders = new ArrayList<Order>();


    public static void listOrders(){
        readOrders();
        Scanner in = new Scanner(System.in);
        if (allTableOrders.isEmpty()) {
            System.out.println("No Orders found");
            return;
        }
        int count = 0;
        for (Order i : allTableOrders) {
            count ++;
            System.out.println( "\n" + "===============================================\n" + "[Order " + count + "]");
            System.out.println(i);
        }
        System.out.println("Press Enter to continue");
        in.nextLine();
    }
    public static void placeOrder(){
        System.out.println("What table is placing an order?");
        Scanner in = new Scanner(System.in);
        Order order = new Order(in.nextInt());

        takeOrder(order);
    }


    public static void addItem(Order order, String item)
    {
        MenuItem i = POS.findMenuItem(item);

        if(i != null)
        {

            String note;
            System.out.println("Add any additional requests for the order");
            Scanner in = new Scanner(System.in);
            note = in.nextLine();
            Dish j = new Dish(note);
            j.setName(i.getName());
            j.setPrice(i.getPrice());
            order.getDishes().add(j);

        }
        else{
            System.out.println("This item is not on the menu.");
        }


    }

    public static void takeOrder(Order order)
    {
        System.out.println("----------------------[Menu]-----------------------------");
        POS.displayMenu();
        System.out.println("---------------------------------------------------------");
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the name of the dish the customer wants to order or");
        System.out.println("enter N to quit adding dishes: ");
        String item = in.nextLine();
        while(!(item.compareTo("N") == 0))
        {
            addItem(order, item);
            System.out.println("Please enter the name of the dish the customer wants to order or");
            System.out.println("enter N to quit adding dishes: ");
            item = in.nextLine();
        }
        allTableOrders.add(order);
        writeOrders();
    }

    public static void writeOrders(){
        try {
            File jsonPath = new File("./JSON/Orders.json");
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.writeValue(jsonPath, allTableOrders);
        }
        catch (IOException e) {
            System.out.println("ERROR: Menu cannot be saved.");
            e.printStackTrace();
        }
    }

    public static void readOrders()  {
        // Relative file path
        File jsonPath = new File("./JSON/Orders.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            allTableOrders = mapper.readValue(jsonPath,
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Order.class));
        }
        catch (IOException e) {
            System.out.println("ERROR: Unable to read the Orders file. It may not have been created or it may be in the wrong format.");
            //e.printStackTrace();
        }
    }

    public static void editOrder(){
        Scanner in = new Scanner(System.in);
        int order;
        Order temp;
        String input;
        listOrders();
        System.out.println("Which order would you like to change? ");
        order = in.nextInt();
        try{
            temp = allTableOrders.get(order-1);
            allTableOrders.remove(order-1); // Needs to be done so that it's not saved as a new Order
            System.out.println("Would you like to add or remove a dish? Enter add to add or remove" +
                    " to remove: ");
            in.nextLine();
            input = in.nextLine();
            switch(input){
                case "add":
                    takeOrder(temp);
                    break;
                case "remove":
                    temp.dishNameAndPrice();
                    System.out.println("Enter the priority number of the Dish you want to remove from the order: ");
                    int dishNum = in.nextInt();
                    allTableOrders.get(order -1 ).removeDish(dishNum);
                    break;
            }
            writeOrders();
        }
        catch(Exception e)
        {
            System.out.println("This order number does not exist.");
        }

    }

    public static void cancelOrder(){
        Scanner in = new Scanner(System.in);
        int order;
        Order temp;
        listOrders();
        System.out.println("Which order would you like to remove? ");
        order = in.nextInt();
        try{
            temp = allTableOrders.get(order-1);
            allTableOrders.remove(temp);

        }
        catch(Exception e)
        {
            System.out.println("This order number does not exist.");
        }
        writeOrders();
    }

    public static void moveOrder(){
        Scanner in = new Scanner(System.in);
        int order;
        Order temp;
        String input;
        listOrders();
        System.out.println("Which order would you like to move? ");
        order = in.nextInt();
        try{
            temp = allTableOrders.get(order-1);
            System.out.println("Move to front or back? Type front or back: ");
            in.nextLine();
            input = in.nextLine();

            switch(input){
                case "front":
                   allTableOrders.add(0, temp);
                   allTableOrders.remove(order);
                    break;
                case "back":
                    allTableOrders.add(temp);
                    allTableOrders.remove(order -1);
                    break;
            }
        }
        catch(Exception e)
        {
            System.out.println("This order number does not exist.");
        }
        writeOrders();
    }


}
