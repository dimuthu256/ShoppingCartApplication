package com.org.shoppingcart.dto;

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

	public ProductDto() {
		super();
	}

	public ProductDto(int id, String name, double price, int quantity, String description, boolean status) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.status = status;
	}

}
