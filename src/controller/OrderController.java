package controller;

import entity.MenuItem;
import entity.Order;
import entity.OrderList;
import entity.OrderStatus;
import tool.SerializeDB;

import java.io.File;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class OrderController {
    private static final String dir = "src/data/order.dat";
    private OrderList orderList;

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

    public void printOrder(String roomNum) {
        updateOrder();
        System.out.println(orderList.getOrderList(roomNum));
    }

    public double getTotalPrice(String roomNum, LocalDateTime checkinTime, LocalDateTime checkoutTime) {
        return orderList.getOrderPrice(roomNum, checkinTime, checkoutTime);
    }
}
