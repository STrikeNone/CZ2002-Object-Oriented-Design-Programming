package mgr;

import java.io.Serializable;
import entities.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The controller app for the Orders of the restaurant
 */
public class OrderMgr implements Serializable{
    /**
     * GST of the restaurant
     */
    public static final double GST = 0.07;
    /**
     * serviceCharge of the restaurant
     */
    public static final double SERVICE_CHARGE = 0.1 * (1+GST);

    /**
     * Array list of order items
     */
    private ArrayList<Order> invoice;

    /**
     * Identity of staff
     */
    private Staff staff;

    /**
     * Total Orders, to be reset every month/week
     */
    private static long totalOrders = 0;

    /**
     * OrderID for a particular table
     */
    private long orderID;

    /**
     * Menu to be accessed
     */
    private ArrayList<MenuItem> menu;

    /**
     * Start Time for a table
     */
    private LocalDate date;

    /**
     * Start Time for a table
     */
    private LocalTime startTime;

    /**
     * End Time for a table
     */
    private LocalTime endTime;

    /**
     * Boolean true if order is completed
     */
    private boolean orderFinished;

    /**
     * TableNum associated
     */
    private int tableNum;

    /**
     * Membership associated
     */
    private boolean membership;

    /**
     * percentage discount for members
     */
    private static final double membershipDiscount = 0.05;

    /**
     * quantity discounted
     */
    private double discount;



    /**
     * Constructor to create a new OrderMgr for each table. Initializes the OrderMgr with ArrayList of order, staffID, OrderID.
     * @param staff identity of staff
     * @param member membership status of customer
     * @param table table number of customer
     */
    public OrderMgr(Staff staff, int table, boolean member) {
        this.staff = staff;
        invoice = new ArrayList<Order>();
        orderID = ++totalOrders;
        menu = FoodMgr.getMenu();
        orderFinished = false;
        startTime = LocalTime.now();
        date = LocalDate.now();
        tableNum = table;
        membership = member;
    }

    /**
     * Set new staff
     * @param staff 	identity of staff
     */
    public void setStaff(Staff staff) {
        this.staff = staff;
        orderID = ++totalOrders;
    }

    /**
     * @return Staff
     */
    public Staff getStaff() {
        return staff;
    }

    /**
     * @return endTime
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * @return startTime
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * @return startTime
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @return tableNum
     */
    public int getTableNum() {
        return tableNum;
    }

    /**
     * @return orderID
     */
    public long getOrderID() {
        return orderID;
    }

    /**
     * static method to reset total orders
     */
    public static void resetOrders() {
        totalOrders = 0;
    }

    /**
     * static method to get total orders
     * @return total number of orders
     */
    public static long getTotalOrders() {
        return totalOrders;
    }

    /**
     * get discount
     * @return value of discount
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * Accessor method to get membership
     * @return true if member, false otherwise
     */
    public boolean getMembership() {
        return membership;
    }

    /**
     * @return ArrayList of Order per invoice
     */
    public ArrayList<Order> getInvoice() {
        return invoice;
    }

    /**
     * Create a new order and add to ArrayList
     * @param menuitem name of food item ordered
     * @param quantity	quantity of food item
     * @return boolean true if item is added successfully
     */
    public boolean addFoodToOrder(String menuitem, int quantity){

        for(int i = 0; i<invoice.size(); i++) {
            if (invoice.get(i).getFood().toLowerCase().equals(menuitem.toLowerCase())) {
                int newQuant = invoice.get(i).getQuantity() + quantity;
                invoice.get(i).updateItem(newQuant);
                return true;
            }
        }

        for(MenuItem food: menu) {
            if (food instanceof Food && food.getName().toLowerCase().equals(menuitem.toLowerCase())) {
                Order order = new Order((Food) food, quantity);
                invoice.add(order);
                return true;
            }
            else if (food instanceof Promotion && food.getName().toLowerCase().equals(menuitem.toLowerCase())) {
                Order order = new Order((Promotion) food, quantity);
                invoice.add(order);
                return true;
            }
        }

        return false;
    }

