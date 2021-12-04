package mgr;

import application.Restaurant;
import entities.Reservations;
import entities.Table;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * The controller app for the Tables and Reservations of the restaurant
 */
public class TableMgr {
    /**
     * Arraylist to store all the table arrays
     */
    private static ArrayList<Table[]> tables = Restaurant.tables;


    /**
     * To create the layout of a new restaurant
     * @param tableConfig array of integers denoting the number of 2,4,6,8,10 seater tables
     * @return Returns an Arraylist of Table arrays to represent the layout of the restaurant
     */
    public static ArrayList<Table[]> initialiseRestaurant(int tableConfig[]){
        tables = new ArrayList<Table[]>();
        int tableNum = 1;

        for(int i = 0; i < 5; i++){
            Table individualTables[] = new Table[tableConfig[i]];
            for(int j = 0; j < tableConfig[i]; j++){
                individualTables[j] = new Table(tableNum);
                tableNum++;
            }
            tables.add(individualTables);
        }
        return tables;
    }

    /**
     * Assigns a table to a customer be it walk in or reservation customers. For walk in customers, we will check
     * whether there are any reservations less than 1hr later for that table. If there is then we will not give
     * that table to a walk in customer. (Eg. a table with a reservation at 1700 will not be available to a
     * walk in customer from 1600 onwards
     * @param numPax number of person
     * @param reservation boolean whether the customer has a reservation
     * @return table number assigned, -1 if failed to assign
     **/
    public static int assignTable(int numPax, boolean reservation){
        int i, group = tableGroup(numPax);
        Scanner scanner = new Scanner(System.in);
        removeExpiredReservationsFromTables();

        if(numPax > 10){
            System.out.println("Restaurant can only accommodate a maximum of 10 pax");
            return -1;
        }

        if(reservation){

            String customerName, customerNum;

            System.out.print("Customer Name: ");
            customerName = scanner.nextLine();

            System.out.print("Customer Number: ");
            customerNum = scanner.next();

            for(i = 0; i < tables.get(group).length; i++){
                if(tables.get(group)[i].deleteReservation(customerName, customerNum, false)){
                    tables.get(group)[i].setNumPax(numPax);
                    tables.get(group)[i].setOccupied(true);
                    return tables.get(group)[i].getTableNum();
                }
            }
            System.out.println("Reservation not found!");
            return -1;
        }

        else{
            for(i = 0; i < tables.get(group).length; i++){
                GregorianCalendar now = new GregorianCalendar();
                Reservations nextRes = tables.get(group)[i].getNextReservation();

                if(nextRes == null){
                    if(!tables.get(group)[i].isOccupied()) {
                        tables.get(group)[i].setOccupied(true);
                        tables.get(group)[i].setNumPax(numPax);
                        return tables.get(group)[i].getTableNum();
                    }
                }
                else{
                    // Check if a table is not reserved in the next hour and is also not occupied
                    Calendar nextResTime = (Calendar) nextRes.getReservationTime().clone();
                    nextResTime.add(Calendar.HOUR, -1);
                    if(now.before(nextResTime) && !tables.get(group)[i].isOccupied()){
                        tables.get(group)[i].setOccupied(true);
                        tables.get(group)[i].setNumPax(numPax);
                        return tables.get(group)[i].getTableNum();
                    }
                }
            }
        }

        System.out.println("No available tables at the moment!");
        return -1;
    }

    /**
     * Prints the status of all tables whether or not is occupied
     */
    public static void viewStatusOfTable(){
        int i, j;
        removeExpiredReservationsFromTables();
        for(i = 0; i < tables.size(); i++){
            for(j = 0; j < tables.get(i).length; j++){
                if(tables.get(i)[j].isOccupied())
                    System.out.println("Table " + tables.get(i)[j].getTableNum() + " (" + ((i+1) * 2) + " Seater) : Occupied\n" +
                            "Number of Pax: " + tables.get(i)[j].getNumPax() + "\n");
                else
                    System.out.println("Table " + tables.get(i)[j].getTableNum() + " (" + ((i+1) * 2) + " Seater) : Vacant\n" +
                            "Number of Pax: " + tables.get(i)[j].getNumPax() + "\n");

            }
        }
    }

