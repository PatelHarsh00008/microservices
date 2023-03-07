package com.harsh.rating.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.harsh.rating.entities.Rating;
import com.harsh.rating.exceptions.ResourceNotFoundException;
import com.harsh.rating.repositories.RatingRepository;

@Service
public class RatingServiceImpl implements RatingService{

	@Autowired
	private RatingRepository ratingRepository;
	
	
	@Override
	public Rating createRating(Rating rating) {
		String id = UUID.randomUUID().toString();
		rating.setRatingId(id);
		return this.ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getAllRatings() {
		// TODO Auto-generated method stub
		return this.ratingRepository.findAll();
	}

	@Override
	public Rating getRating(String id) {
		// TODO Auto-generated method stub
		return this.ratingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("resource not found with this id in the server!!"));
	}

	@Override
	public List<Rating> findByHotelId(String hotelId) {
		
		return this.ratingRepository.findByHotelId(hotelId);
	}

	@Override
	public List<Rating> findByUserId(String userId) {
		// TODO Auto-generated method stub
		return this.ratingRepository.findByUserId(userId);
	}

}
