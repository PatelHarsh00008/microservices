package com.harsh.apigateway.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harsh.apigateway.controllers.model.AuthResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@GetMapping("/login")
	public ResponseEntity<AuthResponse> login(
			@RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client,
			@AuthenticationPrincipal  OidcUser user,
			Model model) {
		logger.info("user email id: {}" + user.getEmail());
		
		// creating auth response object
		AuthResponse authResponse = new AuthResponse();
		
		// setting email to authResponse
		authResponse.setUserId(user.getEmail());
		
		// setting token to authResponse
		authResponse.setAccessToken(client.getAccessToken().getTokenValue());
		
		authResponse.setRefreshToken(client.getRefreshToken().getTokenValue());
		
		authResponse.setExpireAt(client.getAccessToken().getExpiresAt().getEpochSecond());
		
		// converted collection of granted authority to collection of string
		List<String> authorities = user.getAuthorities().stream().map(grantedAuthorities -> {
			return grantedAuthorities.getAuthority();	
		}).collect(Collectors.toList());
		authResponse.setAuthorities(authorities);
		
		return ResponseEntity.ok(authResponse);
		
	}
}
