package entity;

import java.time.LocalDateTime;

public class Room {
	
    public enum RoomType{SINGLE, DOUBLE, DELUXE, VIP, SUITE};//deluxe 4, vip 8, suite 4, double 16, single 16
    RoomType roomType;
    public enum RoomBedType{SINGLE, STANDARDDOUBLE, KING};//deluxe 4 king, vip 4 king 4 single, suite 2 king 2 single, double 16 standarddouble, single 16 single
    RoomBedType roomBedType;
    public enum RoomFacing{NORTH, SOUTH, EAST, WEST};//deluxe 1 each, vip 2 each, suite 1 each, double 4 each, single 4 each
    RoomFacing roomFacing;
    public enum RoomStatus{VACANT, OCCUPIED, RESERVED, MAINTENANCE};
    RoomStatus roomStatus;
    private final String roomNumber;
    private boolean hasWifi;//2 single rooms and 2 double rooms dont have
    private boolean isSmokingFree;//4 single rooms and 4 double rooms are not
    private double rate;
    private LocalDateTime checkInTime;
    private Guest guest; //guest will be updated by check-in controller using setGuest(Guest guest)
    
    public Room(String roomNumber) {
    	this.roomNumber = roomNumber;
    }

    public Room(String roomNumber, RoomType roomType, RoomBedType roomBedType, RoomFacing roomFacing, RoomStatus roomStatus,
                  boolean hasWifi, boolean isSmokingFree, double rate, LocalDateTime checkInTime) {
    	this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomBedType = roomBedType;
        this.roomFacing = roomFacing;
        this.roomStatus = roomStatus;
        this.hasWifi = hasWifi;
        this.isSmokingFree = isSmokingFree;
        this.rate = rate;
        this.checkInTime = checkInTime;
    }
    
    public Guest getGuest() {
    	return guest;
    }

    public void setGuest(Guest guest) {
    	this.guest = guest;
    }
    
    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public RoomBedType getRoomBedType() {
        return roomBedType;
    }

    public void setRoomBedType(RoomBedType roomBedType){
        this.roomBedType = roomBedType;
    }

    public RoomFacing getRoomFacing() {
        return roomFacing;
    }

    public void setRoomFacing(RoomFacing roomFacing){
        this.roomFacing = roomFacing;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus){
        this.roomStatus = roomStatus;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate){
        this.rate = rate;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public boolean getHasWifi() {
        return hasWifi;
    }

    public void setHasWifi(boolean hasWifi) {
        this.hasWifi = hasWifi;
    }

    public boolean getSmokingFree() {
        return isSmokingFree;
    }

    public void setSmokingFree(boolean isSmokingFree) {
        this.isSmokingFree = isSmokingFree;
    }

    @Override
    public String toString() {
//		return "Room{" + '\n' +
//            "RoomType= " + roomType + '\n' +
//            "RoomBedType= " + roomBedType + '\n' +
//            "RoomFacing= " + roomFacing + '\n' +
//            "RoomStatus= " + roomStatus + '\n' +
//            "RoomNum= " + roomNumber + '\n' +
//            "hasWifi= " + hasWifi + '\n' +
//            "isSmokingFree= " + isSmokingFree + '\n' +
//            "Rate= " + rate + '\n' +
//            "checkInTime= " + checkInTime.toString().replace("T", " ") + '\n' +
//            '}';
        StringBuilder sb = new StringBuilder();
        String room = String.format("%s -> %s -> %s -> %s -> %s -> %s -> %s", roomNumber, roomBedType.toString(), roomBedType.toString(),
                roomFacing.toString(), hasWifi ? "Yes" : "No", isSmokingFree ? "Yes" : "No", "S$" + rate);
        sb.append(room);
        return sb.toString();
    }
}