package com.rg.shoppingcart.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.org.shoppingcart.controller.util.exception.ApplicationException;
import com.org.shoppingcart.request.ItemsRequest;
import com.org.shoppingcart.response.ProductResponse;
import com.org.shoppingcart.service.impl.ProductServiceImpl;

@SessionScoped
@ManagedBean
public class ProductManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private ItemsRequest productDtos;
	private ProductResponse productResponse;

	public ProductManagedBean() throws ApplicationException {
		ProductServiceImpl productServiceImpl = new ProductServiceImpl();
		// get all products
		this.productResponse = productServiceImpl.findAll();
	}

	public String index() throws ApplicationException {
		ProductServiceImpl productServiceImpl = new ProductServiceImpl();
		// get all products
		this.productResponse = productServiceImpl.findAll();
		return "index?faces-redirect=true";
	}

	public ItemsRequest getProductDtos() {
		return productDtos;
	}

	public void setProductDtos(ItemsRequest productDtos) {
		this.productDtos = productDtos;
	}

	public ProductResponse getProductResponse() {
		return productResponse;
	}

	public void setProductResponse(ProductResponse productResponse) {
		this.productResponse = productResponse;
	}

}