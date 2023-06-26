package Chef;

import common.Order;
import common.Dish;

import java.util.ArrayList;

public class ChefController {

    public ChefController() { }

    // Change the status of a dish in an order (Queued, Preparing, Complete)
    public static void changeDishStatus(Dish changedDish, Dish.statuses status) {
        if (changedDish.getStatus() != status) {
            changedDish.setStatus(status);
        }
        else {
            System.out.println("The chosen status for the dish has already been set before.");
        }
    }

    // Move Orders around the Order Queue (in the JSON file)
    public static void moveOrders(ArrayList<Order> orders, int oldPosition, int newPosition) {
        Order movedOrder = orders.get(oldPosition - 1);
        orders.remove(oldPosition - 1);
        orders.add(newPosition - 1, movedOrder);
    }
}
