package com.org.shoppingcart.rest.request;

import java.io.Serializable;
import java.util.List;

import com.org.shoppingcart.rest.bean.ProductDto;

import lombok.Data;

@Data
public class ProductRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<ProductDto> productList;

}
