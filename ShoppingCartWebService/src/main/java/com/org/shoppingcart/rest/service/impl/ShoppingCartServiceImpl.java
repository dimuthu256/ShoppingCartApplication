package com.org.shoppingcart.rest.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.org.shoppingcart.rest.config.GsonUtil;
import com.org.shoppingcart.rest.config.REST_CONTROLLER_URL;
import com.org.shoppingcart.rest.config.ServiceManager;
import com.org.shoppingcart.rest.response.ProductResponse;
import com.org.shoppingcart.rest.service.ShoppingCartService;

@Service
@Transactional(rollbackFor = Exception.class)
public class ShoppingCartServiceImpl extends ServiceManager implements ShoppingCartService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;

	@Value("${shopping.cart.service.url}")
	String seviceUrl;

	@Autowired
	public ShoppingCartServiceImpl() {
		super();
	}

	@Override
	public ProductResponse findAllProducts() {
		try {
			logger.info("Begin Method Find All Products Service");
			restTemplate = new RestTemplate();
			String strResponse = restTemplate.getForObject(seviceUrl + REST_CONTROLLER_URL.GET_ALL_ITEMS, String.class);
			logger.info("End Method Find All Products Service. Response : {}", strResponse);
			return GsonUtil.getFromObject(strResponse, ProductResponse.class);
		} catch (Exception e) {
			logger.error("Error in Find All Products Service : {}", e.getMessage());
		}
		return new ProductResponse();
	}

}