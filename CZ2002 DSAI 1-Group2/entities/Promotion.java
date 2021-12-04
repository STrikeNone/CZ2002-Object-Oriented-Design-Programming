package entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Class to handle food promotions
 */
public class Promotion extends MenuItem implements Serializable {
    private static final long serialID = 122333;
    private static final double DISCOUNT = 0.05;

    /**
     * Arraylist of food in the menu
     */
    private ArrayList<Food> foodList;


    /**
     * Constructor to create a new promotional set package. Initialises the item with a name, description and its actual price
     * @param name        name of the food item
     * @param description description of the food item
     * @param price       cost of the food item
     */
    public Promotion(String name, String description, int price) {
        super(name, description, price);
        this.foodList = new ArrayList<Food>();
    }

    /**
     * Add a new food item to the package
     * This method is private because it should only be accessed within the class itself
     * @param food The Food item to be added
     */
    private void addFood(Food food){
        this.foodList.add(food);
        this.updatePrice(food.getPrice());
    }

    /**
     * Add a new food item to the package from the current Menu
     * @param foodMenu The entire menu available to the restaurant
     */
    public void addFood(ArrayList<MenuItem> foodMenu){
        Scanner choice = new Scanner(System.in);
        int index = 1;
        System.out.println("===== Menu Items to choose from =====");
        for(MenuItem menuItem: foodMenu){
            if(menuItem instanceof Food) {
                System.out.printf(index++ + ": " + menuItem.getName() + " ($" + menuItem.getPrice() + ")\n");
            }
        }
        System.out.printf("Which item would you like to add to the package? (-1 to exit): ");
        int selection = choice.nextInt();
        while(selection != -1) {
            try {
                if (foodMenu.get(selection - 1) instanceof Food) {
                    Food addToMenu = (Food) foodMenu.get(selection - 1);
                    this.addFood(addToMenu);
                    System.out.println(addToMenu.getName() + " has been successfully added to the package!\n");
                } else {
                    System.out.println("Selected item is not a food");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Food item not added (invalid index provided)");
            }
            System.out.printf("Enter next choice (-1 to exit): ");
            selection = choice.nextInt();
        }

    }

    /**
     * Remove a FoodItem from the package
     */
    public void removeFood(){
        Scanner choice = new Scanner(System.in);
        int index = 1;
        if(foodList.size() < 1){
            System.out.println("This set package has no items");
            return;
        }

        for(Food foodItem: foodList){
            System.out.println(index++ + ": " + foodItem.getName());
        }

        System.out.println("Enter choice");
        index = choice.nextInt();
        try {
            String foodName = foodList.get(index - 1).getName();
            double price = foodList.get(index-1).getPrice();
            foodList.remove(index - 1);
            this.updatePrice(-1 * price);
            System.out.println(foodName + " has been removed from " + this.getName() + " package successfully");
        }catch(IndexOutOfBoundsException e){
            System.out.println("Invalid index entered. Returning...");
        }
    }

    /**
     * Print out the contents of the package
     */
    public void printPromotion(){
        System.out.println("Package: " + this.getName());
        System.out.println("Description: " + this.getDescription());
        System.out.println("Includes: ");
        for (Food f: new HashSet<Food>(foodList)){
            int count = Collections.frequency(foodList, f);
            System.out.println(count +"x " + f.getName() + " ");
        }
        return;
    }

    @Override
    /**
     * Update Price implementation of abstract method
     * @param newFoodPrice Price of new food item being added
     */
    public void updatePrice(double newFoodPrice){
        double currentPrice = this.getPrice();
        this.setPrice(currentPrice + newFoodPrice * (1-DISCOUNT));
    }

}
