package com.meek.donald.github.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class UserModel implements Serializable{

	private static final long serialVersionUID = 8760746823853811105L;

	private String login;
	private int id;
	private String url;
	private String type;
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
