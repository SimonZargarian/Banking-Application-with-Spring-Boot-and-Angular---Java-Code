package com.kokabmedia.service.UserServiceImpl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kokabmedia.dao.RoleDao;
import com.kokabmedia.dao.UserDao;
import com.kokabmedia.domain.User;
import com.kokabmedia.security.UserRole;
import com.kokabmedia.service.AccountService;
import com.kokabmedia.service.UserService;

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
/*
 * A Transaction involves multiple changes to the database data.
 * 
 * The @Transactional annotation on the class level means that all method in this class will run within the 
 * boundaries of an transaction. If code that apply changes to the database is outside the scope of an
 * transaction it will fail, without the Transaction we will not have a connection to the database.
 * 
 * Transaction handles the relationship with the different database tables (relations).
 * 
 * @Transactional annotation allow us to to make a change in data that effect the database data like updating
 * a Student, the update method and other Entity Manager methods will be contained within a transaction, 
 * the Transaction makes sure that all the actions (steps) that change data in the database are successful 
 * or else the transaction (the process) is rolled back (reversed), if the first step fails then second 
 * step will be reversed.
 * 
 * While a process is within the scope of a transaction the Entity Manager and the Persistence Context
 * keeps track to all the operations and modifications and persist them to the database. The entity  
 * instances and the changes to those instances will be stored in the Persistent Context while the 
 * Transaction is operating. At the start of a Transaction a Persistence Context is created and it is
 * closed at the end of the Transaction.
 * 
 * Only when a transaction (for example within a class or a method) is completed will the database 
 * changes sent out to the database by Hibernate. If there is no transaction then the Persistence Context 
 * will be closed after each Entity Manager method call as each method call will be its own Hibernate session.
 * The Persistence Context will live though the length of a method or as long as the Transaction is running.
 * 
 * Each Transaction in the application is associated with it own Persistence Context that manage the 
 * entities within that specific transaction and every method call with this class will have its own
 * transaction with its own Persistence Context.
 */
@Transactional
public class UserServiceImpl implements UserService{
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	 /*
		 * The @Autowired annotation tells the Spring framework that this object (bean) and its 
		 * implementation is an dependency of this class. It is a mechanism for implementing 
		 * Spring dependency injection.
		 * 
		 * @Autowired annotation enables dependency injection with Spring framework to avoid 
		 * tight coupling and enable loose coupling by calling a interface or the implementation 
		 * of an interface.
		 * 
		 * The Spring framework creates a instance (bean) of the UserDao or its implementation 
		 * and inject (autowires) that instance into the UserServiceImpl object when it is 
		 * instantiated as a autowired dependency.
		 * 
		 * The UserDao bean and its implementation is now a dependency of the UserServiceImpl class.
		 */
	@Autowired
	private UserDao userDao;
	
	@Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder123;
    
    @Autowired
    private AccountService accountService;
	
	public void save(User user) {
        
		 
	            String encryptedPassword = passwordEncoder123.encode(user.getPassword());
	            user.setPassword(encryptedPassword);

	           user.setPrimaryAccount(accountService.createPrimaryAccount());
	            user.setSavingsAccount(accountService.createSavingsAccount());
	       
		
		userDao.save(user);
        
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
    
   
    
    public User createUser(User user, Set<UserRole> userRoles) {
        User localUser = userDao.findByUsername(user.getUsername());

        if (localUser != null) {
            LOG.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
        } else {
            String encryptedPassword = passwordEncoder123.encode(user.getPassword());
            user.setPassword(encryptedPassword);

            for (UserRole ur : userRoles) {
                roleDao.save(ur.getRole());
            }

            user.getUserRoles().addAll(userRoles);

            user.setPrimaryAccount(accountService.createPrimaryAccount());
            user.setSavingsAccount(accountService.createSavingsAccount());

            localUser = userDao.save(user);
        }

        return localUser;
    }
    
    public boolean checkUserExists(String username, String email){
        if (checkUsernameExists(username) || checkEmailExists(username)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUsernameExists(String username) {
        if (null != findByUsername(username)) {
            return true;
        }

        return false;
    }
    
    public boolean checkEmailExists(String email) {
        if (null != findByEmail(email)) {
            return true;
        }

        return false;
    }

    public User saveUser (User user) {
        return userDao.save(user);
    }
    
    public List<User> findUserList() {
        return userDao.findAll();
    }

    public void enableUser (String username) {
        User user = findByUsername(username);
        user.setEnabled(true);
        userDao.save(user);
    }

    public void disableUser (String username) {
        User user = findByUsername(username);
        user.setEnabled(false);
        System.out.println(user.isEnabled());
        userDao.save(user);
        System.out.println(username + " is disabled.");
    }
}
