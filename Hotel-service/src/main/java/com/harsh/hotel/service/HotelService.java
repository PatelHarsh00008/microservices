package com.harsh.hotel.service;

import java.util.List;

import com.harsh.hotel.entities.Hotel;

public interface HotelService {

	Hotel createHotel(Hotel hotel);
	List<Hotel> getAllHotels();
	Hotel getHotel(String id);
}
