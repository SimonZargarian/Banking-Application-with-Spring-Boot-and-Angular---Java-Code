package com.kokabmedia.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kokabmedia.domain.PrimaryTransaction;
import com.kokabmedia.domain.SavingsTransaction;
import com.kokabmedia.domain.User;
import com.kokabmedia.service.TransactionService;
import com.kokabmedia.service.UserService;

/*
* This classes function is to handle HTTP requests, responses and expose recourses to 
* other applications more specifically the Angular build admin section of this application.
* 
* The @RestController annotation will register this class as a Rest Controller and it will
* be able to receive HTTP request when they are sent and match the URL path.
* 
* With this annotation the class can now handle REST requests.
* 
* @Response body annotation which is part of the @RestController annotation is responsible 
* for sending information back from the application to another application. 
* 
* When we put @ResponseBody on a controller, the response from that will be mapped by a 
* http message converter(Jackson) into another format, for example a java object to JSON, 
* XML or HTML. Response body converts the java object and sends the response back. 
*/
@RestController
/*
 * When HTTP request is sent to a certain URL and that URL contains a path which
 * is declared on the @RequestMapping annotation, in this case the appended 
 * "api", this class will be called and response if needed. 
 * 
 * When a GET, POST, PUT or DELETER HTTP request is sent to the URL path with
 * the extension "/api" then the appropriate method in the class will respond.
 * 
 * This is a request mapping for the entire class. 
 * 
 * The dispatcher servlet is the Front Controller for the Spring MVC framework 
 * handles all the requests of the root (/) of the web application. 
 * 
 * Dispatcher servlet knows all the HTTP request methods GET, POST, PUT AND DELETE 
 * and what java methods they are mapped to with annotations. Dispatcher servlet will 
 * delegate which controller should handle a specific request. Dispatcher servlet looks 
 * at the URL and the request method.  
 */
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')") // For admin logging purposes
public class UserResource {

	/*
	 * The @Autowired annotation tells the Spring framework that this object (bean)
	 * and its implementation is an dependency of this class. It is a mechanism for 
	 * implementing Spring dependency injection.
	 * 
	 * @Autowired annotation enables dependency injection with Spring framework to avoid 
	 * tight coupling and enable loose coupling by calling a interface or the implementation 
	 * of an interface.
	 * 
	 * The Spring framework creates a instance (bean) of the UserService or its implementation 
	 * and inject (autowires) that instance into the UserResource object when it is instantiated 
	 * as a autowired dependency.
	 * 
	 * The UserService bean and its implementation is now a dependency of the UserResource class.
	 */
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    /*
	 *  When HTTP GET request is sent to a certain URL and that URL contains a path which
	 * is declared on, the @GetMapping annotation this method will be called. 
	 */
    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public List<User> userList() {
        return userService.findUserList();
    }

    @RequestMapping(value = "/user/primary/transaction", method = RequestMethod.GET)
    public List<PrimaryTransaction> getPrimaryTransactionList(@RequestParam("username") String username) {
        return transactionService.findPrimaryTransactionList(username);
    }

    @RequestMapping(value = "/user/savings/transaction", method = RequestMethod.GET)
    public List<SavingsTransaction> getSavingsTransactionList(@RequestParam("username") String username) {
        return transactionService.findSavingsTransactionList(username);
    }

    /*
   	 * This method returns an user with a specific id from the database using JPA.
   	 * 
   	 * The @GetMapping annotation will bind and make enableUser() method respond 
   	 * to a HTTP GET request.
   	 * 
   	 * The (path = "/{username}") parameter allows the method to read the appended
   	 * String after the URL http://localhost:8080/api/user as a path variable 
   	 * that is attached, so when a String is appended after http://localhost:8080/api/user
   	 * with a GET HTTP request the enableUser() method is called. The name of the "/{username}" 
   	 * parameter must match the @PathVariable annotation argument String username.
   	 */
    @RequestMapping("/user/{username}/enable")
    /*
	 * The @PathVariable annotation will make the path variable in the URL available
	 * for this enableUser method via the method argument. When a user name String is
	 * appended to http://localhost:8080/user/{username}/enable it can be handled by the 
	 * enableUser() method.
	 */
    public void enableUser(@PathVariable("username") String username) {
        userService.enableUser(username);
    }

    @RequestMapping("/user/{username}/disable")
    public void diableUser(@PathVariable("username") String username) {
        userService.disableUser(username);
    }
}
