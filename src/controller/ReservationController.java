package controller;

import entity.Reservation;
import entity.Reservation.PaymentMethod;
import entity.Reservation.ReservationStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReservationController {
	private List<Reservation> reservationList;
    private static ReservationController reservationController = null;

    private ReservationController() {
        this.reservationList = new ArrayList<>();
    }

    public static ReservationController getInstance() {
        if (reservationController == null) {
            reservationController = new ReservationController();
        }
        return reservationController;
    }
    
	
    public Reservation createReservation(String paymentmethod, String guestContact, String checkIn, 
    		String checkOut, int adult, int child, String roomNum) {
    	
    	UUID reservationCode = UUID.randomUUID();
    	PaymentMethod payment = PaymentMethod.valueOf(paymentmethod.toUpperCase());
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");//input format:yyyy-mm-ddThh:mm	
    	LocalDateTime checkInTime = LocalDateTime.parse(checkIn, formatter);
		LocalDateTime checkOutTime = LocalDateTime.parse(checkOut, formatter);
		Integer numAdult = Integer.valueOf(adult);
		Integer numChild = Integer.valueOf(child);
		ReservationStatus status = ReservationStatus.CONFIRMED;
        Reservation reservation = new Reservation(reservationCode, payment, guestContact, checkInTime, 
    			checkOutTime, numAdult, numChild, roomNum, status);        
        this.reservationList.add(reservation);
        return reservation;
    }
    
    public Reservation updateContact(Reservation reservation, String updatedContact) {
    	reservation.setGuestContact(updatedContact);
    	return reservation;
    }
    
    public Reservation updatePayment (Reservation reservation, String updatedPaymentmethod) {
    	PaymentMethod payment = PaymentMethod.valueOf(updatedPaymentmethod.toUpperCase());
    	reservation.setPayment(payment);
    	return reservation;
    }
    
    public Reservation updateCheckInTime(Reservation reservation, String updatedCheckIn) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    	LocalDateTime checkInTime = LocalDateTime.parse(updatedCheckIn, formatter);
    	reservation.setCheckInTime(checkInTime);
    	return reservation;
    }
    
    public Reservation updateCheckOutTime(Reservation reservation, String updatedCheckOut) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime checkOutTime = LocalDateTime.parse(updatedCheckOut, formatter);
        reservation.setCheckOutTime(checkOutTime);
    	return reservation;
    }
    
    public Reservation updateNumberOfGuests(Reservation reservation, int updatedAdult, int updatedChild) {
        reservation.setNumAdult(updatedAdult);
        reservation.setNumChild(updatedChild);
    	return reservation;
    }
    
    public Reservation updateRoomNum(Reservation reservation, String updatedRoomNum) {
    	reservation.setRoomNum(updatedRoomNum);
    	return reservation;
    }
    
    public Reservation updateStatus(Reservation reservation, String updatedStatus) {
    	ReservationStatus status = ReservationStatus.valueOf(updatedStatus.toUpperCase());
    	reservation.setStatus(status);
    	return reservation;
    }
    
    public List<Reservation> searchReservation(String guestContact) {
        return reservationList.stream().filter(o -> o.getGuestContact().equals(guestContact)).collect(Collectors.toList());//return a list of reservations with the specified contact number
    }
    
    public List<Reservation> getAllReservations(){
    	return reservationList;
    }
    
    public void removeReservation(Reservation reservation) {
    		reservationList.remove(reservation);
    }
    
    public boolean checkContactInput(String contact) {
    	if(contact.length()<=15) {
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
