package com.kokabmedia.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kokabmedia.domain.Appointment;
import com.kokabmedia.service.AppointmentService;

/*
* This classes function is to handle HTTP requests, responses and expose recourses to 
* other applications, more specifically the Angular build admin section of this application.
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
 * "api/appointment", this class will be called and response if needed. 
 * 
 * When a GET, POST, PUT or DELETER HTTP request is sent to the URL path with
 * the extension "/api/appointment" then the appropriate method in the class will respond.
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
@RequestMapping("/api/appointment")
@PreAuthorize("hasRole('ADMIN')") // For admin logging purposes
public class AppointmentResource {

	/*
	 * The @Autowired annotation tells the Spring framework that the AppointmentService bean 
	 * and its implementation is an dependency of AppointmentResource class. It is a mechanism 
	 * for implementing Spring dependency injection.
	 * 
	 * @Autowired annotation enables dependency injection with Spring framework to avoid 
	 * tight coupling and enable loose coupling by calling a interface or the implementation 
	 * of an interface.
	 * 
	 * The Spring framework creates a instance (bean) of the AppointmentService or its implementation 
	 * and inject (autowires) that instance into the AppointmentResource object when it is instantiated 
	 * as a autowired dependency.
	 * 
	 * The AppointmentService and its implementation is now a dependency of the AppointmentResource class.
	 */
    @Autowired
    private AppointmentService appointmentService;

    @RequestMapping("/all")
    public List<Appointment> findAppointmentList() {
        List<Appointment> appointmentList = appointmentService.findAll();

        return appointmentList;
    }

    /*
	 * This method returns an appointment with a specific id from the database using JPA.
	 * 
	 * The @GetMapping annotation will bind and make confirmAppointment() method respond 
	 * to a HTTP GET request.
	 * 
	 * The (path = "/{id}") parameter allows the method to read the appended
	 * String after the URL http://localhost:8080/api/appointment/ as a path variable 
	 * that is attached, so when a Long is appended after http://localhost:8080/api/appointment/
	 * with a GET HTTP request the confirmAppointment() method is called. The name of the "/{id}" 
	 * parameter must match the @PathVariable annotation argument Long id.
	 */
    @RequestMapping("/{id}/confirm")
    /*
	 * The @PathVariable annotation will make the path variable in the URL available
	 * for this confirmAppointment method via the method argument. When a appointment 
	 * id Long is appended to http://localhost:8080/{id}/confirm it can be handled 
	 * by the confirmAppointment() method.
	 */
    public void confirmAppointment(@PathVariable("id") Long id) {
        appointmentService.confirmAppointment(id);
    }
}
