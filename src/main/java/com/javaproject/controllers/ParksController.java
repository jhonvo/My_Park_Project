package com.javaproject.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javaproject.models.Category;
import com.javaproject.models.Park;
import com.javaproject.models.Review;
import com.javaproject.models.User;
import com.javaproject.services.CategoryService;
import com.javaproject.services.ParkService;
import com.javaproject.services.ReviewService;
import com.javaproject.services.UserService;

import Util.FileUploadUtil;

@Controller
public class ParksController {

	@Autowired
	UserService uServ;
	
	@Autowired
	ParkService pServ;
	
	@Autowired
	ReviewService rServ;
	
	@Autowired
	CategoryService cServ;
	
	
	// Main page
	@RequestMapping("/")
	public String index(Model model,
						HttpSession session) {
		model.addAttribute("topparks", pServ.findTop());
		model.addAttribute("userId", session.getAttribute("id"));
		return "index.jsp";
	}
	
	// Search for Category
	@RequestMapping("/search/{id}")
	public String searchCategory(@PathVariable ("id") Long id,
						@PathVariable (name="city", required=false) String city,
						Model model,
						HttpSession session) {
//		Category cat = cServ.findById(id);
		model.addAttribute("cat", id);
		model.addAttribute("searchCity", city);
		model.addAttribute("parks", pServ.findAvgCategory(id));
		model.addAttribute("userId", session.getAttribute("id"));
		return "home.jsp";
	}
	
	// Search for Category and City
	@RequestMapping("/search/{id}/{city}")
	public String searchCategoryCity(@PathVariable ("id") Long id,
						@PathVariable (name="city", required=false) String city,
						Model model,
						HttpSession session) {
		Category cat = cServ.findById(id);
		model.addAttribute("cat", id);
		model.addAttribute("searchCity", city);
		model.addAttribute("parks", pServ.findAvgCategoryCity(id, city));
		model.addAttribute("userId", session.getAttribute("id"));
		return "home.jsp";
	}
	
	@RequestMapping(value="/search/city", method=RequestMethod.POST)
	public String searchByCity(@RequestParam(value="city", required=false) String city) {
		if (city.isEmpty()) {
			System.out.println("city is null...");
			return "redirect:/search";
		} else {
			return "redirect:/search/c/"+city;
		}
		
	}
	
	// Search for City
	@RequestMapping("/search/c/{city}")
	public String searchCity(@PathVariable ("city") String city,
						Model model,
						HttpSession session) {
		model.addAttribute("name", session.getAttribute("name"));
		model.addAttribute("city", session.getAttribute("city"));
		model.addAttribute("searchCity", city);
		model.addAttribute("userId", session.getAttribute("id"));
		if (city == null) {
			return "redirect:/search";
		}
		else {
			model.addAttribute("parks", pServ.findAvgCity(city));
		}		
		return "home.jsp";
	}
	
	//	 Search for Name
	@RequestMapping("/search/n/{name}")
	public String searchName(@PathVariable ("name") String name,
						Model model,
						HttpSession session) {
		model.addAttribute("name", session.getAttribute("name"));
		model.addAttribute("city", session.getAttribute("city"));
		model.addAttribute("userId", session.getAttribute("id"));
		model.addAttribute("searchName", name);
		if (name == null) {
			return "redirect:/search";
		}
		else {
			model.addAttribute("parks", pServ.findAvgName("%"+name+"%"));
			System.out.println(name);
			System.out.println(pServ.findAvgName("%"+name+"%"));
		}		
		return "home.jsp";
	}
	
	// Empty Search
	@RequestMapping("/search")
	public String searchCity(Model model,
							HttpSession session) {
		model.addAttribute("parks", pServ.findAllAvg());
		model.addAttribute("userId", session.getAttribute("id"));
		return "home.jsp";		
	}
	
	// Page to submit suggestions of a park
	@RequestMapping("/park/submit")
	public String submitPark (Model model,
							HttpSession session){
		model.addAttribute("userId", session.getAttribute("id"));
		if (session.getAttribute("id") == null) {
			return "redirect:/access";
		}
		model.addAttribute("categories", cServ.findAll());
		return "parksubmit.jsp";
	}
		
