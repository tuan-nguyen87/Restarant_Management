package common;

public class MenuItem {
    //Private variables
    private String name;
    private int price;
    //assume a menu item is not in stock until it is created
    private boolean inStock = false;
    //Used to track item popularity
    private int popularity;

    //Empty Constructor
    MenuItem(){
        name = "UNNAMED DISH";
        price = 0;
        inStock = true;
        popularity = 0;
    }

    //This is the constructor from the uml
    MenuItem(String name){
        this.name = name;
        price = 0;
        inStock = true;
        popularity = 0;
    }

    //This is the constructor I assume we'll need
    public MenuItem(String name, int price){
        this.name = name;
        this.price = price;
        inStock = true;
        popularity = 0;
    }

    //Function to easily create Menu Items
    public static MenuItem addItem(String name, int price){
        MenuItem item = new MenuItem(name, price);
        return item;
    }

    //Getters
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public boolean isInStock(){
        return inStock;
    }

    public int getPopularity() { return popularity; }


    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void Display(){
        int dollars = price/100;
        int cents = price - (100*(price/100));
        System.out.println(name + " $" + dollars + "." + cents);
    }

    public String toString(){
        int dollars = price/100;
        int cents = price - (100*(price/100));
        return getName() + " $" + dollars + "." + cents;
    }

}