package entity;

import java.time.LocalDateTime;

public class Room {
	
    public enum RoomType{SINGLE, DOUBLE, DELUXE, VIP, SUITE};
    RoomType roomType;
    public enum RoomBedType{SINGLE, DOUBLE, MASTER};
    RoomBedType roomBedType;
    public enum RoomFacing{NORTH, SOUTH, EAST, WEST};
    RoomFacing roomFacing;
    public enum RoomStatus{VACANT, OCCUPIED, RESERVED, MAINTENANCE};
    RoomStatus roomStatus;
    private final String roomNumber;
    private boolean hasWifi;
    private boolean isSmokingFree;
    private double rate;
    private LocalDateTime checkInTime;
    
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
        return "Room{" + '\n' +
                "RoomType= " + roomType + '\n' +
                "RoomBedType= " + roomBedType + '\n' +
                "RoomFacing= " + roomFacing + '\n' +
                "RoomStatus= " + roomStatus + '\n' +
                "RoomNum= " + roomNumber + '\n' +
                "hasWifi= " + hasWifi + '\n' +
                "isSmokingFree= " + isSmokingFree + '\n' +
                "Rate= " + rate + '\n' +
                "checkInTime= " + checkInTime + '\n' +
                '}';
    }
}