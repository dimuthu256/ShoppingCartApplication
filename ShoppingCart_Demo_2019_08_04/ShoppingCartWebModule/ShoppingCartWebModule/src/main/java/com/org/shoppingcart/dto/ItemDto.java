package com.org.shoppingcart.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ItemDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private ProductDto productDto;
	private int quantity;

	public ItemDto() {
	}

	public ItemDto(ProductDto productDto, int quantity) {
		this.setProductDto(productDto);
		this.quantity = quantity;
	}
	
}