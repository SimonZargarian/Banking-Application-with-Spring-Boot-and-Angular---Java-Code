package com.kokabmedia.security;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
public class Role {
   
	/*
	 * The @Id annotation makes this field a primary key in the database table.
	 * 
	 * The @GeneratedValue annotation makes the Hibernate generate the primary key value.
	 * 
	 * Primary key will uniquely identify each row in a database table.
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int roleId;

    private String name;

	/* 
	 * This field is for relation mapping purposes, a Role can have a list of multiple 
	 * UserRole.
	 * 
	 * The @OneToMany annotation indicates that Role has one to many relationship 
	 * mapping with UserRole, one Role can have multiple UserRoles. The many side of the
	 * relationship will be the owning side. 
	 * 
	 * The select Role query will also get the UserRole details from the database with 
	 * Eager Fetch from both the Role and the UserRole table.
	 * 
	 * The mappedBy parameter is describing which table will be owning the relationship,
	 * The UserRole table will have a role_id column with a foreign key value and will 
	 * be owning the relationship. The role_id column in the UserRole table will have a 
	 * link to specific row in the Role table. Multiple UserRole can be associated with
	 * the same Role.
	 * 
	 * The mappedBy parameter is set to the non owning side of the relationship. The
	 * mappedBy parameter makes sure that a user_role_id column is not created in the 
	 * Role table with a foreign key value.
	 * 
	 * The fetch strategy for the OneToMany side of the relations is Lazy Fetch. We can 
	 * change the the fetch type parameter fetch=FetchType.Eager this will change the 
	 * fetching strategy of the PrimaryTransaction entity so that the fetch type is Eager 
	 * and Hibernate will fetch the UserRole data when fetching the Role data.
	 * 
	 * The CascadeType.ALL parameter control how changes are cascaded from parent object to 
	 * child objects, if a Role row is updated the all UserRole that are associated with 
	 * that Role will all so be updated.
	 */
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<UserRole> userRoles = new HashSet<>();

    public Role() {

    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }


}
