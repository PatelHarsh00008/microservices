package com.harsh.rating.service;

import java.util.List;

import com.harsh.rating.entities.Rating;

public interface RatingService {

	Rating createRating(Rating rating);
	List<Rating> getAllRatings();
	Rating getRating(String id);
	List<Rating> findByHotelId(String hotelId);
	List<Rating> findByUserId(String userId);
}
