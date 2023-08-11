package com.dsafetech.cricket.service;

import com.dsafetech.cricket.entity.AdminRegistraion;
import com.dsafetech.cricket.entity.TeamEntity;
import com.dsafetech.cricket.entity.UserRegister;

public interface AdminService {
	public AdminRegistraion saveadmin(AdminRegistraion adminRegistraion);
	 boolean isValidadmin(String username, String password);

}
