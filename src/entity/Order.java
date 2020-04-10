package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Order implements Serializable {
    private String roomNum; // foreign key
    private LocalDateTime orderTime;
    private String remarks;
    private OrderStatus status;
    private List<MenuItem> orderItems;

    public Order(String roomNum, LocalDateTime orderTime, String remarks, OrderStatus status, List<MenuItem> orderItems) {
        this.roomNum = roomNum;
        this.orderTime = orderTime;
        this.remarks = remarks;
        this.status = status;
        this.orderItems = orderItems;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<MenuItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<MenuItem> orderItems) {
        this.orderItems = orderItems;
    }
}
