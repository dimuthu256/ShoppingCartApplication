package com.rg.shoppingcart.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.org.shoppingcart.dto.ItemDto;
import com.org.shoppingcart.dto.ProductDto;
import com.org.shoppingcart.request.ProductRequest;
import com.org.shoppingcart.response.ProductResponse;
import com.org.shoppingcart.service.impl.ProductServiceImpl;

@SessionScoped
@ManagedBean
public class CartManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private ProductResponse productResponse;
	private ProductRequest productDtos;

	private List<ItemDto> itemDtos;

	public CartManagedBean() {
		this.itemDtos = new ArrayList<ItemDto>();
		productDtos = new ProductRequest();
		productResponse = new ProductResponse();
	}

	//add a selected item to the cart 
	public String buy(ProductDto productDto) {
		int index = this.exists(productDto);
		if (index == -1) {
			this.itemDtos.add(new ItemDto(productDto, 1));
		} else {
			int quantity = this.itemDtos.get(index).getQuantity() + 1;
			this.itemDtos.get(index).setQuantity(quantity);
		}
		return "cart?faces-redirect=true";
	}

	//delete a selected item from the cart
	public String delete(ProductDto productDto) {
		int index = this.exists(productDto);
		this.itemDtos.remove(index);
		return "cart?faces-redirect=true";
	}

	//total amount of the selected items
	public double total() {
		double amount = 0;
		for (ItemDto itemDto : this.itemDtos) {
			amount += itemDto.getProductDto().getPrice() * itemDto.getQuantity();
		}
		return amount;
	}

	private int exists(ProductDto productDto) {
		for (int i = 0; i < this.itemDtos.size(); i++) {
			if (this.itemDtos.get(i).getProductDto().getId() == productDto.getId()) {
				//if found a selected item in the list
				return i;
			}
		}
		return -1;
	}
	
	//checkout selected items
	public String checkout() {
		ProductServiceImpl productServiceImpl = new ProductServiceImpl();
		try {
			List<ProductDto> productList = new ArrayList<ProductDto>();
			List<ItemDto> itemDtosList = this.itemDtos;
			for(ItemDto list : itemDtosList) {
				productList.add(list.getProductDto());				
			}
			this.productDtos.setProductList(productList);
			this.productResponse = productServiceImpl.checkoutItems(productDtos);
		} catch (Exception e) {
			this.productResponse.setStatus("Failed");
		}
		return "status?faces-redirect=true";
	}


	public List<ItemDto> getItemDtos() {
		return itemDtos;
	}

	public void setItemDtos(List<ItemDto> itemDtos) {
		this.itemDtos = itemDtos;
	}

	public ProductResponse getProductResponse() {
		return productResponse;
	}

	public void setProductResponse(ProductResponse productResponse) {
		this.productResponse = productResponse;
	}

	public ProductRequest getProductDtos() {
		return productDtos;
	}

	public void setProductDtos(ProductRequest productDtos) {
		this.productDtos = productDtos;
	}

}