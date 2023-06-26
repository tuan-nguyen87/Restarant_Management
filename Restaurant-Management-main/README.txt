Github: Restaurant Manager
Link:  https://github.com/OneOdMan/Restaurant-Management.git

-----[BEFORE YOU RUN]-----
Please ensure you have installed the following:
- Java JDK OR Runtime Environment
- The following libraries:
	* jackson-databind
	* jackson-core
	* jackson-annotations
	* jackson-datatype

The above libraries should be provided to you along with the source files.
Reach out to professionals for additional help setting the environment up.

You should also compile the program before you run it.  If you are running the program without an IDE, ensure you
have the above libraries installed into your project before running the commands:

javac Main.java

To run the program, use the following command:
java Main

You can skip these steps if you are using an IDE.

------------------[GENERAL USAGE | Chefs]--------------------
If you are a Chef, select the Chef View by pressing 1 and hitting enter.
You should now end up in the Main Menu for Chefs.  You will then be presented the following options:

1) Display Orders or Change Dish Status
2) Manage Orders
3) Go Back 
4) Exit

Options 3 goes back to the previous menu while option 4 exits the application completely.

=====(Displaying Orders and Changing Dish Status)=====
If you want to display the current Orders and/or change their Dish status, press 1 and hit enter.

A list of all the Orders taken by Servers should be displayed and you will be prompted to either press 0 to go
back or press 1 to change the status of a dish.

You will then be asked to input the Order number first to get the Order of the dish you want to update.
Press 0 to go back to the previous menu.

If you entered an Order Number, you will then be asked to enter the Priority Number associated with the Dish you
want to edit.  Enter the number and press enter.

You will then be asked to change its status to either Queued, Preparing, and Complete.  Input the number 
associated with the status and then press enter OR you can go back to the previous menu.

You can either keep changing the status of more dishes or go back to the previous menu.

=====(Managing Orders)=====
In the Chef Main Menu, press 2 and hit enter to manage Orders.

You will then be presented the options for the following:

1) Add/Remove orders from queue
2) Move an order around the queue orders
3) Import order from Server View
4) Go Back

Select options 1 or two to manage the Order Queue and follow the instructions when prompted.

If you want to update the Order Queue, select 3 and press enter.  You should get a confirmation message stating
that you have imported Orders from the Server View

------------------[GENERAL USAGE | Servers]--------------------
From the Application Main Menu, press 2 to enter the Server View.

You will then be prompted with options specified in the sections below.
{IMPORTANT NOTE}: Servers should always go into the Table View first and add the total tables in the
establishment you are working in. 

=====(Order Queue)=====

You will be able to do the following by entering the corresponding number in the input:

1) List Orders
2) Edit an Order
3) Move Orders Around
4) Add a New Order
5) Remove an Order
6) Go Back

Select an option and follow the instructions from there.

=====(Table View)=====
In this view, you will be able to do the following:

1) Add Tables
2) Display Tables
3) Edit Table Status
4) Go Back
5) Exit

The first option will add a specific amount of tables to the system

The second option will display all the tables created and their status (Cleaned or Occupied)

The third option will allow you to edit their Cleaned and/or Occupation Status

=====(Reservation View)=====
In this view, you will be able to do the following based on the input you choose:

1) Display Reservation
2) Add New Reservation
3) Edit Reservation
4) Cancel Reservation
5) Go Back

Select an option and follow the instructions from there.

------------------[GENERAL USAGE | POS]--------------------
{IMPORTANT NOTE} Options 1and 2 are not available yet.  Only the 3rd option is available.

Select Option 3 to Manage Products for your establishment.

You will be able to do the following:

1) Add a new product
2) Edit a product
3) Remove a product
4) Go Back

Choose and option and follow the instructions from there.
