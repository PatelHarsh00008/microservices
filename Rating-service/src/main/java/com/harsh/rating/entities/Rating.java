package com.harsh.rating.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Ratings")
public class Rating {
	@Id
	private String ratingId;
	private String hotelId;
	private String userId;
	private int rating;
	private String feedback;

}
