package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import entity.Reservation.PaymentMethod;
import entity.Room.RoomBedType;
import entity.Room.RoomFacing;
import entity.Room.RoomType;

public class CheckInputController {

    private static CheckInputController checkInputController = null;
    private CheckInputController() {}

    public static CheckInputController getInstance() {
        if (checkInputController == null) {
            checkInputController = new CheckInputController();
        }
        return checkInputController;
    }
    
    public boolean checkCreditCardInput(String creditCardNo) {
    	//System.out.println(creditCardNo);
    	if(creditCardNo.length()==16) {
    		try {
    			long a = Long.parseLong(creditCardNo);
    			return true;
    		}
    		catch(Exception e) {
    			return false;
    		}
    	}
    	else {return false;}
    }
    
    public boolean checkContactInput(String contact) {
    	if(contact.length() == 8) {
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
    
    public boolean checkGenderInput(String input) {
    	if(input.length()==1) {
    		try {
    			return (input.toUpperCase().contentEquals("F")||input.toUpperCase().contentEquals("M"));
    		}
    		catch(Exception e) {
    			return false;
    		}
    	}
    	else {return false;}
    }
    
	public boolean checkIntInput(String input) {
    	if(input.length()>=1) {
    		try {
    			int b = Integer.parseInt(input);
    			return true;
    		}
    		catch(Exception e) {
    			return false;
    		}
    	}
    	else {return false;}
    }
	
	public boolean checkLongInput(String input) {
    	if(input.length()>=1) {
    		try {
    			Long b = Long.parseLong(input);
    			return true;
    		}
    		catch(Exception e) {
    			return false;
    		}
    	}
    	else {return false;}
    }
	
	public boolean checkDoubleInput(String input) {
    	if(input.length()>=1) {
    		try {
    			double b = Double.parseDouble(input);
    			return true;
    		}
    		catch(Exception e) {
    			return false;
    		}
    	}
    	else {return false;}
    }
	
	public boolean checkpaymentInput(String input) {
    	if(input.length()==4 || input.length() == 10) {
    		try {
    			PaymentMethod payment = PaymentMethod.valueOf(input.toUpperCase());
    			return true;
    		}
    		catch(Exception e) {
    			return false;
    		}
    	}
    	else {return false;}
    }
	
	public boolean checkTimeInput(String input) {
    	if(input.length()==16) {
    		try {
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");//input format:yyyy-mm-ddThh:mm	
    	    	LocalDateTime checkInTime = LocalDateTime.parse(input, formatter);
    			return true;
    		}
    		catch(Exception e) {
    			return false;
    		}
    	}
    	else {return false;}
    }

	public boolean checkTimeInput(String input, String input2) {
		if(input.length()==16) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");//input format:yyyy-mm-ddThh:mm
				LocalDateTime checkInTime = LocalDateTime.parse(input, formatter);
				LocalDateTime checkOutTime = LocalDateTime.parse(input2, formatter);
				if (checkOutTime.isBefore(checkInTime))
					return false;
				else
					return true;
			}
			catch(Exception e) {
				return false;
			}
		}
		else {return false;}
	}

	public boolean checkResStatusInput(String input) {
		if(input.length()==7 || input.length() == 8 || input.length() == 9) {
    		try {
    			PaymentMethod payment = PaymentMethod.valueOf(input.toUpperCase());
    			return true;
    		}
    		catch(Exception e) {
    			return false;
    		}
    	}
    	else {return false;}
	}
	
	public boolean checkRoomTypeInput(String input) {
		if(input.length()==3 || input.length() == 5 || input.length() == 6) {
    		try {
    			RoomType type = RoomType.valueOf(input);
    			return true;
    		}
    		catch(Exception e) {
    			return false;
    		}
    	}
    	else {return false;}
	}
	
	public boolean checkRoomBedInput(String input) {
		if(input.length()==4 || input.length() == 6 || input.length() == 14) {
    		try {
    			RoomBedType bedType = RoomBedType.valueOf(input);
    			return true;
    		}
    		catch(Exception e) {
    			return false;
    		}
    	}
    	else {return false;}
	}
	
	public boolean checkRoomFaceInput(String input) {
		if(input.length()==4 || input.length() == 5) {
    		try {
    			RoomFacing roomFace = RoomFacing.valueOf(input);
    			return true;
    		}
    		catch(Exception e) {
    			return false;
    		}
    	}
    	else {return false;}
	}
}
