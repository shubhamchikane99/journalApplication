package net.google.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.service.UserAccessRoleService;

@RestController
@RequestMapping("v1/user-access-role")
public class UserAccessRoleController {

	@Autowired
	private UserAccessRoleService userAccessRoleService;

	@GetMapping("/by-id")
	public ServiceResponse getUserAccessRoleById(@RequestParam("userId") String userId) {

		return ServiceResponse.asSuccess(userAccessRoleService.getUserAccessRoleById(userId));

	}
}
