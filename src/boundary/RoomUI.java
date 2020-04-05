package boundary;

import java.util.Scanner;
import java.util.List;

import controller.RoomController;
import entity.Reservation;
import entity.Room;

public class RoomUI {
	private static RoomUI single_instance = null;
    private RoomController roomController = RoomController.getInstance();
    private static Scanner in = new Scanner(System.in);
    
    private RoomUI() {}

    public static RoomUI getInstance()
    {
        if (single_instance == null)
            single_instance = new RoomUI();
        return single_instance;
    }
    
    public void run() {
        int choice = this.displayOptions();
        while (choice != -1) {
            switch (choice) {
                case 1:
                	roomController.createRooms();
                	System.out.print("All rooms successfully created. ");
                    break;
                case 2:
                	updateRoomUI();
                    break;
                case 3:
                	System.out.println("1. Check room status via room number: ");
                    System.out.println("2. Check room status via guest name: ");
                    System.out.println("3. Check room status via room type: ");
                    System.out.println("Your choice: ");
                    int choice2 = in.nextInt();
                    Room room;
                    String check;
                    switch(choice2) {
                    	case 1: 
                    		System.out.print("Please enter room number: ");
                        	String roomNo2 = in.next();
                        	check = "r" + roomNo2;
                    	case 2:
                    		System.out.print("Please enter guest name: ");
                        	String guest = in.next();
                        	check = "g" + guest;
                    	case 3:
                    		System.out.print("Please enter room type: ");
                        	String type = in.next();
                        	check = "t" + type;
                    }
                    room = roomController.checkRoom(check);
            		room.toString();
                    break;
                case 4:
                	System.out.print("Please enter room number: ");
                	String roomNo3 = in.next();
                	List<Room> rooms = roomController.findRoom(roomNo3);
                	if (rooms.isEmpty()) {
                    	System.out.println("No such room");
                    } 
                    else {
                    	System.out.println("Similar names are present in the system record: ");
                    	for (Reservation reservation : reservations) {
                        	System.out.println(reservation.toString());
                        	System.out.println("Is this the reservation you are looking for?");
                        	System.out.println("1. Yes");
                        	System.out.println("2. No");
                        	System.out.println("Your choice: ");
                            int choice = in.nextInt();
                            if(choice == 1) {
                            	System.out.println("1. Remove this reservation");
                                System.out.println("2. Cancel operation");
                                System.out.println("Your choice: ");
                                choice = in.nextInt();
                                switch (choice) {
            	                    case 1:
            					        roomController.removeReservation(reservation);
            					        System.out.println("Reservation removed: ");
            					        System.out.println(reservation.toString());
            					        return;
            	                    case 2:
            	                        return;
                                }
                            }
                            else if(choice == 2) {}
                        }
                    }
                    break;
                default:
                	break;
                /*case 0:
                	MainUI;
                	break;*/
            }choice = this.displayOptions();
        }
    }

    private int displayOptions() {
        System.out.println("-1. Exit");
        System.out.println("0. Go back to MainUI");
        System.out.println("1. Create all rooms");
        System.out.println("2. Update room details");
        System.out.println("3. Check room status");
        System.out.println("4. Get room details");
        System.out.println("Your choice: ");
        int choice = in.nextInt();
        return choice;
    }
    
    private void updateRoomUI() {
    	System.out.print("Please enter room number: ");
    	String roomNo = in.next();
    	System.out.print("Please enter room type: ");
    	String roomType = in.next();
    	System.out.print("Please enter bed type: ");
    	String roomBedType = in.next();
    	System.out.print("Please enter where the room is facing: ");
    	String roomFacing = in.next();
    	System.out.print("Please enter room status: ");
    	String roomStatus = in.next();
    	System.out.print("Please enter if room has wifi: ");
    	String hasWifi = in.next();
    	System.out.print("Please enter if room is smoke-free: ");
    	String isSmokingFree = in.next();
    	System.out.print("Please enter room rate: ");
    	double rate = in.nextDouble();
    	System.out.print("Please enter check-in time: ");
    	String checkInTime = in.next();
    	roomController.updateRoom(roomNo, roomType, roomBedType, roomFacing, roomStatus, hasWifi, isSmokingFree, rate, checkInTime);
    }
    
}
