package com.myspringbootproject.myrestfulwebservices.usermanagement.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MyUsersDao {
	
	private static List<MyUsers> users = new ArrayList<>();
	private int countUserId = 3;
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");;
	
	static {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			users.add(new MyUsers("Bishwadeep",1,"Delhi",sdf.parse("06-09-1990")));
			users.add(new MyUsers("Ranu",2,"Patna", sdf.parse("08-10-1994")));
			users.add(new MyUsers("Neha",3,"Agra",sdf.parse("25-01-1993")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public List<MyUsers> findAll() {
		
		return users;
	}
	
	public MyUsers save(MyUsers user) {
		if(user.getUserId()==null) {
			user.setUserId(++countUserId);
		}
		if(user.getBirthdate()==null) {
			try {
				user.setBirthdate(sdf.parse("06-09-1990"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		users.add(user);
	    return user;
	}
	
	public void delete() {		
		users.clear();		
	}

	public MyUsers findOne(int id) {
		for (MyUsers user : users) {
			if(user.getUserId() == id) {
				return user;
			}		
		}
		return null;
}
}
