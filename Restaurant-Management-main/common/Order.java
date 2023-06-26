package common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Order {

    private int tableNumber;
    private LocalDateTime orderDate;
    private PriorityQueue<Dish> dishes;



    //Empty constructor required for Jackson lib
    public Order() {
        tableNumber = 0;
        orderDate = LocalDateTime.now();
        dishes = new PriorityQueue<Dish>();
    }

    //create an order with an empty list of food items and the current data and time
    public Order(int tableNumber){
        dishes = new PriorityQueue<Dish>();
        orderDate = LocalDateTime.now();
        this.tableNumber = tableNumber;
    }

    public int getTableNumber(){
        return tableNumber;
    }

    public String dishNameAndPrice(){
        String out = "Table " + tableNumber + "\n";
        for (Dish dish:
                getDishes()) {
            out += dish.toString() + "\n";
        }
        return out;
    }

    //add a food item to the order
    public void addDish(Dish dish){

        dishes.add(dish);

    }

    //remove a food item from the order
    public void removeDish(int priority){

        for (Dish d: dishes) {
            if(d.getPriority() == priority )
            {
                dishes.remove(d);
                Dish.decrementCounter();
            }

        }

        //dishes.poll();
        //Dish.decrementCounter();

    }

    // Makes retrieving dishes easier
    @JsonIgnore
    public ArrayList<Dish> getDishesInArrayList() {
        return new ArrayList<>(dishes);
    }

    public PriorityQueue<Dish> getDishes() {
        return dishes;
    }

    //change priority of a food item to another priority
    public void changePriority(int listPosition, int newlistPostion){

    }

    //set priority of all food items to the same value
    public void deliverOrderAllAtOnce(){

    }

    //return number of minutes since order placed
    public int checkElapsedTime(){
        return 0;
    }

    // Getters and setters
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setDishes(PriorityQueue<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "Table Number: " + tableNumber + "\norderDate: " + orderDate.getMonth() + "-" + orderDate.getDayOfMonth() + "-" + orderDate.getYear() +
                " " + orderDate.getHour() + ":" + orderDate.getMinute() + " " + dishes;
    }
}

