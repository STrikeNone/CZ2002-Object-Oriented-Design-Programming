package entities;


import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

/**
 * Class to represent a table in the restaurants
 */
public class Table extends ReservationList implements Serializable {

    /**
     * The table number
     */
    private int tableNum;
    /**
     * Seating Capacity of the table
     */
    private int numPax;
    /**
     * A flag to show if a table is occupied
     */
    private boolean occupied;
    /**
     * A flag to show if the customer at the table is a member
     */

    private LocalTime seatedTime;


    /**
     * Creates a new table with the given table number
     * @param tableNum the assigned table number
     */
    public Table(int tableNum){
        this.tableNum = tableNum;
        this.numPax = 0;
        this.occupied = false;
        // this.seatedTime = null;
    }

    /**
     * Get the seating capacity of the table
     * @return The seating capacity of the table
     */
    public int getNumPax() {
        return numPax;
    }

    /**
     * Get information on whether a table is occupied or not
     * @return true if it's occupied and false if it's not
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * Get the table number of the table
     * @return The table number
     */
    public int getTableNum(){return tableNum; }

    /**
     * Set the number of pax seated
     * @param numPax number of diners
     */
    public void setNumPax(int numPax){this.numPax = numPax; }

    /**
     * Set whether the table is occupied or not
     * @param occupied state of table
     */
    public void setOccupied(boolean occupied){this.occupied = occupied; }

    /**
     * Sets table back to unoccupied state
     */
    public void freeTable(){
        numPax = 0;
        occupied = false;
        //  seatedTime = null;
    }
}
