package entity;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class OrderList implements Serializable {
    private static final long serialVersionUID = 4495062569469348968L;
    private List<Order> orders;

    public OrderList(List<Order> orders) {
        this.orders = orders;
    }

    public void updateOrderStatus() {
        long duration;
        for (Order order : orders) {
            duration = Duration.between(order.getOrderTime(), LocalDateTime.now()).toSeconds();
            if (duration > 60) {
                order.setStatus(OrderStatus.DELIVERED);
            } else if (duration > 30) {
                order.setStatus(OrderStatus.PREPARING);
            } else {
                order.setStatus(OrderStatus.CONFIRMED);
            }
        }
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public OrderList getOrderList(String roomNum) {
        OrderList orderList = new OrderList(new LinkedList<>());
        for (Order order : orders) {
            if (order.getRoomNum().equals(roomNum)) {
                orderList.addOrder(order);
            }
        }
        return orderList;
    }

    public double getOrderPrice(String roomNum, LocalDateTime in, LocalDateTime out) {
        double totalPrice = 0;
        for (Order order : orders) {
            if (order.getRoomNum().equals(roomNum)) {
                if (Duration.between(in, order.getOrderTime()).toSeconds() > 0 &&
                        Duration.between(order.getOrderTime(), out).toSeconds() > 0)
                    for (MenuItem item: order.getOrderItems()) {
                        totalPrice += item.getPrice();
                    }
            }
        }
        return totalPrice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Order order : orders) {
            sb.append("Order{" +
                    "roomNum='" + order.getRoomNum() + '\'' +
                    ", orderTime=" + order.getOrderTime() +
                    ", remarks='" + order.getRemarks() + '\'' +
                    ", status=" + order.getStatus() +
                    ", orderItems=" + '\n');
            for (MenuItem item: order.getOrderItems()) {
                sb.append("\tMenuItem{" +
                        "name='" + item.getName() + '\'' +
                        ", description='" + item.getDescription() + '\'' +
                        ", price=" + String.valueOf(item.getPrice()) +
                        '}');
            }
            sb.append("}\n");
        }
        return sb.toString();
    }
}
