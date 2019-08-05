package com.org.shoppingcart.core.request;

import java.io.Serializable;
import java.util.List;

import com.org.shoppingcart.core.bean.ProductDto;

import lombok.Data;

@Data
public class ProductRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ProductDto> productList;

}
