package Chef;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import common.Dish;
import common.MenuItem;
import common.Order;

import com.fasterxml.jackson.databind.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Chef{

    private static ArrayList<Order> orders = new ArrayList<>();

    public Chef() {}

    // Prints the main menu of the Chef View
    public boolean mainMenu_Chef(Scanner scan) {
        boolean finished = false;
        String chefMenu = "1) Display Orders or Change Dish Status\n2) Manage Orders\n3) Go Back\n4) Exit";
        while (!finished){
            System.out.println("------------------------[Chef - Main Menu]-----------------------------");
            System.out.println(chefMenu);
            System.out.print("Input: ");
            int input = scan.nextInt();
            switch (input){
                case 1:
                    displayOrders(scan);
                    break;
                case 2:
                    manageOrdersSubMenu(scan);
                    break;
                case 3:
                    // Goes back to the previous menu
                    finished = true;
                    break;
                case 4:
                    return true;
            }
        }
        System.out.println("-----------------------------------------------------");
        return false;
    }

    // Prints the sub menu of Manage Orders from the Chef View
    protected void manageOrdersSubMenu(Scanner scan) {
        boolean finished = false;
        String manageOrdersSubMenu = "1) Add / remove orders from Queue" +
                "\n2) Move an order around the Queue Orders" +
                "\n3) Import order from Server View" +
                "\n4) Go Back";
        while (!finished) {
            System.out.println("------------------------[Chef - Manage Orders]-----------------------------");
            System.out.println(manageOrdersSubMenu);
            System.out.print("Input: ");
            int input = scan.nextInt();
            switch (input) {
                case 1:
                    // TODO
                    break;
                case 2:
                    moveOrdersSubMenu(scan);
                    break;
                case 3:
                    readOrders();
                    System.out.println("Orders have been imported from Server View");
                    break;
                case 4:
                    // Goes back to the previous menu
                    finished = true;
                    break;
            }
        }
        System.out.println("-----------------------------------------------------");
    }

    // Prints the sub menu for moving orders around the Queue
    protected void moveOrdersSubMenu(Scanner scan) {
        boolean finished = false;
        while (!finished) {
            readOrders();
            displayOrders();
            System.out.println("--------------------------[Chef - Move Orders]---------------------------");
            System.out.print("Input position number of order you want to move (0 will go back): ");
            int inputOldPosition = scan.nextInt();
            if (inputOldPosition == 0) {
                finished = true;
            }
            else if (inputOldPosition < 1 || inputOldPosition > orders.size()) {
                System.out.println("ERROR: The position is not valid");
            }
            else {
                boolean moveFinished = false;
                while (!moveFinished) {
                    System.out.println("-------------------------[Chef - Move Orders]----------------------------");
                    System.out.print("Input new position number (0 will go back): ");
                    int inputNewPosition = scan.nextInt();
                    if (inputNewPosition == 0) {
                        moveFinished = true;
                    }
                    else if (inputNewPosition < 1 || inputNewPosition > orders.size()) {
                        System.out.println("ERROR: The new position is not valid");
                    }
                    else {
                        ChefController.moveOrders(orders, inputOldPosition, inputNewPosition);
                        writeOrders();
                        System.out.println("The order had been moved.");
                        moveFinished = true;
                    }
                }
            }
        }
        System.out.println("-----------------------------------------------------");
    }

    // Getter
    public ArrayList<Order> getOrders() {
        return orders;
    }

    // Retrieve orders from the JSON fle
    public void readOrders()  {
        // Relative file path
        File jsonPath = new File("./JSON/Orders.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            ArrayList<Order> list = mapper.readValue(jsonPath,
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Order.class));
            setOrders(list);
        }
        catch (IOException e) {
            System.out.println("ERROR: Unable to read the file. It is either missing or in the incorrect format.");
            e.printStackTrace();
        }
    }

    // Setter
    public static void setOrders(ArrayList<Order> orders) {
        Chef.orders = orders;
    }

    // Writes orders to the JSON file
    public void writeOrders() {
        try {
            //Creates directory if it doesn't exist
            File directory = new File("./JSON");
            if (!directory.exists()) directory.mkdirs();
            File jsonPath = new File(directory + "/Orders.json");
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.writeValue(jsonPath, this.orders);
        }
        catch (IOException e) {
            System.out.println("ERROR: The file cannot be saved.");
            e.printStackTrace();
        }
    }

    // For when you don't want to modify any dishes
    public void displayOrders() {
        readOrders();
        if (orders != null) {
            for (int i = 0; i < orders.size(); i++){
                System.out.print(i + 1);
                System.out.print(":");
                System.out.println(orders.get(i));
            }
        }
        else {
            System.out.println("There are no orders in queue currently.");
        }
    }

    // Allows a chef to modify dish status
    public void displayOrders(Scanner scan) {
        readOrders();
        if (orders != null) {
            for (int i = 0; i < orders.size(); i++){
                System.out.println("///////////////////////////////////////////////////////////////");
                System.out.print(i + 1);
                System.out.print(":");
                System.out.println(orders.get(i));
            }
            // Menu for changing status of dishes for selected order
            boolean finished = false;
            while (!finished) {
                System.out.println("-----------------------------------------------------");
                System.out.println("Press 0 to go back or press 1 to change the status of a dish");
                System.out.print("Input: ");
                int input = scan.nextInt();
                switch (input) {
                    case 1:
                        changeDishStatusSubMenu(scan);
                        break;
                    case 0:
                        // Goes back to the previous menu
                        finished = true;
                        break;
                }
            }
        }
        else {
            System.out.println("There are no orders in queue currently.");
        }
    }

    // Sub menu for changing the status of a selected dish
    protected void changeDishStatusSubMenu(Scanner scan) {
        boolean finished = false;
        while (!finished) {
            // Get input for which order
            System.out.println("--------------------------[Chef - Change Dish Status]---------------------------");
            System.out.print("Input position number of the order that has a dish that needs a status change " +
                    "(0 will go back): ");
            int inputPosition = scan.nextInt();
            if (inputPosition == 0) {
                finished = true;
            }
            else if (inputPosition < 1 || inputPosition > orders.size()) {
                System.out.println("ERROR: The position is not valid");
            }
            else {
                // Get input for which dish
                Order inputOrder = orders.get(inputPosition - 1);
                boolean dishFinished = false;
                while (!dishFinished) {
                    System.out.println("---------------------------[Chef - Change Dish Status]--------------------------");
                    System.out.print("Input the priority number of the dish that needs a status change " +
                            "(0 will go back): ");
                    int inputPriority = scan.nextInt();
                    if (inputPriority == 0) {
                        dishFinished = true;
                    }
                    else if (inputPriority < 1 || inputPriority > inputOrder.getDishes().size()) {
                        System.out.println("ERROR: The priority is not valid");
                    }
                    else {
                        // Get input for which status
                        Dish inputDish = inputOrder.getDishesInArrayList().get(inputPriority - 1);
                        boolean statusFinished = false;
                        String statusSubMenu = "1) Change to Queued" +
                                "\n2) Change to Preparing" +
                                "\n3) Change to Complete" +
                                "\n4) Go Back";
                        while (!statusFinished) {
                            System.out.println("---------------------------[Chef - Change Dish Status]--------------------------");
                            System.out.println(statusSubMenu);
                            System.out.println("Input: ");
                            int inputStatus = scan.nextInt();
                            switch (inputStatus) {
                                case 1:
                                    ChefController.changeDishStatus(inputDish, Dish.statuses.Queued);
                                    writeOrders();
                                    System.out.println("The status had been changed.");
                                    statusFinished = true;
                                    break;
                                case 2:
                                    ChefController.changeDishStatus(inputDish, Dish.statuses.Preparing);
                                    writeOrders();
                                    System.out.println("The status had been changed.");
                                    statusFinished = true;
                                    break;
                                case 3:
                                    ChefController.changeDishStatus(inputDish, Dish.statuses.Complete);
                                    writeOrders();
                                    System.out.println("The status had been changed.");
                                    statusFinished = true;
                                    break;
                                case 4:
                                    // Goes back to the previous menu
                                    statusFinished = true;
                                    break;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("-----------------------------------------------------");
    }
}