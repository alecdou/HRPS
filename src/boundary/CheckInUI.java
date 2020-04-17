package boundary;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import controller.CheckInputController;
import controller.GuestController;
import controller.ReservationController;
import controller.RoomController;
import entity.Guest;
import entity.Reservation;
import entity.Reservation.ReservationStatus;
import entity.Room;
import jdk.jshell.Snippet.Status;


public class CheckInUI {
    // static variable single_instance of type CheckinUI
    private static CheckInUI single_instance = null;
    private GuestUI guestUI = GuestUI.getInstance();
    private GuestController gc = GuestController.getInstance();
    private ReservationController resc = ReservationController.getInstance();
    private RoomUI rui = RoomUI.getInstance();
    private CheckInputController inputCheck = CheckInputController.getInstance();
    private RoomController rc = RoomController.getInstance();
    private Scanner in = new Scanner(System.in);

    // private constructor restricted to this class itself
    private CheckInUI() {
    }

    // static method to create instance of CheckinUI class
    public static CheckInUI getInstance() {
        if (single_instance == null)
            single_instance = new CheckInUI();
        return single_instance;
    }

    public void run() {
        int choice = this.displayOptions();

        switch (choice) {
            case 1:
                reservationCheckInUI();
                break;
            case 2:
                walkInCheckInUI();
                break;
        }

    }

    private void walkInCheckInUI() {
        
        List<Room> rooms = rc.checkAvailableRooms(rui.findRoomUI());
        Guest newGuest;
        Room checkedIn = null;
        // call the room controller to find a list of vacant rooms (based on the above criteria)
        if(rooms.isEmpty()){
    		System.out.println("There is no available room with the specified requirements. ");
    	}
    	else{
    		System.out.println("These are the available rooms with the specified requirements: ");
    		for(Room room: rooms) {
    			System.out.println(room.getRoomNumber());
    		}
    		System.out.println("Select a room: ");
    		String roomNumber = in.nextLine();
        
	        // call the guest ui to create a new guest
	        System.out.print("Please enter Guest Contact: ");
	    	String guestContact = in.nextLine().trim().replace(" ", "");
            boolean valid = inputCheck.checkContactInput(guestContact);
            while(valid!=true) {
            	System.out.print("Invalid Contact. Please re-enter Contact: ");
                guestContact = in.nextLine().trim().replace(" ", "");
                valid = inputCheck.checkContactInput(guestContact);
            }
        	guestContact = in.nextLine().trim();
	        List<Guest> guests = guestUI.lookForExistingGuests(guestContact);
	        if(guests.isEmpty()) {
	        	newGuest = guestUI.newGuestUI(guestContact);
	        	System.out.print("Please input Check In time (yyyy-MM-dd HH:mm): ");
	            String time = in.nextLine().trim().replace(" ", "T");
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	        	LocalDateTime checkInTime = LocalDateTime.parse(time, formatter);
	        	checkedIn = rc.checkIn(roomNumber, newGuest);
	        	checkedIn.setCheckInTime(checkInTime);
                System.out.println("Checked in: \n");
                System.out.println("Guest Information: --------------------\n");
                System.out.println(newGuest.toString());
                System.out.println("Room Information: --------------------\n");
                System.out.println(rc.findRoom(roomNumber).get(0).toString());
	        }
	        else {//if there are existing guests
				System.out.println("Similar records are present in the system: ");
	        	for (Guest guest : guests) {
	            	System.out.println(guest.toString());
	            	System.out.println("Is this this who you are looking for?");
	            	System.out.println("1. Yes");
	            	System.out.println("2. No");
	            	System.out.println("Your choice: ");
	                int choice2 = in.nextInt();
	                in.nextLine();
	                if(choice2 == 1) {//it has to be yes because with the same contact number, no new guest can be created 
	                	checkedIn = rc.checkIn(roomNumber, guest);
	                	System.out.print("Please input Check In time (yyyy-MM-dd HH:mm): ");
	                    String time = in.nextLine().trim().replace(" ", "T");
	                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	                	LocalDateTime checkInTime = LocalDateTime.parse(time, formatter);
	                    checkedIn.setCheckInTime(checkInTime);
	                	System.out.println("Checked in: \n");
	                    System.out.println("Guest Information: --------------------\n");
	                    System.out.println(guest.toString());
	                    System.out.println("Room Information: --------------------\n");
	                    System.out.println(rc.findRoom(roomNumber).get(0).toString());
	                	break;
	                }
	        	}
	        }
    	}
        
    }

    private void reservationCheckInUI() {
        // find the reservation by guest contact and display its details (reservation ui)
    	System.out.print("Please enter Guest Contact: ");
    	String guestContact = in.nextLine().trim().replace(" ", "");
    	boolean valid = resc.checkContactInput(guestContact);
        while(valid!=true) {
        	System.out.print("Invalid Contact. Please re-enter Contact: ");
        	guestContact = in.nextLine().trim().replace(" ", "");
            valid = resc.checkContactInput(guestContact);
        }
        List<Reservation> reservations = resc.searchReservation(guestContact);
        if(reservations.isEmpty()) {
        	System.out.println("No records found!");
        }
        else {
        	Guest guest;
        	Reservation res = reservations.get(0);
        	List<Guest> guests = gc.searchGuestContact(res.getGuestContact());
        	guest = guests.get(0);
        	Room checkedIn = rc.checkIn(res.getRoomNum(), guest);
        	ReservationStatus status = ReservationStatus.CHECKEDIN;
            res.setStatus(status);
            System.out.println("Checked in: \n");
            System.out.println("Guest Information: --------------------\n");
            System.out.println(guest.toString());
            System.out.println("Room Information: --------------------\n");
            System.out.println(res.toString());
        }
    }

//        // confirm checkin
//        System.out.println("Confirm check-in? (Y/N)");
//        String dummy = in.nextLine();
//        System.out.println(dummy);
//
//        try {
//            // TODO: Complete the implementation.
//            System.out.println("Updating...(reservation & room)");
//            // update the reservation through reservation controller (status = CHECKEDIN)
//
//            // update the room through room controller (status = OCCUPIED)
//
//        } catch (Exception e) {
//            System.out.println("Check-in Failed! Please try again.");
//        }

    private int displayOptions() {
        int choice;
        while (true) {
            try {
                System.out.println("1. Check-in (Reservation)");
                System.out.println("2. Check-in (Walk-in)");
                System.out.println("0. Exit");
                System.out.println("Your choice: ");
                choice = in.nextInt();
                in.nextLine(); // clear dummy characters
                if (0 <= choice && choice <= 2) {
                    return choice;
                } else {
                    System.out.println("ERROR: The input should be 0, 1, or 2");
                    System.out.println("Please select again:");
                }
            } catch (Exception e) {
                System.out.println("ERROR: The input should be of type (int)");
                System.out.println("Please select again:");
                in.nextLine(); // clear dummy characters
            }
        }
    }


}
