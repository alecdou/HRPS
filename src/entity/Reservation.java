package entity;
import java.io.Serializable;
import java.util.UUID;
import java.time.LocalDateTime;
public class Reservation implements Serializable {
	UUID reservationCode;
	public enum PaymentMethod{CASH, CREDITCARD}
	PaymentMethod payment;
	private Guest guest;
	private String guestContact;
	private LocalDateTime checkInTime;
	private LocalDateTime checkOutTime;
	private Integer numAdult;
	private Integer numChild;
	private String roomNum;
	public enum ReservationStatus{AVAILABLE, CONFIRMED, WAITLIST, CHECKEDIN, EXPIRED}
	ReservationStatus status;

	
	public Reservation(Guest guest, UUID reservationCode, PaymentMethod payment, String guestContact, LocalDateTime checkInTime, 
			LocalDateTime checkOutTime, Integer numAdult, Integer numChild, String roomNum, ReservationStatus status) {
		this.guest = guest;
		this.payment = payment;
		this.reservationCode = reservationCode;
		this.guestContact = guestContact;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		this.numAdult = numAdult;
		this.numChild = numChild;
		this.roomNum = roomNum;
		this.status = status;
		
	}
    
    public UUID getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(UUID reservationCode) {
		this.reservationCode = reservationCode;
	}

	public PaymentMethod getPayment() {
		return payment;
	}

	public void setPayment(PaymentMethod payment) {
		this.payment = payment;
	}

	public String getGuestContact() {
		return guestContact;
	}

	public void setGuestContact(String guestContact) {
		this.guestContact = guestContact;
	}

	public LocalDateTime getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(LocalDateTime checkInTime) {
		this.checkInTime = checkInTime;
	}

	public LocalDateTime getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(LocalDateTime checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public Integer getNumAdult() {
		return numAdult;
	}

	public void setNumAdult(Integer numAdult) {
		this.numAdult = numAdult;
	}

	public Integer getNumChild() {
		return numChild;
	}

	public void setNumChild(Integer numChild) {
		this.numChild = numChild;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}
	
	@Override
    public String toString() {
        return "Reservation{" + '\n' + 
                "reservationCode= " + reservationCode + '\n' +
                "PaymentMethod= " + payment + '\n' +
                "guestContact= " + guestContact + '\n' +
                "checkInTime= " + checkInTime.toString().replace("T", " ") + '\n' +
                "checkOutTime= " + checkOutTime.toString().replace("T", " ") + '\n' +
                "numAdult= " + numAdult + '\n' +
                "numChild= " + numChild + '\n' +
                "roomNum= " + roomNum + '\n' +
                "ReservationStatus= " + status + '\n' +
                '}';
    }

}
