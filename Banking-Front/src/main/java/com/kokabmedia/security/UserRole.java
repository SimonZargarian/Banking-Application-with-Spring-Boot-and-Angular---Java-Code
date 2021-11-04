package com.kokabmedia.security;

import javax.persistence.*;

import com.kokabmedia.domain.User;

/*
 * This is a entity class for the purpose of retrieving, creating, updating, deleting 
 * data with a database.
 * 
 * It will also act as a model class for values bounded to the HTML view file properties.
 * 
 * The @Entity annotation from javax.persistence enables the JPA framework to manage 
 * the Appointment class as a JPA entity. The Appointment class is an entity and will be mapped to a 
 * database table named Appointment by Hibernate. 
 */
@Entity
@Table(name="user_role")// Name of the table is user_role
public class UserRole {
    
	/*
	 * The @Id annotation makes this field a primary key in the database table.
	 * 
	 * The @GeneratedValue annotation makes the Hibernate generate the primary key value.
	 * 
	 * Primary key will uniquely identify each row in a database table.
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userRoleId;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }


    /*
	 * This field is for relation mapping purposes, it will hold an User object
	 * when it is mapped to it with the @OneToMany(mappedBy) annotation in the 
	 * User class. The UserRole table will have link to the User table with a 
	 * user_id column containing foreign key value.
	 * 
	 * The @ManyToOne annotation indicates that UserRole has a many to one relation 
	 * to User. One User can have many UserRoles. 
	 * 
	 * JPA and Hibernate will with the @ManyToOne annotation on this field create a user_id 
	 * column in the UserRole table. The UserRole table is now owning the relationship.
	 * 
	 * The user_id column will link to a specific row in User table. Multiple  UserRole objects 
	 * (rows) can be linked to the same User row with user_id column. This entity is the owning 
	 * side of the relationship.
	 * 
	 * The fetch strategy for the ManyToOne side of the relations is Eager Fetch and the details 
	 * of User entity will be fetched with the UserRole entity automatically.
	 */
    @ManyToOne(fetch = FetchType.EAGER)
    // The column that will represent specific rows of Users in the UerRole table.
    @JoinColumn(name = "user_id") 
    private User user;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    public UserRole() {}

    public long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}
