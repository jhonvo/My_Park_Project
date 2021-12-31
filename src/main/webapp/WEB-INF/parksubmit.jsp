<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Park Submission</title>
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
		        
		       
		        <li class="nav-item">
		          <a class="nav-link active" aria-current="page" href="/park/submit">Submit a Park</a>
		        </li>

		        
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
	
		<form  method="POST" action="/park/submit"  enctype="multipart/form-data">
		         <div  class="text-danger small" >
		    	<c:out value="${formErrors}" />
		    </div>
		        <p>
		            <label class="col-2 form-label" for="name">Name:</label>
		            <input class="col-4 form-control" type="text" name="name" id="name" value="${parkname}"/>
		        	<p class="text-danger small"><c:out value="${nameError}" /></p>
		        </p>
		        
      	        <p>
		            <label class="col-2 form-label" for="city">City:</label>
		            <input class="col-4 form-control" type="text" name="city" id="city" value="${parkcity}"/>
		            <p class="text-danger small"><c:out value="${cityError}" /></p>
		        </p>
		        
		      	<p>
		            <label class="col-2 form-label" for="location">Location:</label>
		            <input class="col-4 form-control" type="text" name="location" id="location" value="${parklocation}"/>
		            <p class="text-danger small"><c:out value="${locationError}" /></p>
		        </p>
		        

		        <p>
					<label class="col-2 form-label" for="picture">Image:</label>
		    		<input  class="form-control" type="file" id="picture" name="picture" accept="image/png, image/jpeg" />
		        	<p class="text-danger small"><c:out value="${pictureError}" /></p>
		        </p>
		        
		        <p>
					<label class="col-2 form-label" for="">Categories:</label>
					<p class="text-danger small"><c:out value="${categoryError}" /></p>
					<div class="form-check">
					  <input class="form-check-input" type="checkbox" value="1" name="kids" id="kids">
					  <label class="form-check-label" for="kids">Kids</label>
					</div>
					<div class="form-check">
					  <input class="form-check-input" type="checkbox" value="2" name="pets" id="pets">
					  <label class="form-check-label" for="pets">Pets</label>
					</div>
					<div class="form-check">
					  <input class="form-check-input" type="checkbox" value="3" name="friends" id="friends">
					  <label class="form-check-label" for="friends">Friends</label>
					</div>
					<div class="form-check">
					  <input class="form-check-input" type="checkbox" value="4" name="dates" id="dates">
					  <label class="form-check-label" for="dates">Dates</label>
					</div>
					<div class="form-check">
					  <input class="form-check-input" type="checkbox" value="5" name="sports" id="sports">
					  <label class="form-check-label" for="sports">Sports</label>
					</div>
					<div class="form-check">
					  <input class="form-check-input" type="checkbox" value="6" name="nature" id="nature">
					  <label class="form-check-label" for="nature">Nature</label>
					</div>
		        	
		        </p>
		        
		        	
		        <input class="btn btn-success col-lg-4" type="submit" value="Submit Park"/>
		        
		    </form>
	
	</div>
		
	
		    
		    
		    
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
	</body>
</html>