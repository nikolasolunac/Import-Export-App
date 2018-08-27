package model;

public class TransactionClient {

	private String accountName;
	private String receiverName;
	private double amount;
	
	public TransactionClient(String accountName, String receiverName, double amount) {
		super();
		this.accountName = accountName;
		this.receiverName = receiverName;
		this.amount = amount;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
