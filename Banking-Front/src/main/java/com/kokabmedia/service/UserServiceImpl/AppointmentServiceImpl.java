package com.kokabmedia.service.UserServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kokabmedia.dao.AppointmentDao;
import com.kokabmedia.domain.Appointment;
import com.kokabmedia.service.AppointmentService;

/*
* This class will work as a service object and a business logic layer with code that 
* implements the necessary functions that make the application act in a desired 
* manner.
* 
* The @Service annotation allows the Spring framework to creates an instance (bean) 
* of this class and manage it with the Spring Application Context (the IOC container)
* that maintains all the beans for the application.  
*
* The@Service annotation lets the Spring framework manage class as a Spring bean. 
* The Spring framework will find the bean with auto-detection when scanning the class 
* path with component scanning. It turns the class into a Spring bean at the auto-scan 
* time.
* 
* @Service annotation allows this class and to be wired in as dependency 
* to a another object or a bean with the @Autowired annotation.
* 
* The @Service annotation is a specialisation of @Component annotation for more specific 
* use cases.
*/
@Service
public class AppointmentServiceImpl implements AppointmentService {

	/*
	 * The @Autowired annotation tells the Spring framework that this object (bean) and its 
	 * implementation is an dependency of this class. It is a mechanism for implementing 
	 * Spring dependency injection.
	 * 
	 * @Autowired annotation enables dependency injection with Spring framework to avoid tight 
	 * coupling and enable loose coupling by calling a interface or the implementation of an 
	 * interface.
	 * 
	 * The Spring framework creates a instance (bean) of the AppointmentDao or its implementation 
	 * and inject (autowires) that instance into the AppointmentServiceImpl object when it is 
	 * instantiated as a autowired dependency.
	 * 
	 * The AppointmentDao bean and its implementation is now a dependency of the AppointmentServiceImpl class.
	 */
    @Autowired
    private AppointmentDao appointmentDao;

    public Appointment createAppointment(Appointment appointment) {
       return appointmentDao.save(appointment);
    }

    public List<Appointment> findAll() {
        return appointmentDao.findAll();
    }

    public Optional<Appointment> findAppointment(Long id) {
        return appointmentDao.findById(id);
    }

    public void confirmAppointment(Long id) {
        Appointment appointment = findAppointment(id).get();
        appointment.setConfirmed(true);
        appointmentDao.save(appointment);
    }
}
