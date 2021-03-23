POST: http://localhost:9090/authenticate

{
	"username":"Taufiq",
	"password":"Taufiq"
}

GET :http://localhost:9090/employees

UserDetails UserDetailsFromCOntext = (UserDetails)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();