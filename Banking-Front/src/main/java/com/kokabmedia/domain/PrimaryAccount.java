package com.kokabmedia.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * This is a entity class for the purpose of retrieving, creating, updating, deleting 
 * data with a database.
 * 
 * It will also act as a model class for values bounded to the HTML view file properties.
 * 
 * The @Entity annotation from javax.persistence enables the JPA framework to manage 
 * the PrimaryAccount class as a JPA entity. The PrimaryAccount class is an entity and 
 * will be mapped to a database table named Primary_Account by Hibernate. 
 */
@Entity
public class PrimaryAccount {

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
    private int accountNumber;
    private BigDecimal accountBalance;
    
	/* 
	 * This field is for relation mapping purposes, a PrimaryAccount can have a list of
	 * multiple PrimaryTransactions.
	 * 
	 * The @OneToMany annotation indicates that PrimaryAccount has one to many relationship 
	 * mapping with PrimaryTransaction, one PrimaryAccount can have multiple PrimaryTransaction. The many 
	 * side of the relationship will be the owning side. 
	 * 
	 * The select PrimaryAccount query will also get the PrimaryTransaction details from the database with 
	 * Eager Fetch from both the PrimaryAccount and the PrimaryTransaction table.
	 * 
	 * The mappedBy parameter is describing which table will be owning the relationship. 
	 * The PrimaryTransaction table will have a primary_account_id column with a foreign key value and will 
	 * be owning the relationship. The primary_account_id column in the PrimaryTransaction table will have a 
	 * link to specific row in the PrimaryAccount table. Multiple PrimaryTransaction can be associated with
	 * the same PrimaryAccount.
	 * 
	 * The mappedBy parameter is set to the non owning side of the relationship. The mappedBy parameter makes 
	 * sure that a primary_transaction_id column is not created in the PrimaryAccount table with a foreign key 
	 * value.
	 * 
	 * The fetch strategy for the OneToMany side of the relations is Lazy Fetch. We can change the the fetch 
	 * type parameter fetch=FetchType.Eager this will change the fetching strategy of the PrimaryTransaction
	 * entity so that the fetch type is Eager and Hibernate will fetch the PrimaryTransaction data when fetching 
	 * the PrimaryAccount data.
	 * 
	 * The CascadeType.ALL parameter control how changes are cascaded from parent object to 
	 * child objects, if a PrimaryAccount row is updated the all PrimaryTransaction that are associated with 
	 * that PrimaryAccount will all so be updated.
	 */
    @OneToMany(mappedBy = "primaryAccount", cascade = CascadeType.ALL)
    /*
     * The @JsonIgnore annotation enables that when we return the JSON format of the PrimaryAccount 
     * object we are going to ignore primaryTransactionList and break the loop between primaryTransactionList 
     * and PrimaryAccount.
     */
    @JsonIgnore 
    private List<PrimaryTransaction> primaryTransactionList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public List<PrimaryTransaction> getPrimaryTransactionList() {
        return primaryTransactionList;
    }

    public void setPrimaryTransactionList(List<PrimaryTransaction> primaryTransactionList) {
        this.primaryTransactionList = primaryTransactionList;
    }


}



