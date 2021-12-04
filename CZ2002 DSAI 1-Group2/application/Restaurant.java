package application;

import entities.MenuItem;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Scanner;

import entities.*;
import external.ObjectSerialiser;
import ui.*;

/**
 * Restaurant class that controls and saves the objects in the restaurant (Attributes, UI's)
 */
public class Restaurant {

    /**
     * Object to aid in serialising and deserializing of objects
     */
    private static ObjectSerialiser deserialize = new ObjectSerialiser();

    /**
     * Staff members of the Restaurant
     */
    public static ArrayList<Staff> staff;
    /**
     * Menu of the restaurant
     */
    public static ArrayList<MenuItem> menu;
    /**
     * Layout and Reservations of the restaurant
     */
    public static ArrayList<Table[]> tables;
    /**
     * Cumulative monthly revenue reports of the restaurant
     */
    public static HashMap<Integer,MonthlyRevenue> totalMonthlyRev;
    /**
     * Opening time of the restaurant
     */
    public static final GregorianCalendar OPENING_TIME = new GregorianCalendar(2000,0,1,10, 00);
    /**
     * Closing time of the restaurant
     */
    public static final GregorianCalendar CLOSING_TIME = new GregorianCalendar(3000,0,1,22,00 );

    /**
     * Method to initialise the restaurant
     */
    public static void initRestaurant(){
        initTable("Tables.ser");
        initMenu("PandaMenu.ser");
        initRevenue("MonthlyRev.ser");
        initStaff("StaffTeam.ser");
    }

    /**
     * Method to create a new menu if there is no existing one found
     */
    public static void initMenu(){
        menu = new ArrayList<MenuItem>();
    }

    /**
     * Method to read an existing menu and create a new one if no menu is found
     * @param filePath path of existing menu file
     */
    public static void initMenu(String filePath){
        Object o = deserialize.deserialiseItem(filePath);
        if(o instanceof ArrayList) {
            menu = (ArrayList<MenuItem>) o;
        }else{
            initMenu();
        }
    }

    /**
     * Method to create a new restaurant layout
     */
    public static void initTable(){
        tables = TableUI.initialiseRestaurantUI();
    }

    /**
     * Method to read an existing restaurant layout and create a new one if there is none found
     * @param filePath path of existing table layout file
     */
    public static void initTable(String filePath){
        Object o = deserialize.deserialiseItem(filePath);
        if(o instanceof ArrayList) {
            tables = (ArrayList<Table[]>) o;
        }else{
            initTable();
        }
    }

    /**
     * Method to create a new total monthly revenue hashmap
     */
    public static void initRevenue(){
        totalMonthlyRev = new HashMap<Integer, MonthlyRevenue>();
    }

    /**
     * Method to read an existing total monthly revenue hashmap and create a new one if there is none found
     * @param filePath path of existing monthly revenue file
     */
    public static void initRevenue(String filePath){
        Object o = deserialize.deserialiseItem(filePath);
        if(o instanceof HashMap) {
            totalMonthlyRev = (HashMap<Integer, MonthlyRevenue>) o;
        }else{
            initRevenue();
        }
    }

    /**
     * Method to create a new staff list
     */
    public static void initStaff(){
        staff = new ArrayList<Staff>();
    }

    /**
     * Method to read an existing list of staff members to the restaurant and create a new one if there is none found
     * @param filePath path of existing staff file
     */
    public static void initStaff(String filePath){
        Object o = deserialize.deserialiseItem(filePath);
        if(o instanceof ArrayList){
            staff = (ArrayList<Staff>) o;
        }else{
            initStaff();
        }
    }

    /**
     * Method to serialise menu, tables and totalMonthlyRev when app is closed
     */
    public static void saveRestaurant(){
        deserialize.serialiseItem(menu, "PandaMenu");
        deserialize.serialiseItem(tables,"Tables");
        deserialize.serialiseItem(totalMonthlyRev, "MonthlyRev");
        deserialize.serialiseItem(staff, "StaffTeam");
        System.out.println("Saving data...!");
    }

    /**
     * Loading up the RRPSS application with its UIs
     */
    public static void loadRestaurant(){
        System.out.println("~~~~~~~~~~~~~ Welcome to Pura Pura (RRPSS)! ~~~~~~~~~~~~~\n");
        Scanner scanner = new Scanner(System.in);
        initRestaurant();
        int choice;

        do{
            System.out.println("Please select an option (-1 to exit) :\n" +
                    "1) Manage food items\n" +
                    "2) Manage orders\n" +
                    "3) Manage tables");
            choice = scanner.nextInt();
            System.out.println();
            switch(choice){
                case 1:
                    FoodUI.printOptions();
                    break;
                case 2:
                    OrderUI.printOptions();
                    break;
                case 3:
                    TableUI.printOptions();
            }
            System.out.println();
        }while(choice != -1);

    }

}
