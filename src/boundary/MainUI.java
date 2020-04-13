package boundary;

import java.io.IOException;
import java.util.Scanner;

import controller.RoomController;

public class MainUI {
	private static Scanner in = new Scanner(System.in);
    public void run() {
	    
	    try {
	    	RoomController roomController = RoomController.getInstance();
			roomController.createRooms();
		} catch (IOException e) {
			System.out.println("cant run!");
			e.printStackTrace();
		}
        System.out.print("All rooms successfully created.\n");
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
	            case 4:
	            	CheckInUI checkInUI = CheckInUI.getInstance();
	            	checkInUI.run();
	            	break;
				case 5:
					CheckOutUI checkOutUI = CheckOutUI.getInstance();
					checkOutUI.checkOut();
					break;
				case 6:
					ServiceUI serviceUI = new ServiceUI();
					serviceUI.run();
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
	    System.out.println("4. Check in");
	    System.out.println("5. Check out");
	    System.out.println("6. Service related operations");
	    System.out.println("Your choice: ");
	    int choice = in.nextInt();
	    in.nextLine();
	    return choice;
	}
}
