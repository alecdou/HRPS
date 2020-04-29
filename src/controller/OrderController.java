package controller;

import entity.*;
import tool.SerializeDB;

import java.io.File;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class OrderController {
    private static final String dir = "src/data/order.dat";
    private OrderList orderList;
    private static OrderController OrderController = null;

    public static OrderController getInstance() {
        if (OrderController == null) {
            OrderController = new OrderController();
        }
        return OrderController;
    }

    public OrderController() {
        File file = new File(dir);
        if (file.exists()) {
            orderList = (OrderList) SerializeDB.readSerializedObject(dir);
        } else {
            file.getParentFile().mkdir();
            orderList = new OrderList(new LinkedList<>());
            SerializeDB.writeSerializedObject(dir, orderList);
        }
    }

    public void makeOrder(String roomNum, String remarks, List<MenuItem> items) {
        Order order = new Order(roomNum, LocalDateTime.now(), remarks, OrderStatus.CONFIRMED, items);
        orderList.addOrder(order);
        SerializeDB.writeSerializedObject(dir, orderList);
    }

    public void updateOrder() {
        orderList.updateOrderStatus();
        SerializeDB.writeSerializedObject(dir, orderList);
    }

    public void printOrder(String roomNum, LocalDateTime time) {
        updateOrder();
        System.out.println(orderList.filterOrders(roomNum, time));
    }

    public double getTotalPrice(String roomNum, LocalDateTime checkinTime, LocalDateTime checkoutTime) {
        return orderList.getOrderPrice(roomNum, checkinTime, checkoutTime);
    }
}
