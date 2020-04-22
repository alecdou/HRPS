package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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

    public RoomController() {
        this.roomList = new ArrayList<>(48);
    }

    public static RoomController getInstance() {
        if (RoomController == null) {
            RoomController = new RoomController();
        }
        return RoomController;
    }
    
    public Room newRoom(String roomNumber, String roomType, String roomBedType, String roomFacing, String roomStatus,
            String hasWifi, String isSmokingFree, double rate, String checkInTime) {
    	RoomType type = RoomType.valueOf(roomType.toUpperCase());
    	RoomBedType bedType = RoomBedType.valueOf(roomBedType.toUpperCase());
    	RoomFacing roomFace = RoomFacing.valueOf(roomFacing.toUpperCase());
    	RoomStatus status = RoomStatus.valueOf(roomStatus.toUpperCase());
    	boolean wifi, smokeFree;
    	if(hasWifi == "1"||hasWifi.toLowerCase() == "true"||hasWifi.toLowerCase() == "yes") {wifi = true;}
    	else {wifi = false;}
    	if(isSmokingFree == "1" || isSmokingFree.toLowerCase() == "true"||isSmokingFree.toLowerCase() == "yes") {smokeFree = true;}
    	else {smokeFree = false;}
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");//input format:yyyy-mm-ddThh:mm	
    	LocalDateTime checkIn = LocalDateTime.parse(checkInTime, formatter);
    	Room room = new Room(roomNumber, type, bedType, roomFace, status, wifi, smokeFree, rate, checkIn);
        return room;
    	
    }
    public List<Room> createRooms()throws IOException {
        //read from data file
        Path path = Paths.get("..\\HRPS\\src\\entity\\RoomData.txt");
        //read all lines
        List<String> lines = Files.readAllLines(path);
        for(int count = 0; count<lines.size();count++) {
        	String[] description = new String[6];
        	description = lines.get(count).split(",");
        	//System.out.println(description.length);
        	//System.out.println(Arrays.toString(description));
        	Room room = newRoom(description[0], description[1], description[2], description[3], 
        			"vacant", description[4], description[5], 100, "1880-01-01T00:00");
        	roomList.add(room);
        	//break;
        }
        return roomList;
    }
    
    public List<Room> findRoom(String roomNumber) {
        return roomList.stream().filter(o -> o.getRoomNumber().equals(roomNumber)).collect(Collectors.toList());
    }

    public List<Room> findRoomByName(String name) {
//    	return roomList.stream().filter(o -> o.getGuest().getGuestName().equals(name)).collect(Collectors.toList());
		List<Room> rooms = new ArrayList<>();
		for (Room room: roomList) {
			if (room.getGuest() != null && room.getGuest().getGuestName().equals(name)) {
				rooms.add(room);
			}
		}
		return rooms;
	}

	public List<Room> checkRoom(String check) {
		char type = check.charAt(0);
		List<Room> rooms;
		switch(type) {
			case 'r':
				String roomNo = check.substring(1);
				rooms = findRoom(roomNo);
				break;
			case 'g':
				String guestName = check.substring(1);
				rooms = roomList.stream().filter(o -> o.getGuest().getGuestName().equals(guestName)).collect(Collectors.toList());
				break;
			case 't':
				RoomType t = RoomType.valueOf(check.substring(1).toUpperCase());
				rooms = roomList.stream().filter(o -> o.getRoomType().equals(t)).collect(Collectors.toList());
				break;
			default:
				return null;
			
		}
		return rooms;
	}

	//check in/out
	public Room checkIn(String roomNumber, Guest guest) {
		List<Room> rooms = findRoom(roomNumber);
		Room room = rooms.get(0);
		RoomStatus status = RoomStatus.valueOf("OCCUPIED");
		room.setRoomStatus(status);
		room.setGuest(guest);
		return room;
	}
	
	public List<Room> checkAvailableRooms(List<Room> rooms){
		List<Room> availableRooms = new ArrayList<>();
		for(Room room: rooms) {
			if(room.getRoomStatus().toString().equals("VACANT")) {
				availableRooms.add(room);
			}
		}
		return availableRooms;
	}

	public List<Room> checkAvailableRooms(List<Room> rooms, String checkIn, String checkOut){
		List<Room> availableRooms = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");//input format:yyyy-mm-ddThh:mm
		LocalDateTime checkInTime = LocalDateTime.parse(checkIn, formatter);
		LocalDateTime checkOutTime = LocalDateTime.parse(checkOut, formatter);
		ReservationController rc = ReservationController.getInstance();
		for(Room room: rooms) {
			if(room.getRoomStatus().toString().equals("VACANT")) {
				availableRooms.add(room);
			} else if (room.getRoomStatus().toString().equals("RESERVED")) {
				boolean flag = true;
				for (Reservation reservation: rc.getReservationByRoom(room.getRoomNumber())) {
					if (!(reservation.getCheckInTime().isAfter(checkOutTime) ||
							reservation.getCheckOutTime().isBefore(checkInTime))) {
						flag = false;
						break;
					}
				}
				if (flag)
					availableRooms.add(room);
			}
		}
		return availableRooms;
	}
	
	public List<Room> findRoomByFacing(List<Room> rooms, String facing){
		RoomFacing f = RoomFacing.valueOf(facing.toUpperCase());
		List<Room> selectedRooms = rooms.stream().filter(o -> o.getRoomFacing().equals(f)).collect(Collectors.toList());
		return selectedRooms;
	}
	
	public List<Room> findRoomByStatus(List<Room> rooms, String status){
		RoomStatus s = RoomStatus.valueOf(status.toUpperCase());
		List<Room> selectedRooms = rooms.stream().filter(o -> o.getRoomStatus().equals(s)).collect(Collectors.toList());
		return selectedRooms;
	}
	
	public List<Room> findRoomByType(String type){
		return checkRoom("t" + type);
	}
	
	public List<Room> findRoomByBedType(List<Room> rooms, String bedType){
		RoomBedType t = RoomBedType.valueOf(bedType.toUpperCase());
		List<Room> selectedRooms = rooms.stream().filter(o -> o.getRoomBedType().equals(t)).collect(Collectors.toList());
		return selectedRooms;
	}
	
	public Room updateRoomRate(Room room, double updatedRoomRate) {
		room.setRate(updatedRoomRate);
		return room;
	}

	public Room updateCheckInTime(Room room, String updatedCheckInTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");//input format:yyyy-mm-ddThh:mm	
    	LocalDateTime checkIn = LocalDateTime.parse(updatedCheckInTime, formatter);
    	room.setCheckInTime(checkIn);
		return room;
	}

	public Room updateRoomStatus(Room room, String updatedRoomStatus) {
		RoomStatus status = RoomStatus.valueOf(updatedRoomStatus.toUpperCase());
		room.setRoomStatus(status);
		return room;
	}

	
}
