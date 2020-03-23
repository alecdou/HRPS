package boundary;

import controller.CheckOutController;
import controller.GuestController;
import entity.Guest;
import entity.PaymentMethod;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class CheckOutUI {
    private static CheckOutUI single_instance = null;
    private CheckOutController checkOutController = CheckOutController.getInstance();
    private GuestController guestController = GuestController.getInstance();

    private CheckOutUI() {
    }

    public static CheckOutUI getInstance() {
        if (single_instance == null)
            single_instance = new CheckOutUI();
        return single_instance;
    }

    public void run() {

        Scanner sc = new Scanner(System.in);
        String roomNum, promotionStr, paymentMethodStr;
        boolean promotion;
        int paymentChoice;

        System.out.println("Enter guest name for check-out service: ");
        String guestName = sc.nextLine();
        List<Guest> guests = guestController.searchGuest(guestName);
        while(guests.isEmpty()){
            System.out.println("No records found! Enter correct name: "); //test bad input
            guestName = sc.nextLine();
            guests = guestController.searchGuest(guestName);
        }
        for(Guest g : guests){
            System.out.println(g.toString());
        }
        int i = 0;
        if(guests.size()>1){
            System.out.println("Multiple guests with same name, enter line number of guest with matched identity:");
            //is size>1, there will be multiple rows, get guests[i].roomNum for updating room status
            i = sc.nextInt();
        }
        roomNum = guests.get(i).getRoomNum();


        System.out.println("Any promotion for the guest?(Y/N)");
        promotionStr = sc.nextLine();
        while(promotionStr!="Y" || promotionStr!="N"){
            System.out.println("Please entre Y or N, one upper case character:");
            promotionStr = sc.nextLine();
        }
        if(promotionStr=="Y"){
            promotion = true;
        }
        else{
            promotion = false;
        }

        System.out.println("Enter payment method, 1: cash, 2: credit card");
        paymentChoice = sc.nextInt();
        switch (paymentChoice){
            case 1:
                paymentMethodStr = "CASH";
                break;
            case 2:
                paymentMethodStr = "CREDITCARD";
                break;
                default:
                    paymentMethodStr = null;
        }


        String[] results = checkOutController.checkOut(roomNum, promotion);
        for(String str:results){
            System.out.println(str);
        }


        //print guest details and payment details
        System.out.print("Guest name: " + guestName +"\nPayment method: " + PaymentMethod.fromString(paymentMethodStr));
        if(paymentChoice==2){
            System.out.print(" details: " + guests.get(i).getCreditCardDetails());
        }
        System.out.println();
        System.out.println("Successfully check out!");
    }

}