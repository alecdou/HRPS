package boundary;

import java.util.Scanner;

public class MainUI {
	private static Scanner in = new Scanner(System.in);
    public void run() {
	    int choice = this.displayOptions();
	    while (choice != -1) {

	        switch (choice) {
	            case 1:
	            	GuestUI guestUI = GuestUI.getInstance();
	                guestUI.run();
	                break;
	            case 2:
	            	ReservationUI reservationUI = ReservationUI.getInstance();
	            	reservationUI.run();
	                break;
	            case 3:
	            	RoomUI roomUI = RoomUI.getInstance();
				try {
					roomUI.run();
				} catch (Exception e) {
					System.out.println("cant run!");
					e.printStackTrace();
				}
	                break;
	            default:
	            	break;
	        }choice = this.displayOptions();
	    }
	}

	private int displayOptions() {
	    System.out.println("-1. Exit");
	    System.out.println("1. Guest related operations");
	    System.out.println("2. Reservation related operations");
	    System.out.println("3. Room related operations");
	    System.out.println("Your choice: ");
	    int choice = in.nextInt();
	    return choice;
	}
}
