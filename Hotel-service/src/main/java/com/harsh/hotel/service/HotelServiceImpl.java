package com.harsh.hotel.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harsh.hotel.entities.Hotel;
import com.harsh.hotel.exceptions.ResourceNotFoundException;
import com.harsh.hotel.repositories.HotelRepository;

@Service
public class HotelServiceImpl implements HotelService{

	@Autowired
	private HotelRepository hotelRepository;
	
	@Override
	public Hotel createHotel(Hotel hotel) {
		String id = UUID.randomUUID().toString();
		hotel.setId(id);
		return this.hotelRepository.save(hotel);
	}

	@Override
	public List<Hotel> getAllHotels() {
		// TODO Auto-generated method stub
		return this.hotelRepository.findAll();
	}

	@Override
	public Hotel getHotel(String id) {
		// TODO Auto-generated method stub
		return this.hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found with given id in the server!!"));
	}

}