	// Saving the suggestion of the park
	@RequestMapping(value="/park/submit", method=RequestMethod.POST)
	public String saveSumitPark (Model model,
							HttpSession session,
							@RequestParam("name") String name,
							@RequestParam("city") String city,
							@RequestParam("location") String location,
							@RequestParam(value="kids", defaultValue="0") Long kids,
							@RequestParam(value="pets", defaultValue="0") Long pets,
							@RequestParam(value="friends", defaultValue="0") Long friends,
							@RequestParam(value="dates", defaultValue="0") Long dates,
							@RequestParam(value="sports", defaultValue="0") Long sports,
							@RequestParam(value="nature", defaultValue="0") Long nature,
							@RequestParam("picture") MultipartFile multipartFile,
							RedirectAttributes redirectAttributes) throws IOException {
		System.out.println("runing...");
		int errorflag = 0;
		if (name.length() < 3 || name.length() > 45) {
			redirectAttributes.addFlashAttribute("nameError", "Name should be between 3 and 45 characters.");
			errorflag = 1;
		}
		if (city.length() < 3 || city.length() > 45) {
			redirectAttributes.addFlashAttribute("cityError", "Name should be between 3 and 45 characters.");
			errorflag = 1;
		}
		if (location.length() < 3 || location.length() > 45) {
			redirectAttributes.addFlashAttribute("locationError", "Location should be between 3 and 45 characters.");
			errorflag = 1;
		}
		if (multipartFile.isEmpty()) {
			redirectAttributes.addFlashAttribute("pictureError", "Please select an image to upload.");
			errorflag = 1;
		}
		if (kids == 0 && pets == 0 && friends == 0 && dates == 0 && sports == 0 && nature == 0) {
			redirectAttributes.addFlashAttribute("categoryError", "Please select at least one category.");
			errorflag = 1;
		}
		if (errorflag == 1){
			redirectAttributes.addFlashAttribute("formErrors", "* Please review the errors below.");
			redirectAttributes.addFlashAttribute("parkname", name);
			redirectAttributes.addFlashAttribute("parkcity", city);
			redirectAttributes.addFlashAttribute("parklocation", location);
			return "redirect:/park/submit";
		}
		else {
			
			System.out.println("validations completed...");
			
			Park park = new Park();
			park.setName(name);
			park.setCity(city);
			park.setLocation(location);
			park.setStatus("submitted");
			park.setCreator(uServ.findUserById((Long)session.getAttribute("id")));
						
	        List <Category> categories = new ArrayList<Category>(); ;
	        
	        if (kids != 0) {
	        	categories.add(cServ.findById(kids));
	        }
	        if (pets != 0) {
	        	categories.add(cServ.findById(pets));
	        }
	        if (friends != 0) {
	        	categories.add(cServ.findById(friends));
	        }
	        if (dates != 0) {
	        	categories.add(cServ.findById(dates));
	        }
	        if (sports != 0) {
	        	categories.add(cServ.findById(sports));
	        }
	        if (nature != 0) {
	        	categories.add(cServ.findById(nature));
	        }
	        System.out.println("Categories completed...");
	        park.setCategories(categories);
	        Park newPark = pServ.savePark(park);
	        
	        String fileName = newPark.getId() + "_" + StringUtils.cleanPath(multipartFile.getOriginalFilename());
			newPark.setPicture("/img/parks/" + fileName);
			
			String fileLocation = new File("src/main/resources/static/img/parks").getAbsolutePath();
	        FileUploadUtil.saveFile(fileLocation, fileName, multipartFile);
	        
	        pServ.savePark(newPark);
	        
	        redirectAttributes.addFlashAttribute("Sucess", "Park has been submitted");
	        
			return "redirect:/user";
		}
		}
	
	//Creating a review
	@RequestMapping ("/park/{id}/review")
	public String createReview (Model model,
								HttpSession session,
								@PathVariable ("id") Long id) {
		model.addAttribute("userId", session.getAttribute("id"));
		if (session.getAttribute("id") == null) {
			return "redirect:/access";
		}
		Park park = pServ.getPark(id);
		
		model.addAttribute("park", park);
		return "parkreview.jsp";
	}
	
