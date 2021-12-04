package entities;


import java.util.ArrayList;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Abstract class for both DailyRevenue and MonthlyRevenue classes.
 */

public abstract class Revenue implements Serializable{

    /**
     * Hashmap of reports
     */
    private HashMap<String, Integer> report;
    /**
     * Month of report
     */
    private int month;
    /**
     * Total revenue generated
     */
    private double totalCost;

    /**
     * Total discount given
     */
    private double totalDiscount;

    /**
     * Constructor of Revenue
     * @param month month of report
     * @param report report to be stored
     * @param totalCost total revenue
     * @param totalDiscount total discount given
     */
    public Revenue(int month, HashMap<String, Integer> report, double totalCost, double totalDiscount){
        this.month = month;
        this.report = report;
        this.totalCost = totalCost;
        this.totalDiscount = totalDiscount;
    }

    /**
     * Revenue report
     * @return Returns the details of the revenue
     */
    public HashMap<String, Integer> getReport() {
        return report;
    }

    /**
     * Mutator method to set report
     * @param report report to be set
     */
    public void setReport(HashMap<String, Integer> report) {
        this.report = report;
    }

    /**
     * Accessor method to get the month of the report
     * @return the month of the report
     */
    public int getMonth() {
        return month;
    }

    /**
     * Mutator method to set the month of the report
     * @param month month for report
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Accessor method to get total cost/revenue
     * @return total cost/revenue
     */
    public double getTotal() {
        return totalCost;
    }

    /**
     * Mutator method for total cost/revenue
     * @param total Total cost/revenue
     */
    public void setTotal(double total) {
        this.totalCost = total;
    }

    /**
     * Accessor method for discount of report
     * @return totalDiscount
     */
    public double getTotalDiscount() {
        return totalDiscount;
    }

    /**
     * Mutator method for discount of the report
     * @param totalDiscount total discount given
     */
    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    /**
     * Print revenue
     * @param menu Menu of restaurant
     */
    public abstract void printRevenue(ArrayList<MenuItem> menu);
}