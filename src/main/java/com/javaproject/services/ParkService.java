package com.javaproject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaproject.models.Park;
import com.javaproject.repositories.ParkRepository;

@Service
public class ParkService {

	@Autowired
	private ParkRepository parkRepo;
	
	public List <Park> findAll(){
		return parkRepo.findAll();
	}
	
	public Park savePark (Park park) {
		return parkRepo.save(park);
	}
	
	public void deletePark(Long id) {
		parkRepo.deleteById(id);
	}
	
	public Park getPark(Long id) {
		Optional<Park> target = parkRepo.findById(id);
		if(target.isPresent()) {
			return target.get();
		} else {
			return null;
		}
	}
	
	public List <Object[]> findAllAvg (){
		return parkRepo.findAllAvg();
	}
	
	public List <Object[]> findAvgCity (String city){
		return parkRepo.findAvgCity(city);
	}
	
	public List <Object[]> findAvgName (String name){
		return parkRepo.findAvgName(name);
	}
	
	public List <Object[]> findAvgCategory (Long id){
		return parkRepo.findAvgCategory(id);
	}
	
	public List <Object[]> findAvgCategoryCity (Long id, String city){
		return parkRepo.findAvgCategoryCity(id, city);
	}
	
	public List <Object[]> parksByUserId (Long id){
		return parkRepo.parksByUserId(id);
	}
	
	public List <Object[]> parksApprovedByUserId (Long id){
		return parkRepo.parksApprovedByUserId(id);
	}
	
	public List <Object[]> parksFavoritedByUserId (Long id){
		return parkRepo.parksFavoritedByUserId(id);
	}
	
	public Object[] parksByParkId (Long id){
		return parkRepo.parksByParkId(id);
	}
	
	public List <Object[]> findTop (){
		return parkRepo.findTop();
	}
	
//	public List <Park> findByName(String name){
//		return parkRepo.findByNameContaining(name);
//	}
//	
//	public List <Park> findByCategories(Category category){
//		return parkRepo.findByCategoriesContaining(category);
//	}
//	
//	public List <Park> findByCategoriesAndCity(Category category, String city){
//		return parkRepo.findByCategoriesAndCityContaining(category, city);
//	}
//	
//	public List <Park> findTop4(){
//		return parkRepo.findTop4ByOrderByReviewsDesc();
//	}
//	
	
}
