package testing.evolved.example.bank;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

public class BankAccountTest {

	private static final int AMOUNT = 5;
	private static final int INITIAL_BALANCE = 10;
	private static final double DELTA = 0.1;
	
	@Test
	public void testIdIsAutomaticallyAssignedAsPositiveNumber() {
		// setup
		BankAccount bankAccount = new BankAccount();
		// verify
		assertThat(bankAccount.getId()).isNotNegative();
	}
	
	@Test
	public void testIdsAreIncremental() {
		assertThat(new BankAccount().getId())
			.isLessThan(new BankAccount().getId());
	}
	
	@Test
	public void testDepositWhenAmountIsCorrectShouldIncreaseBalance() {
		// setup
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(INITIAL_BALANCE);
		// exercise
		bankAccount.deposit(AMOUNT);
		// verify
		assertThat(bankAccount.getBalance()).isEqualTo(INITIAL_BALANCE + AMOUNT);
		// or with offset
		assertThat(bankAccount.getBalance())
			.isCloseTo(INITIAL_BALANCE + AMOUNT - DELTA, byLessThan(DELTA));
	}
	
	@Test
	public void testDepositWhenAmountIsNegativeShouldThrow() {
		BankAccount bankAccount = new BankAccount();
		assertThatThrownBy(()->bankAccount.deposit(-1)).
			isInstanceOf(IllegalArgumentException.class).
			hasMessage("Negative amount: -1.0");
		// further assertions after the exception is thrown
		assertThat(bankAccount.getBalance()).isZero();
	}
	
	@Test
	public void testWithdrawWhenAmountIsNegativeShouldThrow() {
		BankAccount bankAccount = new BankAccount();
		assertThatThrownBy(()->bankAccount.withdraw(-1)).
			isInstanceOf(IllegalArgumentException.class).
			hasMessage("Negative amount: -1.0");
		// further assertions after the exception is thrown
		assertThat(bankAccount.getBalance()).isZero();
	}
	
	@Test
	public void testWithdrawWhenBalanceIsUnsufficientShouldThrow() {
		BankAccount bankAccount = new BankAccount();
		assertThatThrownBy(()->bankAccount.withdraw(AMOUNT))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Cannot withdraw " + AMOUNT + ".0 from 0.0");
		assertThat(bankAccount.getBalance()).isZero();
	}
	
	@Test
	public void testWithdrawWhenBalanceIsSufficientShouldDecreaseBalance() {
		// setup
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(INITIAL_BALANCE);
		// exercise
		bankAccount.withdraw(AMOUNT); // the method we want to test;
		// verify
		assertThat(bankAccount.getBalance()).isEqualTo(INITIAL_BALANCE - AMOUNT);
	}
}
