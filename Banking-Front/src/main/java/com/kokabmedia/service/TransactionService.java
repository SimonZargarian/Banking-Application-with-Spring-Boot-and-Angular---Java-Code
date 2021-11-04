package com.kokabmedia.service;

import java.security.Principal;
import java.util.List;

import com.kokabmedia.domain.PrimaryAccount;
import com.kokabmedia.domain.PrimaryTransaction;
import com.kokabmedia.domain.Recipient;
import com.kokabmedia.domain.SavingsAccount;
import com.kokabmedia.domain.SavingsTransaction;

/*
 * This interface is a for the TransactionServiceImpl layer, this allows us to code against 
 * an interface and enforce loose coupling with the @Autowire annotation as per 
 * best practises.
 */
public interface TransactionService {
	List<PrimaryTransaction> findPrimaryTransactionList(String username);

    List<SavingsTransaction> findSavingsTransactionList(String username);

    void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction);

    void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction);
    
    void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction);
    void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction);
    
    void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws Exception;
    
    List<Recipient> findRecipientList(Principal principal);

    Recipient saveRecipient(Recipient recipient);

    Recipient findRecipientByName(String recipientName);

    void deleteRecipientByName(String recipientName);
    
    void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount);
}
