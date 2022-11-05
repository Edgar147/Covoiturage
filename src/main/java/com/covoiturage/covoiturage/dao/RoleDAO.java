package com.covoiturage.covoiturage.dao;

import com.covoiturage.covoiturage.entity.Role;


public interface RoleDAO {

	public Role findRoleByName(String theRoleName);
	
}
