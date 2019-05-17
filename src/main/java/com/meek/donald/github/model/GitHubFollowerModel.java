package com.meek.donald.github.model;

import java.io.Serializable;
import java.util.List;

public class GitHubFollowerModel implements Serializable{

	private static final long serialVersionUID = 3130541832324001243L;

	private List<UserModel> users;

	public List<UserModel> getUsers() {
		return users;
	}

	public void setUsers(List<UserModel> followers) {
		this.users = followers;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public UserModel getUserAtIndex(int index) {
		if (users == null) return null;
		if (!users.isEmpty() && users.size() > index) 
		{
			return users.get(index);
		}
		return null;
	}
}	
