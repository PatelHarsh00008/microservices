package com.harsh.rating.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harsh.rating.entities.Rating;

public interface RatingRepository extends JpaRepository<Rating, String>{

	List<Rating> findByHotelId(String hotelId);
	List<Rating> findByUserId(String userId);
}
