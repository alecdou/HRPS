package controller;

import entity.Guest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
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

    public Guest createGuest(String guestName, String creditCardDetails, String address, String country, String gender, String passport, String drivingLicense, String nationality, String contact) {
        Guest guest = new Guest(guestName, creditCardDetails, address, country, gender, passport, drivingLicense, nationality, contact);
        this.guestList.add(guest);
        return guest;
    }

    public List<Guest> searchGuest(String guestName) {
        return guestList.stream().filter(o -> o.getGuestName().equals(guestName)).collect(Collectors.toList());
    }
}
