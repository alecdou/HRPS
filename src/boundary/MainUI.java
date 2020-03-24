package boundary;

import java.util.Scanner;

public class MainUI {

    public void run() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        System.out.println("1: Guest Operations, 2: Reservation Operations, 3: Room Operations, 4: Service Operations, 5: Check In, 6: Check Out, 7: Generate room status statistic report, 8: Quit");
        System.out.println("Enter your choice:");

        while(choice < 1 || choice > 8){
            try{
                choice = sc.nextInt();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        switch(choice){
            case 1:
                GuestUI guestUI = GuestUI.getInstance();
                guestUI.run();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                CheckOutUI checkOutUI = CheckOutUI.getInstance();
                checkOutUI.checkOut();
                break;
            case 7:
                break;
            case 8:
                break;

                default:
                    break;
        }



    }
}
