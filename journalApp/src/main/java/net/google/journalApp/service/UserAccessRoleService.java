package net.google.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.google.journalApp.entity.UserAccessRole;
import net.google.journalApp.repository.UserAccessRoleRepository;

@Service
public class UserAccessRoleService {

	@Autowired
	private UserAccessRoleRepository userAccessRoleRepository;

	public UserAccessRole getUserAccessRoleById(String userId) {
		// get access role by user id

		return userAccessRoleRepository.getUserAccessRoleById(userId);
	}
}
