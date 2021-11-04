package com.kokabmedia.service;

import java.security.Principal;

import com.kokabmedia.domain.PrimaryAccount;
import com.kokabmedia.domain.PrimaryTransaction;
import com.kokabmedia.domain.SavingsAccount;
import com.kokabmedia.domain.SavingsTransaction;

/*
 * This interface is a for the AccountServiceImpl layer, this allows us to code against 
 * an interface and enforce loose coupling with the @Autowire annotation as per 
 * best practises.
 */
public interface AccountService {
	PrimaryAccount createPrimaryAccount();
    SavingsAccount createSavingsAccount();
    void deposit(String accountType, double amount, Principal principal);
    void withdraw(String accountType, double amount, Principal principal);
    
    
}
