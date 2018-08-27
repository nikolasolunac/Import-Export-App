package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import model.AccountServer;
import model.TransactionClient;
import model.TransactionServer;

public class SQLiteDB {

	private static SQLiteDB instance;

	Connection connection;
	AccountServer account;

	public static SQLiteDB getInstance() throws ClassNotFoundException, SQLException {
		if (instance == null) {
			instance = new SQLiteDB();
		}

		return instance;
	}

	private SQLiteDB() {
		account = new AccountServer();
	}

	private void connectToDatabase(String path, String dbName) throws ClassNotFoundException, SQLException, IOException {
		Class.forName(Util.getInstance().get("driver"));
		connection = DriverManager.getConnection(Util.getInstance().get("url") + path + dbName);
	}

	public void createTable(String tableName) throws SQLException {
		Statement statement = connection.createStatement();
		String sql = "CREATE TABLE " + tableName + "(Participant TEXT NOT NULL," + " Amount INTEGER NOT NULL,"
				   + " TxID INTEGER PRIMARY KEY AUTOINCREMENT)";

		System.out.println(sql);

		statement.executeUpdate(sql);
		
		statement.close();
	}

	public void createTableBalance() throws SQLException {
		Statement statement = connection.createStatement();
		String sql = "CREATE TABLE " + "BALANCE" + "(Account TEXT NOT NULL," + " Balance INTEGER NOT NULL,"
				   + " AccID INTEGER PRIMARY KEY AUTOINCREMENT)";

		System.out.println(sql);

		statement.executeUpdate(sql);
		statement.close();
	}

	public boolean insertData(List<TransactionServer> transactions, String path, String dbName, String tableName)
			throws SQLException, ClassNotFoundException, IOException {

		connectToDatabase(path, dbName);

		connection.setAutoCommit(false);

		createTable(tableName);
		
		
		for (TransactionServer transaction : transactions) {

			String participant = transaction.getReceiverName();
			double amount = transaction.getAmount();
			Statement stmt = connection.createStatement();
           
			String sql = "INSERT INTO " + tableName + " (Participant,Amount) " + "VALUES (" + "'" + participant + "'" + ", "
					+ amount + ");";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
		 }

		connection.commit();
		connection.close();

		return false;
	}
	
	public boolean insertDataBalance(List<AccountServer> accounts, String path, String dbName, String tableName)
			throws SQLException, ClassNotFoundException, IOException {

		connectToDatabase(path, dbName);

		connection.setAutoCommit(false);

		createTableBalance();
		
		
		for (AccountServer account : accounts) {

			String accountName = account.getAccountName();
			double amount = sumAllTransactionsOnAccount(account.getListTransaction());
			Statement stmt = connection.createStatement();
           
			String sql = "INSERT INTO " + tableName + " (Account,Balance) " + "VALUES (" + "'" + accountName + "'" + ", "
					+ amount + ");";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
		 }

		connection.commit();
		connection.close();

		return false;
	}
	
	
	public boolean transactionData(TransactionClient tc, String path, String dbName) throws SQLException, ClassNotFoundException, IOException, InterruptedException {
			
			

		connectToDatabase(path, dbName);

		connection.setAutoCommit(false);
		
		


			Statement stmt = connection.createStatement();

			String sql = null;

			if(tc.getAmount()>0) {
				 		sql = "BEGIN TRANSACTION; "
						 
							+"UPDATE BALANCE " + "SET Balance = Balance " + tc.getAmount()*-1 + " WHERE Account = '" + tc.getAccountName() + "'; "
							
							+"UPDATE BALANCE " + "SET Balance = Balance + " + tc.getAmount() + " WHERE Account = '" + tc.getReceiverName() + "'; "
					
							+"INSERT INTO " + tc.getAccountName() + "(Participant, Amount) " + "values('" + tc.getReceiverName() + "', " + tc.getAmount()*-1 + "); "
							
							+"INSERT INTO " + tc.getReceiverName() + "(Participant, Amount) " + "values('" + tc.getAccountName() + "', " + tc.getAmount() + "); "
							 
							+"COMMIT;";

			System.out.println(sql);
			stmt.executeUpdate(sql);
			System.out.println("Transaction succesfull!");
			stmt.close();
			}else {
				System.out.println("Please insert valid number next time...");
			}
			

		connection.commit();
		connection.close();
		

		return false;
	}
	

	private double sumAllTransactionsOnAccount(List<TransactionServer> transactions) {
		double sum = 0;
		 for (TransactionServer transaction : transactions) {
			sum+=transaction.getAmount();
		}
		 return sum;
	}
}