	//Saving a review
	@RequestMapping(value="/park/{id}/review", method=RequestMethod.POST)
	public String saveReview(Model model,
							HttpSession session,
							@PathVariable ("id") Long id,
							@RequestParam("title") String title,
							@RequestParam("review") String review,
							@RequestParam("rating") int rating,
							@RequestParam("picture") MultipartFile multipartFile,
							RedirectAttributes redirectAttributes) throws IOException{
		
		int errorflag = 0;
		if (title.length() < 3 || title.length() > 45) {
			redirectAttributes.addFlashAttribute("titleError", "Title should be between 3 and 45 characters.");
			errorflag = 1;
		}
		if (review.length() < 5 || review.length() > 250) {
			redirectAttributes.addFlashAttribute("reviewError", "Review should be between 5 and 250 characters.");
			errorflag = 1;
		}
		if (errorflag == 1){
			redirectAttributes.addFlashAttribute("formErrors", "* Please review the errors below.");
			redirectAttributes.addFlashAttribute("title", title);
			redirectAttributes.addFlashAttribute("review", review);
			return "redirect:/park/"+id+"/review";
		}
			else {
			
			System.out.println("validations completed...");
			
			Review tempReview = new Review();
			tempReview.setTitle(title);
			tempReview.setRating(rating);
			tempReview.setReview(review);
			tempReview.setPark(pServ.getPark(id));
			tempReview.setUser(uServ.findUserById((Long)session.getAttribute("id")));
			
			Review newReview = rServ.saveReview(tempReview);
			
			if (! multipartFile.isEmpty()) {
				String fileName = newReview.getId() + "_" + StringUtils.cleanPath(multipartFile.getOriginalFilename());
				newReview.setPicture("/img/reviews/" + fileName);
				
				String fileLocation = new File("src/main/resources/static/img/reviews").getAbsolutePath();
		        FileUploadUtil.saveFile(fileLocation, fileName, multipartFile);
		        
		        rServ.saveReview(newReview);
			}
				        
	        redirectAttributes.addFlashAttribute("Sucess", "Review has been submitted");
	        
			return "redirect:/park/"+id;
		}
	}
	
	//Getting information of a park
	@RequestMapping ("/park/{id}")
	public String showPark (Model model,
								HttpSession session,
								@PathVariable ("id") Long id) {
		model.addAttribute("userId", session.getAttribute("id"));
		Object[] park = pServ.parksByParkId(id);
		List <Review> reviews = rServ.findByPark(pServ.getPark(id));
		List <User> favorited = pServ.getPark(id).getFavorited();
		Long userId = (Long) session.getAttribute("id");
		Long creatorId = pServ.getPark(id).getCreator().getId();
		model.addAttribute("park", park[0]);
		model.addAttribute("reviews", reviews);
		System.out.println(creatorId + "/" + userId);
		if ( userId != null && ! userId.equals(creatorId) ) {
			System.out.println(creatorId + "/////" + userId);
			model.addAttribute("owner1", "0" );
			if (favorited.contains(uServ.findUserById((Long) session.getAttribute("id")))) {
				model.addAttribute("action", "Remove from" );
			} else {
				model.addAttribute("action", "Add to" );
			}
		} 
		model.addAttribute("owner2", "0" );
		

		
		return "parkinfo.jsp";
	}
		
	// Adding - Removing Favorites
	@RequestMapping(value="/park/{id}/favorite", method=RequestMethod.POST)
	public String saveFavorite (Model model,
								HttpSession session,
								@PathVariable ("id") Long id) {
		User user = uServ.findUserById((Long)session.getAttribute("id"));
		Park park = pServ.getPark(id);
		List <User> favorited = park.getFavorited();
		
		if (favorited.contains(user)) {
			favorited.remove(user);
			park.setFavorited(favorited);
			pServ.savePark(park);
		} else {
			favorited.add(user);
			park.setFavorited(favorited);
			pServ.savePark(park);
		}
		
		return "redirect:/park/"+id;
	}
	
}
