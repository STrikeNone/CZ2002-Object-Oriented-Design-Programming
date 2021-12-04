package ui;

import entities.Food;
import entities.MenuItem;
import entities.Promotion;
import mgr.FoodMgr;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * User interface app for the Food and Menu
 */
public class FoodUI {
    private static Scanner inputs = new Scanner(System.in);

    /**
     * Load the Food User Interface
     */
    public static void printOptions(){
        int choice;

        do {
            System.out.println("Please select an option:\n" +
                    "1) View menu\n" +
                    "2) Create food items\n" +
                    "3) Remove food items\n" +
                    "4) Edit food item\n" +
                    "5) Create new promotion package\n" +
                    "6) Edit food items to existing promotion package\n" +
                    "7) Delete existing promotion package\n" +
                    "8) View promotional package items\n" +
                    "9) Exit");

            choice = inputs.nextInt();
            inputs.nextLine();
            System.out.println();

            switch(choice){
                case 1:
                    viewMenuUI();
                    break;
                case 2:
                    addFoodItemUI();
                    break;
                case 3:
                    deleteFoodItemUI();
                    break;
                case 4:
                    editFoodItemUI();
                    break;
                case 5:
                    addPromotionUI();
                    break;
                case 6:
                    editPromotionUI();
                    break;
                case 7:
                    deletePromotionUI();
                    break;
                case 8:
                    viewPromotionItemUI();
                    break;
                default:
                    break;
            }
            System.out.println();

        }while(choice != 9);
    }

    /**
     * User Interface to display the Menu
     */
    public static void viewMenuUI(){
        ArrayList<MenuItem> fullMenu = FoodMgr.getMenu();
        if(fullMenu.size() == 0){
            System.out.println("There are currently no items in the menu!");
            return;
        }
        else{
            FoodMgr.viewType(Food.FoodType.STARTERS);
            FoodMgr.viewType(Food.FoodType.MAIN);
            FoodMgr.viewType(Food.FoodType.DRINKS);
            FoodMgr.viewType(Food.FoodType.DESSERT);
            FoodMgr.viewPromotion();
        }

    }

    /**
     * User interface to view a particular Promotional package
     * @return 1 if there is such a promotion, -1 if there is no promotions
     */
    public static int viewPackageUI(){
        return FoodMgr.viewPromotion();
    }

    /**
     * User interface to add Food items to the menu
     */
    public static void addFoodItemUI(){
        Food.FoodType type;
        System.out.printf("Enter new food name: ");
        String name = inputs.nextLine().trim();
        name = name.substring(0,1).toUpperCase() + name.substring(1);
        System.out.printf("Enter food description: ");
        String description = inputs.nextLine();
        System.out.printf("Enter food price: ");
        double price = inputs.nextDouble();
        System.out.print("Enter food type (STARTERS, MAIN, DRINKS, DESSERT): ");
        while(true){
            try{
                type = Food.FoodType.valueOf(inputs.next().toUpperCase());
                break;
            }catch (IllegalArgumentException e){
                System.out.print("Invalid food type!\n" +
                        "Please enter valid food type (STARTERS, MAIN, DRINKS, DESSERT): ");
            }
        }
        FoodMgr.addFoodItem(name, description, price, type);
    }

    /**
     * User interface to delete Food items from the menu
     */
    public static void deleteFoodItemUI(){
        viewMenuUI();
        System.out.printf("\nEnter food name to delete: ");
        String name = inputs.nextLine();
        name = name.substring(0,1).toUpperCase()+name.substring(1);
        FoodMgr.deleteFoodItem(name);
    }

    /**
     * User interface to edit Food items in the menu
     */
    public static void editFoodItemUI(){
        viewMenuUI();
        System.out.println("\nEnter food name to edit: ");
        String choice = inputs.nextLine().trim();
        System.out.print("Enter new food name: ");
        String newName = inputs.nextLine().trim();
        System.out.print("Enter new description: ");
        String description = inputs.nextLine().trim();
        System.out.print("Enter new price: ");
        double price = inputs.nextDouble();
        inputs.nextLine();
        FoodMgr.editFoodItem(choice, newName, description, price);
        return;
    }

    /**
     * User interface to add a new Promotion to the menu
     */
    public static void addPromotionUI(){
        System.out.printf("Enter promotional package name: ");
        String name = inputs.nextLine();
        name = name.substring(0,1).toUpperCase()+name.substring(1);
        System.out.printf("Enter description: ");
        String description = inputs.nextLine();
        description=description.substring(0,1).toUpperCase()+description.substring(1);
        FoodMgr.addPromotion(name, description);
    }

    /**
     * User interface to edit a Promotional package in the menu
     */
    public static void editPromotionUI(){
        int status = FoodUI.viewPackageUI();
        ArrayList<MenuItem> menu = FoodMgr.getMenu();
        if(status == -1){
            return;
        }
        System.out.print("Which package (name) would you like to edit: ");
        String choice = inputs.nextLine().trim();
        for(MenuItem food: menu){
            if(food instanceof Promotion){
                if(food.getName().equals(choice)){
                    Promotion edit = (Promotion)food;
                    System.out.print("> What would you like to do with the " + edit.getName() + " package (1 to add, 2 to delete): ");
                    int idx = inputs.nextInt();
                    switch(idx){
                        case 1, 2:
                            FoodMgr.editPromotion(idx, edit);
                            break;
                        default:
                            System.out.println("Invalid Choice. Returning...");
                            System.out.println();
                            inputs.nextLine();
                    }
                    return;
                }
            }
        }
        System.out.println("Invalid package name entered! Returning...");

    }

    /**
     * User interface to remove a Promotion from the menu
     */
    public static void deletePromotionUI(){
        int choice;
        FoodUI.viewPackageUI();
        System.out.printf("Enter package index to be removed (-1 to exit): ");

        while(true){
            try{
                choice = inputs.nextInt();
                break;
            }catch (InputMismatchException e){
                System.out.print("Please enter the package INDEX to be removed (-1 to exit): ");
                inputs.nextLine();
            }
        }
        if(choice == -1){
            System.out.println("No packages were removed.");
        }else {
            FoodMgr.deletePromotion(choice);
        }
        return;
    }

    /**
     * User interface to view a particular Promotion details
     */
    public static void viewPromotionItemUI(){
        int status = FoodUI.viewPackageUI();
        String choice;
        if(status == -1){return;}
        System.out.println();
        System.out.print("Which package (NAME) would you like to view: ");
        choice = inputs.nextLine();
        FoodMgr.viewPromotionItem(choice);
        return;
    }
}
