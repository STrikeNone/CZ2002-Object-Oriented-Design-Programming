package mgr;

import application.Restaurant;
import entities.Food;
import entities.MenuItem;
import entities.Promotion;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The controller app for the Food and Menu of the restaurant
 */

public class FoodMgr {
    private static ArrayList<MenuItem> fullMenu = Restaurant.menu;
    private static Scanner inputs = new Scanner(System.in);

    /**
     * Accessor method to get the full menu
     * @return menu of the restaurant
     */
    public static ArrayList<MenuItem> getMenu(){
        return fullMenu;
    }

    /**
     * Add a food item to the menu
     * @param name name of food
     * @param description description of food
     * @param price cost of food
     * @param type foodtype
     */
    public static void addFoodItem(String name, String description, double price, Food.FoodType type){
        Food newFoodItem = new Food(name, description, price, type);
        int foodItems = 0;
        for(MenuItem food: fullMenu) {
            if (food instanceof Food) {
                foodItems++;
                if (food.getName().equals(newFoodItem.getName())) {
                    System.out.println("This item already exists! Terminating...");
                    return;
                }
            }
        }
        fullMenu.add(foodItems, newFoodItem);
        System.out.println("This item has been added successfully!");
    }

    /**
     * Add a promotion to the menu
     * @param name name of package
     * @param description description of package
     */
    public static void addPromotion(String name, String description){
        Promotion newPromotion = new Promotion(name, description, 0);
        newPromotion.addFood(fullMenu);
        fullMenu.add(newPromotion);
        System.out.println("New package added successfully!");
        return;
    }

    /**
     * Edit a food item in the menu
     * @param choice name of food item
     * @param newName updated name
     * @param newDescription updated description
     * @param newPrice updated price
     */
    public static void editFoodItem(String choice, String newName, String newDescription, double newPrice){
        for (MenuItem food : fullMenu) {
            if (food instanceof Food && food.getName().equals(choice)) {
                food.setName(newName);
                food.setDescription(newDescription);
                food.setPrice(newPrice);
                System.out.println("Food item updated!");
                return;
            }
        }
        System.out.println("Food entry not found. Returning....");
        return;
    }

    /**
     * Add a food item to promotion
     * @param promo The promotion to modify
     */
    public static void addFoodItemToPromotion(Promotion promo){
        promo.addFood(fullMenu
        );
    }

    /**
     * Remove a food item from promotion
     * @param promo The promotion to modify
     */
    public static void removeFoodItemFromPromotion(Promotion promo){
        promo.removeFood();

    }

    /**
     * Edit a promotion item
     * @param choice choice to edit
     * @param p The Promotion to be edited
     */
    public static void editPromotion(int choice, Promotion p){
        if(choice == 1){
            addFoodItemToPromotion(p);
        }
        else if(choice == 2){
            removeFoodItemFromPromotion(p);
        }
    }

    /**
     * Remove a food item from menu
     * @param name Food to be deleted
     */
    public static void deleteFoodItem(String name){
        for(MenuItem food: fullMenu){
            if(food instanceof Food && food.getName().equals(name)){
                fullMenu.remove(food);
                System.out.println(name + " has been removed from the menu.");
                return;
            }
        }
        System.out.println("Does not exist! Returning ...");
        return;
    }

    /**
     * Delete a promotion from menu
     * @param idx Index of promotion to be removed
     */
    public static void deletePromotion(int idx){
        int count = 1;
        for(MenuItem food: fullMenu){
            if(food instanceof Promotion){
                if(count == idx){
                    fullMenu.remove(food);
                    System.out.println("Package removed successfully!");
                    //inputs.nextLine();
                    return;
                }
                count+=1;
            }
        }
        System.out.println("Invalid index entered! Returning...");
        //inputs.nextLine();
        return;
    }

    /**
     * View all Food Items of a certain type
     * @param type Foodtype to be viewed
     */
    public static void viewType(Food.FoodType type){
        int count = 1;
        System.out.println("================= " + type + " =================");
        for(MenuItem food: fullMenu){
            if(food instanceof Food && ((Food) food).getType() == type){
                System.out.println(count + ". " + food.getName() + " " +  "-".repeat(30 - food.getName().length()) + " $" + food.getPrice());
                System.out.println(food.getDescription());
                count+=1;
            }
        }
        if (count == 1){
            System.out.println("Not available");
        }
        System.out.println();
        return;
    }

    /**
     * View all promotions
     * @return 1 if the operation was successful, -1 otherwise
     */
    public static int viewPromotion(){
        int count = 1;
        System.out.println("=============== PROMOTIONAL PACKAGE ================");
        for(MenuItem food: fullMenu){
            if(food instanceof Promotion){
                System.out.println(count + ". " + food.getName() + " " +  "-".repeat(30 - food.getName().length()) + " $" + food.getPrice());
                System.out.println(food.getDescription());
                count+=1;
            }
        }
        if (count == 1){
            System.out.println("Not available");
            return -1;
        }
        return 1;
    }

    /**
     * View the contents of a particular Promotion package
     * @param name name of promotion
     */
    public static void viewPromotionItem(String name){
        for(MenuItem food: fullMenu){
            if(food instanceof Promotion){
                if(food.getName().equals(name)){
                    Promotion promo = (Promotion) food;
                    promo.printPromotion();
                    return;
                }
            }
        }
        System.out.println("Package does not exist! Returning...");
        return;
    }

}
