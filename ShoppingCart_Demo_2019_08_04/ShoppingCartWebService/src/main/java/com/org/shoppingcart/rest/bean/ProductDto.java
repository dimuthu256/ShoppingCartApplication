package com.org.shoppingcart.rest.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProductDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private double price;
	private int quantity;
	private String description;
	private boolean status;

}
