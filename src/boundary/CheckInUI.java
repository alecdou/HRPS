package boundary;


import java.util.Scanner;

public class CheckInUI {
    // static variable single_instance of type CheckinUI
    private static CheckInUI single_instance = null;
    private GuestUI guestUI = GuestUI.getInstance();
    private Scanner in = new Scanner(System.in);

    // private constructor restricted to this class itself
    private CheckInUI() {
    }

    // static method to create instance of CheckinUI class
    public static CheckInUI getInstance() {
        if (single_instance == null)
            single_instance = new CheckInUI();
        return single_instance;
    }

    public void run() {
        int choice = this.displayOptions();

        switch (choice) {
            case 1:
                reservationCheckInUI();
                break;
            case 2:
                walkInCheckInUI();
                break;
        }

    }

    private void walkInCheckInUI() {
        System.out.println("Please enter the room type, ...");
        String dummy = in.nextLine();
        // call the room controller to find a list of vacant rooms (based on the above criteria)
        System.out.println("Searching vacant rooms...");
        System.out.println("Select a room to check in: ");
        dummy = in.nextLine();

        // call the guest ui to create a new guest
        System.out.println("Recoding guest information");
        guestUI.createGuestUI();

        // update room status

    }

    private void reservationCheckInUI() {

        // TODO: Complete the implementation.
        // find the reservation by guest name and display its details (reservation ui)
        System.out.println("Searching reservation records...(reservation)");

        // confirm checkin
        System.out.println("Confirm check-in? (Y/N)");
        String dummy = in.nextLine();
        System.out.println(dummy);

        try {
            // TODO: Complete the implementation.
            System.out.println("Updating...(reservation & room)");
            // update the reservation through reservation controller (status = CHECKEDIN)

            // update the room through room controller (status = OCCUPIED)

        } catch (Exception e) {
            System.out.println("Check-in Failed! Please try again.");
        }
    }

    private int displayOptions() {
        int choice;
        while (true) {
            try {
                System.out.println("1. Check-in (Reservation)");
                System.out.println("2. Check-in (Walk-in)");
                System.out.println("0. Exit");
                System.out.println("Your choice: ");
                choice = in.nextInt();
                in.nextLine(); // clear dummy characters
                if (0 <= choice && choice <= 2) {
                    return choice;
                } else {
                    System.out.println("ERROR: The input should be 0, 1, or 2");
                    System.out.println("Please select again:");
                }
            } catch (Exception e) {
                System.out.println("ERROR: The input should be of type (int)");
                System.out.println("Please select again:");
                in.nextLine(); // clear dummy characters
            }
        }
    }


}
