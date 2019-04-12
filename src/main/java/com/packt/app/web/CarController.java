package com.packt.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.packt.app.user.Car;
import com.packt.app.user.CarRepository;

@RestController
public class CarController {
	@Autowired
	private CarRepository repository;
	
	@RequestMapping("/cars")
	public Iterable<Car> getCars() {
		return repository.findAll();
	}
}
