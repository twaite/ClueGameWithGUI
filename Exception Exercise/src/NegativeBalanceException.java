
public class NegativeBalanceException extends Exception {

	private double balance;
	
	public NegativeBalanceException(double negativeBalance) {
		balance = negativeBalance;
		System.out.println("I’ve been thinking. When life gives you lemons? Don’t make lemonade. " +
				"Make life take the lemons back! Get mad! I don’t want your damn lemons! What am I supposed " +
				"to do with these? Demand to see life’s manager! Make life rue the day it thought is could " +
				"give me lemons! Do you know who I am? I’m the man who’s going to burn your house down! With" +
				" the lemons! I’m going to get my engineers to invent a combustible lemon that" +
				" burns your house down!");
		System.out.println("Those of you helping us test the Repulsion Gel today, just follow the blue line " + 
				"on the floor. Those of you who volunteered to be injected with praying mantis DNA, I've got " +
				" some good news and some bad news. Bad news is we're postponing those tests indefinitely." +
				"Good news is we've got a much better test for you: fighting an army of mantis men. "+
				"Pick up a rifle and follow the yellow line. You'll know when the test starts.");
	}
	
	@Override
	public String toString() {
		return "Negative Balances are not DEFINITELY ABSOLUTELY UNDOUBTEDLY allowed! Balance would be $" + balance;
	}
}
