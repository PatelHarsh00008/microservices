package com.harsh.user.service.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harsh.user.service.entities.User;
import com.harsh.user.service.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.saveUser(user));
	}
	
	int retryCount = 1;
	
	@GetMapping("/{userId}")
	//@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback") // circuit breaker: circuit open/closed/half-closed
	//@Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback") //retry: to send request to rating service when it is down to retry
	@RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> getSingleUserUser(@PathVariable String userId){
		System.out.println("Retry Count ["+ retryCount +  "]");
		retryCount++;
		return ResponseEntity.ok(this.userService.getUser(userId));
	}
	
	// creating fallback method for circuit breaker
	public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
		System.out.println("Fallback is executed because service is down: " + ex.getMessage());
		User user = new User("123", "dummy", "dummy@gmail.com", "This user is created because service is down", null);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}

}
