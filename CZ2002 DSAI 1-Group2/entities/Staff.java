package entities;

import java.io.Serializable;

/**
 * Class to store staff details
 */

public class Staff implements Serializable{
    /**
     * Name of the classes.Staff member
     */
    private String name;

    /**
     * Gender of the classes.Staff member, m is for male and f is for female
     */
    private char gender;

    /**
     * The unique ID assigned to the particular staff member
     */
    private int employeeID;

    /**
     * Job title held by the classes.Staff
     */
    private String jobTitle;

    /**
     * Constructor that creates a new class.Staff member initialised with name, employeeID, gender and jobTitle
     * @param name                name of the staff
     * @param employeeID          employeeID of the staff
     * @param gender              gender of the staff
     * @param jobTitle            jobTitle of the staff
     */
    public Staff(String name, int employeeID, char gender, String jobTitle){
        this.name = name;
        this.employeeID = employeeID;
        this.gender = gender;
        this.jobTitle = jobTitle;
    }

    /**
     * Convert the details of the staff member into a string
     * @return returns employeeID, name (gender) and jobTitle in a string
     */
    public String toString(){
        return "EmployeeID: " + this.employeeID + "\n   Name: " + this.name + " (" + this.gender + ")\n   Title: " + this.jobTitle;
    }

    /**
     * Set a new staff name
     * @param name New staff name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Set a new staff gender
     * @param gender New staff gender
     */
    public void setGender(char gender){
        this.gender = gender;
    }

    /**
     * Set a new staff employeeID
     * @param employeeID New staff employeeID
     */
    public void setEmployeeID(int employeeID){
        this.employeeID = employeeID;
    }

    /**
     * Set a new staff jobTitle
     * @param jobTitle New staff jobTitle
     */
    public void setJobTitle(String jobTitle){
        this.jobTitle = jobTitle;
    }

    /**
     * Get the name of the staff
     * @return The staff's name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Get the jobTitle of the staff
     * @return The staff's jobtitle
     */
    public String getJobTitle(){
        return this.jobTitle;
    }

    /**
     * Get the ID of the staff
     * @return The staff's ID
     */
    public int getEmployeeID(){
        return this.employeeID;
    }

    /**
     * Get the gender of the staff
     * @return The staff's gender
     */
    public char getGender(){
        return this.gender;
    }
}
