package com.javaproject.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.javaproject.models.Park;
import com.javaproject.models.Review;

@Repository
public interface ReviewRepository extends CrudRepository <Review, Long> {
	
	List <Review> findByPark(Park park);
	

}
