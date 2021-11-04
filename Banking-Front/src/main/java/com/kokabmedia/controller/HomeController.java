package com.kokabmedia.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kokabmedia.dao.RoleDao;
import com.kokabmedia.domain.PrimaryAccount;
import com.kokabmedia.domain.SavingsAccount;
import com.kokabmedia.domain.User;
import com.kokabmedia.security.UserRole;
import com.kokabmedia.service.UserService;

/*
* This class function is to handle HTTP requests, communicate with the database
* and as a transient class for the Dispatcher Servlet to handled the view object
* and java object binding.
* 
* The @Controller annotation allows the Spring framework to creates an instance (bean) 
* of this class and manage it with the Spring Application Context (the IOC container)
* that maintains all the beans for the application.  
*
* The @Controller annotation lets the Spring framework manage this class as a Spring bean. 
* The Spring framework will find the bean with auto-detection when scanning the class 
* path with component scanning. It turns the class into a Spring bean at the auto-scan 
* time.
* 
* @Controller annotation allows this class and to be wired in as dependency 
* to a another object or a bean with the @Autowired annotation.
* 
* The @Controller annotation is a specialisation of @Component annotation for more specific 
* use cases.
* 
* The dispatcher servlet is the Front Controller for the Spring MVC framework 
* handles all the requests of the root (/) of the web application. 
* 
* Dispatcher servlet knows all the HTTP request methods GET, POST, PUT AND DELETE 
* and what java methods they are mapped to with annotations. Dispatcher servlet will 
* delegate which controller should handle a specific request. Dispatcher servlet looks 
* at the URL and the request method.  
*/
@Controller
public class HomeController {

	/*
	 * The @Autowired annotation tells the Spring framework that this object (bean)
	 * and its implementation is an dependency of this class. It is a mechanism for
	 * implementing Spring dependency injection.
	 * 
	 * @Autowired annotation enables dependency injection with Spring framework to
	 * avoid tight coupling and enable loose coupling by calling a interface or the
	 * implementation of an interface.
	 * 
	 * The Spring framework creates a instance (bean) of the UserService or its
	 * implementation and inject (autowires) that instance into the HomeController
	 * object when it is instantiated as a autowired dependency.
	 * 
	 * The UserService bean and its implementation is now a dependency of the
	 * HomeController class.
	 */
	@Autowired
	private UserService userService;

	@Autowired
	private RoleDao roleDao;

	/*
	 * When HTTP request is sent to a certain URL and that URL contains a path which
	 * is declared on the @RequestMapping annotation, in this case the appended "/",
	 * this class will called and response if needed.
	 */
	@RequestMapping("/")
	public String home() {
		// returns the view with the name "index"
		return "redirect:/index";
	}

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	/*
	 * When HTTP GET request is sent to a certain URL and that URL contains a path
	 * which is declared on the @GetMapping annotation this method will be called.
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		User user = new User();

		/*
		 * With the model.addAttribute() method the attribute will be mapped to variable
		 * in the html file that contains the variable "user" and a user will be shown
		 * coming in from the second parameter value. The model instance will make it
		 * available to the html view file, this will be handled by the Dispatcher
		 * Servlet. This will bind the model view object to the java object.
		 */
		model.addAttribute("user", user);

		return "signup";
	}

	/*
	 * This method reacts to a HTTP POST request containing a HTML body and stores
	 * it in the database using JPA. We get the content of the user coming is as
	 * part of the request body.
	 * 
	 * The this method will be a web service end point that converts view paylod
	 * into a java object.
	 * 
	 * When HTTP POST request is sent to a certain URL and that URL contains a path
	 * which is declared on the @PostMapping annotation then this method will be
	 * called.
	 * 
	 * The @ModelAttribute annotation parameter lets the system retrieve a variable
	 * user from the context of the HTML form body and bind the value to the User
	 * object in this method.
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signupPost(@ModelAttribute("user") User user, Model model) {

		if (userService.checkUserExists(user.getUsername(), user.getEmail())) {

			if (userService.checkEmailExists(user.getEmail())) {
				model.addAttribute("emailExists", true);
			}

			if (userService.checkUsernameExists(user.getUsername())) {
				model.addAttribute("usernameExists", true);
			}

			return "signup";
		} else {
			Set<UserRole> userRoles = new HashSet<>();
			userRoles.add(new UserRole(user, roleDao.findByName("ROLE_USER")));

			// userService.createUser(user, userRoles); // Need to set user roles manually in the database
		
			userService.save(user);

			return "redirect:/";
		}
	}

	@RequestMapping("/userFront")
	public String userFront(Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		SavingsAccount savingsAccount = user.getSavingsAccount();

		model.addAttribute("primaryAccount", primaryAccount);
		model.addAttribute("savingsAccount", savingsAccount);

		return "userFront";
	}
}
