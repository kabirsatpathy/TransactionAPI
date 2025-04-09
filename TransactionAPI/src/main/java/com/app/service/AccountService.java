package com.app.service;

import com.app.model.Account;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {

	private final Map<String, Account> accounts = new HashMap<>();

	// Create a new account
	public void createAccount(String accountId, double initialBalance) {
		accounts.put(accountId, new Account(accountId, initialBalance));
	}

	// Get account balance
	public double getBalance(String accountId) {
		Account account = accounts.get(accountId);
		if (account == null) {
			throw new IllegalArgumentException("Account not found");
		}
		return account.getBalance();
	}

	// Transfer money between accounts
	public boolean transfer(String fromAccountId, String toAccountId, double amount) {
		Account fromAccount = accounts.get(fromAccountId);
		Account toAccount = accounts.get(toAccountId);

		if (fromAccount == null || toAccount == null) {
			throw new IllegalArgumentException("One or both accounts not found");
		}

		if (fromAccount.getBalance() < amount) {
			throw new IllegalArgumentException("Insufficient balance");
		}

		fromAccount.setBalance(fromAccount.getBalance() - amount);
		toAccount.setBalance(toAccount.getBalance() + amount);

		return true;
	}
}
