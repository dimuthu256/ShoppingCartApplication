package com.org.shoppingcart.response;

import java.io.Serializable;

import com.org.shoppingcart.dto.DataDto;

import lombok.Data;

@Data
public class ProductResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private DataDto dataDto;
	private String status;
	private int statusCode;

}
