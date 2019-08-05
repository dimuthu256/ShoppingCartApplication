package com.org.shoppingcart.service;

import com.org.shoppingcart.request.ProductRequest;
import com.org.shoppingcart.response.ProductResponse;

public interface ProductService {

	public ProductResponse findAll();

	public ProductResponse checkoutItems(ProductRequest productDtos);
}
