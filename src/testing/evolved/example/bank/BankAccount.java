package testing.evolved.example.bank;

public class BankAccount {
	private static int lastId = 0;
	private int id;
	private double balance = 0;
	
	public BankAccount() {
		this.id = ++lastId;
	}
	
	public void deposit(double amount) {
		handleNegativeAmount(amount);
		balance += amount;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public int getId() {
		return id;
	}

	public void withdraw(double amount) {
		handleNegativeAmount(amount);
		if (balance - amount < 0) {
			throw new IllegalArgumentException("Cannot withdraw " + amount + " from " + balance);
		}
		balance -= amount;
	}
	
	private void handleNegativeAmount(double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Negative amount: " + amount);
		}
	}

	/**
	 * Package-private, for internal use only, for example, for testing
	 * @param balance the balance to set
	 */
	void setBalance(double balance) {
		this.balance = balance;
	}
}
