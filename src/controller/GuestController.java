package controller;

import entity.Guest;
import entity.OrderList;
import tool.SerializeDB;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GuestController {
    private List<Guest> guestList;
    private static GuestController guestController = null;
    private static final String dir = "src/data/guest.dat";

    private GuestController() {
        File file = new File(dir);
        if (file.exists()) {
            guestList = (List<Guest>) SerializeDB.readSerializedObject(dir);
        } else {
            file.getParentFile().mkdir();
            guestList = new LinkedList<Guest>();
            SerializeDB.writeSerializedObject(dir, guestList);
        }
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
        SerializeDB.writeSerializedObject(dir, guestList);
        return guest;
    }

    public Guest updateGuestName(Guest guest, String guestName) {
        guest.setGuestName(guestName);
        SerializeDB.writeSerializedObject(dir, guestList);
        return guest;
    }

    public Guest updateCreditCard(Guest guest, String updatedCreditCardNum, String updatedBillingAddress) {
        guest.setCreditCardDetails(updatedCreditCardNum, updatedBillingAddress);
        SerializeDB.writeSerializedObject(dir, guestList);
        return guest;
    }

    public Guest updateAddress(Guest guest, String updatedAddress) {
        guest.setAddress(updatedAddress);
        SerializeDB.writeSerializedObject(dir, guestList);
        return guest;
    }

    public Guest updateGender(Guest guest, String updatedGender) {
        guest.setGender(updatedGender);
        SerializeDB.writeSerializedObject(dir, guestList);
        return guest;
    }

    public Guest updateCountry(Guest guest, String updatedCountry) {
        guest.setCountry(updatedCountry);
        SerializeDB.writeSerializedObject(dir, guestList);
        return guest;
    }

    public Guest updatePassport(Guest guest, String updatedPassport) {
        guest.setPassport(updatedPassport);
        SerializeDB.writeSerializedObject(dir, guestList);
        return guest;
    }

    public Guest updateDrivingLicense(Guest guest, String updatedDrivingLicense) {
        guest.setDrivingLicense(updatedDrivingLicense);
        SerializeDB.writeSerializedObject(dir, guestList);
        return guest;
    }

    public Guest updateNationality(Guest guest, String updatedNationality) {
        guest.setNationality(updatedNationality);
        SerializeDB.writeSerializedObject(dir, guestList);
        return guest;
    }

    public Guest updateContact(Guest guest, String updatedContact) {
        guest.setContact(updatedContact);
        SerializeDB.writeSerializedObject(dir, guestList);
        return guest;
    }

    public List<Guest> searchGuest(String guestName) {
        return guestList.stream().filter(o -> o.getGuestName().toUpperCase().equals(guestName.toUpperCase())).collect(Collectors.toList());//return a list of guest with the specified name
    }

    public List<Guest> searchGuestContact(String guestContact) {
        return guestList.stream().filter(o -> o.getContact().equals(guestContact)).collect(Collectors.toList());
    }

}
