package entity;

public class CreditCard {
	String creditCardNumber;
	String billingAddress;
	
	public CreditCard(String creditCardNumber, String billingAddress) {
        this.creditCardNumber = creditCardNumber;
        this.billingAddress = billingAddress;}
        
	public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
    
    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }
    
    @Override
    public String toString() {
        return "Credit Card{" + '\n' + 
                "creditCardNumber= " + creditCardNumber + '\n' +
                "billingAddress= " + billingAddress + '}';
    }
        

}
