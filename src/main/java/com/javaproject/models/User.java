package com.javaproject.models;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table (name="users")
public class User {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size (min=3, max=45)
	private String firstname;
	
	@NotNull
	@Size (min=3, max=45)
	private String lastname;
	
	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthdate; 
	
	@NotNull
	@Size (min=3, max=45)
	private String city;
	
	@NotNull
	@Email ()
	private String email;
	
	@Size (max=250)
	private String bio;
	
	@NotNull
	@Size (min=6)
	private String password;
	
	@Transient
    private String passwordConfirmation;
	
	@NotNull
	private String status;
	
	@NotNull
	private String role;
	
	@NotNull
	private String picture;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "favorites", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "park_id")
    	)
	private List<Park> favorites;
        
    @OneToMany(mappedBy="creator", fetch=FetchType.LAZY)
	private List<Park> submissions;
        
    @OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private List<Review> reviews;
	
    @Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    
    @PrePersist // Before the item is created.
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate // Before the item is updated.
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
    
    // Constructor 
    
    public User () {}
    
    // Getters and Setters
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date bithdate) {
		this.birthdate = bithdate;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public List<Park> getFavorites() {
		return favorites;
	}
	public void setFavorites(List<Park> favorites) {
		this.favorites = favorites;
	}

	public List<Park> getSubmissions() {
		return submissions;
	}
	public void setSubmissions(List<Park> submissions) {
		this.submissions = submissions;
	}
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
    
    
}
