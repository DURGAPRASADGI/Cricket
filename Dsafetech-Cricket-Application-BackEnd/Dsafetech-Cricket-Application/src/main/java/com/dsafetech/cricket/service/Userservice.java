package com.dsafetech.cricket.service;


import com.dsafetech.cricket.entity.UserRegister;

public interface Userservice {
	public UserRegister save1(UserRegister userRegister);
	  boolean isValidUser(String username, String password);
}
