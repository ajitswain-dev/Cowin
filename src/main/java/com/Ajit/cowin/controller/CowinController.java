package com.Ajit.cowin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Ajit.cowin.model.Centers;
import com.Ajit.cowin.service.CowinService;

@RestController
@RequestMapping
public class CowinController {
	
	@Autowired
	CowinService service;
	
	@GetMapping("/cowin")
	public String greeting(Model model) {
		model.addAttribute("name", "Ajit");
		return "Home";
	}
	
	@GetMapping("/calendarByDistrict")
	public List<Centers> getCenters(){
		return service.getcenter();		
	}

}
