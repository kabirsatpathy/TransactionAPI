package com.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.service.AccountService;

@SpringBootTest
class TransactionApiApplicationTests {

	@Autowired
	private AccountService accountService;

	@BeforeEach
	public void setup() {
		// Reset accounts or prepare the in-memory DB if necessary
		accountService.createAccount("ACC1", 1000);
		accountService.createAccount("ACC2", 500);
	}

	@Test
	public void testTransferMoney_Success() {
		// Try transferring money between two accounts
		accountService.transfer("ACC1", "ACC2", 200);

		// Verify that the balance is updated correctly
		assertEquals(800, accountService.getBalance("ACC1")); // from account should have 800
		assertEquals(700, accountService.getBalance("ACC2")); // to account should have 700
	}

	@Test
	public void testTransferMoney_InsufficientFunds() {
		// Try transferring more money than the account balance
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			accountService.transfer("ACC1", "ACC2", 1500); // not enough funds
		});
		assertEquals("Insufficient balance", thrown.getMessage());
	}

	@Test
	public void testGetAccountBalance() {
		// Test getting account balance
		int balance = (int) accountService.getBalance("ACC1");
		assertEquals(1000, balance);
	}
}
