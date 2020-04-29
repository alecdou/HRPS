package boundary;

import controller.CheckInputController;
import controller.ReservationController;
import controller.RoomController;
import entity.Guest;
import entity.Reservation;
import entity.Room;
import entity.Room.RoomStatus;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ReservationUI {
	private static ReservationUI single_instance = null;
	private ReservationController reservationController = ReservationController.getInstance();
	private CheckInputController inputCheck = CheckInputController.getInstance();
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
					guestContact = in.nextLine().trim().replace(" ", "");
					boolean valid = reservationController.checkContactInput(guestContact);
					while(valid!=true) {
						System.out.print("Invalid Contact. Please re-enter Contact: ");
						guestContact = in.nextLine().trim().replace(" ", "");
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
							System.out.println("Similar records are present in the system: ");
							for (Reservation reservation : reservations) {
								System.out.println(reservation.toString());
								System.out.println("Is this the reservation you are looking for?");
								System.out.println("1. Yes");
								System.out.println("2. No");
								System.out.println("Your choice: ");
								String choice2 = in.nextLine().trim().replace(" ", "");
								boolean valid2 = inputCheck.checkIntInput(choice2);
								while(valid2!=true) {
									System.out.print("Invalid choice. Please re-enter choice: ");
									choice2 = in.nextLine().trim().replace(" ", "");
									valid2 = inputCheck.checkIntInput(choice2);
								}
								int choice3 = Integer.parseInt(choice2);
								if(choice3 == 1) {
									if(choice == 2) {updateReservationUI(reservation);}
									else if(choice == 3) {removeReservationUI(reservation);}
									break;
								}
								else if(choice3 == 2) {}
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
		int choice;
		while (true) {
			try {
				System.out.println("0. Go back to MainUI");
				System.out.println("1. Create a new reservation");
				System.out.println("2. Update reservation details");
				System.out.println("3. Remove a reservation");
				System.out.println("4. Print all reservations");
				System.out.println("Your choice: ");
				choice = in.nextInt();
				in.nextLine();
				if (0 <= choice && choice <= 4) {
					return choice;
				} else {
					System.out.println("ERROR: The input should be 0, 1, 2, 3 or 4");
					System.out.println("Please select again:");
				}
			} catch (Exception e) {
				System.out.println("ERROR: The input should be of type (int)");
				System.out.println("Please select again:");
				in.nextLine(); // clear dummy characters
			}
		}
	}

	private void newReservationUI(String guestContact) {
		//find available rooms
		RoomUI rui = RoomUI.getInstance();
		RoomController rc = RoomController.getInstance();
		GuestUI guestUI = GuestUI.getInstance();
		Guest guest = guestUI.newGuestUI(guestContact);
		//enter reservation details
		String paymentmethod, checkIn, checkOut, roomNum;
		int  adult, child;
		System.out.print("Check In time (yyyy-MM-dd HH:mm): ");
		checkIn = in.nextLine().trim().replace(" ", "T");
		while(true){
			if(inputCheck.checkTimeInput(checkIn)) {
				break;
			}
			else {
				System.out.print("Please enter time in the format of (yyyy-MM-dd HH:mm): ");
				checkIn = in.nextLine().trim().replace(" ", "T");
			}
		}
		System.out.print("Check Out time (yyyy-MM-dd HH:mm): ");
		checkOut = in.nextLine().trim().replace(" ", "T");
		while(true){
			if(inputCheck.checkTimeInput(checkIn, checkOut)) {
				break;
			}
			else {
				System.out.print("Please enter time in the format of (yyyy-MM-dd HH:mm): ");
				checkOut = in.nextLine().trim().replace(" ", "T");
			}
		}
		List<Room> rooms = rc.checkAvailableRooms(rui.findRoomUI(), checkIn, checkOut);
		if(rooms.isEmpty()){
			System.out.println("There is no available room with the specified requirements. ");
		}
		else{
			System.out.println("These are the available rooms with the specified requirements: ");
			System.out.println("roomNumber -> RoomType -> RoomBedType -> RoomFacing -> Wifi -> SmokingFree -> Rate");
			for(Room room: rooms) {
				System.out.println(room.toString());
			}

			System.out.println("Enter the room number to select: ");
			roomNum = in.nextLine();
			while(rc.findRoom(roomNum).isEmpty() || !rooms.contains(rc.findRoom(roomNum).get(0))) {
				System.out.println("Please enter a valid room number in the format of level-room (ll-rr): ");
				roomNum = in.nextLine();
			}
			RoomController r = RoomController.getInstance();
			r.updateRoomStatus(rc.findRoom(roomNum).get(0), "reserved");
			System.out.print("Payment Method(cash/creditcard): ");
			paymentmethod = in.nextLine();
			while(true){
				if(inputCheck.checkpaymentInput(paymentmethod)) {
					break;
				}
				else {
					System.out.print("Please enter either 'cash' or 'creditcard': ");
					paymentmethod = in.nextLine();
				}
			}

			System.out.print("Number Of Adults: ");
			String noOfAdults = in.nextLine();
			boolean v = inputCheck.checkIntInput(noOfAdults);
			while(v!=true) {
				System.out.print("Invalid number. Please enter an integer: ");
				noOfAdults = in.nextLine();
				v = inputCheck.checkIntInput(noOfAdults);
			}
			adult = Integer.parseInt(noOfAdults);
			System.out.print("Number Of Children: ");
			String noOfChildren = in.nextLine();
			v = inputCheck.checkIntInput(noOfChildren);
			while(v!=true) {
				System.out.print("Invalid number. Please enter an integer: ");
				noOfChildren = in.nextLine();
				v = inputCheck.checkIntInput(noOfChildren);
			}
			child = Integer.parseInt(noOfChildren);
			Reservation res = reservationController.createReservation(guest, paymentmethod, guestContact, checkIn,
					checkOut, adult, child, roomNum);
			System.out.println("New reservation added to the system: ");
			System.out.println(res.toString());
		}
	}



	private void printReservationUI() {
		List<Reservation> reservations = reservationController.getAllReservations();
		if (reservations.isEmpty()) {
			System.out.println("No records found!");
		}
		else {
			System.out.println("Printing all reservations in the system record...");
			for(Reservation reservation : reservations) {
				// filter expired reservation
				if (reservation.getCheckInTime().isAfter(LocalDateTime.now())) {
					System.out.println(reservation.toString());
					System.out.print('\n');
				}
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
		int choice;
		while (true) {
			try {
				System.out.println("Please enter the information that you want to update: ");
				System.out.println("1. Guest Contact ");
				System.out.println("2. Payment Method ");
				System.out.println("3. Check In time ");
				System.out.println("4. Check Out time ");
				System.out.println("5. Room Information ");
				System.out.println("6. Number Of Guests ");
				System.out.println("7. Status of Reservation ");
				System.out.println("0. Cancel ");
				System.out.println("Your choice: ");
				choice = in.nextInt();
				in.nextLine(); // clear dummy characters
				if (0 <= choice && choice <= 7) {
					break;
				} else {
					System.out.println("ERROR: The input should be 0, 1, 2, 3, 4, 5, 6 or 7");
					System.out.println("Please select again:");
				}
			} catch (Exception e) {
				System.out.println("ERROR: The input should be of type (int)");
				System.out.println("Please select again:");
				in.nextLine(); // clear dummy characters
			}
		}

		System.out.println("Please enter the updated information: ");
		switch (choice) {
			case 1:
				System.out.print("Guest Contact: ");
				String updatedGuestContact = in.nextLine().trim().replace(" ", "");
				boolean valid = reservationController.checkContactInput(updatedGuestContact);
				while(valid!=true) {
					System.out.print("Invalid Contact. Please re-enter Contact: ");
					updatedGuestContact = in.nextLine().trim().replace(" ", "");
					valid = reservationController.checkContactInput(updatedGuestContact);
				}
				Reservation updatedContact = reservationController.updateContact(reservation, updatedGuestContact);
				System.out.println("Reservation details updated: ");
				System.out.println(updatedContact.toString());
				break;
			case 2:
				System.out.print("Payment Method: ");
				String updatedPaymentmethod = in.nextLine();
				while(true){
					if(inputCheck.checkpaymentInput(updatedPaymentmethod)) {
						break;
					}
					else {
						System.out.print("Please enter either 'cash' or 'creditcard': ");
						updatedPaymentmethod = in.nextLine();
					}
				}
				Reservation updatedPayment = reservationController.updatePayment(reservation, updatedPaymentmethod);
				System.out.println("Reservation details updated: ");
				System.out.println(updatedPayment.toString());
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
				Reservation updatedCheckIn = reservationController.updateCheckInTime(reservation, updatedCheckInTime);
				System.out.println("Reservation details updated: ");
				System.out.println(updatedCheckIn.toString());
				break;
			case 4:
				System.out.print("Check Out time (yyyy-MM-dd HH:mm): ");
				String updatedCheckOutTime = in.nextLine().trim().replace(" ", "T");
				while(true){
					if(inputCheck.checkTimeInput(updatedCheckOutTime)) {
						break;
					}
					else {
						System.out.print("Please enter time in the format of (yyyy-MM-dd HH:mm): ");
						updatedCheckOutTime = in.nextLine().trim().replace(" ", "T");
					}
				}
				Reservation updatedCheckOut = reservationController.updateCheckOutTime(reservation, updatedCheckOutTime);
				System.out.println("Reservation details updated: ");
				System.out.println(updatedCheckOut.toString());
				break;
			case 5:
				RoomController rc = RoomController.getInstance();
				System.out.print("Room Number: ");
				String updatedRoomNum = in.nextLine();
				while(rc.findRoom(updatedRoomNum).isEmpty()) {
					System.out.println("Please enter a valid room number in the format of level-room (ll-rr): ");
					updatedRoomNum = in.nextLine();
				}
				Reservation updatedRoomNumber = reservationController.updateRoomNum(reservation, updatedRoomNum);
				System.out.println("Reservation details updated: ");
				System.out.println(updatedRoomNumber.toString());
				break;
			case 6:
				System.out.print("Number of adults: ");
				String noOfAdults = in.nextLine();
				boolean v = inputCheck.checkIntInput(noOfAdults);
				while(v!=true) {
					System.out.print("Invalid number. Please enter an integer: ");
					noOfAdults = in.nextLine();
					v = inputCheck.checkIntInput(noOfAdults);
				}
				int updatedNumOfAdult = Integer.parseInt(noOfAdults);
				System.out.print("Number of children: ");
				String noOfChildren = in.nextLine();
				v = inputCheck.checkIntInput(noOfChildren);
				while(v!=true) {
					System.out.print("Invalid number. Please enter an integer: ");
					noOfChildren = in.nextLine();
					v = inputCheck.checkIntInput(noOfChildren);
				}
				int updatedNumOfChild = Integer.parseInt(noOfChildren);
				Reservation updatedNoOfGuests = reservationController.updateNumberOfGuests(reservation, updatedNumOfAdult, updatedNumOfChild);
				System.out.println("Reservation details updated: ");
				System.out.println(updatedNoOfGuests.toString());
				break;
			case 7:
				System.out.println("Status of reservation: ");
				String updatedStatus = in.nextLine();
				while(true){
					if(inputCheck.checkResStatusInput(updatedStatus)) {
						break;
					}
					else {
						System.out.print("Please enter either 'AVAILABLE', 'CONFIRMED', 'WAITLIST', 'CHECKEDIN', or 'EXPIRED': ");
						updatedStatus = in.nextLine();
					}
				}
				Reservation updatedRes = reservationController.updateStatus(reservation, updatedStatus);
				System.out.println("Reservation status updated: ");
				System.out.println(updatedRes.toString());
				break;
			case 0:
				break;
			default:
				break;
		}
		return;
	}
}
