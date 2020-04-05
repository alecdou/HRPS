package boundary;

import controller.GuestController;
import entity.Guest;
import entity.Reservation;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class GuestUI {
    // static variable single_instance of type GuestUI
    private static GuestUI single_instance = null;
    private GuestController guestController = GuestController.getInstance();
    private static Scanner in = new Scanner(System.in);
    
    // private constructor restricted to this class itself
    private GuestUI() {}

    // static method to create instance of GuestUI class
    public static GuestUI getInstance()
    {
        if (single_instance == null)
            single_instance = new GuestUI();
        return single_instance;
    }

    public void run() {
        int choice = this.displayOptions();
        String guestName;
        while (choice != 0) {
            switch (choice) {
                case 1:
                case 2:
                case 3:
	                {System.out.print("Please enter Guest Name: ");
	            	guestName = in.next();
	                List<Guest> guests = lookForExistingGuests(guestName);
	                if(guests.isEmpty()) {
	                	if(choice == 1) {
	                		newGuestUI(guestName);
	                	}
	                	else if(choice == 2 || choice == 3) {
	                		System.out.println("No records found!");
	                	}
	                }
	                else {//if there are existing guests
                		if(choice == 1|| choice == 2) {
                			System.out.println("Similar names are present in the system record: ");
	                		int noOfGuests = 1;
	                    	for (Guest guest : guests) {
	                        	System.out.println(guest.toString());
	                        	System.out.println("Is this this who you are looking for?");
	                        	System.out.println("1. Yes");
	                        	System.out.println("2. No");
	                        	System.out.println("Your choice: ");
	                            int choice2 = in.nextInt();
	                            if(choice2 == 1) {
	                            	if(choice == 1) {
	                            		System.out.println("Target guest exists in system record. No new guest created.");
	                            		break;}
	                            	else if(choice == 2) {updateGuestUI(guest);}
	                            	break;
	                            }
	                            else if(choice == 1 && choice2 == 2) {
	                            	if(noOfGuests == guests.size()) {
	                            		newGuestUI(guestName);
	                            		break;
	                            	}
	                            }
	                            noOfGuests += 1;
	                    	}
                		}
                		else if(choice == 3) {
                			System.out.println("Printing all guests in the system record...");
                			for(Guest guest : guests) {
                				System.out.println(guest.toString());
                				System.out.print('\n');
                			}
                		}
	                }
	                break;
	            }
                default:
                	break;
            }choice = this.displayOptions();
        }
    }
    
    private List<Guest> lookForExistingGuests(String guestName){
		 List<Guest> guests = guestController.searchGuest(guestName);//check if there is any existing reservations if (reservations.isEmpty()) {
		 return guests;
	}

    private int displayOptions() {
        System.out.println("0. Go back to MainUI");
        System.out.println("1. Create a new guest");
        System.out.println("2. Update guest details");
        System.out.println("3. Find a guest");
        System.out.println("Your choice: ");
        int choice = in.nextInt();
        return choice;
    }

    
    private void updateGuestUI(Guest guest) {
    	System.out.println("Please enter the information that you want to update: ");
    	System.out.println("1. Guest Name ");
        System.out.println("2. Credit Card information ");
        System.out.println("3. Address ");
        System.out.println("4. Gender ");
        System.out.println("5. Country ");
        System.out.println("6. Passport ");
        System.out.println("7. Driving license ");
        System.out.println("8. Nationality ");
        System.out.println("9. Contact ");
        System.out.println("0. Cancel ");
        int choice = in.nextInt();
        System.out.println("Please enter the updated information: ");
    		switch (choice) {
            	case 1:
			    	System.out.print("Guest Name: ");
			        String updatedName = in.next();
			        Guest updatedGuestName = guestController.updateGuestName(guest, updatedName);
			        System.out.println("Guest details updated: ");
			        System.out.println(updatedGuestName.toString());
			        break;
            	case 2:
            		System.out.print("Credit card information: ");
			        String updatedCreditCardNum = in.next();
			        boolean valid = guestController.checkCreditCardInput(updatedCreditCardNum);
			        while(valid!=true) {
			        	System.out.print(updatedCreditCardNum.length());
			        	System.out.print("Invalid Credit Card Number. Please re-enter Credit Card Number: ");
			            updatedCreditCardNum = in.next();
			            valid = guestController.checkCreditCardInput(updatedCreditCardNum);
			        }
			        String updatedBillingAddress = in.next();
			        Guest updatedCreditCardInfo = guestController.updateCreditCard(guest, updatedCreditCardNum, updatedBillingAddress);
			        System.out.println("Guest details updated: ");
			        System.out.println(updatedCreditCardInfo.toString());
			        break;
            	case 3:
            		System.out.print("Address: ");
			        String updatedGuestAddress = in.next();
			        Guest updatedAddress = guestController.updateAddress(guest, updatedGuestAddress);
			        System.out.println("Guest details updated: ");
			        System.out.println(updatedAddress.toString());
			        break;
            	case 4:
            		System.out.print("Gender: ");
			        String updatedGuestGender = in.next();
			        Guest updatedGender = guestController.updateGender(guest, updatedGuestGender);
			        System.out.println("Guest details updated: ");
			        System.out.println(updatedGender.toString());
			        break;
            	case 5:
            		System.out.print("Country: ");
			        String updatedGuestCountry = in.next();
			        Guest updatedCountry = guestController.updateCountry(guest, updatedGuestCountry);
			        System.out.println("Guest details updated: ");
			        System.out.println(updatedCountry.toString());
			        break;
            	case 6:
            		System.out.print("Passport: ");
			        String updatedGuestPassport = in.next();
			        Guest updatedPassport = guestController.updatePassport(guest, updatedGuestPassport);
			        System.out.println("Guest details updated: ");
			        System.out.println(updatedPassport.toString());
			        break;
            	case 7:
            		System.out.print("Driving license: ");
			        String updatedGuestDrivingLicense = in.next();
			        Guest updatedDrivingLicense = guestController.updateDrivingLicense(guest, updatedGuestDrivingLicense);
			        System.out.println("Guest details updated: ");
			        System.out.println(updatedDrivingLicense.toString());
			        break;
            	case 8:
            		System.out.print("Nationality: ");
			        String updatedGuestNationality = in.next();
			        Guest updatedNationality = guestController.updateNationality(guest, updatedGuestNationality);
			        System.out.println("Guest details updated: ");
			        System.out.println(updatedNationality.toString());
			        break;
            	case 9:
            		System.out.print("Contact: ");
			        String updatedGuestContact = in.next();
			        boolean valid2 = guestController.checkContactInput(updatedGuestContact);
			        while(valid2!=true) {
			        	System.out.print("Invalid Contact. Please re-enter Contact: ");
			        	updatedGuestContact = in.next();
			            valid2 = guestController.checkContactInput(updatedGuestContact);
			        }
			        Guest updatedContact = guestController.updateContact(guest, updatedGuestContact);
			        System.out.println("Guest details updated: ");
			        System.out.println(updatedContact.toString());
			        break;
            	case 0:
            		break;
    		}
	    return;
	}
    
    private void newGuestUI(String guestName) {
    	String creditCardNo, creditBillingAddress, address, country, gender, passport, drivingLicense, nationality, contact;
    	System.out.print("Credit Card Number: ");
        creditCardNo = in.next();
        boolean valid = guestController.checkCreditCardInput(creditCardNo);
        while(valid!=true) {
        	System.out.print(creditCardNo.length());
        	System.out.print("Invalid Credit Card Number. Please re-enter Credit Card Number: ");
            creditCardNo = in.next();
            valid = guestController.checkCreditCardInput(creditCardNo);
        }
        System.out.print("Credit Card Billing Address: ");
        creditBillingAddress = in.next();
        System.out.print("Address: ");
        address = in.next();
        System.out.print("Country: ");
        country = in.next();
        System.out.print("Gender: ");
        gender = in.next();
        System.out.print("Passport: ");
        passport= in.next();
        System.out.print("Driving License: ");
        drivingLicense = in.next();
        System.out.print("Nationality: ");
        nationality = in.next();
        System.out.print("Contact: ");
        contact = in.next();
        boolean valid2 = guestController.checkContactInput(contact);
        while(valid2!=true) {
        	System.out.print("Invalid Contact. Please re-enter Contact: ");
            contact = in.next();
            valid2 = guestController.checkContactInput(contact);
        }
        Guest guest = guestController.createGuest(guestName, creditCardNo, creditBillingAddress, address, country, gender, passport, drivingLicense, nationality, contact);
        System.out.println("New guest added to the system: ");
        System.out.println(guest.toString());
    }
    



//    private void findGuestUI(String guestName) {
//        List<Guest> guests = guestController.searchGuest(guestName);
//        if(guests.isEmpty()) {
//    		System.out.println("No records found!");
//    	}
//    	else{
//    		for (Guest guest : guests) {
//            System.out.println(guest.toString());
//    		}
//    	}
//    }
}
