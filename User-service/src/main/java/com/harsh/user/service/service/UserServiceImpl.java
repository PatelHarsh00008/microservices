package com.harsh.user.service.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.harsh.user.service.entities.Hotel;
import com.harsh.user.service.entities.Rating;
import com.harsh.user.service.entities.User;
import com.harsh.user.service.exceptions.ResourceNotFoundException;
import com.harsh.user.service.externals.service.HotelService;
import com.harsh.user.service.repositories.UserRepository;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	// to send request to other server/project
	@Autowired
	private RestTemplate restTemplate;
	
	// to get data from HOTEL-SERVICE using fiegn client 
	@Autowired
	private HotelService hotelService;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public User saveUser(User user) {
		String randomUserIdString = UUID.randomUUID().toString();
		user.setUserId(randomUserIdString);
		return this.userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return this.userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Resource not found with given id on server!!"));
		// get ratings from other server using rest template
		Rating[] ratings = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+ user.getUserId(), Rating[].class);
		
		List<Rating> ratingList = Arrays.asList(ratings).stream().map(rating ->{
			// api call to hotel service to get the hotel
			// http://localhost:8082/hotels/e67cd5c2-4f92-4e00-beb1-1077a7340f39
			//ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+ rating.getHotelId(), Hotel.class);
			// forEntity.getStatusCode();
			Hotel hotel = this.hotelService.getHotel(rating.getHotelId());
			rating.setHotel(hotel);
			return rating;
			
		}).collect(Collectors.toList());
		
		 
		user.setRatings(ratingList);
		return user;
	}

	@Override
	public User updateUser(String userId, User user) {
		User u = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Resource not found with given id on server!!"));
		
		return u;
	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Resource not found with given id on server!!"));
		this.userRepository.delete(user);
		
	}

}
