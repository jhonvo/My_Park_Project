package com.javaproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaproject.models.Park;
import com.javaproject.models.Review;
import com.javaproject.repositories.ReviewRepository;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository revRepo;
	
	public List <Review> findByPark(Park park){
		return revRepo.findByPark(park);
	}
	
	public Review saveReview (Review rev) {
		return revRepo.save(rev);
	}

}
