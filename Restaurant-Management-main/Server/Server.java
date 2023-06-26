package Server;

import java.util.Scanner;


public class Server {

    //static HashMap<Integer, MenuItem> Menu = new HashMap<Integer, MenuItem>();

    static ReservationController reservationController;

    public Server() {
        reservationController = new ReservationController();
    }

    // -------------- [SERVER VIEW FUNCTIONS] ------------------------------------------


      // Prints the main menu of the Server View
      public boolean mainMenu_Server(Scanner scan){
        //while flag is true do while loop
        boolean flag = true;
        String view = "1) Order Queue\n2) Table View\n3) Reservation View\n4) Go Back\n5) Exit";

        while (true) {
            System.out.println("-------------------------[Server - Main Menu]----------------------------");
            System.out.println(view);
            System.out.print("Input: ");
            int input = scan.nextInt();

            switch (input) {
                case 1:
                    order_Queue(scan);
                    break;
                case 2:
                    table_View(scan);
                    break;
                case 3:
                    reservation_View(scan);
                    break;
                case 4:
                    // Go back
                    return false;
                case 5:
                    return true;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    // -------------- [ORDER FUNCTIONS] ------------------------------------------
    static void order_Queue(Scanner scan){
        //while flag is true do while loop
        boolean flag = true;
        ServerController.readOrders();
        String view = "1) List Orders\n2) Edit an Order\n3) Move Orders Around\n4) Add a New Order\n5) Remove an Order\n6) Go Back";

        while(flag){
            System.out.println("--------------------------[Server - Order Queue]---------------------------");
            System.out.println(view);
            System.out.print("Input: ");
            int input =scan.nextInt();

            switch (input) {
                case 1:
                    ServerController.listOrders();
                    break;
                case 2:
                    ServerController.editOrder();
                    break;
                case 3:
                    ServerController.moveOrder();
                    break;
                case 4:
                    ServerController.placeOrder();
                    break;
                case 5:
                    ServerController.cancelOrder();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
    // -------------- [TABLE FUNCTIONS] ------------------------------------------
    static void table_View(Scanner scan){
         //while flag is true do while loop
         boolean flag = true;
         String view = "1) Add Tables\n2) Display Tables\n3) Edit Table Status\n4) Go Back";
 
         while(flag){
             System.out.println(view);
             System.out.print("Input: ");
             int input = scan.nextInt();
 
             switch(input){
                case 1:
                    System.out.println("Enter table count:");
                    input = scan.nextInt();
                    TableController.createTables(input);
                    break; 
                case 2:
                    if (TableController.areThereTables()) {
                        TableController.printListOfTables();
                    }
                    break;
                 case 3:
                 if (TableController.areThereTables()){
                    editTableStatus_menu(scan);
                 }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void editTableStatus_menu(Scanner scan){
        String choices="1) Occupation Status 2) Cleaning Status 3) Go Back";
        while(true){
            int tNumber;
            String stat = "";
            System.out.println("-------------------------[Server - Edit Table]----------------------------");
            System.out.println(choices);
            System.out.print("Input: ");
            int input=scan.nextInt();

            switch(input){
                case 1:
                    if (TableController.areThereTables()) {
                        TableController.printListOfTables();
                    } else {
                        break;
                    }
                    System.out.print("Table Number: ");
                    tNumber=scan.nextInt();
                    scan.nextLine();
                    System.out.print("Occupation Status (t/f): ");
                    stat=scan.nextLine();
                    if(stat.compareToIgnoreCase("t") == 0){
                        TableController.changeOccupationStatus(tNumber, true);
                    }
                    else if(stat.compareToIgnoreCase("f") == 0)
                        TableController.changeOccupationStatus(tNumber, false);
                    break;
                case 2:
                    if (TableController.areThereTables()) {
                        TableController.printListOfTables();
                    } else {
                        break;
                    }
                    System.out.print("Table Number: ");
                     try {
                         tNumber=scan.nextInt();
                     } catch (Exception e) {
                         System.out.println("Not a valid number");
                         return;
                     }
                    scan.nextLine();
                    System.out.print("Cleaning Status (t/f): ");
                    stat=scan.nextLine();
                    if(stat.compareToIgnoreCase("t") == 0)
                        TableController.changeCleaningStatus(tNumber, true);
                    else if(stat.compareToIgnoreCase("f") == 0)
                        TableController.changeCleaningStatus(tNumber, false);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    // -------------- [RESERVATION FUNCTIONS] ------------------------------------------
    void reservation_View(Scanner scan) {
        String view = "1) Display Reservation\n2) Add New Reservation\n3) Edit Reservation\n4) Cancel Reservation\n5) Go Back";

        while (true) {
            System.out.println("--------------------------[Server - Reservation View]---------------------------");
            System.out.println(view);
            System.out.print("Input: ");
            int input = scan.nextInt();

            switch (input) {
                case 1:
                    reservationController.displayReservations();
                    break;
                case 2:
                    reservationController.makeReservation(scan);
                    break;
                case 3:
                    editReservationMenu(scan);
                    break;
                case 4:
                    reservationController.cancelReservation(scan);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    void editReservationMenu(Scanner scan) {
        String view = "1) Change Name\n2) Change Reservation Date and Time\n3) Change Party Size\n4) Go Back";
        while (true) {
            System.out.println("--------------------------[Server - Edit Reservation]---------------------------");
            System.out.println(view);
            System.out.print("Input: ");
            int input = scan.nextInt();

            switch (input) {
                case 1:
                    reservationController.changeReservationName(scan);
                    break;
                case 2:
                    reservationController.moveReservation(scan);
                    break;
                case 3:
                    reservationController.changeReservationPartySize(scan);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}