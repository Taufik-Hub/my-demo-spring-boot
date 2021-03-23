package com.spring.oauth.crud.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.oauth.crud.entity.DealerAssignCriteria;
import com.spring.oauth.crud.entity.DealerAssignSource;
import com.spring.oauth.crud.entity.Users;
import com.spring.oauth.crud.service.IUsersService;

@RestController
public class DataController {

	@Autowired
	private IUsersService userService;
	
	@GetMapping("/hello")
	public String firstPage() {
		return "Hello World";
	}

	@GetMapping("/**")
	public String falloutPage() {
		return "you Reached at correct place";
	}

	@GetMapping("/users")	
	public List<Users> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@GetMapping("/userss")	
	public void saveUser(@PathParam ("value") int value){
		 userService.saveNewUser(value);
	}
	
	@PostMapping("/readDealer")
	public DealerAssignCriteria getCriteria(@RequestBody DealerAssignCriteria criteria) {
		System.out.println(criteria);
		return criteria;
		
	}
	
	@GetMapping("/readDealer")
	public DealerAssignCriteria getCriteria() {
		DealerAssignCriteria dealerCriteria = new DealerAssignCriteria();

		Long orderId = 1L;
		String orderTypeCode = "FO";
		String internalDealerCode = "12345";
		String phhDealerCode = "445566";
		Integer makeCode = 13;
		String productClass = "LT";
		String isoCountryCode = "US";
		String make = "BMW";

		dealerCriteria.setOrderId(orderId)//
				.setOrderTypeCode(orderTypeCode)//
				.setInternalDealerCode(internalDealerCode)//
				.setPhhDealerCode(phhDealerCode)//
				.setMakeCode(makeCode)//
				.setMake(make)//
				.setProductClass(productClass)//
				.setIsoCountryCode(isoCountryCode)//
				.setDoeIndicator(true)//
				.setoFlag(false).setDealerAssignSource(DealerAssignSource.DEAQ);//
		
		return dealerCriteria;
	}
	
}
