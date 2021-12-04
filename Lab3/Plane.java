package Lab3;


public class Plane{
    private PlaneSeat[] seat;
    private int numEmptySeat;

    public Plane(){
        numEmptySeat = 12;
        seat = new PlaneSeat[12];
        for(int i=0; i<12; i++){
            seat[i] = new PlaneSeat(i+1);
        }
    }

    private PlaneSeat[] sortSeats(){
        PlaneSeat[] temp = seat.clone();
        for(int i=0; i<temp.length; i++){
            for(int j=i; j>0; j--){
                if(temp[j].getCustomerID() < temp[j-1].getCustomerID() && temp[j].isOccupied() || !temp[j-1].isOccupied()){
                    PlaneSeat holding = temp[j];
                    temp[j] = temp[j-1];
                    temp[j-1] = holding;
                }else{
                    break;
                }
            }
        }
        return temp;
    }

    public void showNumEmptySeats(){
        int count = 0;
        for(int i = 0; i<seat.length; i++){
            if(!seat[i].isOccupied()){
                count+=1;
            }
        }

        System.out.println("There are " + count + " empty seats");
        return;
    }

    public void showEmptySeats(){
        System.out.println("The following seats are empty:");
        for(int i=0; i<seat.length; i++){
            if(!seat[i].isOccupied()){
                System.out.println("SeatID " + seat[i].getSeatID());
            }
        }
    }

    public void showAssignedSeats(boolean bySeatId){
        System.out.println("The seat assignments are as follow:");
        if(bySeatId){
            for(int i=0; i<seat.length; i++){
                if(seat[i].isOccupied()) {
                    System.out.printf("SeatID %d assigned to CustomerID %d\n", seat[i].getSeatID(), seat[i].getCustomerID());
                }
            }
        }
        else{
            PlaneSeat[] temp = sortSeats();
            for(int i=0; i<seat.length; i++){
                if(temp[i].isOccupied()) {
                    System.out.printf("SeatID %d assigned to CustomerID %d\n", temp[i].getSeatID(), temp[i].getCustomerID());
                }
            }
        }
    }

    public void assignSeat(int seatId, int cust_id){
        PlaneSeat temp = seat[seatId-1];
        if(temp.isOccupied()){
            System.out.println("Seat already assigned to a customer!");
            return;
        }
        temp.assign(cust_id);
        System.out.println("Seat Assigned!");
    }

    public void unAssignSeat(int seatId){
        seat[seatId-1].unAssign();
        System.out.println("Seat Unassigned!");
        return;
    }

}