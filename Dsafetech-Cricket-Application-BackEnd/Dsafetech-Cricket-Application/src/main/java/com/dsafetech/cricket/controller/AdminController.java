package com.dsafetech.cricket.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsafetech.cricket.entity.AdminRegistraion;
import com.dsafetech.cricket.entity.AdminValidationRequest;
import com.dsafetech.cricket.entity.TeamEntity;
import com.dsafetech.cricket.entity.UserRegister;
import com.dsafetech.cricket.entity.UserResetPasswordRequest;
import com.dsafetech.cricket.entity.UserValidationRequest;
import com.dsafetech.cricket.repo.Adminrepo;
import com.dsafetech.cricket.repo.TeamRepo;
import com.dsafetech.cricket.service.AdminService;
import com.dsafetech.cricket.serviceimp.TeamServiceImpl;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/dsafetech")
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private TeamRepo teamRepo;
	@Autowired
	private Adminrepo adminrepo;
	@Autowired
	private TeamServiceImpl teamserviceimpl;

	@ApiOperation(value = "This API is used to  register new admin", response = UserRegister.class)
	@PostMapping("/reg/admin")
	public ResponseEntity<AdminRegistraion> saveadmin(@RequestBody AdminRegistraion adminRegistraion) {
		return new ResponseEntity<AdminRegistraion>(adminService.saveadmin(adminRegistraion), HttpStatus.ACCEPTED);

	}
	@ApiOperation(value = "this API is used to  validate admin", response = UserValidationRequest.class, tags = "/admin/Validation")
	@PostMapping("/admin/Validation")
	public String validateAdmin(@RequestBody AdminValidationRequest request1) {
		String username = request1.getusername();
		String password = request1.getPassword();
		if (adminService.isValidadmin(username, password)) {
			return "Valid user";
		} else {
			return "Invalid user" + "Click on forgot password to change password";
		}
	}
	@ApiOperation(value = "This API is used to  get match from database by date")
	@GetMapping("/{date}")
	public List<TeamEntity> getteams(@PathVariable String date) {
		return teamserviceimpl.getteams(date);

	}

	@ApiOperation(value = "This API is used to reset admin password")
	@PostMapping("/admin/forgotPassword")
	public ResponseEntity<UserResetPasswordRequest> resetUserPassword(@RequestBody UserResetPasswordRequest request) {
		String username = request.getUsername();
		String gmailId = request.getGmailId();
		String newPassword = request.getNewPassword();
		AdminRegistraion adminRegistraion = adminrepo.findByUsernameAndPassword(username, newPassword);
		if (adminRegistraion != null) {
			adminRegistraion.setPassword(newPassword);
			adminRegistraion.setResetToken(null);
			adminrepo.save(adminRegistraion);

			return new ResponseEntity<UserResetPasswordRequest>(HttpStatus.OK);
		} else {
			return new ResponseEntity<UserResetPasswordRequest>(HttpStatus.BAD_REQUEST);
		}
}
	@ApiOperation(value = "This API is used to  get  all match from database history")
	@GetMapping("/getAllMatches")
	public List<TeamEntity> getAllMatches(TeamEntity teamEntity){
		return teamserviceimpl.getAllMatches();
	}
	@ApiOperation(value = "This API is used to  update team fields from database by date")
	 @PutMapping("/user/update/	{contactnumber}")
	    public ResponseEntity<TeamEntity> updateTeamEntity(@PathVariable long contactnumber, @RequestBody TeamEntity updatedTeamEntity) {
		Optional<TeamEntity> updatedTeam = teamserviceimpl.updateTeamFields(contactnumber, updatedTeamEntity);
        if (updatedTeam.isPresent()) {
            return ResponseEntity.ok(updatedTeam.get());
        } else {
            return ResponseEntity.notFound().build();
        }
	    }
}
