package entity;


import java.time.LocalDateTime;
import java.util.List;

public class Bill {
    private final Double WEEKDAYOFF = 0.9;
    private final Double DISCOUNTRATE = 0.8;
    private final Double TAX = 0.07;

    private PaymentMethod paymentMethod;
    private Double totalAmount;
    private LocalDateTime billingTime;
    private Double roomCharges;
    private Double tax;
    private Double serviceCharges;
    private List<Order> roomServices;

    public Bill(){
        this.roomCharges = Double.valueOf(0);
        this.totalAmount = Double.valueOf(0);
        this.serviceCharges = Double.valueOf(0);
        billingTime = LocalDateTime.now();
    }

    public Bill(PaymentMethod paymentMethod, Double totalAmount, LocalDateTime billingTime, Double roomCharges, Double tax, List<Order> roomServices) {
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.billingTime = billingTime;
        this.roomCharges = roomCharges;
        this.tax = tax;
        this.roomServices = roomServices;
    }

    public Double getServiceCharges() {
        return serviceCharges;
    }

    public void setServiceCharges(Double serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public Double getWEEKDAYOFF() {
        return WEEKDAYOFF;
    }

    public Double getDISCOUNTRATE() {
        return DISCOUNTRATE;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getBillingTime() {
        return billingTime;
    }

    public void setBillingTime(LocalDateTime billingTime) {
        this.billingTime = billingTime;
    }

    public Double getRoomCharges() {
        return roomCharges;
    }

    public void setRoomCharges(Double roomCharges) {
        this.roomCharges = roomCharges;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public List<Order> getRoomServices() {
        return roomServices;
    }

    public void setRoomServices(List<Order> roomServices) {
        this.roomServices = roomServices;
    }

    public Double getTAX() {
        return TAX;
    }
}
