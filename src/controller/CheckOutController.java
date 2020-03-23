package controller;

import entity.Bill;
import entity.Guest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheckOutController {
    private Room room;
    private Bill bill;

    private static CheckOutController checkOutController = null;

    private CheckOutController(){
        this.room = null;
        this.bill = null;
    }


    public static CheckOutController getInstance() {
        if (checkOutController == null) {
           checkOutController = new CheckOutController();
        }
        return checkOutController;
    }


    public String[] checkOut(String roomNum, boolean promotion){ //
        String[] result = new String[5];
        // total amout, numStay(numWeekday, numWeekend), servicehistory(each price, total price), total room, tax, total amount
        room = roomController.searchRoom(roomNum);

        //get room charge, sum each day
        LocalDate begin = room.getCheckInTime().toLocalDate;
        LocalDate end = bill.getBillingTime().toLocalDate();
        int numStay = 0, numWeekday = 0, numWeekend = 0;
        while (!begin.equals(end)){
            begin.plusDays(1);
            numStay++;
            DayOfWeek dayOfWeek = begin.getDayOfWeek();
            switch(dayOfWeek.getValue()){
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    bill.setRoomCharges(bill.getRoomCharges() + bill.getWEEKDAYOFF() * room.getRate());
                    numWeekday++;
                    break;
                case 6:
                case 7:
                    bill.setRoomCharges(bill.getRoomCharges() + room.getRate());
                    numWeekend++;
                    break;
            }
            numStay++;
        }

        //get service charges



        //get tax
        bill.setTax(bill.getTAX() * (bill.getRoomCharges()+bill.getServiceCharges()));

        //get total amount
        bill.setTotalAmount(bill.getRoomCharges() + bill.getServiceCharges() + bill.getTax());

        //set promotion
        if(promotion == true){
        bill.setTotalAmount(bill.getTotalAmount() * bill.getDISCOUNTRATE());
        }


        //print total bill
        System.out.println();


        //print service history



        //print payment details


        //set room to vacant
        room.setStatus("Vacant");

    }
}
