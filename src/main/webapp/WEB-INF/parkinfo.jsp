<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Park Information</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	</head>
	<body class="bg-success bg-opacity-10">
	
		<nav class="navbar navbar-expand-lg navbar-light bg-success bg-opacity-50">
		  <div class="container-fluid">
		    <a class="navbar-brand " href="/"><img style="width:150px;" src="/img/main/logo.png"/></a>
		    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		      <span class="navbar-toggler-icon"></span>
		    </button>
		    <div class="collapse navbar-collapse" id="navbarSupportedContent">
		      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
		        <li class="nav-item">
		          <a class="nav-link " aria-current="page" href="/">Home</a>
		        </li>
		        
		        <li class="nav-item dropdown ">
		          <a class="nav-link dropdown-toggle " href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
		            Categories
		          </a>
		          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
		            <li><a class="dropdown-item" href="/search/1">Kids</a></li>
		            <li><a class="dropdown-item" href="/search/2">Pets</a></li>
		            <li><a class="dropdown-item" href="/search/3">Friends</a></li>
		            <li><a class="dropdown-item" href="/search/4">Dates</a></li>
		            <li><a class="dropdown-item" href="/search/5">Sports</a></li>
		            <li><a class="dropdown-item" href="/search/6">Nature</a></li>
		          </ul>
		        </li>
		        <c:if test="${ userId != null }">
		        <li class="nav-item">
		          <a class="nav-link" aria-current="page" href="/user">My Profile</a>
		        </li>
		        </c:if>
		        
		        <c:choose>
				<c:when test="${ userId == null }">
					<li class="nav-item">
		          <a class="nav-link" href="/access">Login and Register</a>
		        </li>
				</c:when>
				<c:otherwise>
					<li class="nav-item">
		          <a class="nav-link" href="/logout">Logout</a>
		        </li>
				</c:otherwise>
			</c:choose>
		        
		        
		      </ul>
		      <form class="d-flex" action="/search/city" method="POST">
		        <input class="form-control me-2" type="search" name="city" placeholder="Search by city" aria-label="Search by city">
		        <button class="btn btn-outline-success" type="submit">Search</button>
		      </form>
		    </div>
		  </div>
		</nav>
	
	
	<div class="container-md bg-white pb-5 pt-3 px-5">
		<p class="text-success small"><c:out value="${Sucess}" /></p>
	
  					<h4>Name: <c:out value="${park[1]}"/> </h4>
		    		<p>City: <c:out value="${park[2]}"/></p>
		    		<p>Location: <c:out value="${park[3]}"/></p>
		    		<p>Created By: <a href="/user/${park[8]}"><c:out value="${park[9]}"/> <c:out value="${park[10]}"/> </a> </p>
		    		<p><img src="${park[5]}" style="width:100px;"/></p>
		    		<p>Reviews: <c:out value="${park[6]}"/> </p>
		    		<p>Rating: 
		    			<fmt:formatNumber var="formattedRating" type="number" minFractionDigits="2" maxFractionDigits="2" value="${park[7]}" />
						<c:set var="rating" value="${formattedRating}" />
		    			<c:choose>
							<c:when test="${park[7] == null }">
								0/5
							</c:when>
							<c:otherwise>
								<c:out value="${rating}"/>/5
							</c:otherwise>
						</c:choose>
		    		</p>
		    		
		    		
		    	<h4>Reviews</h4>
		    	
		    	<c:forEach var="review" items="${reviews}"> 
		    		<h5>Title: <c:out value="${review.title}"/></h5>
		    		<p> <c:out value="${review.review}"/></p>
		    		<p>Rating: <c:out value="${review.rating}"/>/5</p>
		    		<p>Created By: <a href="/user/${review.user.id}"><c:out value="${review.user.firstname}"/> <c:out value="${review.user.lastname}"/></a></p>
		    		
					<c:if test="${ review.picture != null }">
						<p>Picture:</p>
						<p><img src="${review.picture}" style="width:100px;"/></p>
					</c:if>
					
		    	</c:forEach>

		    <a  class="btn btn-secondary col-lg-4 mb-2" href="/park/${park[0]}/review">Write a Review</a>
			
		    <c:if test="${ owner1 == owner2 }"> 
		    	
					<form method="post" action="/park/${park[0]}/favorite">
					 <input class="btn btn-success col-lg-4" type="submit" value="<c:out value="${ action }"/> Favorites"/>
					</form>
				
		    </c:if>
	</div>
	
	
		  
		    
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
	</body>
</html>