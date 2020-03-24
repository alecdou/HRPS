package controller;

import entity.Bill;
import entity.Guest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheckOutController {
    private Double totalRoomCharge;
    private Room room;
    private Bill bill;
    private int numStay = 0, numWeekday = 0, numWeekend = 0;

    // total amout, numStay(numWeekday, numWeekend), servicehistory(each price, total price), total room, tax

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
    public void setRoomVacant(String roomNum){
        roomController.searchRoom(roomNum).setStatus("Vacant");//set to enum VACANT
    }

    public void setStayDays(String roomNum){
        room = roomController.searchRoom(roomNum);
        LocalDate begin = room.getCheckInTime().toLocalDate;
        LocalDate end = bill.getBillingTime().toLocalDate();
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
    }

    public Double getRoomCharge(){
        //get room charge, sum each day
        return bill.getRoomCharges();
    }

    public Double getServiceCharges(){



        return bill.getServiceCharges();
    }

    public Double getTax(){
        bill.setTax(bill.getTAX() * (bill.getRoomCharges()+bill.getServiceCharges()));
        return bill.getTax();
    }

    public Double getTotalAmount(boolean promotion){
        bill.setTotalAmount(bill.getRoomCharges() + bill.getServiceCharges() + bill.getTax());
        if(promotion == true){
            bill.setTotalAmount(bill.getTotalAmount() * bill.getDISCOUNTRATE());
        }
        return bill.getTotalAmount();
    }

    public int getNumStay() {
        return numStay;
    }

    public int getNumWeekday() {
        return numWeekday;
    }

    public int getNumWeekend() {
        return numWeekend;
    }
}
