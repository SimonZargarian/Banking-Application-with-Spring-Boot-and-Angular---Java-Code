package com.kokabmedia.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class Appointment {

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
    private Date date;
    private String location;
    private String description;
    private boolean confirmed;

	/*
	 * This field is for relation mapping purposes, it will hold an User object
	 * when it is mapped to it with the @OneToMany(mappedBy) annotation in the 
	 * User class. The Appointment table will have link to the User table with a 
	 * user_id column containing foreign key value.
	 * 
	 * The @ManyToOne annotation indicates that Appointment has a many to one relation 
	 * to User. One User can have many reviews. 
	 * 
	 * JPA and Hibernate will with the @ManyToOne annotation on this field create
	 * a user_id column in the Appointment table. The Appointment table is now owning the 
	 * relationship.
	 * 
	 * The user_id column will link to a specific row in User table. Multiple 
	 * appointment objects (rows) can be linked to the same User row with user_id
	 * column. This entity is the owning side of the relationship.
	 * 
	 * The fetch strategy for the ManyToOne side of the relations is Eager Fetch
	 * and the details of User entity will be fetched with the Appointment entity 
	 * automatically.
	 */
    @ManyToOne
    @JoinColumn(name = "user_id") // Creates a column in the name user_id in the Appointment table
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", date=" + date +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }
}
