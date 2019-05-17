package com.meek.donald.github;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meek.donald.github.model.UserModel;
import com.meek.donald.github.model.RepositoryModel;

@Service
public class GitHubServiceImpl implements GitHubService{

	private String gitHubApiUri =  "https://api.github.com";
	
	@Override
	public List<UserModel> getFollowersById(String userid)
			throws JsonParseException, IOException {
		ResponseEntity<String> gitHubResponse;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> requestEntity = 
				new HttpEntity<>(null, headers);
		gitHubResponse = restTemplate.exchange(
				gitHubApiUri + "/users/{username}/followers", 
				HttpMethod.GET,
				requestEntity, 
				String.class,
				userid);
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		@SuppressWarnings("unchecked")
		List<UserModel> followerModel = (List<UserModel>) objMapper.readValue(
				gitHubResponse.getBody(),
				new TypeReference<List<UserModel>>() {});
		return followerModel;
	}
	@Override
	public List<RepositoryModel> getRepositoriesById(String userid) 
			throws JsonParseException, IOException {
		ResponseEntity<String> gitHubResponse;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> requestEntity = 
				new HttpEntity<>(null, headers);
		gitHubResponse = restTemplate.exchange(
				gitHubApiUri + "/users/{username}/subscriptions",
				HttpMethod.GET,
				requestEntity,
				String.class,
				userid);
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		@SuppressWarnings("unchecked")
		List<RepositoryModel> repositories = 
				(List<RepositoryModel>) objMapper.readValue(
						gitHubResponse.getBody(),
						new TypeReference<List<RepositoryModel>>() {});
		return repositories;
	}
	@Override
	public List<UserModel> getStargazersByUser(String userid, String repo) 
			throws JsonParseException, IOException {
		ResponseEntity<String> gitHubResponse;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> requestEntity = 
				new HttpEntity<>(null, headers);
		gitHubResponse = restTemplate.exchange(
				gitHubApiUri + "/repos/{owner}/{repo}/stargazers", 
				HttpMethod.GET,
				requestEntity, 
				String.class,
				userid,
				repo);
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		@SuppressWarnings("unchecked")
		List<UserModel> userModel = (List<UserModel>) objMapper.readValue(
				gitHubResponse.getBody(),
				new TypeReference<List<UserModel>>() {});
		return userModel;
	}
	
}
