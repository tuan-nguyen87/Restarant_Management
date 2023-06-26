package Server;

import java.util.ArrayList;
import java.util.Scanner;

//This class adds and removes Tables from a list and changes the status of 
//the tables cleanliness and occupation.
public class TableController {
	private static final String COLUMN_FORMAT = "%-15s%-17s%-12s\n";
	private static final String TABLE_FORMAT = "%-15d%-17s%-12s\n";
	private static ArrayList<Table> tables = new ArrayList<Table>();

	public static void createTables(int howMany){
		for(int i=1; i<=howMany; i++){
			Table table=new Table(i);
			tables.add(table);
		}
	}

	// takes in a table that we want to remove and removes it from
	// our list of tables
	/*
	 * @param tableNumber is the table's number
	 */
	public static void removeTable(int tableNumber) {
		for (Table i : tables) {
			if (i.getTableNumber() == tableNumber) {
				tables.remove(i);
			}

		}
	}

	//changes the occupation status of a table in the list of tables
	/*
	 * @param tableNumber - takes in the tables number who's occupation you want to change
	 * @param status - takes in the status that you want to change that table's occupation 
	 * status to
	 */
	public static void changeOccupationStatus(int tableNumber, boolean status) {
		for (Table i : tables) {
			if (i.getTableNumber() == tableNumber) {
				i.setOccupationStatus(status);
			}

		}

	}

	//changes the cleaning status of the table in the list of tables
		/*
		 * @param tableNumber - takes in the tables number who's cleaning status you want to change
		 * @param status - takes in the status that you want to change that table's cleaning
		 * status to
		 */
	public static void changeCleaningStatus(int tableNumber, boolean status) {

		for (Table i : tables) {
			if (i.getTableNumber() == tableNumber) {
				i.setCleaningStatus(status);
			}

		}

	}

	public static boolean areThereTables(){
		if (tables.size() < 1){
			System.out.println("No Tables found");
			return false;
		}
		return true;
	}

	/*
	 * prints the list of tables 
	 */
	public static void printListOfTables() {
		System.out.println("-----------------------------------------------------");
		System.out.printf(COLUMN_FORMAT, "Table Number", "Needs Cleaning", "Occupied");
		System.out.println("-----------------------------------------------------");

		for (Table t : tables) {
			System.out.printf(TABLE_FORMAT, t.getTableNumber(), t.checkClean(), t.isOpen());
		}
	}

	/*
	 * Allow user to display all tables and select one via console
	 * Returns a Table if successful. Returns null if user failed to choose a table
	 */
	public static Table selectTable(Scanner scan) {
		if (TableController.areThereTables()) {
			printListOfTables();
		} else {
			return null;
		}
		System.out.println("-----------------------------------------------------");
		System.out.println("Type 'cancel' to return to the previous menu");
		System.out.println("Select a Table\n");
		String input = scan.nextLine();
		if (input.equalsIgnoreCase("cancel")) {
			return null;
		}
		if (hasOpenTables()) {
			for (Table t : tables) {
				try {
					Integer.parseInt(input);
				} catch (Exception e) {
					System.out.println("Not a valid number");
					break;
				}
				if (t.getTableNumber() == Integer.parseInt(input)) {
					if (t.isOpen()) {
						System.out.println("Table " + t.getTableNumber() + " is occupied");
						break;
					}
					else if (t.checkClean()) {
						System.out.println("Table " + t.getTableNumber() + " has not been cleaned yet");
						break;
					}
					else {
						System.out.println("Table " + t.getTableNumber() + " selected");
						return t;
					}
				}
			}
		} else {
			System.out.println("No Table openings found");
		}
		return null;
	}

	public static boolean hasOpenTables() {
		for (Table t : tables) {
			if (!t.isOpen()) {
				if (!t.checkClean()) {
					return true;
				}
			}
		}
		return false;
	}
}
