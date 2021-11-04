package com.kokabmedia.service.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kokabmedia.dao.UserDao;
import com.kokabmedia.domain.User;

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
public class UserSecurityService implements UserDetailsService {

    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(UserSecurityService.class);

    /*
	 * The @Autowired annotation tells the Spring framework that this object (bean)and its 
	 * implementation is an dependency of this class. It is a mechanism for implementing 
	 * Spring dependency injection.
	 * 
	 * @Autowired annotation enables dependency injection with Spring framework to avoid 
	 * tight coupling and enable loose coupling by calling a interface or the implementation 
	 * of an interface.
	 * 
	 * The Spring framework creates a instance (bean) of the UserDao or its implementation 
	 * and inject (autowires) that instance into the UserSecurityService object when it is 
	 * instantiated as a autowired dependency.
	 * 
	 * The UserDao bean and its implementation is now a dependency of the UserSecurityService 
	 * class.
	 */
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (null == user) {
            LOG.warn("Username {} not found", username);
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return user;
    }
}
