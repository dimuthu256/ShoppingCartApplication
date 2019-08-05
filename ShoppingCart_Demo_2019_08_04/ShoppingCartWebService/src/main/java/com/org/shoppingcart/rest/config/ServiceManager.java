package com.org.shoppingcart.rest.config;

import java.io.Serializable;

import org.springframework.web.client.RestTemplate;

public class ServiceManager implements Serializable{

	public ServiceManager() {
		super();
	}
	private static final long serialVersionUID = 1L;
	
	protected RestTemplate restTemplate;

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}


}
