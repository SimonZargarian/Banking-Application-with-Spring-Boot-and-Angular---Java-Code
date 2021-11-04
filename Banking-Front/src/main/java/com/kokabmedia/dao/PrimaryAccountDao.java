package com.kokabmedia.dao;

import com.kokabmedia.domain.PrimaryAccount;
import org.springframework.data.repository.CrudRepository;

/*
 * Interface that gives access to to CRUD methods for handling data in a database,
 * the CrudRepository interface has methods that perform SQL queries and lets the 
 * application create and update data in the database, it takes an entity class 
 * and the primary key type of that entity as argument.
 *  
 * CrudRepository is an abstraction over EntityManager.
 */
public interface PrimaryAccountDao extends CrudRepository<PrimaryAccount,Long> {

	/* 
	 * JPA custom method with special designed names lets Spring understands that we 
	 * want to retrieve a specific column from the database. 
	 */
    PrimaryAccount findByAccountNumber (int accountNumber);
}
