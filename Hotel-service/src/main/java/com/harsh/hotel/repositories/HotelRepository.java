package com.harsh.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harsh.hotel.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, String>{}
