package com.kokabmedia.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kokabmedia.security.Authority;
import com.kokabmedia.security.UserRole;

/*
 * This is a entity class for the purpose of retrieving, creating, updating, deleting 
 * data with a database.
 * 
 * It will also act as a model class for values bounded to the HTML view files properties.
 * 
 * The @Entity annotation from javax.persistence enables the JPA framework to manage 
 * the User class as a JPA entity. The User class is an entity and will be mapped to a 
 * database table named User by Hibernate. 
 * 
 * UserDetails interface is part of Spring Security and handles the updating of the user
 * data values.
 */
@Entity
public class User implements UserDetails{


	/*
	 * The @Id annotation makes this field a primary key in the database table.
	 * 
	 * The @GeneratedValue annotation makes the Hibernate generate the primary key value.
	 * 
	 * Primary key will uniquely identify each row in a database table.
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	// The name of the column is course_id and the field cannot have a null value 
    @Column(name = "userId", nullable = false, updatable = false) // update - cannot be updated once it has gotten the initial value.
    private Long userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    private String phone;

    private boolean enabled=true;

    /*
	 * Creates a OneToOne relationship mapping with the @OneToOne annotation, User can have
	 * one PrimaryAccount and one PrimaryAccount can be associated with one User.
	 *  
	 * JPA and Hibernate will with the @OneToOne annotation on this field create a primary_account_id 
	 * column with a foreign key value in the User_Details table. The User_Details table is now owning 
	 * the relationship. The  primary_account_id column will link to a specific row in the PrimaryAccount 
	 * table. 
	 * 
	 * The select User query will also get the passport details from the database with 
	 * Eager Fetch from both the User and the PrimaryAccount table.
	 * 
	 * User has a Unidirectional association with PrimaryAccount.
	 */
    @OneToOne
    private PrimaryAccount primaryAccount;

    @OneToOne
    private SavingsAccount savingsAccount;
  
    /* 
	 * This field is for relation mapping purposes, a User can have a list of multiple 
	 * Appointment.
	 * 
	 * The @OneToMany annotation indicates that User has one to many relationship mapping
	 * with Appointment, one course can have multiple reviews. The many side of the
	 * relationship will be the owning side. 
	 * 
	 * The select User query will also get the Appointment details from the database with 
	 * Eager Fetch from both the User and the Appointment table.
	 * 
	 * The mappedBy parameter is describing which table will be owning the relationship,
	 * The Appointment table will have a user_id column with a foreign key value and will 
	 * be owning the relationship. The user_id column in the Appointment table will have a 
	 * link to specific row in the User table. Multiple Appointment can be associated with
	 * the same User.
	 * 
	 * The mappedBy parameter is set to the non owning side of the relationship. The
	 * mappedBy parameter makes sure that a appointment_id column is not created in the 
	 * User table with a foreign key value.
	 * 
	 * The fetch strategy for the OneToMany side of the relations is Lazy Fetch. We can 
	 * change the the fetch type parameter fetch=FetchType.Eager this will change the fetching 
	 * strategy of the Appointment entity so that the fetch type is Eager and Hibernate will fetch 
	 * the Appointment data when fetching the User data.
	 * 
	 * The CascadeType.ALL parameter control how changes are cascaded from parent object to 
	 * child objects, if a User row is updated the all Appointment that are associated with 
	 * that User will all so be updated.
	 */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    /*
     * The @JsonIgnore annotation enables that when we return the JSON format of the User 
     * object we are going to ignore Appointment and break the loop between Appointment and User.
     */
    @JsonIgnore
    private List<Appointment> appointmentList;

    // Bidirectional 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Recipient> recipientList;

    // Bidirectional 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public List<Recipient> getRecipientList() {
        return recipientList;
    }

    public void setRecipientList(List<Recipient> recipientList) {
        this.recipientList = recipientList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PrimaryAccount getPrimaryAccount() {
        return primaryAccount;
    }

    public void setPrimaryAccount(PrimaryAccount primaryAccount) {
        this.primaryAccount = primaryAccount;
    }

    public SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    public void setSavingsAccount(SavingsAccount savingsAccount) {
        this.savingsAccount = savingsAccount;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

	/*
	 * The purpose of this method is to returns a textual representation 
	 * of the object, instead of for example hash code in the logger.
	 */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", appointmentList=" + appointmentList +
                ", recipientList=" + recipientList +
                ", userRoles=" + userRoles +
                '}';
    }

    // UserDetails interface implemented methods art of Spring Security 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        userRoles.forEach(ur -> authorities.add(new Authority(ur.getRole().getName())));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}
