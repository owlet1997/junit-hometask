package com.haulmont;

import com.haulmont.order.HttpClient;
import com.haulmont.order.Order;
import com.haulmont.order.OrderStatus;

public class OrderService {
    HttpClient client;

    public OrderService(HttpClient client) {
        this.client = client;
    }

    public boolean reserveOrder(Order order) {
        if (order.getStatus() == OrderStatus.RESERVED) {
            return true;
        }
        String response = client.executeRequest("localhost/v1/order/reserve", order);
        if (response.equals("success")) {
            order.setStatus(OrderStatus.RESERVED);
            return true;
        }
        order.setStatus(OrderStatus.CANCELLED);
        return false;
    }

}
