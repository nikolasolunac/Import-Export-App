package model;

public class TransactionServer {

	private String receiverName;
	private double amount;
	
	public TransactionServer() {
		super();
	}

	public TransactionServer(String receiverName, double amount) {
		super();
		this.receiverName = receiverName;
		this.amount = amount;
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
