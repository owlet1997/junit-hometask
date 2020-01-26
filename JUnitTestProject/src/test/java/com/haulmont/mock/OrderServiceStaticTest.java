package com.haulmont.mock;

import com.haulmont.OrderServiceAdv;
import com.haulmont.order.HttpClient;
import com.haulmont.order.Order;
import com.haulmont.order.OrderLoader;
import com.haulmont.order.OrderUpdater;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(OrderLoader.class)
public class OrderServiceStaticTest {
    @Mock
    private HttpClient client;
    @Mock
    private OrderUpdater orderUpdaterMock;
    private OrderServiceAdv orderService;

    @Before
    public void setUpMock() {
        orderService = new OrderServiceAdv(client, orderUpdaterMock);
    }

    @Test
    public void testOrderLoaderStatic() {
        Order order = new Order();
        PowerMockito.mockStatic(OrderLoader.class);
        Mockito.when(OrderLoader.loadOrder()).thenReturn(order);
        assertEquals(orderService.loadOrderFromDb(), order);

    }
}
