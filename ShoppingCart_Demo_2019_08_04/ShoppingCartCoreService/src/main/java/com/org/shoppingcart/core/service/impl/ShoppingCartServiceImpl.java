package com.org.shoppingcart.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.org.shoppingcart.core.bean.DataDto;
import com.org.shoppingcart.core.bean.ProductDto;
import com.org.shoppingcart.core.entities.OrderDetails;
import com.org.shoppingcart.core.entities.Products;
import com.org.shoppingcart.core.repository.OrderDetailsRepository;
import com.org.shoppingcart.core.repository.ProductsRepository;
import com.org.shoppingcart.core.request.ProductRequest;
import com.org.shoppingcart.core.response.ProductResponse;
import com.org.shoppingcart.core.service.ShoppingCartService;

@Service
@Transactional(rollbackFor = Exception.class)
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private ProductsRepository productsRepository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	public ShoppingCartServiceImpl(ProductsRepository productsRepository,
			OrderDetailsRepository orderDetailsRepository) {
		super();
		this.productsRepository = productsRepository;
		this.orderDetailsRepository = orderDetailsRepository;
	}

	@Override
	public ProductResponse findAllProducts() {
		ProductResponse response = new ProductResponse();
		try {
			List<Products> productList = productsRepository.findAll();
			List<ProductDto> productDtoList = new ArrayList<>();
			DataDto dataDto = new DataDto();
			for (Products product : productList) {
				ProductDto productDto = new ProductDto();
				productDto.setId(product.getId());
				productDto.setName(product.getName());
				productDto.setDescription(product.getDescription());
				productDto.setPrice(product.getPrice());
				productDto.setQuantity(product.getQuantity());
				productDtoList.add(productDto);
			}
			dataDto.setProductList(productDtoList);
			response.setDataDto(dataDto);
			response.setStatus("Success");
			response.setStatusCode(200);

		} catch (Exception e) {
			response.setStatus("Failed");
			response.setStatusCode(0);
		}
		return response;
	}

	@Override
	public boolean saveAll(ProductRequest request) {
		try {
			List<ProductDto> productDetailsList = request.getProductList();
			List<OrderDetails> orderDetailsList = new ArrayList<>();
			if (!productDetailsList.isEmpty()) {
				for (ProductDto product : productDetailsList) {
					OrderDetails order = new OrderDetails();
					Optional<Products> poductById = productsRepository.findById(product.getId());
					if (poductById.isPresent()) {
						order.setProducts(poductById.get());
						order.setAmount(product.getPrice());
						order.setPrice(product.getPrice());
						order.setName(product.getName());
						order.setQuantity(product.getQuantity());
						order.setDescription(product.getDescription());
						order.setStatus(true);
						orderDetailsList.add(order);
					} else {
						System.out.println("WARNING : Product Details Not Found: PID = " + product.getId());
					}
				}
				orderDetailsRepository.saveAll(orderDetailsList);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}