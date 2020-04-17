package boundary;

import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.CheckInputController;
import controller.RoomController;
import entity.Reservation;
import entity.Room;
import entity.Room.RoomStatus;

public class RoomUI {
	private static RoomUI single_instance = null;
    private RoomController roomController = RoomController.getInstance();
    private CheckInputController inputCheck = CheckInputController.getInstance();
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
        List<Room> rooms;
        while (choice != 0) {
            switch (choice) {
                case 1:
				try {
					roomController.createRooms();
				} catch (IOException e) {
					System.out.println("cant run!");
					e.printStackTrace();
				}
                	System.out.print("All rooms successfully created.\n");
                    break;
                case 2:
                	while(true) {
	                	System.out.println("Please enter room number: ");
	                	String roomNo = in.nextLine();
	                	rooms = roomController.findRoom(roomNo);
	                	if (rooms.isEmpty()) {
	                    	System.out.println("No such room, please enter a correct room number:");
	                    } 
	                    else {
	                    	System.out.println(rooms.get(0).toString());
	                    	updateRoomUI(rooms.get(0));
	                    	break;
	                    }
                	}
                	break;
                case 3:
                	System.out.println("1. Check room status via room number: ");
                    System.out.println("2. Check room status via guest name: ");
                    System.out.println("3. Check room status via room type: ");
                    System.out.println("Your choice: ");
                    int choice2;
                    List<Room> rooms2;
                    String check = null;
            		while (true) {
                        try {
                        	choice2 = in.nextInt();
            		        in.nextLine();
            		        if (0 <= choice2 && choice2 <= 4) {
                                break;
                            } else {
                                System.out.println("ERROR: The input should be 0, 1, 2 or 3");
                                System.out.println("Please select again:");
                            }
                        } catch (Exception e) {
                            System.out.println("ERROR: The input should be of type (int)");
                            System.out.println("Please select again:");
                            in.nextLine(); // clear dummy characters
                        }
            		}
                    switch(choice2) {
                    	case 1: 
                    		System.out.print("Please enter room number: ");
                        	String roomNo2 = in.nextLine();
                        	check = "r" + roomNo2;
                        	break;
                    	case 2:
                    		System.out.print("Please enter guest name: ");
                        	String guest = in.nextLine();
                        	check = "g" + guest;
                        	break;
                    	case 3:
                    		System.out.print("Please enter room type: ");
                        	String type = in.nextLine();
                	    	while(true){
                	    		if(inputCheck.checkRoomTypeInput(type)) {
                	    			break;
                	    		}
                	    		else {
                	    			System.out.print("Please enter either 'SINGLE', 'DOUBLE', 'DELUXE', 'VIP' or 'SUITE': ");
                	    			type = in.nextLine();
                	    		}
                	    	}
                        	check = "t" + type;
                        	break;
                    }
                    rooms2 = roomController.checkRoom(check);
                    if(!rooms2.isEmpty()) {
                    	for(Room room : rooms2){
                    		System.out.println(room.getRoomNumber().toString());
                    		System.out.println(room.getRoomStatus().toString());
                    	}
                    }
                    else {
                    	System.out.println("There is no room with the specified requirements.\n");
                    }
            		break;
                case 4:
                	int choice4 = 1;
                	while(choice4 == 1){
                		System.out.print("Please enter room number: ");
	                	String roomNo3 = in.nextLine();
	                	rooms = roomController.findRoom(roomNo3);
	                	if (rooms.isEmpty()) {
	                    	System.out.println("No such room, please enter a correct room number:");
	                    } 
	                    else {
	                    	for (Room room2 : rooms) {
	                        	System.out.println(room2.toString());
	                        	System.out.println("1. Get details of another room");
	                        	System.out.println("2. Cancel operation");
	                        	System.out.println("Your choice: ");
	                        	String choicee = in.nextLine().trim().replace(" ", "");
	        	                boolean valid2 = inputCheck.checkIntInput(choicee);
	        	                while(valid2!=true) {
	        	                	System.out.print("Invalid choice. Please re-enter choice: ");
	        	                    choicee = in.nextLine().trim().replace(" ", "");
	        	                    valid2 = inputCheck.checkIntInput(choicee);
	        	                }
	                            choice4 = Integer.parseInt(choicee);
	                            while(choice4 != 1 && choice4 != 2) {
	                            	System.out.println("1. Get details of another room");
		                        	System.out.println("2. Cancel operation");
		                        	System.out.println("Your choice: ");
		                        	String choicee2 = in.nextLine().trim().replace(" ", "");
		        	                boolean valid3 = inputCheck.checkIntInput(choicee2);
		        	                while(valid3!=true) {
		        	                	System.out.print("Invalid choice. Please re-enter choice: ");
		        	                    choicee2 = in.nextLine().trim().replace(" ", "");
		        	                    valid3 = inputCheck.checkIntInput(choicee2);
		        	                }
		                            choice4 = Integer.parseInt(choicee2);
		                            }
	                    	}
	                    }
                	}
                    break;
                case 5:
                	List<Room> preferredRooms = findRoomUI();
                	if(preferredRooms.isEmpty()){
                		System.out.println("There is no room with the specified requirements. ");
                	}
                	else{
                		System.out.println("These are the rooms with the specified requirements: ");
                		for(Room room: preferredRooms) {
                			System.out.println(room.toString());
                		}
                	}
                	break;
                default:
                	break;
            }choice = this.displayOptions();
        }
    }

    public List<Room> findRoomUI() {
    	System.out.println("Please choose the preferred room type (Single, Double, Deluxe, VIP, Suite):");
        String type = in.nextLine().toUpperCase();
        while(true){
    		if(inputCheck.checkRoomTypeInput(type)) {
    			break;
    		}
    		else {
    			System.out.print("Please enter either 'SINGLE', 'DOUBLE', 'DELUXE', 'VIP' or 'SUITE': ");
    			type = in.nextLine().toUpperCase();
    		}
    	}
        //System.out.println(type);
        List<Room> rooms = roomController.findRoomByType(type);
        System.out.println("Please choose the preferred bed type (Single, StandardDouble, King)");
    	String bedType = in.nextLine().toUpperCase();
    	while(true){
    		if(inputCheck.checkRoomBedInput(bedType)) {
    			break;
    		}
    		else {
    			System.out.print("Please enter either 'SINGLE', 'STANDARDDOUBLE' or 'KING': ");
    			bedType = in.nextLine().toUpperCase();
    		}
    	}
    	List<Room> rooms2 = roomController.findRoomByBedType(rooms, bedType);
    	while(rooms2.isEmpty()) {
    		System.out.println(bedType + " bed type is not available in " + type + " room type.");
    		System.out.println("Please re-select the preferred bed type (Single, StandardDouble, King) or type 'cancel'");
        	bedType = in.nextLine().toUpperCase();
        	if(!bedType.equals("CANCEL")) {
        		rooms2 = roomController.findRoomByBedType(rooms, bedType);
        	}
        	else {return rooms2;}
    	}
    	System.out.println("Please choose the preferred room facing (North, South, East, West)");
    	String facing = in.nextLine().toUpperCase();
    	while(true){
    		if(inputCheck.checkRoomFaceInput(facing)) {
    			break;
    		}
    		else {
    			System.out.print("Please enter either 'NORTH', 'SOUTH', 'EAST' or 'WEST': ");
    			facing = in.nextLine().toUpperCase();
    		}
    	}
    	List<Room> rooms3 = roomController.findRoomByFacing(rooms2, facing);
    	while(rooms3.isEmpty()) {
    		System.out.println(facing + " room facing is not available in " + type + " room type with " + bedType + " bed type.");
    		System.out.println("Please re-select the preferred room facing (North, South, East, West) or type 'cancel'");
        	facing = in.nextLine().toUpperCase();
        	while(true){
        		if(facing.toUpperCase().equals("CANCEL") || inputCheck.checkRoomFaceInput(facing)) {
        			break;
        		}
        		else {
        			System.out.print("Please enter either 'NORTH', 'SOUTH', 'EAST', 'WEST' or 'CANCEL': ");
        			facing = in.nextLine().toUpperCase();
        		}
        	}
        	if(!facing.equals("CANCEL")) {
        		rooms3 = roomController.findRoomByFacing(rooms2, facing);
        	}
        	else {break;}
    	}
    	return rooms3;
	}

	private int displayOptions() {
		int choice;
		while (true) {
            try {
		        System.out.println("0. Go back to MainUI");
		        System.out.println("1. Create all rooms");
		        System.out.println("2. Update room details");
		        System.out.println("3. Check room status");
		        System.out.println("4. Get room details");
		        System.out.println("5. Find a room based on preferences");
		        System.out.println("Your choice: ");
		        choice = in.nextInt();
		        in.nextLine();
		        if (0 <= choice && choice <= 5) {
		            return choice;
		        } else {
		            System.out.println("ERROR: The input should be 0, 1, 2, 3, 4 or 5");
		            System.out.println("Please select again:");
		        }
		    } catch (Exception e) {
		        System.out.println("ERROR: The input should be of type (int)");
		        System.out.println("Please select again:");
		        in.nextLine(); // clear dummy characters
		    }
		}
    }
    
    private void updateRoomUI(Room room) {
    	int choice;
		while (true) {
            try {
		    	System.out.println("Please enter the information that you want to update: ");
				System.out.println("1. Room Status: ");
		    	System.out.println("2. Room Rate ");
		        System.out.println("3. Check In time ");
		        System.out.println("0. Cancel ");
	        choice = in.nextInt();
	        in.nextLine();
	        if (0 <= choice && choice <= 5) {
	            break;
	        } else {
	            System.out.println("ERROR: The input should be 0, 1, 2 or 3");
	            System.out.println("Please select again:");
	        }
		    } catch (Exception e) {
		        System.out.println("ERROR: The input should be of type (int)");
		        System.out.println("Please select again:");
		        in.nextLine(); // clear dummy characters
		    }
		}
        Room updatedRoom;
        System.out.println("Please enter the updated information: ");
    		switch (choice) {
	    		case 1:
			    	System.out.print("Room Status: {VACANT, OCCUPIED, RESERVED, MAINTENANCE}");
			        String updatedRoomStatus = in.nextLine();
			        updatedRoom = roomController.updateRoomStatus(room, updatedRoomStatus);
			        System.out.println("Room status updated: ");
			        System.out.println(updatedRoom.toString());
			        break;
            	case 2:
            		System.out.print("Room Rate: ");
			        String roomRate = in.nextLine();
	                boolean valid = inputCheck.checkIntInput(roomRate);
	                while(valid!=true) {
	                	System.out.print("Invalid input. Please enter a number: ");
	                	roomRate = in.nextLine();
	                    valid = inputCheck.checkIntInput(roomRate);
	                }
                    double updatedRoomRate = Double.parseDouble(roomRate);
			        updatedRoom = roomController.updateRoomRate(room, updatedRoomRate);
			        System.out.println("Room rate updated: ");
			        System.out.println(updatedRoom.toString());
			        break;
            	case 3:
			    	System.out.print("Check In time (yyyy-MM-dd HH:mm): ");
			    	String updatedCheckInTime = in.nextLine().trim().replace(" ", "T");
			    	while(true){
			    		if(inputCheck.checkTimeInput(updatedCheckInTime)) {
			    			break;
			    		}
			    		else {
			    			System.out.print("Please enter time in the format of (yyyy-MM-dd HH:mm): ");
			    			updatedCheckInTime = in.nextLine().trim().replace(" ", "T");
			    		}
			    	}
			        updatedRoom = roomController.updateCheckInTime(room, updatedCheckInTime);
			        System.out.println("Room details updated: ");
			        System.out.println(updatedRoom.toString());
			        break;
            	case 0:
            		break;
    		}
	    return;
	}
    
}
