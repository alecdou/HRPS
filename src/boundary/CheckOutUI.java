package boundary;

import controller.CheckOutController;
import controller.GuestController;
import controller.RoomController;
import entity.Guest;
import entity.PaymentMethod;
import entity.Room;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CheckOutUI {
    private static CheckOutUI checkOutUI_instance = null;
    private RoomController roomController = RoomController.getInstance();
    private CheckOutController checkOutController = CheckOutController.getInstance();
    private GuestController guestController = GuestController.getInstance();

    private CheckOutUI() {
    }

    public static CheckOutUI getInstance() {
        if (checkOutUI_instance == null)
            checkOutUI_instance = new CheckOutUI();
        return checkOutUI_instance;
    }

    public void checkOut() {
        Scanner sc = new Scanner(System.in);
        String roomNum, promotionStr, paymentMethodStr;
        boolean promotion;
        int paymentChoice;

        System.out.println("Enter guest name for check-out service: ");
        String guestName = sc.nextLine();
//        List<Guest> guests = guestController.searchGuest(guestName);
        List<Room> rooms = roomController.findRoomByName(guestName);

        while(rooms.isEmpty()){
            System.out.println("No records found! Enter correct name: "); //test bad input
            guestName = sc.nextLine();
            rooms = roomController.findRoomByName(guestName);
        }

        for (Room room: rooms) {
            System.out.println(room.toString());
        }

        int i = 0;
        if(rooms.size()>1){
            System.out.println("Multiple guests with same name, enter line number of guest with matched identity:");
            //is size>1, there will be multiple rows, get guests[i].roomNum for updating room status
            i = sc.nextInt();
        }
        roomNum = rooms.get(i).getRoomNumber();

//        System.out.println("Enter room number for check-out service: ");
//        roomNum = sc.nextLine();
//
//        while(!isValidRoomNum(roomNum)){
//            System.out.println("Room number is not valid!");
//            System.out.println("Enter room number for check-out service: ");
//            roomNum = sc.nextLine();
//        }

        System.out.println("Any promotion for the guest?(Y/N)");
        promotionStr = sc.nextLine();
        while(!promotionStr.equals("Y") && !promotionStr.equals("N")) {
            System.out.println("Please enter Y or N, one upper case character:");
            promotionStr = sc.nextLine();
        }
        if(promotionStr.equals("Y")){
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


        StringBuffer sb = new StringBuffer();
        sb.append("----BILL INVOICE----\n");


        //print days of stay
        checkOutController.setStayDays(roomNum);
        sb.append(checkOutController.getNumStay() +  " days of stay. "
                + checkOutController.getNumWeekday() + " weekdays and "
                + checkOutController.getNumWeekend() + " weekend days\n");

        //print total room charge
        sb.append("Total room charge: " + checkOutController.getRoomCharge());
        sb.append("\n");


        //print service charge
        sb.append("Total service charge: "+ checkOutController.getServiceCharge());
        sb.append("\n");

        //print tax
        sb.append(String.format("Tax: %.2f", checkOutController.getTax()));
        sb.append("\n");


        //print total amount
        sb.append(String.format("Total bill amount: %.2f", checkOutController.getTotalAmount(promotion)));
        sb.append("\n");


        System.out.print(sb);

        //print odered items
        checkOutController.printItems();

        //print guest details and payment details
        System.out.print("Guest name: " + guestName +"\nPayment method: " + PaymentMethod.fromString(paymentMethodStr));
        if(paymentChoice==2){
            System.out.print(" details: " + rooms.get(i).getGuest().getCreditCardDetails());
        }
        System.out.println();

        checkOutController.setRoomVacant(roomNum);
        System.out.println("Successfully check out!");
    }

    public boolean isValidRoomNum(String roomNum){
        return !roomController.findRoom(roomNum).isEmpty();
    }
}
