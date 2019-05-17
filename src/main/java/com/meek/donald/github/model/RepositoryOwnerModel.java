package com.meek.donald.github.model;

import java.io.Serializable;
import java.util.List;

public class RepositoryOwnerModel implements Serializable {

	private static final long serialVersionUID = 7847072091940650870L;
	private int id;
	private String login;
	private String url;
	private String type;
	private List<String> repositories;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<String> getRepositories() {
		return repositories;
	}
	public void setRepositories(List<String> repositories) {
		this.repositories = repositories;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
