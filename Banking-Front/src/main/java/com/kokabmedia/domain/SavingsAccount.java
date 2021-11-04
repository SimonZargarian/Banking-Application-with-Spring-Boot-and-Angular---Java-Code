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
 * the SavingsAccount class as a JPA entity. The SavingsAccount class is an entity and
 * will be mapped to a database table named Savings_Account by Hibernate. 
 */
@Entity
public class SavingsAccount {


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
	 * This field is for relation mapping purposes, a SavingsAccount can have a list of multiple
	 * SavingsTransaction.
	 * 
	 * The @OneToMany annotation indicates that SavingsAccount has one to many relationship mapping 
	 * with SavingsTransaction, one course can have multiple reviews. The many side of the relationship 
	 * will be the owning side. 
	 * 
	 * The select SavingsAccount query will also get the SavingsTransaction details from the database with 
	 * Eager Fetch from both the SavingsAccount and the SavingsTransaction table.
	 * 
	 * The mappedBy parameter is describing which table will be owning the relationship, The SavingsTransaction
	 * table will have a savings_account_id column with a foreign key value and will be owning the relationship. 
	 * The savings_account_id column in the SavingsTransaction table will have a link to specific row in the 
	 * SavingsAccount table. Multiple SavingsTransaction can be associated with the same SavingsAccount.
	 * 
	 * The mappedBy parameter is set to the non owning side of the relationship. The mappedBy parameter makes sure 
	 * that a savings_transactions_id column is not created in the SavingsAccount table with a foreign key value.
	 * 
	 * The fetch strategy for the OneToMany side of the relations is Lazy Fetch. We can change the the fetch type 
	 * parameter fetch=FetchType.Eager this will change the fetching strategy of the SavingsTransaction entity so 
	 * that the fetch type is Eager and Hibernate will fetch the SavingsTransaction data when fetching the 
	 * SavingsAccount data.
	 * 
	 * The CascadeType.ALL parameter control how changes are cascaded from parent object to child objects, if a 
	 * SavingsAccount row is updated the all SavingsTransaction that are associated with that SavingsAccount will 
	 * all so be updated.
	 */
    @OneToMany(mappedBy = "savingsAccount", cascade = CascadeType.ALL)
    /*
     * The @JsonIgnore annotation enables that when we return the JSON format of the SavingsAccount 
     * object we are going to ignore SavingsTransaction and break the loop between SavingsTransaction and SavingsAccount.
     */
    @JsonIgnore
    private List<SavingsTransaction> savingsTransactionList;

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

    public List<SavingsTransaction> getSavingsTransactionList() {
        return savingsTransactionList;
    }

    public void setSavingsTransactionList(List<SavingsTransaction> savingsTransactionList) {
        this.savingsTransactionList = savingsTransactionList;
    }


}
