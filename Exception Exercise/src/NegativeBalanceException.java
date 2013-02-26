
public class NegativeBalanceException extends Exception {

	private double balance;
	
	public NegativeBalanceException(double negativeBalance) {
		balance = negativeBalance;
	}
	
	@Override
	public String toString() {
		return "Negative Balances are not allowed! Balance would be $" + balance;
	}
}
