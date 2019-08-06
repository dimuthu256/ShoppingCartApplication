package com.org.shoppingcart.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.org.shoppingcart.core.bean.DataDto;
import com.org.shoppingcart.core.bean.ItemDto;
import com.org.shoppingcart.core.bean.ProductDto;
import com.org.shoppingcart.core.entities.OrderDetails;
import com.org.shoppingcart.core.entities.Products;
import com.org.shoppingcart.core.exception.ApplicationException;
import com.org.shoppingcart.core.repository.OrderDetailsRepository;
import com.org.shoppingcart.core.repository.ProductsRepository;
import com.org.shoppingcart.core.request.ProductRequest;
import com.org.shoppingcart.core.response.ProductResponse;
import com.org.shoppingcart.core.service.ShoppingCartService;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ShoppingCartServiceImpl implements ShoppingCartService {

	private Logger logger;

	@Autowired
	private ProductsRepository productsRepository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	public ShoppingCartServiceImpl(ProductsRepository productsRepository,
			OrderDetailsRepository orderDetailsRepository) {
		super();
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.productsRepository = productsRepository;
		this.orderDetailsRepository = orderDetailsRepository;
	}

	@Override
	// @Cacheable(cacheNames = "findAllProducts")
	public ProductResponse findAllProducts() throws ApplicationException {
		try {
			logger.info("Begin method find all products");
			DataDto dataDto = new DataDto();
			dataDto.setProductList(addProductDetails(this.productsRepository.findAll()));
			return ProductResponse.builder().dataDto(dataDto).status("Success").statusCode(200).build();
		} catch (Exception e) {
			throw new ApplicationException("Error in find all products");
		}
	}

	@Override
	public boolean saveAll(ProductRequest request) throws ApplicationException {
		try {
			logger.info("Begin method save all items : {}", request.toString());
			List<ItemDto> itemDetailsList = request.getItemList();
			List<OrderDetails> orderDetailsList = new ArrayList<>();
			if (!itemDetailsList.isEmpty()) {
				for (ItemDto item : itemDetailsList) {
					Optional<Products> poductById = this.productsRepository.findById(item.getProductDto().getId());
					if (poductById.isPresent()) {
						double currentProductQuantity = poductById.get().getQuantity();
						updateProductQuentity(poductById.get(), item);
						orderDetailsList.add(addOrderDetails(poductById.get(), item, currentProductQuantity));
					} else {
						logger.error("Error : Product Id Not Found : PID = " + item.getProductDto().getId());
						throw new ApplicationException("Product Details Not Found");
					}
				}
			}
			this.orderDetailsRepository.saveAll(orderDetailsList);
			logger.info("End method save all items");
			return true;
		} catch (Exception e) {
			throw new ApplicationException("Product details adding failed");
		}
	}

	private List<ProductDto> addProductDetails(List<Products> productList) {
		logger.info("Begin method add product details");
		List<ProductDto> productDtoList = new ArrayList<>();
		for (Products product : productList) {
			productDtoList.add(ProductDto.builder().id(product.getId()).name(product.getName())
					.description(product.getDescription()).price(product.getPrice()).quantity(product.getQuantity())
					.build());
		}
		logger.info("End Method add product details : {}", productDtoList.toString());
		return productDtoList;
	}

	private OrderDetails addOrderDetails(Products productDetails, ItemDto item, double currentProductQuantity) {
		logger.info("Begin method add order details");
		// if item quantity is lager than product quantity , item quantity should be
		// equal product quantity.and item amount should be calculated with the current
		// quantity
		double newItemsAmount = 0;
		if (item.getQuantity() > currentProductQuantity) {
			newItemsAmount = item.getTotalAmount() / item.getQuantity() * currentProductQuantity;
			item.setQuantity((int) currentProductQuantity);
		} else {
			newItemsAmount = item.getTotalAmount();
		}
		return OrderDetails.builder().name(productDetails.getName()).products(productDetails).amount(newItemsAmount)
				.quantity(item.getQuantity()).description(productDetails.getDescription()).status(true).build();
	}

	private void updateProductQuentity(Products product, ItemDto item) throws ApplicationException {
		try {
			logger.info("Begin method update product quentity");
			// check whether product is still available with the selected quantity
			if (product.getQuantity() >= item.getQuantity()) {
				product.setQuantity(product.getQuantity() - item.getQuantity());
				productsRepository.save(product);
				logger.info("End method update product quentity");
			} else {
				product.setQuantity(0);
				productsRepository.save(product);
			}
		} catch (Exception e) {
			throw new ApplicationException("Product details adding failed");
		}
	}
}