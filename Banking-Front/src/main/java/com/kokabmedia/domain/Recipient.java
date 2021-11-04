package com.kokabmedia.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * This is a entity class for the purpose of retrieving, creating, updating, deleting 
 * data with a database.
 * 
 * It will also act as a model class for values bounded to the HTML view file properties.
 * 
 * The @Entity annotation from javax.persistence enables the JPA framework to manage 
 * the Recipient class as a JPA entity. The Recipient class is an entity and will be mapped to a 
 * database table named Recipient by Hibernate. 
 */
@Entity
public class Recipient {

	/*
	 * The @Id annotation makes this field a primary key in the database table.
	 * 
	 * The @GeneratedValue annotation makes the Hibernate generate the primary key value.
	 * 
	 * Primary key will uniquely identify each row in a database table.
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String accountNumber;
    private String description;

    /*
	 * This field is for relation mapping purposes, it will hold an User object
	 * when it is mapped to it with the @OneToMany(mappedBy) annotation in the 
	 * User class. The Recipient table will have link to the User table with a 
	 * user_id column containing foreign key value.
	 * 
	 * The @ManyToOne annotation indicates that Recipient has a many to one relation 
	 * to User. One User can have many Recipients. 
	 * 
	 * JPA and Hibernate will with the @ManyToOne annotation on this field create a user_id 
	 * column in the Recipient table. The Recipient table is now owning the relationship.
	 * 
	 * The user_id column will link to a specific row in User table. Multiple  Recipient objects 
	 * (rows) can be linked to the same User row with user_id column. This entity is the owning
	 * side of the relationship.
	 * 
	 * The fetch strategy for the ManyToOne side of the relations is Eager Fetch and the details of 
	 * User entity will be fetched with the Recipient entity automatically.
	 */
    @ManyToOne
    @JoinColumn(name = "user_id")// Creates a column in the name user_id in the Recipient table
    /*
     * The @JsonIgnore annotation enables that when we return the JSON format of the Recipient 
     * object we are going to ignore User and break the loop between User and Recipient.
     */
    @JsonIgnore
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

