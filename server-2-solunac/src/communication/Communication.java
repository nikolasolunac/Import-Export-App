package communication;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

import database.SQLiteDB;
import model.AccountServer;
import model.FileType;
import model.TransactionServer;
import model.TransactionClient;
import importfile.AbstractImport;
import importfile.FactoryImport;
import exportfile.AbstractExport;
import exportfile.FactoryExport;

public class Communication {

	Socket socket;
	DataInputStream inputStream;

	public void connect(String[] args) throws IOException, ClassNotFoundException, SQLException {

		ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[3]));
		System.out.println("Server is running");

		socket = serverSocket.accept();
		try {
			String[] pathRead = args[0].split("/");
			String inputFileNameRead = pathRead[pathRead.length - 1];
			String inputPathRead = args[0].replace(pathRead[pathRead.length - 1], "");
			FileType inputFileTypeRead = FileType.valueOf(inputFileNameRead.split("\\.")[1].toUpperCase());
			int numberOfAccounts = Integer.parseInt(args[2]);

			FactoryImport factoryReader = new FactoryImport();
			AbstractImport reader = factoryReader.getImport(inputFileTypeRead, inputPathRead, inputFileNameRead,
					numberOfAccounts);

			List<AccountServer> accountList = reader.prepareSource();
			
			for (AccountServer account : accountList) {
				System.out.println(account.getAccountName());
				for (TransactionServer transaction : account.getListTransaction()) {
					System.out.print(transaction.getReceiverName() + " ");
					System.out.println(transaction.getAmount());
				}
			}

			String[] pathWrite = args[1].split("/");
			String inputFileNameWrite = pathWrite[pathWrite.length - 1];
			String inputPathWrite = args[1].replace(pathWrite[pathWrite.length - 1], "");
			FileType inputFileTypeWrite = FileType.valueOf(inputFileNameWrite.split("\\.")[1].toUpperCase());

			FactoryExport factoryWriter = new FactoryExport();
			AbstractExport writer = factoryWriter.getExport(inputFileTypeWrite, inputPathWrite, inputFileNameWrite);

			for (AccountServer account : accountList) {
				writer.exportResource(account.getListTransaction(), account.getAccountName());

			}

			writer.exportResourceBalance(accountList, "BALANCE");
			
			TransactionClient tc = readData();
			System.out.println(tc.getAccountName() + " " + tc.getReceiverName() + " " + tc.getAmount());
			
			SQLiteDB.getInstance().transactionData(tc, inputPathWrite, inputFileNameWrite);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}
	
	private TransactionClient readData() throws IOException, ClassNotFoundException, SQLException {
		inputStream = new DataInputStream(socket.getInputStream());
		
		int lengthAccountName=inputStream.readInt();
		byte[] dataAccountName = new byte[lengthAccountName];
		inputStream.readFully(dataAccountName);
		String accountName =new String(dataAccountName,"UTF-8");
		
		int lengthReceiverName=inputStream.readInt();
		byte[] dataReceiverName = new byte[lengthReceiverName];
		inputStream.readFully(dataReceiverName);
		String receiverName =new String(dataReceiverName,"UTF-8");
		
		
		double amount = inputStream.readDouble();
		
		TransactionClient transactionClient = new TransactionClient(accountName, receiverName, amount);
		
		return transactionClient;
		}
}
