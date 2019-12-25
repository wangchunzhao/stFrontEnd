package com.qhc.steigenberger.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qhc.steigenberger.domain.OrderQuery;
import com.qhc.steigenberger.domain.Result;
import com.qhc.steigenberger.domain.form.Order;

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
//		String startTag = "${";
//		String endTag = "}";
//		// parameter start tag size
//		int pslen = startTag.length();
//		// parameter end tag size
//		int pelen = endTag.length();
//		String regex = "\\$\\{.+?\\}";
//		regex = startTag.replace("$", "\\$").replace("{", "\\{") + ".+?" + endTag.replace("}", "\\}");
//		System.out.println(regex);
//		Pattern p = Pattern.compile(regex);
//		String text = "*****${abc}dddddd";
//		Matcher matcher = p.matcher(text);
//		System.out.println(matcher.find());
//		System.out.println(text.substring(matcher.start() + pslen, matcher.end() - 1));
		
		
		Result result = orderService.findOrderDetail(1);
		System.out.println(result);
	}

}
