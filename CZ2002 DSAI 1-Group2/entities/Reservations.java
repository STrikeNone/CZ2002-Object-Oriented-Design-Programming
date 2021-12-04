package entities;


import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.time.LocalTime;

/**
 * A reservation object that stores the detail of a person's reservation at the restaurant
 */

public class Reservations implements Serializable {
    /**
     * The customer's name
     */
    private String customerName;
    /**
     * The customer's number
     */
    private String customerNum;
    /**
     * The number of pax for booking
     */
    private int numPax;
    /**
     * The reservation time
     */
    private GregorianCalendar reservationTime;
    /**
     * The reservation expiry time which is reservation time + 15min
     */
    private GregorianCalendar reservationDue;

    /**
     * Creates a new Reservation with the customer's details as well as number of pax and reservation date
     * @param customerName This Customer's Name
     * @param customerNum  This Customer's Number
     * @param datetime     This Customer's Reservation Time
     * @param numPax       This Customer's number of guest
     */
    public Reservations(String customerName, String customerNum, int numPax, GregorianCalendar datetime){
        this.customerName = customerName;
        this.customerNum = customerNum;
        this.numPax = numPax;
        this.reservationTime = (GregorianCalendar) datetime.clone();
        this.reservationDue = (GregorianCalendar) datetime.clone();
        this.reservationDue.add(Calendar.MINUTE, 15);
    }

    /**
     * Accessor method to return the customer's name
     * @return customer's name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Accessor method to return the customer's Number
     * @return customer's number
     */
    public String getCustomerNum(){
        return customerNum;
    }

    /**
     * Accessor method to return number of diners
     * @return number of diners
     */
    public int getNumPax(){ return numPax; }

    /**
     * Accessor the customer's reservation time
     * @return GregorianCalendar Object which contains the time information
     */
    public GregorianCalendar getReservationTime(){return reservationTime;}

    /**
     * Checks whether the reservation is expired.
     * @return true if it's expired and false if otherwise
     */
    public boolean checkReservationExpiry(){
        GregorianCalendar now = new GregorianCalendar();
        now.getTime();
        if(now.after(reservationDue)){
            return true;
        }
        return false;
    }

    /**
     * Check if the reservation made by a customer is within the opening and closing -1hr time of the restaurant
     * @param openingTime Restaurant's opening time
     * @param closingTime Restaurant's closing time
     * @return true if reservation time lies between the given range and false if otherwise
     */
    public boolean checkReservationValidity(GregorianCalendar openingTime, GregorianCalendar closingTime){
        GregorianCalendar temp = (GregorianCalendar) closingTime.clone();
        temp.add(Calendar.HOUR, -1);
        temp.add(Calendar.MINUTE, 1);

        LocalTime reservation = LocalTime.of(reservationTime.get(Calendar.HOUR_OF_DAY), reservationTime.get(Calendar.MINUTE));
        LocalTime opening = LocalTime.of(openingTime.get(Calendar.HOUR_OF_DAY), openingTime.get(Calendar.MINUTE));
        LocalTime closing = LocalTime.of(temp.get(Calendar.HOUR_OF_DAY), temp.get(Calendar.MINUTE));

        if(reservation.isBefore(closing) && opening.isBefore(reservation)){
            return true;
        }
        return false;
    }


}
