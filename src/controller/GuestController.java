package controller;

import entity.Guest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GuestController {
    private List<Guest> guestList;
    private static GuestController guestController = null;

    private GuestController() {
        this.guestList = new ArrayList<>();
    }

    public static GuestController getInstance() {
        if (guestController == null) {
            guestController = new GuestController();
        }
        return guestController;
    }

    public Guest createGuest(String guestName, String creditCardNo, String creditBillingAddress, String address, 
    		String country, String gender, String passport, String drivingLicense, String nationality, String contact) {
    	Guest guest = new Guest(guestName, creditCardNo, creditBillingAddress, address, country, gender, passport, drivingLicense, nationality, contact);
        this.guestList.add(guest);
        return guest;
    }
    
    public Guest updateGuestName(Guest guest, String guestName) {
    	guest.setGuestName(guestName);
        return guest;
    }
    
    public Guest updateCreditCard(Guest guest, String updatedCreditCardNum, String updatedBillingAddress) {
    	guest.setCreditCardDetails(updatedCreditCardNum, updatedBillingAddress);
        return guest;
    }
    
    public Guest updateAddress(Guest guest, String updatedAddress) {
    	guest.setAddress(updatedAddress);
        return guest;
    }
    
    public Guest updateGender(Guest guest, String updatedGender) {
    	guest.setGender(updatedGender);
        return guest;
    }
    
    public Guest updateCountry(Guest guest, String updatedCountry) {
    	guest.setCountry(updatedCountry);
        return guest;
    }
    
    public Guest updatePassport(Guest guest, String updatedPassport) {
    	guest.setPassport(updatedPassport);
        return guest;
    }
    
    public Guest updateDrivingLicense(Guest guest, String updatedDrivingLicense) {
    	guest.setDrivingLicense(updatedDrivingLicense);
        return guest;
    }
    
    public Guest updateNationality(Guest guest, String updatedNationality) {
    	guest.setNationality(updatedNationality);
        return guest;
    }
    
    public Guest updateContact(Guest guest, String updatedContact) {
    	guest.setContact(updatedContact);
        return guest;
    }

    public List<Guest> searchGuest(String guestName) {
        return guestList.stream().filter(o -> o.getGuestName().equals(guestName)).collect(Collectors.toList());//return a list of guest with the specified name
    }
    
    public boolean checkCreditCardInput(String creditCardNo) {
    	if(creditCardNo.length()==16) {
    		try {
    			long a = Long.parseLong(creditCardNo);
    			return true;
    		}
    		catch(Exception e) {
    			return false;
    		}
    	}
    	else {return false;}
    }
    
    public boolean checkContactInput(String contact) {
    	if(contact.length()<=15) {
    		try {
    			Long b = Long.parseLong(contact);
    			return true;
    		}
    		catch(Exception e) {
    			return false;
    		}
    	}
    	else {return false;}
    }
}
