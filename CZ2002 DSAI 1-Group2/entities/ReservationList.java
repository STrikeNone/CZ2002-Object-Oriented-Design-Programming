package entities;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Abstract class for tables. Handles all reservations related problems
 */

abstract class ReservationList implements Serializable {
    /**
     * An array list to store the reservations
     */
    private ArrayList<Reservations> reservations = new ArrayList<Reservations>();

    /**
     * Get the total number of reservations
     * @return number of reservation
     */
    public int getNumReservations(){return reservations.size(); }

    /**
     * To sort the reservations arraylist in ascending order
     */
    private void sortReservation(){
        Collections.sort(reservations, Comparator.comparing(Reservations::getReservationTime));
    }

    /**
     * Adds a reservation to the arraylist
     * @param newReservation new Reservation
     */
    public void setReservations(Reservations newReservation){
        reservations.add(reservations.size(), newReservation);
        sortReservation();
    }

    /**
     * Searches for a possible slot to slot in a given reservation
     * @param reservationTime timing of reservation
     * @return true if there is a slot, false otherwise
     */
    public boolean searchPossibleSlot(GregorianCalendar reservationTime){
        GregorianCalendar previousReservation, nextReservation;
        int i;

        if (reservations.size() == 1){
            previousReservation = (GregorianCalendar) reservations.get(0).getReservationTime().clone();
            previousReservation.add(Calendar.HOUR, 1);

            nextReservation = (GregorianCalendar) reservations.get(0).getReservationTime().clone();
            nextReservation.add(Calendar.HOUR, -1);

            // if reservationtime is one hour after or one hour before the only reservation
            if(reservationTime.after(previousReservation) || reservationTime.before(nextReservation)){
                return true;
            }
            return false;
        }

        else{
            for(i = 0; i < reservations.size()-1; i++){
                previousReservation = (GregorianCalendar) reservations.get(i).getReservationTime().clone();
                previousReservation.add(Calendar.HOUR, 1);

                nextReservation = (GregorianCalendar) reservations.get(i+1).getReservationTime().clone();
                nextReservation.add(Calendar.HOUR, -1);
                System.out.println(reservationTime.before(nextReservation.getTime()));
                if((reservationTime.after(previousReservation.getTime())) && (reservationTime.before(nextReservation.getTime()))){
                    return true;
                }
            }

            nextReservation = (GregorianCalendar) reservations.get(i).getReservationTime().clone();
            nextReservation.add(Calendar.HOUR, 1);

            if(reservationTime.after(nextReservation)){
                return true;
            }
            return false;
        }
    }

    /**
     * Returns the next reservation for the table
     * @return next earliest Reservation
     */
    public Reservations getNextReservation(){
        if(reservations.size() == 0){
            return null;
        }
        return reservations.get(0);
    }

    /**
     * Removes the first reservation of the table
     */
    public void deleteFirstReservation(){
        reservations.remove(0);
    }

    /**
     * Deletes a reservation by matching the customer's name and number once a customer who reserved a table arrives
     * at the restaurant. However, in the event of a cancellation beforehand, forceDeletion will be set to true to
     * delete the reservation based on the same conditions
     * @param customerName The reserved customer's name
     * @param customerNum The reserved customer's number
     * @param forceDeletion Whether is a deletion before hand or delete a reservation because a customer has arrived
     * @return true if the deletion was successful and false otherwise
     */
    public boolean deleteReservation(String customerName, String customerNum, boolean forceDeletion){
        int i;
        for(i = 0; i < reservations.size(); i++){
            if(reservations.get(i).getCustomerName().equals(customerName) && reservations.get(i).getCustomerNum().equals(customerNum)){
                GregorianCalendar reservationDate = new GregorianCalendar(reservations.get(i).getReservationTime().get(Calendar.YEAR), reservations.get(i).getReservationTime().get(Calendar.MONTH), reservations.get(i).getReservationTime().get(Calendar.DAY_OF_MONTH));

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, MMMM d, yyyy");
                GregorianCalendar today = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                if(forceDeletion){
                    System.out.println("Removed reservation for: " + customerName);
                    reservations.remove(i);
                    return true;
                }
                if(today.before(reservationDate)){
                    System.out.println("Reservation is for " + dateFormatter.format(reservationDate.getTime()) + "! Today is the " + dateFormatter.format(today.getTime()));
                    return false;
                }
                else{
                    reservations.remove(i);
                    return true;
                }
            }
        }
        /*if(forceDeletion){
            System.out.println("Unable to find matching details in the reservation list!");
            return false;
        } */
        return false;
    }

    /**
     * Prints the Reservations details which includes the table number, customer's details and reservation time
     */
    public void printDetailedReservations(){
        int i;

        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, MMMM d, yyyy, HH:mm");

        if(reservations.size() == 0){
            System.out.println("No reservations found");
        }
        for(i = 0; i < reservations.size(); i++){
            System.out.println((i+1) + ") Name: " + reservations.get(i).getCustomerName() +
                    "\n   Contact: " + reservations.get(i).getCustomerNum() +
                    "\n   Number of Pax: " + reservations.get(i).getNumPax() +
                    "\n   Timing: " + dateFormatter.format(reservations.get(i).getReservationTime().getTime()));
        }
    }

    /**
     * Removes all expired reservation for the table
     */
    public void removeExpiredReservations(){
        Reservations check = getNextReservation();
        if(check != null && check.checkReservationExpiry()){
            deleteFirstReservation();
        }
    }

}

