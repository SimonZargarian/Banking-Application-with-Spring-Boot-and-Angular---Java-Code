package com.kokabmedia.domain;

import java.math.BigDecimal;
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
 * the PrimaryTransaction class as a JPA entity. The PrimaryTransaction class is an entity and 
 * will be mapped to a database table named Primary_Transaction by Hibernate. 
 */
@Entity
public class PrimaryTransaction {

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
    private String description;
    private String type;
    private String status;
    private double amount;
    private BigDecimal availableBalance;

    public PrimaryTransaction() {}


    public PrimaryTransaction(Date date, String description, String type, String status, double amount, BigDecimal availableBalance, PrimaryAccount primaryAccount) {
        this.date = date;
        this.description = description;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.availableBalance = availableBalance;
        this.primaryAccount = primaryAccount;
    }

    /*
	 * This field is for relation mapping purposes, it will hold an PrimaryAccount object
	 * when it is mapped to it with the @OneToMany(mappedBy) annotation in the PrimaryAccount
	 * class. The PrimaryTransaction table will have link to the PrimaryAccount table with a 
	 * primary_account_id column containing foreign key value.
	 * 
	 * The @ManyToOne annotation indicates that PrimaryTransaction has a many to one relation 
	 * to PrimaryAccount. One PrimaryAccount can have many PrimaryTransactions. 
	 * 
	 * JPA and Hibernate will with the @ManyToOne annotation on this field create a primary_account_id 
	 * column in the PrimaryTransaction table. The PrimaryTransaction table is now owning the relationship.
	 * 
	 * The primary_account_id column will link to a specific row in PrimaryAccount table. Multiple 
	 * PrimaryTransaction objects (rows) can be linked to the same PrimaryAccount row with primary_account_id
	 * column. This entity is the owning side of the relationship.
	 * 
	 * The fetch strategy for the ManyToOne side of the relations is Eager Fetch and the details of 
	 * PrimaryAccount entity will be fetched with the PrimaryTransaction entity automatically.
	 */
    @ManyToOne
    @JoinColumn(name = "primary_account_id") // Creates a column in the name primary_account_id in the PrimaryTransaction table
    private PrimaryAccount primaryAccount;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public PrimaryAccount getPrimaryAccount() {
        return primaryAccount;
    }

    public void setPrimaryAccount(PrimaryAccount primaryAccount) {
        this.primaryAccount = primaryAccount;
    }

}
