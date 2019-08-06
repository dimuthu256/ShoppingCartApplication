package com.org.shoppingcart.service.impl;

import java.util.Collections;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.org.shoppingcart.controller.util.GsonUtil;
import com.org.shoppingcart.controller.util.REST_CONTROLLER_URL;
import com.org.shoppingcart.controller.util.ServiceManager;
import com.org.shoppingcart.controller.util.exception.ApplicationException;
import com.org.shoppingcart.request.ProductRequest;
import com.org.shoppingcart.response.ProductResponse;

public class ProductServiceImpl extends ServiceManager {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);

	public ProductResponse findAll() throws ApplicationException {
		try {
			final String restURL = CONTEXT_PATH + REST_CONTROLLER_URL.GET_ALL_ITEMS;
			logger.info("Find All Rest Service URL :" + restURL);
			restTemplate = new RestTemplate();
			String strResponse = restTemplate.getForObject(restURL, String.class);
			logger.info("Find All Response :" + strResponse);
			return GsonUtil.getFromObject(strResponse, ProductResponse.class);
		} catch (Exception e) {
			logger.error("Error Occured in Product Service findAll: {}", e);
			throw new ApplicationException("Error Occured in Product Service findAll");
		}
	}

	public ProductResponse checkoutItems(ProductRequest productDtos) throws ApplicationException {
		try {

			final String restURL = CONTEXT_PATH + REST_CONTROLLER_URL.SUBMIT_SELECTED_ITEMS;
			logger.info("Submit All Rest Service URL :" + restURL);
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			String request = GsonUtil.getToString(productDtos, ProductRequest.class);
			// send request and parse result
			HttpEntity<String> entity = new HttpEntity<>(request, headers);
			ResponseEntity<String> response = restTemplate.exchange(restURL, HttpMethod.POST, entity, String.class);
			return GsonUtil.getFromObject(response.getBody(), ProductResponse.class);

		} catch (Exception e) {
			logger.error("Error Occured in Product Service checkoutItems : {}", e);
			throw new ApplicationException("Error Occured in Product Service checkoutItems");
		}
	}

}
