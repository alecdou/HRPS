package boundary;

public class MainUI {

    public void run() {
        GuestUI guestUI = GuestUI.getInstance();
        CheckInUI checkInUI = CheckInUI.getInstance();
        checkInUI.run();
    }
}
