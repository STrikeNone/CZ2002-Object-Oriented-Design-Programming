package Lab3;

import java.util.Scanner;

public class PlaneApp {
    public static void main(String[] args) {
        int option;
        Plane boeing = new Plane();
        Scanner sc = new Scanner(System.in);
        System.out.println("(1) Show the number of empty seats");
        System.out.println("(2) Show the list of empty seats");
        System.out.println("(3) Show the list of seat assignment by seat ID");
        System.out.println("(4) Show the list of seat assignment by customer ID");
        System.out.println("(5) Assign a customer to a seat");
        System.out.println("(6) Remove a seat assignment");
        System.out.println("(7) Exit");

        do {
            System.out.println("Enter the number of your choice:");
            option = sc.nextInt();
            switch(option){
                case 1:
                    boeing.showNumEmptySeats();
                    break;
                case 2:
                    boeing.showEmptySeats();
                    break;
                case 3:
                    boeing.showAssignedSeats(true);
                    break;
                case 4:
                    boeing.showAssignedSeats(false);
                    break;
                case 5:
                    System.out.println("Assigning Seat ..");
                    System.out.println("Please enter SeatID:");
                    int seat_id = sc.nextInt();
                    System.out.println("Please enter CustomerID");
                    int customer_id = sc.nextInt();
                    boeing.assignSeat(seat_id, customer_id);
                    break;
                case 6:
                    System.out.println("Enter SeatID to unassign customer from:");
                    int seat_Id = sc.nextInt();
                    boeing.unAssignSeat(seat_Id);
                    break;
                default:
                    break;
            }
        } while (option < 7);
    }
}
