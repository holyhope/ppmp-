package DefaultNamespace;

public class BankAccount {
	
	private double balance;
	
	private final long 			 id;
	private final String		 firstname;
	private final String		 lastname;
	private final String		 currency;
	
	public BankAccount(long id, double balance, String firstname, String lastname,String currency) {
		super();
		this.balance = balance;
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.currency = currency;
	}
	
	public double getBalance(){
		return this.balance;
	}
	
	public void addMoney(double val){
		this.balance += val;
	}
	
	public boolean removeMoney(double val){
		if(balance - val >= 0){
			this.balance -= val;
			return true;
		}
		return false;
	}
	
	public String getCurrency(){
		return this.currency;
	}
	
	public boolean isValid(long id, String firstname, String lastname)
	{
		return (this.id==id && this.firstname.equals(firstname) && this.lastname.equals(lastname));

	}
}
