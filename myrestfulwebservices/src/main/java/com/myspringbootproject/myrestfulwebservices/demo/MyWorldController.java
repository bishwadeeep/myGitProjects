package com.myspringbootproject.myrestfulwebservices.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyWorldController {
	//Using Request Mapping Annotation, requires method parameter
	@RequestMapping(method = RequestMethod.GET, path ="/my-world")
	public String myWorld() {		
		return "welcome to my world using @RestController !!!";
	}
	
	//Using Get Mapping Annotation, don't requires method parameter, work for GET type request
	@GetMapping(path ="/my-NewWorld")
	public String myNewWorld() {		
		return "welcome to my New world using @GetMapping!!!";
	}
	
	//Returning a bean that converts the bean into json in the end
	@GetMapping(path ="/my-world-bean")
	public MyWorldBean myWorldBean() {		
		return new MyWorldBean("Welcome to my world bean");
	}
	
	//Using a path variable to be mapped with the name
	@GetMapping(path ="/my-world/path-variable/{name}")
	public MyWorldBean myWorldBeanPath(@PathVariable String name) {		
		return new MyWorldBean("Welcome to my world bean " +name);
	}

}
