package com.kokabmedia.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kokabmedia.domain.Appointment;
import com.kokabmedia.domain.User;
import com.kokabmedia.service.AppointmentService;
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
 * is declared on the @RequestMapping annotation, in this case the appended "/",
 * this class will called and response if needed. 
 * 
 * When a GET, POST, PUT or DELETER HTTP request is sent to the URL path with
 * the extension "/" then the appropriate method in the class will respond.
 * 
 * This is a request mapping for the entire class. 
 */
@RequestMapping("/appointment")
public class AppointmentController {

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
	 * and inject (autowires) that instance into the AppointmentController object when it is instantiated 
	 * as a autowired dependency.
	 * 
	 * The UserService bean and its implementation is now a dependency of the AppointmentController class.
	 */
	 @Autowired
	 private UserService userService;
	
	@Autowired
    private AppointmentService appointmentService;


	/*
	 *  When HTTP GET request is sent to a certain URL and that URL contains a path which
	 * is declared on the @GetMapping annotation this method will be called. 
	 */
    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public String createAppointment(Model model) {
        Appointment appointment = new Appointment();
        
        /* 
		 * With the model.addAttribute() method the attribute will be mapped to variable in the html file that contains the 
		 * variable "appointment" and a appointment will be shown coming in from the second parameter value. The model instance will make 
		 * it available  to the html view file, this will be handled by the Dispatcher Servlet. This will bind the model view 
		 * object to the java object.
		 */
        model.addAttribute("appointment", appointment);
        model.addAttribute("dateString", "");

        return "appointment";
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
	 * context of the HTML form body and bind the value to the Appointment object in this method.
	 */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public String createAppointmentPost(@ModelAttribute("appointment") Appointment appointment, @ModelAttribute("dateString") String date, Model model, Principal principal) throws ParseException {

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date d1 = format1.parse( date );
        appointment.setDate(d1);

        User user = userService.findByUsername(principal.getName());
        appointment.setUser(user);

        appointmentService.createAppointment(appointment);

        return "redirect:/userFront";
    }


}
