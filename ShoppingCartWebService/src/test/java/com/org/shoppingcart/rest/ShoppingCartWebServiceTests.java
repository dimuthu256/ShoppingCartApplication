package com.org.shoppingcart.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.org.shoppingcart.rest.bean.ProductDto;
import com.org.shoppingcart.rest.response.ProductResponse;
import com.org.shoppingcart.rest.service.ShoppingCartService;
import com.org.shoppingcart.rest.service.impl.QueueMessageServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShoppingCartWebServiceTests {

	@Autowired
	ShoppingCartService shoppingCartService;
	@Autowired
	QueueMessageServiceImpl queueMessageServiceImpl;

	public ShoppingCartWebServiceTests() {
	}

	@Test
	@Order(1)
	public void findAllProducts() {
		try {
			ProductResponse response = shoppingCartService.findAllProducts();
			assertEquals(200, response.getStatusCode());
		} catch (Exception e) {
			assert false;
		}
	}

	@Test
	@Order(2)
	public void testCause2() {
		try {
			List<ProductDto> productList = new ArrayList<ProductDto>();
			for (int i = 1; i < 5; i++) {
				productList.add(ProductDto.builder().id(i).name("item" + i).description("item description" + i)
						.price(i * 10).quantity(i * 2).status(true).build());
			}
			//queueMessageServiceImpl.send(ProductRequest.builder().itemList(itemList));
			assert true;
		} catch (Exception e) {
			assert false;
		}
	}

}
