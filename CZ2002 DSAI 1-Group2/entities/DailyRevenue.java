package entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import mgr.OrderMgr;

/**
 * Class to handle daily revenue reports
 */

public class DailyRevenue extends Revenue implements Serializable {

    private static final long serialID = 122333;
    /**
     * Date of report
     */
    private int date;

    /**
     * Constructor for DailyRevenue
     * @param date date of report
     * @param month month of report
     * @param report report to be stored
     * @param totalCost total revenue generated
     * @param totalDiscount total discount given
     */
    public DailyRevenue(int date, int month, HashMap<String, Integer> report, double totalCost, double totalDiscount){
        super(month, report, totalCost, totalDiscount);
        this.date = date;
    }
    /**
     * Print out the revenue for a particular day of a month
     * @param menu The menu of items and its prices
     */
    @Override
    public void printRevenue(ArrayList<MenuItem> menu) {
        System.out.println("--------------------------");
        double unitPrice = 0, foodCost;
        int foodQty;
        System.out.println("Daily report for " + this.date + "-" + this.getMonth() + "-2021");
        System.out.println("Name                     Qty     Cost");
        for(String foodItem: this.getReport().keySet()){
            for (MenuItem menuItem: menu) {
                if (menuItem.getName().equals(foodItem.substring(0,1).toUpperCase() + foodItem.substring(1))){
                    unitPrice = menuItem.getPrice();

                    break;
                }
            }
            System.out.print(foodItem);
            System.out.print(" ".repeat(25-foodItem.length()));
            foodQty = this.getReport().get(foodItem);
            foodCost = this.getReport().get(foodItem) * unitPrice;
            foodCost = Math.round(foodCost*100)/100.0;
            System.out.println(foodQty + " ".repeat(8-String.valueOf(foodQty).length()) + "$" + String.format("%.2f", foodCost));

        }
        System.out.println("Total cost: $" + String.format("%.2f", this.getTotal()));
        System.out.println("Total discount: -$" + String.format("%.2f", this.getTotalDiscount()));
        System.out.println("--------------------------");
    }
    /**
     * Get the date of the revenue object
     * @return Date of the total revenue checked
     */
    public int getDate() {
        return date;
    }

    /**
     * Set the date of the daily revenue
     * @param date The current date
     */
    public void setDate(int date) {
        this.date = date;
    }


}
