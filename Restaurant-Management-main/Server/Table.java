package Server;

import java.util.ArrayList;

//creates a Table object
public class Table {
	private static int numberofTables = 1;
	private int number;
	private boolean isOccupied;
	private boolean needCleaning;

	// default contructor sets these parameters by default
	//each table is assigned a unique table number 
	public Table(int n) {
		number= numberofTables;
		numberofTables++;
		isOccupied = false;
		needCleaning = false;
	}

	// set the cleaning status of one table
	public void setCleaningStatus(boolean status) {
		needCleaning = status;
	}

	// check the cleaning status of one table
	public boolean checkClean() {
		return needCleaning;
	}

	// get a table's number
	public int getTableNumber() {
		return number;
	}

	// set the occupation status of one table
	public void setOccupationStatus(boolean status) {
		isOccupied = status;
	}

	// retrieve the occupation status of one table
	public boolean isOpen() {
		return isOccupied;
	}

	//what the table will look like when it is printed
	public String toString() {
		String output = "Table Number: " + number + " CleaningStatus: " + needCleaning + " OccupationStatus: "
				+ isOccupied;
		return output;
	}

}