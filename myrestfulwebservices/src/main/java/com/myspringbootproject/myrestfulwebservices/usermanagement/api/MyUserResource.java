package com.myspringbootproject.myrestfulwebservices.usermanagement.api;

import java.net.URI;
//import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class MyUserResource {
	
	@Autowired
	private MyUsersDao myuserDao;
	
	@GetMapping(path="/myUserResource/getAllUsers")
	public List<MyUsers> findAllusers(){
		
		return myuserDao.findAll();
	}
	
	
	@GetMapping("/myUserResource/getOneUser/{id}")
	public MyUsers findOneUser(@PathVariable int id){
		MyUsers myuser = myuserDao.findOne(id);
		if(myuser==null)
				throw new UserNotFoundException("id-"+id);
		return myuser;
	}
	
	
	@PostMapping("/myUserResource/createUser")
	public ResponseEntity<Object> createUser(@Valid @RequestBody MyUsers myuser){
		MyUsers myNewuser = myuserDao.save(myuser);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(myNewuser.getUserId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	
	@DeleteMapping("/myUserResource/deleteAllUsers")
	public void deleteAllUser(){
		
     myuserDao.delete();
	}

	
}
