package entities;


import java.io.Serializable;

/**
 * Abstract class for both Food and Promotion. Handles pricing and descriptions
 */

public abstract class MenuItem implements Serializable {

    private static final long serialID = 122333;

    /**
     * Name of the MenuItem object
     */
    private String name;

    /**
     * Description of what the MenuItem is
     */
    private String description;

    /**
     * Price of the MenuItem
     */
    private double price;

    /**
     * Constructor to create a new MenuItem. Initialises the item with a name, description and its actual price
     * @param name          name of the food item
     * @param description   description of the food item
     * @param price         cost of the food item
     */
    public MenuItem(String name, String description, double price){
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * Get the name of the MenuItem
     * @return menuitem's name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Get the description of the MenuItem
     * @return menuitem's description
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Get the price of the MenuItem
     * @return menuitem's price
     */
    public double getPrice(){
        return Math.round(this.price * 100.0)/100.0;
    }

    /**
     * Set a name for the MenuItem
     * @param name New MenuItem name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Set a description for the MenuItem
     * @param description New MenuItem description
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Set a new price for the MenuItem
     * @param price New MenuItem price
     */
    public void setPrice(double price){
        this.price = price;
    }

    /**
     * Method to set a price for MenuItem
     * @param price price to be updated
     */
    public abstract void updatePrice(double price);
}

