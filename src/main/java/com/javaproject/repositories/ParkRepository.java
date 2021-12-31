package com.javaproject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javaproject.models.Park;

@Repository
public interface ParkRepository extends CrudRepository <Park, Long> {
	
	List <Park> findAll();
	
	List <Park> findByStatus(String Status);
	
	@Query (value="SELECT p.id, p.name, p.city, p.location, p.status, p.picture, count(r.id), AVG(r.rating), p.user_id, u.firstname, u.lastname FROM parks p  LEFT JOIN reviews r ON p.id = r.park_id LEFT JOIN users u ON p.user_id = u.id WHERE p.status = 'approved' GROUP BY p.id ORDER BY p.created_at DESC", nativeQuery=true)
	List <Object[]> findAllAvg ();
	
	@Query (value="SELECT p.id, p.name, p.city, p.location, p.status, p.picture, count(r.id), AVG(r.rating), p.user_id, u.firstname, u.lastname FROM parks p  LEFT JOIN reviews r ON p.id = r.park_id LEFT JOIN users u ON p.user_id = u.id WHERE p.status = 'approved' AND p.city LIKE %?1% GROUP BY p.id", nativeQuery=true)
	List <Object[]> findAvgCity (String city);
	
	@Query (value="SELECT p.id, p.name, p.city, p.location, p.status, p.picture, count(r.id), AVG(r.rating), p.user_id, u.firstname, u.lastname FROM parks p  LEFT JOIN reviews r ON p.id = r.park_id LEFT JOIN users u ON p.user_id = u.id WHERE p.status = 'approved' AND p.name LIKE %?1% GROUP BY p.id", nativeQuery=true)
	List <Object[]> findAvgName (String name);
	
	@Query (value="SELECT p.id, p.name, p.city, p.location, p.status, p.picture, count(r.id), AVG(r.rating), p.user_id, u.firstname, u.lastname FROM parks p  LEFT JOIN reviews r ON p.id = r.park_id LEFT JOIN users u ON p.user_id = u.id LEFT JOIN park_categories c ON p.id = c.park_id WHERE p.status = 'approved' AND c.category_id = ?1 GROUP BY p.id", nativeQuery=true)
	List <Object[]> findAvgCategory (Long id);
	
	@Query (value="SELECT p.id, p.name, p.city, p.location, p.status, p.picture, count(r.id), AVG(r.rating), p.user_id, u.firstname, u.lastname FROM parks p  LEFT JOIN reviews r ON p.id = r.park_id LEFT JOIN users u ON p.user_id = u.id LEFT JOIN park_categories c ON p.id = c.park_id WHERE p.status = 'approved' AND c.category_id = ?1 AND p.city LIKE %?2% GROUP BY p.id", nativeQuery=true)
	List <Object[]> findAvgCategoryCity (Long id, String city);
	
	@Query (value="SELECT p.id, p.name, p.city, p.location, p.status, p.picture, count(r.id), AVG(r.rating), p.user_id, u.firstname, u.lastname FROM parks p LEFT JOIN reviews r ON r.park_id = p.id LEFT JOIN users u ON p.user_id = u.id WHERE p.status = \"approved\" GROUP BY p.id ORDER BY SUM(r.id) DESC LIMIT 3", nativeQuery=true)
	List <Object[]> findTop ();
	
	@Query (value="SELECT p.id, p.name, p.city, p.location, p.status, p.picture, count(r.id), AVG(r.rating), p.user_id FROM parks p  LEFT JOIN reviews r ON p.id = r.park_id WHERE p.user_id = ?1  GROUP BY p.id",  nativeQuery=true )
	List <Object[]> parksByUserId (Long id);
	
	@Query (value="SELECT p.id, p.name, p.city, p.location, p.status, p.picture, count(r.id), AVG(r.rating), p.user_id FROM parks p  LEFT JOIN reviews r ON p.id = r.park_id WHERE p.user_id = ?1 AND p.status = 'approved' GROUP BY p.id",  nativeQuery=true )
	List <Object[]> parksApprovedByUserId (Long id);
	
	@Query (value="SELECT p.id, p.name, p.city, p.location, p.status, p.picture, count(r.id), AVG(r.rating), p.user_id FROM parks p  LEFT JOIN reviews r ON p.id = r.park_id LEFT JOIN favorites f on p.id = f.park_id  WHERE f.user_id = ?1 GROUP BY p.id",  nativeQuery=true )
	List <Object[]> parksFavoritedByUserId (Long id);
	
	@Query (value="SELECT p.id, p.name, p.city, p.location, p.status, p.picture, count(r.id), AVG(r.rating), p.user_id, u.firstname, u.lastname FROM parks p LEFT JOIN reviews r ON r.park_id = p.id LEFT JOIN users u ON p.user_id = u.id WHERE p.id = ?1",  nativeQuery=true )
	Object[] parksByParkId (Long id);
	
//	@Query ("SELECT p, AVG(r.rating) from Park p JOIN p.reviews r WHERE p.id = ?1")
//	List <Object[]> selectByParkId (Long id);
	
//	@Query ("select p, AVG(r.rating) from Park p JOIN Review r WHERE p.city LIKE %?1%")
//	List <Object[]> findByCity (String city);
//	
//	List <Park> findByNameContaining(String name);
//	
//	@Query ("select p, AVG(r.rating) from Park p JOIN Review r WHERE p.name LIKE %?1%")
//	List <Object[]> findByName (Category city);
//	
//	List <Park> findByCategoriesContaining(Category category);
//	
//	@Query ("select p, AVG(r.rating) from Park p JOIN Review r WHERE p.categories IN ?1")
//	List <Object[]> findByCategory (Category category);
//	
//	List <Park> findByCategoriesAndCityContaining(Category category, String city);
//	
//	@Query ("select p, AVG(r.rating) from Park p JOIN Review r WHERE p.categories IN ?1 AND p.city LIKE %?2%")
//	List <Object[]> findByCategoryCity (Category category, String city);
//		
//	@Query (value="SELECT p.id, p.name, p.city, SUM(r.id), AVG(r.rating) FROM parks p JOIN reviews r JOIN park_categories c WHERE c.park_id = ?1 GROUP BY p.id ORDER BY SUM(r.id) DESC LIMIT 4 ", nativeQuery=true)


//	public List <Park> findAllSorted(Sort sort);
	
//	List <Park> findTop4ByOrderByReviewsDesc();
	
	

	
	

}
