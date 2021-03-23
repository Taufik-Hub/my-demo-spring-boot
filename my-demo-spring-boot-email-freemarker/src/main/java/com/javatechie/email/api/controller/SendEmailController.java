package com.javatechie.email.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javatechie.email.api.dto.BikeInfo;
import com.javatechie.email.api.dto.MailRequest;
import com.javatechie.email.api.dto.MailResponse;
import com.javatechie.email.api.service.EmailService;

@RestController
public class SendEmailController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailController.class);

//	@Value("${ahemad.taufik06@gmail.com}")
//	private String fromEmailId;
	
	@Autowired
	private EmailService service;

	@PostMapping("/sendingEmail")
	public MailResponse sendEmail(@RequestBody MailRequest request) {
		LOGGER.info("SendEmailController sendEmail MailRequest - {}", request);
		// How to access MAP from Free Marker Template
		// Handle null value - ${(object.attribute)!}   ${(object.attribute)!"default text"}
		BikeInfo bikeInfo = getBikeInfoService();
	
		Map<String, Object> model = new HashMap<>();
		model.put("bikeinfo", bikeInfo);// ${bikeinfo.year}==2020  AND ${bikeinfo.make}==FORD AND ${bikeinfo.model}==FTXL 
		model.put("Name", request.getName());//${Name}
		model.put("location", "Bangalore,India");//${location}==Bangalore,India
		

		MailResponse mailResponse = service.sendEmail(request, model);
		return mailResponse;
	}
	
	private BikeInfo getBikeInfoService(){
		return new BikeInfo("2020", "FORD", "FTXL");
	}

	@GetMapping("/ping")
	public MailRequest check() {
		return new MailRequest();
//		return "Service is UP...!";

	}

}