package ui;


import application.Restaurant;
import entities.Table;
import mgr.TableMgr;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * User interface app for the tables and reservations
 */
public class TableUI implements Serializable {
    private static final long serialID = 123;

    /**
     * Arraylist to store all the table arrays
     */
    private static ArrayList<Table[]> tables = Restaurant.tables;
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Load the Table Manager User Interface
     */
    public static void printOptions(){
        int choice;

        do {
            System.out.println("Select your option:\n" +
                    "1) Assign a table to a customer\n" +
                    "2) View status of all tables\n" +
                    "3) Create new reservation\n" +
                    "4) Cancel existing reservation\n" +
                    "5) View reservations for all tables\n" +
                    "6) View reservations for a table\n" +
                    "7) Release Table\n" +
                    "8) Exit");

            choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println();


            switch (choice){

                case 1:
                    assignTableUI();
                    break;

                case 2:
                    viewStatusOfTableUI();
                    break;

                case 3:
                    createReservationUI();
                    break;

                case 4:
                    cancelReservationUI();
                    break;

                case 5:
                    showReservationsUI();
                    break;

                case 6:
                    showReservationsSpecificTableUI();
                    break;

                case 7:
                    releaseTableUI();
                    break;
            }

            System.out.println();

        }while(choice != 8);
    }

    /**
     * User interface to assign table to customer
     **/
    public static void assignTableUI(){
        boolean reservation, membership;
        int empID, numPax, tableNum;

        //Basic inputs to check if a customer has reservation and membership
        System.out.print("Does customer have any reservation (true/false): ");
        reservation = scanner.nextBoolean();

        System.out.print("Does customer have a membership (true/false): ");
        membership = scanner.nextBoolean();

        // Check if a staff has been created (log in) before, if not the user would not be able to continue
        if(OrderUI.getNumStaff() == 0){
            System.out.println("No Staff has been registered in the system! Please have a staff log in first!");
            return;
        }
        System.out.println("Enter employee ID (-1 to exit):");
        empID = scanner.nextInt();
        if(empID == -1){
            return;
        }
        while(!OrderUI.startNewOrder(empID,9999,membership) && empID != -1) {
            System.out.println("Enter employee ID (-1 to exit):");
            empID = scanner.nextInt();
        }
        if(empID == -1){
            return;
        }

        // Once we verify staff is valid, get number of pax of the customer
        System.out.print("Number of Pax: ");
        numPax = scanner.nextInt();

        // Attempt to assign a table. If no table we will not allow any more continuation
        tableNum = TableMgr.assignTable(numPax, reservation);
        if(tableNum == -1){
            return;
        }

        OrderUI.startNewOrder(empID, tableNum, membership);
        System.out.println("Table Assigned: " + tableNum);
        return;

    }

    /**
     * User interface that prints status of all tables whether or not is occupied
     */
    public static void viewStatusOfTableUI(){
        TableMgr.viewStatusOfTable();
    }

    /**
     * User interface to create a new reservation for the restaurant
     */
    public static void createReservationUI(){
        GregorianCalendar reservationDate;
        String customerName, customerNum;
        int numPax;
        boolean correctDate;

        System.out.print("Enter customer name: ");
        customerName = scanner.nextLine().trim();

        System.out.print("Enter customer contact: ");
        customerNum = scanner.next().trim();

        System.out.print("Enter number of pax: ");
        numPax = scanner.nextInt();

        do{
            reservationDate = (GregorianCalendar) createDateObject().clone();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, MMMM d, yyyy, HH:mm");
            System.out.println("Please check if the booking is correct:\n" +
                    dateFormatter.format(reservationDate.getTime()));

            System.out.print("Please verify if it is correct (true/false): ");
            correctDate = scanner.nextBoolean();
            scanner.nextLine();
        }while(!correctDate);

        TableMgr.createReservation(customerName, customerNum, numPax, reservationDate);
    }

    /**
     * User interface to cancel an existing reservation
     */
    public static void cancelReservationUI(){
        String customerName, customerNum;
        int numPax;

        System.out.print("Enter customer name: ");
        customerName = scanner.nextLine().trim();

        System.out.print("Enter customer contact: ");
        customerNum = scanner.next().trim();

        System.out.print("Enter number of pax: ");
        numPax = scanner.nextInt();

        TableMgr.cancelReservation(customerName, customerNum, numPax);
    }

    /**
     * User interface to print all reservations of the restaurant
     */
    public static void showReservationsUI() {
        TableMgr.showReservations();
    }

    /**
     * User interface shows the reservation of a particular table
     */
    public static void showReservationsSpecificTableUI() {
        int tableNum;

        System.out.print("Enter table number: ");
        tableNum = scanner.nextInt();

        TableMgr.showReservations(tableNum);
    }

    /**
     * User interface to open a table once a customer has paid
     */
    public static void releaseTableUI() {
        int tableNum;

        System.out.println("Enter table number: ");
        tableNum = scanner.nextInt();

        TableMgr.releaseTable(tableNum);
    }

    /**
     * To create a GregorianCalander object it tries to keep the date to within a reasonable value
     * @return GregorianCalander object
     */
    private static GregorianCalendar createDateObject(){
        Scanner scanner = new Scanner(System.in);
        String datestr, datecomponents[];
        int timestr, timecomponents[];

        System.out.println("Enter your date of booking (DD/MM/YYYY): ");
        while(true){
            datestr = scanner.next();
            datecomponents = datestr.split("/");

            if(datecomponents.length != 3){
                System.out.println("Invalid Date Format!");
            }
            else if (Integer.parseInt(datecomponents[0]) < 1 || Integer.parseInt(datecomponents[0]) > 31){
                System.out.println("Invalid Date given");
            }
            else if (Integer.parseInt(datecomponents[1]) < 1 || Integer.parseInt(datecomponents[1]) > 12){
                System.out.println("Invalid Month given");
            }
            else if (Integer.parseInt(datecomponents[2]) < 2021 || Integer.parseInt(datecomponents[2]) > 2050){
                System.out.println("Invalid Year given");
            }
            else{
                break;
            }
        }

        System.out.println("Enter your time of booking (24 Hour format): ");
        do{
            timestr = scanner.nextInt();
            timecomponents = new int[2];
            timecomponents[1] = timestr % 100;
            timecomponents[0] = timestr / 100;
        }while(timecomponents[1] > 59 || timecomponents[1] < 0 || timecomponents[0]  > 24 || timecomponents[0]  < 0);

        GregorianCalendar date = new GregorianCalendar(Integer.parseInt(datecomponents[2]), Integer.parseInt(datecomponents[1])-1,
                Integer.parseInt(datecomponents[0]), timecomponents[0], timecomponents[1]);

        return date;


    }

    /**
     * User interface to create the layout of a new restaurant
     * @return Returns an Arraylist of Table arrays to represent the layout of the restaurant
     */
    public static ArrayList<Table[]> initialiseRestaurantUI(){

        int tableConfig[] = new int[5];


        System.out.println("No previous table setup found! Please enter new layout.");

        for(int i = 0; i < 5; i++){
            System.out.printf("Enter number of %d seater tables: ", (i+1)*2);
            tableConfig[i] = scanner.nextInt();
        }

        return TableMgr.initialiseRestaurant(tableConfig);
    }

}
