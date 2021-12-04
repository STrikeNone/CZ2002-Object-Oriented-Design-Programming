package entities;

import java.io.Serializable;

/**
 * Class to store order details
 */

public class Order implements Serializable {
    /**
     * Contains the 2 possible values of OrderType
     */
    public enum OrderType {ALACARTE, PROMOTION};

    /**
     * Type of order based on OrderType
     */
    private OrderType Type;

    /**
     * Order of food object
     */
    private Food food;

    /**
     * Order of promotion object
     */
    private Promotion promotion;

    /**
     * Quantity of order
     */
    private int quantity;

    /**
     * Price of order
     */
    private double price;

    /**
     * Total price of order
     */
    private double totalPrice;


    /**
     * Constructor to create a new Order. Initializes the order with order item, quantity, price, total price and type
     * @param food  menu item
     * @param quantity quantity of item
     */
    public Order(Food food, int quantity) {
        this.food = food;
        this.quantity = quantity;
        this.price = food.getPrice();
        this.totalPrice  = price*quantity;
        setType(OrderType.ALACARTE);
    }

    /**
     * Constructor to create a new Order. Initializes the order with order item, quantity, price, total price and type
     * @param promotion  menu item
     * @param quantity quantity of item
     */
    public Order(Promotion promotion, int quantity) {
        this.promotion = promotion;
        this.quantity = quantity;
        this.price = promotion.getPrice();
        this.totalPrice  = price*quantity;
        setType(OrderType.PROMOTION);
    }

    /**
     * Set Type of Item
     * @param Type Enum of order
     */
    public void setType(OrderType Type) {
        this.Type = Type;
    }

    /**
     * Get the name of Order
     * @return name of Order
     */
    public String getFood() {
        if (this.Type == OrderType.ALACARTE) {
            return this.food.getName();
        }
        else if (this.Type == OrderType.PROMOTION) {
            return this.promotion.getName();
        }
        else {
            return "Order not created!";
        }
    }

    /**
     * Get the quantity of Order
     * @return quantity of Order
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Get the unit price of Order
     * @return unit price of Order
     */
    public double getPrice() {
        return price;
    }

    /**
     * Get the total price of Order
     * @return total price of Order
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Update quantity and total price of Order
     * @param quantity quantiy of item in order
     */
    public void updateItem(int quantity) {
        this.quantity = quantity;
        this.totalPrice = quantity * this.price;
    }
}