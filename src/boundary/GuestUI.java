package boundary;

import controller.CheckInputController;
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
    private CheckInputController inputCheck = CheckInputController.getInstance();
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
        String guestContact;
        Guest newGuest;
        while (choice != 0) {
            switch (choice) {
                case 1:
                case 2:
                case 3:
	                {System.out.print("Please enter Guest Contact: ");
	                guestContact = in.nextLine().trim().replace(" ", "");
	                boolean valid = inputCheck.checkContactInput(guestContact);
	                while(valid!=true) {
	                	System.out.print("Invalid Contact. Please re-enter Contact: ");
	                    guestContact = in.nextLine().trim().replace(" ", "");
	                    valid = inputCheck.checkContactInput(guestContact);
	                }
	                List<Guest> guests = lookForExistingGuests(guestContact);
	                if(guests.isEmpty()) {
	                	if(choice == 1) {
	                		newGuest = newGuestUI(guestContact);
	                		System.out.println("New guest added to the system: ");
	                        System.out.println(newGuest.toString());
	                	}
	                	else if(choice == 2 || choice == 3) {
	                		System.out.println("No records found!");
	                	}
	                }
	                else {//if there are existing guests
                		if(choice == 2) {
                			System.out.println("Similar records are present in the system: ");
	                    	for (Guest guest : guests) {
	                        	System.out.println(guest.toString());
	                        	System.out.println("Is this this who you are looking for?");
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
	                            	updateGuestUI(guest);
	                            	break;
	                            }
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

    public List<Guest> lookForExistingGuests(String guestContact){
		 List<Guest> guests = guestController.searchGuestContact(guestContact);
		 return guests;
	}

    private int displayOptions() {
    	int choice;
		while (true) {
            try {
		        System.out.println("0. Go back to MainUI");
		        System.out.println("1. Create a new guest");
		        System.out.println("2. Update guest details");
		        System.out.println("3. Find a guest");
		        System.out.println("Your choice: ");
		        choice = in.nextInt();
		        in.nextLine();
		        if (0 <= choice && choice <= 3) {
                    return choice;
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
        String choice2 = in.nextLine().trim().replace(" ", "");
        boolean valid2 = inputCheck.checkIntInput(choice2);
        while(valid2!=true) {
        	System.out.print("Invalid choice. Please re-enter choice: ");
            choice2 = in.nextLine().trim().replace(" ", "");
            valid2 = inputCheck.checkIntInput(choice2);
        }
        int choice = Integer.parseInt(choice2);
        System.out.println("Please enter the updated information: ");
    		switch (choice) {
            	case 1:
			    	System.out.print("Guest Name: ");
			        String updatedName = in.nextLine().trim();
			        Guest updatedGuestName = guestController.updateGuestName(guest, updatedName);
			        System.out.println("Guest details updated: ");
			        System.out.println(updatedGuestName.toString());
			        break;
            	case 2:
            		System.out.print("Credit card number: ");
			        String updatedCreditCardNum = in.nextLine().trim().replace(" ", "");
			        boolean valid = inputCheck.checkCreditCardInput(updatedCreditCardNum);
			        while(valid!=true) {
			        	//System.out.print(updatedCreditCardNum.length());
			        	System.out.print("Invalid Credit Card Number. Please re-enter Credit Card Number: ");
			            updatedCreditCardNum = in.nextLine().trim().replace(" ", "");
			            valid = inputCheck.checkCreditCardInput(updatedCreditCardNum);
			        }
			        System.out.print("Credit card billing address: ");
			        String updatedBillingAddress = in.nextLine().trim();
			        Guest updatedCreditCardInfo = guestController.updateCreditCard(guest, updatedCreditCardNum, updatedBillingAddress);
			        System.out.println("Guest details updated: ");
			        System.out.println(updatedCreditCardInfo.toString());
			        break;
            	case 3:
            		System.out.print("Address: ");
			        String updatedGuestAddress = in.nextLine().trim();
			        Guest updatedAddress = guestController.updateAddress(guest, updatedGuestAddress);
			        System.out.println("Guest details updated: ");
			        System.out.println(updatedAddress.toString());
			        break;
            	case 4:
            		System.out.print("Gender: ");
			        String updatedGuestGender = in.nextLine();
			        boolean valid4 = inputCheck.checkGenderInput(updatedGuestGender);
			        while(valid4!=true) {
			        	System.out.print("Invalid input. Please re-enter Gender ('f' or 'm'): ");
			        	updatedGuestGender = in.nextLine();
			            valid4 = inputCheck.checkGenderInput(updatedGuestGender);
			        }
			        Guest updatedGender = guestController.updateGender(guest, updatedGuestGender);
			        System.out.println("Guest details updated: ");
			        System.out.println(updatedGender.toString());
			        break;
            	case 5:
            		System.out.print("Country: ");
			        String updatedGuestCountry = in.nextLine().trim();
			        Guest updatedCountry = guestController.updateCountry(guest, updatedGuestCountry);
			        System.out.println("Guest details updated: ");
			        System.out.println(updatedCountry.toString());
			        break;
            	case 6:
            		System.out.print("Passport: ");
			        String updatedGuestPassport = in.nextLine().trim();
			        Guest updatedPassport = guestController.updatePassport(guest, updatedGuestPassport);
			        System.out.println("Guest details updated: ");
			        System.out.println(updatedPassport.toString());
			        break;
            	case 7:
            		System.out.print("Driving license: ");
			        String updatedGuestDrivingLicense = in.nextLine().trim();
			        Guest updatedDrivingLicense = guestController.updateDrivingLicense(guest, updatedGuestDrivingLicense);
			        System.out.println("Guest details updated: ");
			        System.out.println(updatedDrivingLicense.toString());
			        break;
            	case 8:
            		System.out.print("Nationality: ");
			        String updatedGuestNationality = in.nextLine().trim();
			        Guest updatedNationality = guestController.updateNationality(guest, updatedGuestNationality);
			        System.out.println("Guest details updated: ");
			        System.out.println(updatedNationality.toString());
			        break;
            	case 9:
            		System.out.print("Contact: ");
			        String updatedGuestContact = in.nextLine().trim().replace(" ", "");
			        boolean valid3 = inputCheck.checkContactInput(updatedGuestContact);
			        while(valid3!=true) {
			        	System.out.print("Invalid Contact. Please re-enter Contact: ");
			        	updatedGuestContact = in.nextLine().trim().replace(" ", "");
			            valid3 = inputCheck.checkContactInput(updatedGuestContact);
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

    public Guest newGuestUI(String guestContact) {
    	String guestName, creditCardNo, creditBillingAddress, address, country, gender, passport, drivingLicense, nationality;
    	System.out.print("Credit Card Number: ");
        creditCardNo = in.nextLine().trim().replace(" ", "");
        //System.out.println(creditCardNo);
        //while(creditCardNo.length() == 0) {creditCardNo = in.nextLine().trim().replace(" ", "");}
        boolean valid = inputCheck.checkCreditCardInput(creditCardNo);
        while(valid!=true) {
        	//System.out.print(creditCardNo.length());
        	System.out.print("Invalid Credit Card Number. Please re-enter Credit Card Number: ");
            creditCardNo = in.nextLine().trim().replace(" ", "");
            valid = inputCheck.checkCreditCardInput(creditCardNo);
        }
        System.out.println("Guest Name: ");
        guestName = in.nextLine();
        System.out.print("Credit Card Billing Address: ");
        creditBillingAddress = in.nextLine().trim();
        System.out.print("Address: ");
        address = in.nextLine().trim();
        System.out.print("Country: ");
        country = in.nextLine().trim();
        System.out.print("Gender: ");
        gender = in.nextLine();
        boolean valid4 = inputCheck.checkGenderInput(gender);
        while(valid4!=true) {
        	System.out.print("Invalid input. Please re-enter Gender ('f' or 'm'): ");
        	gender = in.nextLine();
            valid4 = inputCheck.checkGenderInput(gender);
        }
        System.out.println("Use 1. Passport or 2. Driving License as identification: ");
        System.out.println("Your choice: ");
        String choice2 = in.nextLine().trim().replace(" ", "");
        boolean valid2 = inputCheck.checkIntInput(choice2);
        while(valid2!=true) {
        	System.out.print("Invalid choice. Please re-enter choice: ");
            choice2 = in.nextLine().trim().replace(" ", "");
            valid2 = inputCheck.checkIntInput(choice2);
        }
        int choice = Integer.parseInt(choice2);
        if(choice == 1) {	
	        System.out.print("Passport: ");
	        passport= in.nextLine().trim();
	        drivingLicense = String.valueOf(0);
        }
        else {
        	System.out.print("Driving License: ");
        	drivingLicense = in.nextLine().trim();
        	passport = String.valueOf(0);
        }
        System.out.print("Nationality: ");
        nationality = in.nextLine().trim();
        Guest guest = guestController.createGuest(guestName, creditCardNo, creditBillingAddress, address, country, gender, passport, drivingLicense, nationality, guestContact);
        return guest;
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
