
public class BankAccount {

	private double balance;
	
	public BankAccount(double balance) {
		this.balance = balance;
	}
	
	public void withdraw(double amount) throws NegativeBalanceException {
		if (amount > balance) {
			throw new NegativeBalanceException(balance - amount);
		} else
			balance = balance - amount;
	}
	
	public void handleTransaction(double amount) {
		try {
			withdraw(amount);
		} catch (NegativeBalanceException e) {
			System.out.println(e);
		}
	}
	
	public static void main(String[] args) {
		BankAccount acct = new BankAccount(500.0);
		acct.handleTransaction(600.00);
	}

}
