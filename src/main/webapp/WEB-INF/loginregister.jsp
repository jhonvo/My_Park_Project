<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Access to MyPark</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	</head>
<body class="bg-success bg-opacity-10 ">
<div class=" mx-auto my-5 row col-md-9">

		<a  href="/"><img class="col-4 mb-4"  src="/img/main/logo.png"/></a>

		<div class="container col-md-6 bg-white p-4">
				 
				<nav class="nav nav-tabs" id="nav-tab" role="tablist">
				  <a class="nav-link active" id="login-tab" data-bs-toggle="tab" href="#nav-home" role="tab" aria-controls="nav-home" aria-selected="true">Login</a>
				  <a class="nav-link" id="register-tab" data-bs-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="false">Register</a>
				</nav>
				<div class="tab-content" id="nav-tabContent">
				<div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="login-tab">
				  <div>
			    	<p class="text-danger small"><c:out value="${loginError}" /></p>
			    </div>
				  	<form  method="post" action="/login">
			        
			     
			           
					    	<p>
					            <label class="form-label" for="email">Email</label>
					            <input class="form-control" type="text" id="email" name="email"/>
					        </p>
					        <p>
					            <label class="form-label" for="password">Password</label>
					            <input class=" form-control" type="password" id="password" name="password"/>
					        </p>
					        <input class="btn btn-success col-lg-4" type="submit" value="Login!"/>
					    </form>  
				  </div>
				  <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="register-tab">
				  
						    	<div  class="text-danger small" >
							    	<c:out value="${formErrors}" />
							    </div>
							    <form  method="POST" action="/register">
				        
						        
						        <p>
						            <label class="form-label" for="firstname">First Name:</label>
						            <input class="form-control" type="text" name="firstname" id="firstname" value="${firstname}"/>
						        	<p class="text-danger small"><c:out value="${firstNameError}" /></p>
						        </p>
						        
						        <p>
						            <label class="form-label" for="lastname" >Last Name:</label>
						            <input class="form-control" type="text" name="lastname" id="lastname" value="${lastname}"/>
						            <p class="text-danger small"><c:out value="${lastNameError}" /></p>
						        </p>
						        
						        <p>
						            <label class="form-label" for="birthdate">Birth Date:</label>
						            <input class="form-control" type="date" name="birthdate" id="birthdate"/>
						            <p class="text-danger small"><c:out value="${birthDateError}" /></p>
						        </p>
						        
						        <p>
						            <label class="form-label" for="city">City:</label>
						            <input class=" form-control" type="text" name="city" id="city" value="${city}"/>
						            <p class="text-danger small"><c:out value="${cityError}" /></p>
						        </p>
						        
						        <p>
						            <label class=" form-label" for="email">Email:</label>
						            <input class=" form-control" type="email" name="email" id="email" value="${email}"/>
						            <p class="text-danger small"><c:out value="${emailError}" /></p>
						            <p class="text-danger small"><c:out value="${duplicateError}" /></p>
						        </p>
						        
						        <p> 
						            <label class=" form-label" for="password">Password:</label>
						            <input class=" form-control" type="password" name="password" id="password"/>
						            <p class="text-danger small"><c:out value="${passwordError}" /></p>
						        </p>
						        
						        <p>
						            <label class=" form-label" for="passwordConfirmation">Password Confirmation:</label>
						            <input class="form-control" type="password" name="passwordConfirmation" id="passwordConfirmation"/>
						            <p class="text-danger small"><c:out value="${registerError}" /></p>
						        </p>
						        
						        
						        <input class="btn btn-success col-lg-4" type="submit" value="Register!"/>
						        
						    </form>
						    </div>
				</div>
		</div>
<div class="container col-md-6 bg-white p-4">
<img class="col-12"  src="/img/main/loginpark3.jpeg"/>
</div>
</div>






<%-- <div class="container mx-auto bg-white py-4 rounded mt-5">

	 <h1 class="  display-4 text-center mb-3">Lyrics Lab!</h1>

	<div class="d-flex flex-row flex-wrap justify-content-evenly mt-5">

		<div class="col-md-4 mx-3">
		
		<h1 class="display-6 text-center mb-3">Register!</h1>
		    
		   
		    <div  class="text-danger small" >
		    	<c:out value="${formErrors}" />
		    </div>
		    <form  method="POST" action="/register">
		        
		        <p>
		            <label class="col-2 form-label" for="firstname">First Name:</label>
		            <input class="col-4 form-control" type="text" name="firstname" id="firstname" value="${firstname}"/>
		        	<p class="text-danger small"><c:out value="${firstNameError}" /></p>
		        </p>
		        
		        <p>
		            <label class="col-2 form-label" for="lastname" >Last Name:</label>
		            <input class="col-4 form-control" type="text" name="lastname" id="lastname" value="${lastname}"/>
		            <p class="text-danger small"><c:out value="${lastNameError}" /></p>
		        </p>
		        
		        <p>
		            <label class="col-2 form-label" for="birthdate">Birth Date:</label>
		            <input class="col-4 form-control" type="date" name="birthdate" id="birthdate"/>
		            <p class="text-danger small"><c:out value="${birthDateError}" /></p>
		        </p>
		        
		        <p>
		            <label class="col-2 form-label" for="city">City:</label>
		            <input class="col-4 form-control" type="text" name="city" id="city" value="${city}"/>
		            <p class="text-danger small"><c:out value="${cityError}" /></p>
		        </p>
		        
		        <p>
		            <label class="col-2 form-label" for="email">Email:</label>
		            <input class="col-4 form-control" type="email" name="email" id="email" value="${email}"/>
		            <p class="text-danger small"><c:out value="${emailError}" /></p>
		            <p class="text-danger small"><c:out value="${duplicateError}" /></p>
		        </p>
		        
		        <p> 
		            <label class="col-2 form-label" for="password">Password:</label>
		            <input class="col-4 form-control" type="password" name="password" id="password"/>
		            <p class="text-danger small"><c:out value="${passwordError}" /></p>
		        </p>
		        
		        <p>
		            <label class="col-2 form-label" for="passwordConfirmation">Password Confirmation:</label>
		            <input class="col-4 form-control" type="password" name="passwordConfirmation" id="passwordConfirmation"/>
		            <p class="text-danger small"><c:out value="${registerError}" /></p>
		        </p>
		        
		        
		        <input class="btn btn-info col-lg-4" type="submit" value="Register!"/>
		        
		    </form>
	    
	    </div>
       
    
	    <form class="col-md-4 mx-3" method="post" action="/login">
	        
	        <h1 class="  display-6 text-center mb-3">Login</h1>
	           <div>
	    	<p class="text-danger small"><c:out value="${loginError}" /></p>
	    </div><p>
	            <label class="col-2 form-label" for="email">Email</label>
	            <input class="col-4 form-control" type="text" id="email" name="email"/>
	        </p>
	        <p>
	            <label class="col-2 form-label" for="password">Password</label>
	            <input class="col-4 form-control" type="password" id="password" name="password"/>
	        </p>
	        <input class="btn btn-success col-lg-4" type="submit" value="Login!"/>
	    </form>  
    
    </div>
    
 </div>   --%>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
	
</body>
</html>