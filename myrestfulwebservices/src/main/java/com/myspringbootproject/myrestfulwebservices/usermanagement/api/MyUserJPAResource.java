package com.myspringbootproject.myrestfulwebservices.usermanagement.api;

import java.net.URI;
//import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class MyUserJPAResource {
	
	@Autowired
	private MyUsersDao myuserDao;
	
	@Autowired
	private MyUserDataRepository myuserRepository;
	
	@GetMapping(path="/myUserResource/jpa/getAllUsers")
	public List<MyUsers> findAllusers(){
		
		return myuserRepository.findAll();
	}
	
	
	@GetMapping("/myUserResource/jpa/getOneUser/{id}")
	public Resource<MyUsers> findOneUser(@PathVariable int id){
		Optional<MyUsers> myuser = myuserRepository.findById(id);
		if(!myuser.isPresent())
				throw new UserNotFoundException("id-"+id);
		
		Resource<MyUsers> resource = new Resource<MyUsers>(myuser.get());
		
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).findOneUser(id));
		
		resource.add(linkTo.withRel("one-user"));

		return resource;
	}
	
	
	@PostMapping("/myUserResource/jpa/createUser")
	public ResponseEntity<Object> createUser(@Valid @RequestBody MyUsers myuser){
		MyUsers myNewuser = myuserDao.save(myuser);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(myNewuser.getUserId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	
	@DeleteMapping("/myUserResource/jpa/deleteAllUsers")
	public void deleteAllUser(){
		
     myuserDao.delete();
	}

	
}
