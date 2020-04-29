package controller;

import entity.Guest;
import entity.OrderList;
import entity.Reservation;
import entity.Reservation.PaymentMethod;
import entity.Reservation.ReservationStatus;
import tool.SerializeDB;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReservationController {
    private static final String dir = "src/data/reservation.dat";
	private List<Reservation> reservationList;
    private static ReservationController reservationController = null;

    private ReservationController() {
        File file = new File(dir);
        if (file.exists()) {
            reservationList = (List<Reservation>) SerializeDB.readSerializedObject(dir);
        } else {
            file.getParentFile().mkdir();
            reservationList = new LinkedList<>();
            SerializeDB.writeSerializedObject(dir, reservationList);
        }
    }

    public static ReservationController getInstance() {
        if (reservationController == null) {
            reservationController = new ReservationController();
        }
        return reservationController;
    }
    
	
    public Reservation createReservation(Guest guest, String paymentmethod, String guestContact, String checkIn, 
    		String checkOut, int adult, int child, String roomNum) {
    	
    	UUID reservationCode = UUID.randomUUID();
    	PaymentMethod payment = PaymentMethod.valueOf(paymentmethod.toUpperCase());
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");//input format:yyyy-mm-ddThh:mm	
    	LocalDateTime checkInTime = LocalDateTime.parse(checkIn, formatter);
		LocalDateTime checkOutTime = LocalDateTime.parse(checkOut, formatter);
		Integer numAdult = Integer.valueOf(adult);
		Integer numChild = Integer.valueOf(child);
        ReservationStatus status;
		if (checkInTime.isBefore(LocalDateTime.now()))
		    status = ReservationStatus.EXPIRED;
		else
		    status = ReservationStatus.CONFIRMED;
        Reservation reservation = new Reservation(guest, reservationCode, payment, guestContact, checkInTime, 
    			checkOutTime, numAdult, numChild, roomNum, status);        
        this.reservationList.add(reservation);
        SerializeDB.writeSerializedObject(dir, reservationList);
        return reservation;
    }
    
    public Reservation updateContact(Reservation reservation, String updatedContact) {
    	reservation.setGuestContact(updatedContact);
        SerializeDB.writeSerializedObject(dir, reservationList);
    	return reservation;
    }
    
    public Reservation updatePayment (Reservation reservation, String updatedPaymentmethod) {
    	PaymentMethod payment = PaymentMethod.valueOf(updatedPaymentmethod.toUpperCase());
    	reservation.setPayment(payment);
        SerializeDB.writeSerializedObject(dir, reservationList);
    	return reservation;
    }
    
    public Reservation updateCheckInTime(Reservation reservation, String updatedCheckIn) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    	LocalDateTime checkInTime = LocalDateTime.parse(updatedCheckIn, formatter);
    	reservation.setCheckInTime(checkInTime);
        SerializeDB.writeSerializedObject(dir, reservationList);
    	return reservation;
    }
    
    public Reservation updateCheckOutTime(Reservation reservation, String updatedCheckOut) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime checkOutTime = LocalDateTime.parse(updatedCheckOut, formatter);
        reservation.setCheckOutTime(checkOutTime);
        SerializeDB.writeSerializedObject(dir, reservationList);
    	return reservation;
    }
    
    public Reservation updateNumberOfGuests(Reservation reservation, int updatedAdult, int updatedChild) {
        reservation.setNumAdult(updatedAdult);
        reservation.setNumChild(updatedChild);
        SerializeDB.writeSerializedObject(dir, reservationList);
    	return reservation;
    }
    
    public Reservation updateRoomNum(Reservation reservation, String updatedRoomNum) {
    	reservation.setRoomNum(updatedRoomNum);
        SerializeDB.writeSerializedObject(dir, reservationList);
    	return reservation;
    }
    
    public Reservation updateStatus(Reservation reservation, String updatedStatus) {
    	ReservationStatus status = ReservationStatus.valueOf(updatedStatus.toUpperCase());
    	reservation.setStatus(status);
        SerializeDB.writeSerializedObject(dir, reservationList);
    	return reservation;
    }
    
    public List<Reservation> searchReservation(String guestContact) {
        return reservationList.stream().filter(o -> o.getGuestContact().equals(guestContact)).collect(Collectors.toList());//return a list of reservations with the specified contact number
    }

    public List<Reservation> getReservationByRoom(String roomNum) {
        List<Reservation> roomResList = new ArrayList<>();
        for (Reservation reservation: reservationList) {
            if (reservation.getRoomNum().equals(roomNum)) {
                roomResList.add(reservation);
            }
        }
        return roomResList;
    }

    public List<Reservation> getAllReservations(){
    	return reservationList;
    }
    
    public void removeReservation(Reservation reservation) {
    		reservationList.remove(reservation);
    }
    
    public boolean checkContactInput(String contact) {
    	if(contact.length()==8) {
    		try {
    			Long b = Long.parseLong(contact);
    			return true;
    		}
    		catch(Exception e) {
    			return false;
    		}
    	}
    	else {return false;}
    }
	
}