    /**
     * Remove specific quantity of items from ArrayList
     * @param item	name of food item to be removed
     * @param quantity	quantity of food item
     * @return boolean true if removed
     */
    public boolean removeFoodFromOrder(String item, int quantity) {

        for(Order order: invoice) {
            if (order.getFood().toLowerCase().equals(item.toLowerCase())) {
                if (quantity > order.getQuantity()) {
                    System.out.println("Quantity Invalid");
                    return false;
                }
                else if (quantity == order.getQuantity() ) {
                    invoice.remove(order);
                    return true;
                }
                else {
                    int newQuant = order.getQuantity() - quantity;
                    order.updateItem(newQuant);
                    return true;
                }
            }
        }
        System.out.println("Item not found");
        return false;

    }

    /**
     * Print Items Ordered
     */
    public void viewOrder() {
        System.out.println("Desc		               Qty   Unit Price");
        System.out.println("--------------------------------------------");
        int quant = 0;

        for (Order item: invoice) {
            String foodname = item.getFood();

            System.out.print(foodname);
            quant = 29-foodname.length();
            for(int i = 0;i <quant; i++)
                System.out.print(' ');
            System.out.print(item.getQuantity());
            for(int i = 0;i <6; i++)
                System.out.print(' ');
            double unitPrice = item.getPrice();
            System.out.print(unitPrice);
            quant = 12 - String.valueOf(unitPrice).length();
            for(int i = 0;i <quant; i++)
                System.out.print(' ');
            System.out.println("\n");
        }
    }

    /**
     * Indicate that order has been completed and update the endTime
     */
    public void finishOrder() {
        endTime = LocalTime.now();
        orderFinished = true;
        printInvoice();
        return;
    }

    /**
     * Print Invoice
     */
    public void printInvoice() {
        Date date = new Date();
        SimpleDateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");

        System.out.println("                  Pura Pura");
        System.out.print("Date: " + formatterDate.format(date));
        System.out.println("             Time: " + formatterTime.format(date));
        System.out.println("Order ID: " + orderID);
        System.out.println("Staff: " + this.getStaff().getName());
        System.out.println("Desc                        Qty   Unit Price  Amount");
        System.out.println("----------------------------------------------------");

        int quant = 0;
        double subtotal = 0, gst = 0, service = 0, total = 0;

        for (Order item: invoice) {
            String foodname = item.getFood();

            System.out.print(foodname);
            quant = 29-foodname.length();
            for(int i = 0;i <quant; i++)
                System.out.print(' ');
            System.out.print(item.getQuantity());
            for(int i = 0;i <5; i++)
                System.out.print(' ');
            double unitPrice = item.getPrice();
            System.out.print("$" + unitPrice);
            quant = 11 - String.valueOf(unitPrice).length();
            for(int i = 0;i <quant; i++)
                System.out.print(' ');
            System.out.println("$" + item.getTotalPrice());
            subtotal += item.getTotalPrice();

        }
        gst = Math.round(subtotal*GST*100)/100.0;
        service = Math.round(subtotal* SERVICE_CHARGE *100)/100.0;
        if(getMembership()) {
            discount = Math.round(subtotal*membershipDiscount);
        }
        else discount = 0;
        total = (Math.round(subtotal*(1+GST+ SERVICE_CHARGE)*100)/100.0) - discount;


        System.out.println("----------------------------------------------------\n" +
                "Subtotal:                                      $" + subtotal + "\n" +
                "GST:                                           $" + String.format("%.2f", gst) + "\n" +
                "Service Charge:                                $" + String.format("%.2f", service) + "\n" +
                "Discount:                                      $-" + String.format("%.2f", discount) +"\n" +
                "Total:                                         $" +
                "" + String.format("%.2f", total));
        return;
    }

}