package entities;


import java.io.Serializable;

/**
 * Class to handle food items
 */

public class Food extends MenuItem implements Serializable {

    private static final long serialID = 122333;

    /**
     * Stores the 4 possible values of food type
     */
    public enum FoodType {STARTERS, MAIN, DRINKS, DESSERT};

    /**
     * Type of food based on FoodType
     */
    private FoodType Type;
    /**
     * Constructor to create a new Food item. Initialises the item with a name, description, type and its actual price
     * @param name        name of the food item
     * @param description description of the food item
     * @param price       cost of the food item
     * @param Type    Type of the Food
     */
    public Food(String name, String description, double price, FoodType Type) {
        super(name, description, price);
        this.Type = Type;
    }

    /**
     * Get the type of food
     * @return Type of food
     */
    public FoodType getType(){
        return this.Type;
    }

    /**
     * Set the new food type
     * @param Type Type of the Food
     */
    public void setType(FoodType Type){
        this.Type = Type;
    }

    @Override
    /**
     * Update Price implementation of abstract method
     * @param price Price of new object
     */
    public void updatePrice(double price){
        this.setPrice(price);
    }
}
