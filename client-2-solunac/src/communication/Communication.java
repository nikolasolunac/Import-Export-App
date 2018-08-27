package communication;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import communication.Communication;
import model.TransactionClient;


public class Communication {

	Socket socket;
	DataOutputStream outputStream;

	public void connect(String[] args) throws IOException {
		socket = new Socket("localhost", Integer.parseInt(args[0]));
		System.out.println("Client connected!");

		String accountName = args[1];
		String receiverName = args[2];
		double amount = Double.parseDouble(args[3]);

		TransactionClient tc = new TransactionClient(accountName, receiverName, amount);

		try {
			sendData(tc);
		} catch (Exception ex) {
			Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
		}

		socket.close();
	}

	public void sendData(TransactionClient tc) throws IOException {
			outputStream = new DataOutputStream(socket.getOutputStream());
			
			byte[] accountName = tc.getAccountName().getBytes("UTF-8");
			outputStream.writeInt(accountName.length);
			outputStream.write(accountName);
			
			byte[] receiverName = tc.getReceiverName().getBytes("UTF-8");
			outputStream.writeInt(receiverName.length);
			outputStream.write(receiverName);
			
			
			outputStream.writeDouble(tc.getAmount());
        
    }
}
