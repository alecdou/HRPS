package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import entity.Guest;
import entity.Reservation;
import entity.Room;
import entity.Reservation.PaymentMethod;
import entity.Reservation.ReservationStatus;
import entity.Room.RoomBedType;
import entity.Room.RoomFacing;
import entity.Room.RoomStatus;
import entity.Room.RoomType;

public class RoomController {
	private List<Room> roomList;
    private static RoomController RoomController = null;

    private RoomController() {
        this.roomList = new ArrayList<>(48);
    }

    public static RoomController getInstance() {
        if (RoomController == null) {
            RoomController = new RoomController();
        }
        return RoomController;
    }
    public Room updateRoom(String roomNumber, String roomType, String roomBedType, String roomFacing, String roomStatus,
            String hasWifi, String isSmokingFree, double rate, String checkInTime) {
    	boolean wifi, smokeFree;
    	RoomType type = RoomType.valueOf(roomType.toUpperCase());
    	RoomBedType bedType = RoomBedType.valueOf(roomBedType.toUpperCase());
    	RoomFacing roomFace = RoomFacing.valueOf(roomFacing.toUpperCase());
    	RoomStatus status = RoomStatus.valueOf(roomStatus.toUpperCase());
    	if(hasWifi == "1") {wifi = true;}
    	else {wifi = false;}
    	if(isSmokingFree == "1") {smokeFree = true;}
    	else {smokeFree = false;}
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");//input format:yyyy-mm-ddThh:mm	
    	LocalDateTime checkIn = LocalDateTime.parse(checkInTime, formatter);
    	
    	Room room = new Room(roomNumber, type,bedType,roomFace,status, wifi, smokeFree, rate, checkIn);      
        this.roomList.add(room);
        return room;
    	
    }
    public ArrayList<Room> createRooms() {
        int level, unit;
        //read from data file
    }
    
    public List<Room> findRoom(String roomNumber) {
        return roomList.stream().filter(o -> o.getRoomNumber().equals(roomNumber)).collect(Collectors.toList());
    }
	
}