    /**
     * To create a new reservation for the restaurant
     * @param customerName The customer's name
     * @param customerNum The customer's number
     * @param numPax The customer's number of guest
     * @param reservationTime The customer's reservation time
     */
    public static void createReservation(String customerName, String customerNum, int numPax, GregorianCalendar reservationTime){
        Reservations newReservation = new Reservations(customerName, customerNum, numPax, reservationTime);
        GregorianCalendar now = new GregorianCalendar();
        GregorianCalendar oneHourLater = (GregorianCalendar) now.clone();
        oneHourLater.add(Calendar.HOUR, 1);
        removeExpiredReservationsFromTables();


        if(numPax > 10){
            System.out.println("Restaurant can only accommodate a maximum of 10 pax");
            return;
        }

        //assign the correct table size index for the number of pax
        int group = tableGroup(numPax);

        // if user specifies a reservation time that is outside of restaurant's opening time or is 1hr before the closing time
        // return as invalid reservation
        if(!newReservation.checkReservationValidity(Restaurant.OPENING_TIME, Restaurant.CLOSING_TIME)){
            System.out.println("Reservation made is outside restaurant's opening or closing time");
            return;
        }

        else if(reservationTime.before(now)){
            System.out.println("Reservation time/date has already passed!");
            return;
        }

        else if(reservationTime.before(oneHourLater)){
            System.out.println("Reservation must be made at least 1 hour in advance!");
            return;
        }

        // else we search the current table for a possible slot if it doesnt have we move on to the next table
        else{
            int i;

            // looping through the list of tables for the specified pax
            for(i = 0; i < tables.get(group).length; i++){
                // if a table does not have any reservation in time, we assign that table first
                if(tables.get(group)[i].getNumReservations() == 0){
                    tables.get(group)[i].setReservations(newReservation);
                    System.out.println("Reservation for " + reservationTime.getTime() + " Successful");
                    return;
                }

                // else we loop through the reservations of the table to find a possible slot
                else{
                    if(tables.get(group)[i].searchPossibleSlot(reservationTime)){

                        // If there is a possible reservation, we insert the reservation in
                        tables.get(group)[i].setReservations(newReservation);
                        System.out.println("Reservation for " + reservationTime.getTime() + " Successful");
                        return;
                    }
                }
            }
            System.out.println("No possible table for chosen slot");
        }


    }

    /**
     * Cancel's an existing reservation
     * @param customerName The customer's name
     * @param customerNum The customer's number
     * @param numPax The customer's number of gues
     */
    public static void cancelReservation(String customerName, String customerNum, int numPax){
        removeExpiredReservationsFromTables();

        if(numPax > 10){
            System.out.println("Number of Pax must be lesser 10");
            return;
        }

        int i, j, group = tableGroup(numPax);

        for (j = 0; j < tables.get(group).length; j++) {
            if(tables.get(group)[j].deleteReservation(customerName, customerNum, true))
                return;
        }
        System.out.println("Unable to find reservation detail for customer");
        return;
    }

    /**
     * A private method to check through all the tables first reservation (if it exists) and remove them if the time
     * exceeds. This method is called by every other method in this class and is the first action of the all methods
     * except for printOptions method
     */
    private static void  removeExpiredReservationsFromTables(){
        int i, j;

        for(i = 0; i < tables.size(); i++) {
            for (j = 0; j < tables.get(i).length; j++) {
                tables.get(i)[j].removeExpiredReservations();
            }
        }
    }

    /**
     * A method to print all reservations of the restaurant
     */
    public static void showReservations() {
        int i, j;
        removeExpiredReservationsFromTables();
        for (i = 0; i < tables.size(); i++) {
            for (j = 0; j < tables.get(i).length; j++) {
                System.out.println("Table " + tables.get(i)[j].getTableNum() + ":");
                tables.get(i)[j].printDetailedReservations();
                System.out.println();
            }
        }
    }

    /**
     * An overloaded method. Shows the reservation of a particular table
     * @param tableNum Table Number
     */
    public static void showReservations(int tableNum) {
        removeExpiredReservationsFromTables();

        int i, j, group = tableGroup(tableNum);

        for(j = 0; j < tables.get(group).length; j++){
            if(tables.get(group)[j].getTableNum() == tableNum){
                if(tables.get(group)[j].getNumReservations() != 0){
                    System.out.println("\nTable " + tableNum + ":");
                    tables.get(group)[j].printDetailedReservations();
                    return;
                }
                else{
                    System.out.println("\nTable " + tableNum + ": No Reservations found");
                }

            }
        }
    }

    /**
     * A private method to assign the correct table size to the customer.
     * Eg. If the customer has 3 Pax, then we will assign a table of size 4 (index 1) to the customer
     *     If the customer has 2 Pax, then we will assign a table of size 2 (index 0) to the customer
     * The formula to assign the group is as follows:
     *     if numPax is odd, we will return the floor division
     *     else we will return the floor division -1
     */
    private static int tableGroup(int numPax){
        if (numPax % 2 == 0){
            return (numPax/2-1);
        }
        else {
            return (numPax/2);
        }
    }

    /**
     * To open a table once a customer has paid
     * @param tableNum Table number to be released
     */
    public static void releaseTable(int tableNum) {
        int i, j;

        for (i = 0; i < tables.size(); i++) {
            for (j = 0; j < tables.get(i).length; j++) {
                if (tables.get(i)[j].getTableNum() == tableNum) {
                    tables.get(i)[j].freeTable();
                    return;
                }
            }
        }
    }
}
