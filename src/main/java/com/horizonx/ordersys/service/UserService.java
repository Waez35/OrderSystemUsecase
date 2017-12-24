package com.horizonx.ordersys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horizonx.ordersys.model.User;
import com.horizonx.ordersys.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository usrRepo;

	public void addEmp(User usr) {		
		usrRepo.save(usr);		
	}

	public List<User> getAllUsers() {
		List<User> l = new ArrayList<User>();
		usrRepo.findAll().forEach(l::add);
		return l;
	}

}
