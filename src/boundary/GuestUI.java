package boundary;

import controller.GuestController;
import entity.Guest;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class GuestUI {
    // static variable single_instance of type GuestUI
    private static GuestUI single_instance = null;
    private GuestController guestController = GuestController.getInstance();

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
        while (choice != 0) {
            switch (choice) {
                case 1:
                    createGuestUI();
                    break;
                case 2:
                    findGuestUI();
                    break;
            }
            choice = this.displayOptions();
        }
    }

    private int displayOptions() {
        System.out.println("1. Create a new guest");
        System.out.println("2. Find a guest");
        System.out.println("0. Exit");
        System.out.println("Your choice: ");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        return choice;
    }

    public void createGuestUI() {
        Scanner in = new Scanner(System.in);
        final String guestName, creditCardDetails, address, country, gender, passport, drivingLicense, nationality, contact;
        System.out.print("Guest Name: ");
        guestName = in.nextLine();
        System.out.print("Credit Card: ");
        creditCardDetails = in.nextLine();
        System.out.print("Address: ");
        address = in.nextLine();
        System.out.print("Country: ");
        country = in.nextLine();
        System.out.print("Gender: ");
        gender = in.nextLine();
        System.out.print("Passport: ");
        passport= in.nextLine();
        System.out.print("Driving License: ");
        drivingLicense = in.nextLine();
        System.out.print("Nationality: ");
        nationality = in.nextLine();
        System.out.print("Contact: ");
        contact = in.nextLine();
        Guest guest = guestController.createGuest(guestName, creditCardDetails, address, country, gender, passport, drivingLicense, nationality, contact);
        System.out.println(guest.toString());
    }

    private void findGuestUI() {
        Scanner in = new Scanner(System.in);
        final String guestName;
        System.out.print("Guest Name: ");
        guestName = in.nextLine();
        List<Guest> guests = guestController.searchGuest(guestName);
        if (guests.isEmpty()) {
            System.out.println("No records found!");
        } else {
            for (Guest guest : guests) {
                System.out.println(guest.toString());
            }
        }


    }
}
