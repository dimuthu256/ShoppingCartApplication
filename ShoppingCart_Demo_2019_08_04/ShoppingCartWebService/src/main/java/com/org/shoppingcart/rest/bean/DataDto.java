package com.org.shoppingcart.rest.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class DataDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<ProductDto> productList;
	
}
