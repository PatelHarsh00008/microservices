package com.harsh.user.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harsh.user.service.entities.User;

public interface UserRepository extends JpaRepository<User, String>{

}
