package entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Class to handle monthly revenue reports
 */
public class MonthlyRevenue extends Revenue implements Serializable {

    private static final long serialID = 122333;

    /**
     * Arraylist of daily revenue reports
     */
    private ArrayList<DailyRevenue> dailyRev;

    /**
     * Constructor to initialise a monthly revenue object
     * @param month Month being checked
     * @param report The details about that months revenue
     * @param totalCost Total cost of food sold for that day
     * @param totalDiscounts Total discounts given out
     */
    public MonthlyRevenue(int month, HashMap<String, Integer> report, double totalCost, double totalDiscounts){
        super(month, report, totalCost, totalDiscounts);
        dailyRev = new ArrayList<DailyRevenue>();
    }

    /**
     * Print out the revenue for a particular month
     * @param menu The menu of items and its prices
     */
    @Override
    public void printRevenue(ArrayList<MenuItem> menu) {
        System.out.println("--------------------------");
        double unitPrice = 0, foodCost, discount;
        int foodQty;
        System.out.println("Monthly report for " + this.getMonth() + "-2021");
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
        discount = Math.round(this.getTotalDiscount()*100)/100.0;
        System.out.println("Total discount: -$" + String.format("%.2f" , discount));
        System.out.println("--------------------------");
    }


    /**
     * Return the revenue for a particular day
     * @param day Day to be found
     * @param menu Menu of items and its cost
     */
    public void getDailyRevenue(int day, ArrayList<MenuItem> menu){
        //System.out.println(dailyRev.size());
        for(DailyRevenue curr: dailyRev){
            if(curr.getDate() == day){
                curr.printRevenue(menu);
                return;
            }
        }
        System.out.println("This report does not exist");
    }

    /**
     * Updates the monthly revenue with a new DailyRevenue report
     * @param daily daily revenue report to be included
     */
    public void updateDailyRevenue(DailyRevenue daily){
        updateMonthlyRevenue(daily);
        dailyRev.sort(Comparator.comparing(DailyRevenue::getDate));

    }

    /**
     * Update the monthly revenue with a new daily revenue entry;
     * @param daily Pass in a particular date DailyRevenue and store it in the month
     */
    public void updateMonthlyRevenue(DailyRevenue daily){
        HashMap<String, Integer> dReport = daily.getReport();
        HashMap<String, Integer> mReport = this.getReport();
        dailyRev.add(daily);
        for(String foodItem: dReport.keySet()){
            if(mReport.containsKey(foodItem)) {
                mReport.put(foodItem, mReport.get(foodItem) + dReport.get(foodItem));
            }
            else{
                mReport.put(foodItem, dReport.get(foodItem));
            }
        }
        this.setTotal(this.getTotal()+daily.getTotal());
        this.setTotalDiscount(this.getTotalDiscount()+daily.getTotalDiscount());
        this.setReport(mReport);

    }


}