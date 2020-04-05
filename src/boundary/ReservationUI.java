package boundary;

import controller.ReservationController;
import entity.Reservation;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class ReservationUI {
    private static ReservationUI single_instance = null;
    private ReservationController reservationController = ReservationController.getInstance();
    private static Scanner in = new Scanner(System.in);
    
    private ReservationUI() {}

    public static ReservationUI getInstance()
    {
        if (single_instance == null)
            single_instance = new ReservationUI();
        return single_instance;
    }
    
    public void run() {
        String guestContact;
    	int choice = this.displayOptions();
        while (choice != 0) {
            switch (choice) {
                case 1:
                case 2:
                case 3:
                	{System.out.print("Please enter Guest Contact: ");
                	guestContact = in.next();
                	boolean valid = reservationController.checkContactInput(guestContact);
			        while(valid!=true) {
			        	System.out.print("Invalid Contact. Please re-enter Contact: ");
			        	guestContact = in.next();
			            valid = reservationController.checkContactInput(guestContact);
			        }
                    List<Reservation> reservations = lookForExistingReservations(guestContact);
                    if(reservations.isEmpty()) {
                    	if(choice == 1) {
                    		newReservationUI(guestContact);
                    	}
                    	else if(choice == 2 || choice == 3) {
                    		System.out.println("No records found!");
                    	}
                    }
                    else {//if there are existing reservations
                    	if(choice == 1) {
                    		System.out.println("There is a reservation with the same guest contact: ");
                    		for (Reservation reservation : reservations) {
                            	System.out.println(reservation.toString());}
                    		break;
                    	}
                    	else if(choice==2 || choice == 3) {
                    		System.out.println("Similar names are present in the system record: ");
                        	for (Reservation reservation : reservations) {
                            	System.out.println(reservation.toString());
                            	System.out.println("Is this the reservation you are looking for?");
                            	System.out.println("1. Yes");
                            	System.out.println("2. No");
                            	System.out.println("Your choice: ");
                                int choice2 = in.nextInt();
                                if(choice2 == 1) {
                                	if(choice == 2) {updateReservationUI(reservation);}
                                	else if(choice == 3) {removeReservationUI(reservation);}
                                	break;
                                }
                                else if(choice2 == 2) {}
                            }
                    	}
                    }
                    break;
                }
                case 4:
                    printReservationUI();
                    break;
                default:
                	break;
            }choice = this.displayOptions();
        }
    }
    
	
	 private List<Reservation> lookForExistingReservations(String guestContact){
		 List<Reservation> reservations = reservationController.searchReservation(guestContact);//check if there is any existing reservations if (reservations.isEmpty()) {
		 return reservations;
	}

    private int displayOptions() {
        System.out.println("0. Go back to MainUI");
        System.out.println("1. Create a new reservation");
        System.out.println("2. Update reservation details");
        System.out.println("3. Remove a reservation");
        System.out.println("4. Print all reservations");
        System.out.println("Your choice: ");
        int choice = in.nextInt();
        return choice;
    }
    
	private void newReservationUI(String guestContact) {
		String paymentmethod, checkIn, checkOut, roomNum;
		int  adult, child;
    	System.out.print("Payment Method: ");
        paymentmethod = in.next();
        System.out.print("Check In time (yyyy-MM-dd'T'HH:mm): ");
        checkIn = in.next();
        System.out.print("Check In time (yyyy-MM-dd'T'HH:mm): ");
        checkOut = in.next();
        System.out.print("Room Number: ");//use room ui to check room availability
        roomNum = in.next();
        System.out.print("Number Of Adults: ");
        adult = in.nextInt();
        System.out.print("Number Of Children: ");
        child = in.nextInt();
        
		Reservation res = reservationController.createReservation(paymentmethod, guestContact, checkIn, 
				checkOut, adult, child, roomNum);
		System.out.println("New reservation added to the system: ");
        System.out.println(res.toString());
	}

	
    
    private void printReservationUI() {
		List<Reservation> reservations = reservationController.getAllReservations();
		if (reservations.isEmpty()) {
        	System.out.println("No records found!");
        }
		else {
			System.out.println("Printing all reservations in the system record...");
			for(Reservation reservation : reservations) {
				System.out.println(reservation.toString());
				System.out.print('\n');
			}
		}
		
	}

	private void removeReservationUI(Reservation reservation) {
		reservationController.removeReservation(reservation);			        
		System.out.println("Reservation removed: ");
		System.out.println(reservation.toString());
		return;		
	}

	private void updateReservationUI(Reservation reservation) {
		System.out.println("Please enter the information that you want to update: ");
		System.out.println("1. Guest Contact ");
    	System.out.println("2. Payment Method ");
        System.out.println("3. Check In time ");
        System.out.println("4. Check Out time ");
        System.out.println("5. Room Number ");
        System.out.println("6. Number Of Guests ");
        System.out.println("7. Status of Reservation ");
        System.out.println("0. Cancel ");
        int choice = in.nextInt();
        System.out.println("Please enter the updated information: ");
    		switch (choice) {
	    		case 1:
			    	System.out.print("Guest Contact: ");
			        String updatedGuestContact = in.next();
			        boolean valid = reservationController.checkContactInput(updatedGuestContact);
			        while(valid!=true) {
			        	System.out.print("Invalid Contact. Please re-enter Contact: ");
			        	updatedGuestContact = in.next();
			            valid = reservationController.checkContactInput(updatedGuestContact);
			        }
			        Reservation updatedContact = reservationController.updateContact(reservation, updatedGuestContact);
			        System.out.println("Reservation details updated: ");
			        System.out.println(updatedContact.toString());
			        break;
            	case 2:
			    	System.out.print("Payment Method: ");
			        String updatedPaymentmethod = in.next();
			        Reservation updatedPayment = reservationController.updatePayment(reservation, updatedPaymentmethod);
			        System.out.println("Reservation details updated: ");
			        System.out.println(updatedPayment.toString());
			        break;
            	case 3:
			    	System.out.print("Check In time (yyyy-MM-dd'T'HH:mm): ");
			    	String updatedCheckInTime = in.next();
			        Reservation updatedCheckIn = reservationController.updateCheckInTime(reservation, updatedCheckInTime);
			        System.out.println("Reservation details updated: ");
			        System.out.println(updatedCheckIn.toString());
			        break;
            	case 4:
			    	System.out.print("Check Out time (yyyy-MM-dd'T'HH:mm): ");
			    	String updatedCheckOutTime = in.next();
			        Reservation updatedCheckOut = reservationController.updateCheckOutTime(reservation, updatedCheckOutTime);
			        System.out.println("Reservation details updated: ");
			        System.out.println(updatedCheckOut.toString());
			        break;
            	case 5:
			    	System.out.print("Room Number: ");
			    	String updatedRoomNum = in.next();
			        Reservation updatedRoomNumber = reservationController.updateRoomNum(reservation, updatedRoomNum);
			        System.out.println("Reservation details updated: ");
			        System.out.println(updatedRoomNumber.toString());
			        break;
            	case 6:
			    	System.out.print("Number of guests: ");
			        int updatedNumOfAdult = in.nextInt();
			        int updatedNumOfChild = in.nextInt();
			        Reservation updatedNoOfGuests = reservationController.updateNumberOfGuests(reservation, updatedNumOfAdult, updatedNumOfChild);
			        System.out.println("Reservation details updated: ");
			        System.out.println(updatedNoOfGuests.toString());
			        break;
            	case 7:
            		System.out.println("Status of reservation: ");
            		String updatedStatus = in.next();
            		Reservation updatedRes = reservationController.updateStatus(reservation, updatedStatus);
        	        System.out.println("Reservation status updated: ");
        	        System.out.println(updatedRes.toString());
			        break;
            	case 0:
            		break;
    		}
	    return;
	}
}
