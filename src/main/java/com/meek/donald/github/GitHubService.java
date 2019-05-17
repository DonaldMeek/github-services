package com.meek.donald.github;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.meek.donald.github.model.GitHubFollowerModel;
import com.meek.donald.github.model.RepositoryModel;
import com.meek.donald.github.model.UserModel;

public interface GitHubService {

	List<UserModel> getStargazersByUser(String userid, String repo) 
			throws JsonParseException, JsonMappingException, IOException;

	List<RepositoryModel> getRepositoriesById(String userid) 
			throws JsonParseException, JsonMappingException, IOException;
	
	List<UserModel> getFollowersById(String userid)
			throws JsonParseException, JsonMappingException, IOException;

}
