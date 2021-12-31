package com.javaproject.controllers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javaproject.models.User;
import com.javaproject.services.CategoryService;
import com.javaproject.services.ParkService;
import com.javaproject.services.ReviewService;
import com.javaproject.services.UserService;

import Util.FileUploadUtil;

import org.springframework.util.StringUtils;

@Controller
public class UsersController {
	@Autowired
	UserService uServ;
	
	@Autowired
	ParkService pServ;
	
	@Autowired
	ReviewService rServ;
	
	@Autowired
	CategoryService cServ;
	
	
	
	// Logging out
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
	     session.invalidate();
		 return "redirect:/";
	 }
	
	// Registering a user
	@RequestMapping(value="/register", method=RequestMethod.POST)
    public String registerUser(@RequestParam("firstname") String firstname,
    						@RequestParam("lastname") String lastname,
    						@RequestParam(value="birthdate", defaultValue="") @DateTimeFormat(pattern="yyyy-MM-dd") Date birthdate,
    						@RequestParam("city") String city,
    						@RequestParam("email") String email,
    						@RequestParam("password") String password,
    						@RequestParam("passwordConfirmation") String passwordConfirmation,
    						HttpSession session,
    						RedirectAttributes redirectAttributes) {
		System.out.println("Running!" + birthdate);
		User finduser = uServ.findByEmail(email);
			
			int errorflag = 0;
			if( finduser != null ) {
				redirectAttributes.addFlashAttribute("duplicateError", "Email is already taken.");
				errorflag = 1;
			}
			if (firstname.length() < 2 || firstname.length() > 45) {
				redirectAttributes.addFlashAttribute("firstNameError", "First Name should be between 2 and 45 characters.");
				errorflag = 1;
			}
			if (lastname.length() < 2 || lastname.length() > 45) {
				redirectAttributes.addFlashAttribute("lastNameError", "Last Name should be between 2 and 45 characters.");
				errorflag = 1;
			}
			if (birthdate == null) {
				redirectAttributes.addFlashAttribute("birthDateError", "Please provide a valid date.");
				errorflag = 1;
			}
			if (email.length() < 2) {
				redirectAttributes.addFlashAttribute("emailError", "Please provide a valid email address.");
				errorflag = 1;
			}
			if (city.length() < 2) {
				redirectAttributes.addFlashAttribute("cityError", "Please provide a valid city.");
				errorflag = 1;
			}
			if (password.length() < 6) {
				redirectAttributes.addFlashAttribute("passwordError", "Password should be more than 8 characters.");
				errorflag = 1;
			}
			if (!passwordConfirmation.equals(password)) {
				redirectAttributes.addFlashAttribute("registerError", "Passwords do not match");
				errorflag = 1;
			}
			if (errorflag == 1){
				System.out.println("Errors!");
				redirectAttributes.addFlashAttribute("formErrors", "* Please review the errors below.");
				redirectAttributes.addFlashAttribute("firstname", firstname);
				redirectAttributes.addFlashAttribute("lastname", lastname);
				redirectAttributes.addFlashAttribute("city", city);
				redirectAttributes.addFlashAttribute("email", email);
				return "redirect:/access";
			}
			else {
				User newUser = new User();
				System.out.println("NoErrors!");
				newUser.setFirstname(firstname);
				newUser.setLastname(lastname);
				newUser.setBirthdate(birthdate);
				newUser.setCity(city);
				newUser.setEmail(email);
				newUser.setPassword(password);
				newUser.setStatus("active");
				newUser.setRole("user");
				newUser.setPicture("/img/main/usericon.png");
				uServ.registerUser(newUser);
				session.setAttribute("id", newUser.getId());
				session.setAttribute("name", newUser.getFirstname());
				session.setAttribute("city", newUser.getCity());
				session.setAttribute("role", newUser.getRole());
				
				return "redirect:/";
				
			}
					
        }
        
	// Logging in
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginUser(@RequestParam("email") String email, 
			 				@RequestParam("password") String password, 
			 				RedirectAttributes redirectAttributes, 
			 				Model model, 
			 				HttpSession session) {
	     User user = uServ.findByEmail(email);
	     if( user == null ) {
				redirectAttributes.addFlashAttribute("loginError", "Wrong credentials");
				return "redirect:/access";
			}
	     else if (uServ.authenticateUser(email, password)) {
	    	 	session.setAttribute("id", user.getId());
				session.setAttribute("name", user.getFirstname());
				session.setAttribute("city", user.getCity());
				session.setAttribute("role", user.getRole());
	    	 return "redirect:/";
	     }
	     else {
	    	 redirectAttributes.addFlashAttribute("loginError", "Incorrect password");
	    	 return "redirect:/access";
	     }
		 // if the user is authenticated, save their user id in session
	     // else, add error messages and return the login page
	 }
	
	// Login and register page
	@RequestMapping ("/access")
	public String accessPage (HttpSession session) {
		if (session.getAttribute("id") ==  null) {
			return "loginregister.jsp";
		} else {
			return "redirect:/";
		}
	}
	
	// My Information page
	@RequestMapping ("/user")
	public String myInfo (HttpSession session,
							Model model) {
		model.addAttribute("userId", session.getAttribute("id"));
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		} else {
			User user = uServ.findUserById((Long) session.getAttribute("id"));
			SimpleDateFormat fm = new SimpleDateFormat("MMMM dd, YYYY");
			List <Object []> favparks = pServ.parksFavoritedByUserId((Long)session.getAttribute("id")) ;
			List <Object []> parks = pServ.parksByUserId((Long)session.getAttribute("id"));
			model.addAttribute("bDate", fm.format(user.getBirthdate())) ;
			model.addAttribute("user", user);
			model.addAttribute("favparks", favparks);
			model.addAttribute("parks", parks);
			return "myinfo.jsp";
		}
		
	}
	
	//Updating My Information
	@RequestMapping(value="/user/{id}/update", method=RequestMethod.POST)
	public String updateMyInfo (HttpSession session,
							Model model,
							@PathVariable ("id") Long id,
							@RequestParam("firstname") String firstname,
    						@RequestParam("lastname") String lastname,
    						@RequestParam("city") String city,
    						@RequestParam("email") String email,
    						@RequestParam("bio") String bio,
    						RedirectAttributes redirectAttributes
							) {
		User user = uServ.findUserById(id);
		int errorflag = 0;
		if (firstname.length() < 2 || firstname.length() > 45) {
			redirectAttributes.addFlashAttribute("firstNameError", "First Name should be between 2 and 45 characters.");
			errorflag = 1;
		}
		if (lastname.length() < 2 || lastname.length() > 45) {
			redirectAttributes.addFlashAttribute("lastNameError", "Last Name should be between 2 and 45 characters.");
			errorflag = 1;
		}
		if (email.length() < 2) {
			redirectAttributes.addFlashAttribute("emailError", "Please provide a valid email address.");
			errorflag = 1;
		}
		if (city.length() < 2) {
			redirectAttributes.addFlashAttribute("cityError", "Please provide a valid city.");
			errorflag = 1;
		}
		if (bio.length() > 250) {
			redirectAttributes.addFlashAttribute("bioError", "Bio should be less than 200 characters.");
			errorflag = 1;
		}
		if (errorflag == 1){
			System.out.println("Errors!");
			redirectAttributes.addFlashAttribute("formErrors", "* Please review the errors below.");
			redirectAttributes.addFlashAttribute("firstname", firstname);
			redirectAttributes.addFlashAttribute("lastname", lastname);
			redirectAttributes.addFlashAttribute("city", city);
			redirectAttributes.addFlashAttribute("email", email);
			redirectAttributes.addFlashAttribute("bio", bio);
			return "redirect:/user";
		}
		else {
			System.out.println("NoErrors!");
			user.setFirstname(firstname);
			user.setLastname(lastname);
		
			user.setCity(city);
			user.setEmail(email);
			user.setBio(bio);
			uServ.saveUser(user);
			redirectAttributes.addFlashAttribute("Sucess", "Information has been upated!");

			session.setAttribute("name", user.getFirstname());
			session.setAttribute("city", user.getCity());
		
			return "redirect:/user";
			
		}
	}
	
	// Updating profile image
	@RequestMapping(value="/user/{id}/updateimage", method=RequestMethod.POST)
	public String updateMyInfo (HttpSession session,
			@PathVariable ("id") Long id,
			@RequestParam("newImage") MultipartFile multipartFile,
			RedirectAttributes redirectAttributes
			) throws IOException {
		User user = uServ.findUserById(id);
		
		String fileName = id + "_" + StringUtils.cleanPath(multipartFile.getOriginalFilename());
        System.out.println(fileName);
        
        user.setPicture("/img/users/" + fileName );
        uServ.saveUser(user);

        
        
        String fileLocation = new File("src/main/resources/static/img/users").getAbsolutePath();
                
        
        FileUploadUtil.saveFile(fileLocation, fileName, multipartFile);
        
		redirectAttributes.addFlashAttribute("Sucess", "Image has been upated!");

		return "redirect:/user";
		}
	
	// Getting info for user
	@RequestMapping ("/user/{id}")
	public String userInfo (HttpSession session,
							@PathVariable ("id") Long id,
							Model model) {
		model.addAttribute("userId", session.getAttribute("id"));
			User user = uServ.findUserById(id);
			
			// Setting BirthDate to standard date format
			SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
			String bdate = fm.format(user.getBirthdate());
			// date of birth
	        LocalDate pdate = LocalDate.parse(bdate);
	        // current date
	        LocalDate now = LocalDate.now();
	        // difference between current date and date of birth
	        Period diff = Period.between(pdate, now);
			
			
			System.out.println("Items loaded 1");
			List <Object []> parks = pServ.parksApprovedByUserId(id);
			System.out.println(parks);
			model.addAttribute("age", diff.getYears()) ;
			model.addAttribute("user", user);
			model.addAttribute("parks", parks);
			return "userinfo.jsp";
		}
	
}
