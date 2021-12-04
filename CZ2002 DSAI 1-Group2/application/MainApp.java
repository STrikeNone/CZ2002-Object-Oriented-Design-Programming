package application;

/**
 * Main Application/Driver class whose sole responsibility is to start up the restaurant
 */
public class MainApp {
    public static void main(String[] args){
        // Loading the restaurant
        Restaurant.loadRestaurant();

        // Save the restaurant data
        Restaurant.saveRestaurant();

        System.out.println("Terminating...");
        System.exit(0);
    }
}
