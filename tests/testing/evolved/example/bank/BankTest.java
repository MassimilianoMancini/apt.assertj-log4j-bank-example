package testing.evolved.example.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class BankTest {
	
	private static final int AMOUNT = 5;
	private static final int INITIAL_BALANCE = 10;
	
	private Bank bank;
	
	// the collaborator of Bank that we manually instrument and inspect
	private List<BankAccount> bankAccounts;
	
	@Before
	public void setup() {
		bankAccounts = new ArrayList<BankAccount>();
		bank = new Bank(bankAccounts);
	}
	
	@Test
	public void testOpenNewAccountShouldReturnPositiveIdAndStoreTheAccount() {
		int newAccountId = bank.openNewBankAccount(0);
		assertThat(newAccountId).isPositive();
		assertThat(bankAccounts).
			hasSize(1).
			extracting(BankAccount::getId)
			.contains(newAccountId);
	}
	
	@Test
	public void testDepositWhenAccountIsNotFoundShouldThrow() {
		assertThatThrownBy(()->bank.deposit(1, AMOUNT))
			.isInstanceOf(NoSuchElementException.class)
			.hasMessage("No account found with id: 1");
	}
	
	@Test
	public void testDepositWhenAccountIsFoundShouldIncrementBalance() {
		// setup
		BankAccount testAccount = createTestAccount(INITIAL_BALANCE);
		bankAccounts.add(testAccount);
		// exercise
		bank.deposit(testAccount.getId(), AMOUNT);
		// verify
		assertThat(testAccount.getBalance()).isEqualTo(INITIAL_BALANCE + AMOUNT);
	}
	
		
	@Test
	public void testWithdrawWhenAccountIsFoundShouldDecrementBalance() {
		// setup
		BankAccount testAccount = createTestAccount(INITIAL_BALANCE);
		bankAccounts.add(testAccount);
		// exercise
		bank.withdraw(testAccount.getId(), AMOUNT);
		// verify
		assertThat(testAccount.getBalance()).isEqualTo(INITIAL_BALANCE-AMOUNT);
	}
	
	/**
	 * Utility method for creating a BankAccount for testing
	 */
	private BankAccount createTestAccount(double initialBalance) {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(initialBalance);
		return bankAccount;
	}

}