package com.kokabmedia.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kokabmedia.domain.PrimaryAccount;
import com.kokabmedia.domain.PrimaryTransaction;
import com.kokabmedia.domain.SavingsAccount;
import com.kokabmedia.domain.SavingsTransaction;
import com.kokabmedia.domain.User;
import com.kokabmedia.service.AccountService;
import com.kokabmedia.service.TransactionService;
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
/*
 * When HTTP request is sent to a certain URL and that URL contains a path which
 * is declared on the @RequestMapping annotation, in this case the appended "/account",
 * this class will called and response if needed. 
 * 
 * When a GET, POST, PUT or DELETER HTTP request is sent to the URL path with
 * the extension "/account" then the appropriate method in the class will respond.
 * 
 * This is a request mapping for the entire class.   
 */
@RequestMapping("/account")
public class AccountController {
	
	/*
	 * The @Autowired annotation tells the Spring framework that this object (bean)
	 * and its implementation is an dependency of this class. It is a mechanism 
	 * for implementing Spring dependency injection.
	 * 
	 * @Autowired annotation enables dependency injection with Spring framework to avoid 
	 * tight coupling and enable loose coupling by calling a interface or the implementation 
	 * of an interface.
	 * 
	 * The Spring framework creates a instance (bean) of the UserService or its implementation 
	 * and inject (autowires) that instance into the AccountController object when it is instantiated 
	 * as a autowired dependency.
	 * 
	 * The UserService bean and its implementation is now a dependency of the AccountController class.
	 */
	@Autowired
    private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping("/primaryAccount")
	public String primaryAccount(Model model, Principal principal) {
		List<PrimaryTransaction> primaryTransactionList = transactionService.findPrimaryTransactionList(principal.getName());
		
		User user = userService.findByUsername(principal.getName());
        PrimaryAccount primaryAccount = user.getPrimaryAccount();

    	/* 
		 * The "primaryAccount" attribute will be mapped to variable in the html file that contains the variable "primaryAccount"
		 * and a primaryAccount will be shown coming in from the second parameter value. The model instance will
		 * make it available to the html view file, this will be handled by the Dispatcher Servlet. This will bind the model view
		 * object to the java object.
		 */
        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("primaryTransactionList", primaryTransactionList);
		
		return "primaryAccount";
	}

	@RequestMapping("/savingsAccount")
    public String savingsAccount(Model model, Principal principal) {
		List<SavingsTransaction> savingsTransactionList = transactionService.findSavingsTransactionList(principal.getName());
        User user = userService.findByUsername(principal.getName());
        SavingsAccount savingsAccount = user.getSavingsAccount();

        model.addAttribute("savingsAccount", savingsAccount);
        model.addAttribute("savingsTransactionList", savingsTransactionList);

        return "savingsAccount";
    }
	
	/*
	 *  When HTTP GET request is sent to a certain URL and that URL contains a path which
	 * is declared on the @GetMapping annotation this method will be called. 
	 */
	@RequestMapping(value = "/deposit", method = RequestMethod.GET)
    public String deposit(Model model) {
		 
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "deposit";
    }

	/*
	 * This method reacts to a HTTP POST request containing a HTML body and stores
	 * it in the database using JPA. We get the content of the user coming is as part of the 
	 * request body.
	 * 
	 * The this method will be a web service end point that converts view
	 * paylod into a java object.
	 * 
	 * When HTTP POST request is sent to a certain URL and that URL contains a path which
	 * is declared on the @PostMapping annotation then this method will be called.
	 * 
	 * The @ModelAttribute annotation parameter lets the system retrieve a variable from the 
	 * context of the HTML form body and bind the value to the object in this method.
	 */
    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public String depositPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
        accountService.deposit(accountType, Double.parseDouble(amount), principal);

        return "redirect:/userFront";
    }
    
    @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    public String withdraw(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "withdraw";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public String withdrawPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
        accountService.withdraw(accountType, Double.parseDouble(amount), principal);

        return "redirect:/userFront";
    }
}
