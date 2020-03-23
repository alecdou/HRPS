package entity;

public class Guest {
    String guestName;
    String creditCardDetails; // Should we create a separate class for this? //I think yes, number and billing address
    String address;
    String country;
    String gender;
    String passport;
    String drivingLicense;
    String nationality;
    String contact;
    // int roomNum; //? maybe String type

    public Guest(String guestName, String creditCardDetails, String address, String country, String gender, String passport, String drivingLicense, String nationality, String contact) {
        this.guestName = guestName;
        this.creditCardDetails = creditCardDetails;
        this.address = address;
        this.country = country;
        this.gender = gender;
        this.passport = passport;
        this.drivingLicense = drivingLicense;
        this.nationality = nationality;
        this.contact = contact;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getCreditCardDetails() {
        return creditCardDetails;
    }

    public void setCreditCardDetails(String creditCardDetails) {
        this.creditCardDetails = creditCardDetails;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "guestName='" + guestName + '\'' +
                ", creditCardDetails='" + creditCardDetails + '\'' +
                ", address='" + address + '\'' +
                ", country='" + country + '\'' +
                ", gender='" + gender + '\'' +
                ", passport='" + passport + '\'' +
                ", drivingLicense='" + drivingLicense + '\'' +
                ", nationality='" + nationality + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
