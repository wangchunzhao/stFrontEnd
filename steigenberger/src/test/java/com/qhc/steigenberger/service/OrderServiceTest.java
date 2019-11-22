package com.qhc.steigenberger.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qhc.steigenberger.domain.OrderQuery;
import com.qhc.steigenberger.domain.form.AbsOrder;

@SpringBootTest
class OrderServiceTest {
	
	@Autowired
	private OrderService orderService;

	@Test
	void testFindOrders() {
		OrderQuery query = new OrderQuery();
		query.setLast(false);
		query.setIncludeDetail(true);
		orderService.findOrders(query);
	}

	@Test
	void testFindOrderDetail() {
		AbsOrder order = orderService.findOrderDetail("123", "1-2", "");
		System.out.println(order.getCreateTime());
	}

}
