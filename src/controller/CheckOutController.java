package controller;

import entity.Bill;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class CheckOutController {
    private Room room;
    private Bill bill;
    private int numStay = 0, numWeekday = 0, numWeekend = 0;
    private static CheckOutController checkOutController = null;
    private RoomController roomController = new RoomController();
    private OrderController orderController = new OrderController();
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
        roomController.searchRoom(roomNum).setType("VACANT");//set to enum VACANT
    }

    public void setStayDays(String roomNum){
        room = roomController.findRoom(roomNum);
        LocalDate begin = room.getCheckInTime().toLocalDate;
        LocalDate end = bill.getBillingTime().toLocalDate();
        while (!begin.equals(end)){
            begin.plusDays(1);
            //for example: 01/04 to 02/04 is one day of stay, so INC is required, otherwise will be 2 days
            numStay++;
            DayOfWeek dayOfWeek = begin.getDayOfWeek();
            switch(dayOfWeek.getValue()){  //whether this day is weekday: different rate
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
        return bill.getRoomCharges();
    }

    public Double getServiceCharge(){
        bill.setServiceCharges(orderController.getTotalPrice(room.getRoomNumber(), room.getCheckInTime(), room.getCheckOutTime(), bill.getBillingTime()));

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

    public void printItems(){
        orderController.printOrder(room.getRoomNumber());
    };

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
