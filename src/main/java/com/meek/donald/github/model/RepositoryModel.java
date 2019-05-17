package com.meek.donald.github.model;

import java.io.Serializable;

public class RepositoryModel implements Serializable{

	private static final long serialVersionUID = -1222624287586579514L;
	private String name;
	private int id;
	private RepositoryOwnerModel owner;
	private String language;
	private int size;
	private String archived;
	private String homepage;
	private boolean fork;
	private String url;
	private String description;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public RepositoryOwnerModel getOwner() {
		return owner;
	}
	public void setOwner(RepositoryOwnerModel owner) {
		this.owner = owner;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getArchived() {
		return archived;
	}
	public void setArchived(String archived) {
		this.archived = archived;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public boolean isFork() {
		return fork;
	}
	public void setFork(boolean fork) {
		this.fork = fork;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
