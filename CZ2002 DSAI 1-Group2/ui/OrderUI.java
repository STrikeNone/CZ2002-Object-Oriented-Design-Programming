package ui;


import application.Restaurant;
import entities.*;
import mgr.FoodMgr;
import mgr.OrderMgr;
import mgr.TableMgr;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashMap;


/**
 * User interface app for the orders
 */
public class OrderUI {

    /**
     * ArrayList of Open orders. For system to place orders only by table number.
     */
    private static ArrayList<OrderMgr> openOrders = new ArrayList<OrderMgr>();
    /**
     * ArrayList of Closed orders. For system to calculate monthly revenue.
     */
    private static ArrayList<OrderMgr> closedOrders = new ArrayList<OrderMgr>();
    /**
     * ArrayList of staffs who will interact with the system.
     */
    private static ArrayList<Staff> staffs = Restaurant.staff;
    /**
     * foodUI to store the menu for interaction.
     */
    private static FoodUI foodUI;
    /**
     * Tagged Table number
     */
    private static int tableNum;

    private static Scanner scanner = new Scanner(System.in);


    /**
     * Method to print the menu options
     */
    public static void  printOptions(){
        int choice;
        do{

            System.out.println("Select your option:\n" +
                    "1) Place Orders\n" +
                    "2) Cancel Orders\n" +
                    "3) View Orders\n" +
                    "4) Finish Order\n" +
                    "5) Print order invoice\n" +
                    "6) Close orders for the day\n" +
                    "7) Print Monthly Revenue Report\n" +
                    "8) Print Daily Revenue Report\n" +
                    "9) Add New Staff\n" +
                    "10) View Staff\n" +
                    "11) Reset Orders\n" +
                    "12) Exit");

            choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    placeOrders();
                    break;

                case 2:
                    cancelOrders();
                    break;

                case 3:
                    viewOrders();
                    break;

                case 4:
                    TableMgr.releaseTable(finishOrder());
                    break;

                case 5:
                    printInvoice();
                    break;

                case 6:
                    closeRestaurant();
                    break;

                case 7:
                    printMonthlyRevenue();
                    break;

                case 8:
                    printDailyRevenue();
                    break;

                case 9:
                    addStaff();
                    break;

                case 10:
                    displayStuff();
                    break;

                case 11:
                    resetOrders();
                    break;

            }
            System.out.println();
        }while (choice != 12);
    }

    /**
     * Method to return the number of staffs
     * @return int number of staff
     */
    public static int getNumStaff(){return staffs.size();}

    /**
     * Method to display staff information
     */
    public static void displayStuff(){
        if(getNumStaff() == 0){
            System.out.println("No staff available! Returning...");
            return;
        }
        int count = 1;
        for(Staff curr: staffs){
            System.out.println(count++ + ": " + curr.toString());
        }
    }

    /**
     * Start new order when a set of customers is seated.
     * @param staffID from driver code
     * @param table number from driver code
     * @param membership boolean from driver code
     * @return true if order was successfully started, false otherwise
     */
    public static boolean startNewOrder(int staffID, int table, boolean membership) {
        for (Staff staff: staffs) {
            if(staff.getEmployeeID() == staffID) {
                openOrders.add(new OrderMgr(staff, table, membership));
                return true;
            }
        }
        return false;
    }

    /**
     * Place order for seated customers.
     */
    public static void placeOrders() {
        boolean success;
        String menuitem,cont;
        int table, quant;

        if(FoodMgr.getMenu().size() < 1){
            FoodUI.viewMenuUI();
            return;
        }

        FoodUI.viewMenuUI();
        System.out.print("\nEnter table number: ");

        while(true){
            try{
                table = scanner.nextInt();
                scanner.nextLine();
                break;
            }catch(InputMismatchException e){
                scanner.nextLine();
                System.out.print("Enter a valid TABLE NUMBER: ");
            }
        }

        for (OrderMgr order: openOrders) {
            if (order.getTableNum() == table) {
                do {
                    System.out.print("Enter Menu Item Name: ");
                    menuitem = scanner.nextLine();
                    System.out.print("Enter Quantity: ");
                    quant = scanner.nextInt();
                    scanner.nextLine();
                    success = order.addFoodToOrder(menuitem, quant);
                    if (success == false)
                        System.out.println("Invalid Menu Item\n" +
                                "Try Again?\n" +
                                "(y)   (n)");
                    else
                        System.out.println("Order sent to the kitchen!\n" +
                                "Continue adding?\n" +
                                "(y)   (n)");
                    cont=scanner.nextLine();
                } while (cont.toLowerCase().charAt(0) == 'y' && cont.toLowerCase().charAt(0) != 'n');

                System.out.println("All Orders Placed Successfully");
                return;
            }
        }

        System.out.println("Table has not been opened!");
        return;
    }

    /**
     * Cancel order for seated customers.
     */
    public static void cancelOrders() {
        boolean success;
        String menuitem,cont;
        int table, quant;

        System.out.print("Enter table number: ");
        table = scanner.nextInt();
        scanner.nextLine();

        if(viewOrders(table)){
            for (OrderMgr order: openOrders) {
                if (order.getTableNum() == table) {
                    do {
                        System.out.print("Enter Menu Item Name: ");
                        menuitem = scanner.nextLine();
                        System.out.print("Enter Quantity: ");
                        quant = scanner.nextInt();
                        scanner.nextLine();
                        success = order.removeFoodFromOrder(menuitem, quant);
                        if (success == false)
                            System.out.println("Try Again?\n" +
                                    "(y)   (n)");
                        else
                            System.out.println("Order cancelled!\n" +
                                    "Continue removing?\n" +
                                    "(y)   (n)");
                        cont = scanner.nextLine();
                    } while (cont.toLowerCase().charAt(0) == 'y');

                    System.out.println("All Orders Removed Successfully");
                    return;
                }
            }
        }
        return;
    }

    /**
     * View order for seated customers.
     * @return boolean flag
     */
    public static boolean viewOrders() {
        int table;

        System.out.print("Enter table number: ");
        table = scanner.nextInt();

        for (OrderMgr order: openOrders) {
            if (order.getTableNum() == table) {
                order.viewOrder();
                return true;
            }
        }
        System.out.println("Table has not been opened!");
        return false;
    }

    /**
     * View order for seated customers.
     * @param table int
     * @return static flag
     */
    public static boolean viewOrders(int table) {
        for (OrderMgr order: openOrders) {
            if (order.getTableNum() == table) {
                order.viewOrder();
                return true;
            }
        }
        System.out.println("Table has not been opened!");
        return false;
    }

    /**
     * This method moves an order from the ArrayList of openOrders to closedOrders.
     * Also prints out the invoice.
     * @return int flag
     */
    public static int finishOrder() {
        int table;

        System.out.print("Enter table number: ");
        table = scanner.nextInt();

        tableNum = table;
        for (OrderMgr order: openOrders) {
            if (order.getTableNum() == table) {
                order.finishOrder();
                openOrders.remove(order);
                closedOrders.add(order);
                return table;
            }
        }
        System.out.println("Table has not been opened!");
        return -1;
    }

    /**
     * This method access and prints an invoice by the table number and time in the event
     * that the original invoice is lost.
     */
    public static void printInvoice() {

        System.out.print("Enter table number: ");
        int table = scanner.nextInt();

        System.out.print("Enter date of visit (YYYY-MM-DD)): ");
        String visitDate = scanner.next();

        System.out.print("Enter time of visit (HH:MM:SS): ");
        String visitTime = scanner.next();

        for (OrderMgr order: closedOrders) {
            if (order.getTableNum() == table && LocalDate.parse(visitDate).equals(order.getDate()) )
                if (LocalTime.parse(visitTime).isBefore(order.getEndTime()) && LocalTime.parse(visitTime).isAfter(order.getStartTime())){
                    order.printInvoice();
                    return;
                }
        }
        System.out.println("Invoice not found!");
        return;
    }

    /**
     * This method closes the restaurant and prints revenue for the day
     */
    public static void closeRestaurant(){
        int indicator = 0;
        HashMap<String, Integer> report = new HashMap<String, Integer>();
        DailyRevenue dailyRev = new DailyRevenue(LocalDate.now().getDayOfMonth(),LocalDate.now().getMonthValue(),report,0,0);

        //close orders for the day
        for(int key : Restaurant.totalMonthlyRev.keySet()){
            if(key == LocalDate.now().getMonthValue()){ //month alr exist
                indicator = 1;
                getRevenue(dailyRev, Restaurant.totalMonthlyRev.get(LocalDate.now().getMonthValue()));
                break;
            }
        }

        if(indicator!=1){
            //1st day of month
            MonthlyRevenue monthlyRev = new MonthlyRevenue(LocalDate.now().getMonthValue(),report,0,0);
            Restaurant.totalMonthlyRev.put(LocalDate.now().getMonthValue(),monthlyRev);
            monthlyRev = getRevenue(dailyRev, Restaurant.totalMonthlyRev.get(LocalDate.now().getMonthValue()));
            System.out.println("\n\n");
            Restaurant.totalMonthlyRev.replace(LocalDate.now().getMonthValue(),monthlyRev);
        }
    }

    /**
     * This method prints the selected monthly revenue
     */
    public static void printMonthlyRevenue(){
        int indicator = 0;
        System.out.println("Enter month (1-12):");
        int month = scanner.nextInt();
        for(int key : Restaurant.totalMonthlyRev.keySet()){
            if(key == month){ //alr exist
                indicator = 1;
                Restaurant.totalMonthlyRev.get(month).printRevenue(FoodMgr.getMenu());
                break;
            }
        }
        if(indicator !=1) { //month doesnt exist in hashmap yet
            if(LocalDate.now().getMonthValue()<month){
                System.out.println("Month entered has not occurred!");
            }
            else System.out.println("No records found");
        }
    }

    /**
     * This method prints the selected daily revenue
     */
    public static void printDailyRevenue(){
        //print daily rev ,usually done @ END OF DAY.
        System.out.println("\n");
        System.out.println("Enter date (1-31): ");
        int date = scanner.nextInt();
        System.out.println("Enter month (1-12): ");
        int month = scanner.nextInt();
        int indicator = 0;
        for(int key : Restaurant.totalMonthlyRev.keySet()){
            if(key == month){ //alr exist
                indicator = 1;
                Restaurant.totalMonthlyRev.get(month).getDailyRevenue(date,FoodMgr.getMenu());
                break;
            }
        }
        if(indicator != 1) { //monthlyrev deosnt exist yet for a particular month
            if(month > LocalDate.now().getMonthValue()) System.out.println("Month entered has not passed!");
            else {
                System.out.println("No records found");
            }
        }
    }

    /**
     * This method adds staff to the database
     */
    public static void addStaff(){
        System.out.println("Enter name of staff:");
        String staffName=scanner.nextLine();

        System.out.println("Enter employee ID:");
        int empID=scanner.nextInt();

        scanner.nextLine();
        System.out.println("Enter gender:");
        char gender=scanner.next().charAt(0);

        System.out.println("Enter job title:");
        String jobTitle=scanner.next();

        if(addNewStaff(staffName, empID, gender, jobTitle)){
            System.out.println("Staff successfully added!");
        }
        else{
            System.out.println("Staff not added. Please try again.");
        }
    }

    /**
     * This method prints the monthly revenue of a restaurant.
     * An ArrayList of HashMaps of each month's revenue can be stored in driver's code
     * @param dailyRev DailyRevenue for the day
     * @param monthlyRev MonthlyRevenue to be updated
     * @return MonthlyRevenue Object
     */
    public static MonthlyRevenue getRevenue(DailyRevenue dailyRev, MonthlyRevenue monthlyRev) {
        HashMap<String, Integer> monthlyOrders = new HashMap<String, Integer>();
        LocalDate date = LocalDate.now();
        int updatedQuant, members = 0;
        double totalDisc = 0;

        for (MenuItem menuItem: FoodMgr.getMenu()) {
            monthlyOrders.put(menuItem.getName().toLowerCase(), 0);
        }

        for (OrderMgr order: closedOrders) {
            if (order.getMembership()) {
                members++;
                totalDisc += order.getDiscount();
            }

            for (Order item: order.getInvoice()) {
                updatedQuant = monthlyOrders.get(item.getFood().toLowerCase()) + item.getQuantity();
                //System.out.println(updatedQuant);
                monthlyOrders.put(item.getFood().toLowerCase(), updatedQuant);
            }
        }

        System.out.println("Date: " + date);
        System.out.println("Total Unique Sales: " + OrderMgr.getTotalOrders());
        System.out.println("Total Unique Member Sales: " + members);
        System.out.println("Desc                    Qty   Unit Price  Amount      ");
        System.out.println("--------------------------------------------------");

        int subtotal = 0;
        double unitPrice = 0;
        int quantity, gap;
        double gst = 0, service = 0, total = 0;

        for (String foodItem : monthlyOrders.keySet()) {

            System.out.print(foodItem);
            gap = 25-foodItem.length();
            for(int i = 0;i <gap; i++)
                System.out.print(' ');
            quantity = monthlyOrders.get(foodItem);
            System.out.print(quantity);
            gap = 8 - String.valueOf(quantity).length();
            for(int i = 0;i <gap; i++)
                System.out.print(' ');
            for (MenuItem menuItem: FoodMgr.getMenu()) {
                if (menuItem.getName().toLowerCase().equals(foodItem)) {
                    unitPrice = menuItem.getPrice();
                    break;
                }
            }
            System.out.print(unitPrice);
            gap = 9 - String.valueOf(unitPrice).length();
            for(int i = 0;i <gap; i++)
                System.out.print(' ');
            System.out.println("$" + quantity*unitPrice);
            subtotal += quantity*unitPrice;
        }

        gst = Math.round(subtotal*OrderMgr.GST*100)/100.0;
        service = Math.round(subtotal*OrderMgr.SERVICE_CHARGE *100)/100.0;
        total = Math.round(subtotal*(1+OrderMgr.GST+OrderMgr.SERVICE_CHARGE)*100)/100.0;
        total = total - totalDisc;

        dailyRev.setTotal(total);
        dailyRev.setTotalDiscount(totalDisc);
        dailyRev.setReport(monthlyOrders);

        monthlyRev.updateMonthlyRevenue(dailyRev);


        System.out.println("--------------------------------------------------\n" +
                "Subtotal:                                 $" + subtotal + "\n" +
                "GST:                                      $" + String.format("%.2f", gst) + "\n" +
                "Service Charge:                           $" + String.format("%.2f", service) + "\n" +
                "Discount:                                 $-" + String.format("%.2f", totalDisc) + "\n" +
                "Revenue:                                  $" + String.format("%.2f", total));
        return monthlyRev;
    }

    /**
     * Method resets the ArrayList closedOrders and totalOrders.
     * Order ID starts from 0 for the new month.
     * @return ArrayList of all invoices in the closedOrders;
     */
    public static ArrayList<OrderMgr> resetOrders() {
        ArrayList<OrderMgr> temp = closedOrders;
        closedOrders.clear();
        OrderMgr.resetOrders();
        return temp;
    }

    /**
     * Method adds new staff to staffs ArrayList
     * @param name of staff
     * @param employeeID of staff
     * @param gender of staff
     * @param jobTitle Title of staff
     * @return Boolean true if staff is added successfully
     */
    private static boolean addNewStaff(String name, int employeeID, char gender, String jobTitle) {
        Staff newStaff = new Staff(name, employeeID, gender, jobTitle);

        for (Staff staff: staffs) {
            if (staff.getEmployeeID() == employeeID)
                return false;
        }
        staffs.add(newStaff);
        return true;
    }
}