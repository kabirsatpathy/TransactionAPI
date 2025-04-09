package com.app.controller;

import com.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private final AccountService accountService;

	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	// Create a new account
	@PostMapping("/create")
	public String createAccount(@RequestParam(name = "accountId") String accountId,
			@RequestParam(name = "initialBalance") double initialBalance) {
		accountService.createAccount(accountId, initialBalance);
		return "Account created successfully!";
	}

	// Get account balance
	@GetMapping("/{accountId}/balance")
	public double getBalance(@PathVariable(name = "accountId") String accountId) {
		return accountService.getBalance(accountId);
	}

	// Transfer money between accounts
	@PostMapping("/transfer")
	public String transferMoney(@RequestParam(name = "fromAccountId") String fromAccountId,
			@RequestParam(name = "toAccountId") String toAccountId, @RequestParam double amount) {
		accountService.transfer(fromAccountId, toAccountId, amount);
		return "Transfer successful!";
	}
}
