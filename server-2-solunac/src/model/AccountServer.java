package model;

import java.util.List;

public class AccountServer {

	private String accountName;
	private List<TransactionServer> listTransaction;

	public AccountServer() {

	}

	public AccountServer(String accountName, List<TransactionServer> listTransaction) {
		super();
		this.accountName = accountName;
		this.listTransaction = listTransaction;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public List<TransactionServer> getListTransaction() {
		return listTransaction;
	}

	public void setListTransaction(List<TransactionServer> listTransaction) {
		this.listTransaction = listTransaction;
	}

}
