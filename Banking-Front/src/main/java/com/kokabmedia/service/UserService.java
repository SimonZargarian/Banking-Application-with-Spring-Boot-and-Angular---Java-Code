package com.kokabmedia.service;

import java.util.List;
import java.util.Set;

import com.kokabmedia.domain.User;
import com.kokabmedia.security.UserRole;

/*
 * This interface is a for the UserServiceImpl layer, this allows us to code against 
 * an interface and enforce loose coupling with the @Autowire annotation as per 
 * best practises.
 */
public interface UserService {
	User findByUsername(String username);

    User findByEmail(String email);

    boolean checkUserExists(String username, String email);

    boolean checkUsernameExists(String username);

    boolean checkEmailExists(String email);
    
    void save (User user);
    
    User createUser(User user, Set<UserRole> userRoles);
    
    User saveUser (User user); 
    
    List<User> findUserList();

    void enableUser (String username);

    void disableUser (String username);
}
