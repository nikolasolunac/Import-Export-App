package importfile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import model.AccountServer;
import model.FileType;
import model.TransactionServer;

public class TXTImport extends AbstractImport {

	private int numberOfAccounts;

	public TXTImport(FileType fileType, String path, String fileName, int numberOfAccounts) {
		this.fileType = fileType;
		this.fileName = fileName;
		this.path = path;
		this.numberOfAccounts = numberOfAccounts;
	}

	@Override
	public List<AccountServer> prepareSource() throws IOException {

		List<AccountServer> accountList = new ArrayList<>();

		try {

			String line;
			int i = 0;
			while (i < numberOfAccounts) {
				FileInputStream txtFile = new FileInputStream(getFullFileName());
				InputStreamReader streamReader = new InputStreamReader(txtFile);
				LineNumberReader in = new LineNumberReader(streamReader);
				AccountServer account = new AccountServer();
				List<TransactionServer> transactionList = new ArrayList<>();
				int lineNumber = i + 1;
				while ((line = in.readLine()) != null) {
					
					if (lineNumber == in.getLineNumber()) {
						account.setAccountName(line.toString());
					}
					if (line.contains(",")) {
						String[] lineAttributes = line.split(",");
						if (lineAttributes[0].equals(account.getAccountName())) {
							TransactionServer transaction = new TransactionServer();
							transaction.setReceiverName(lineAttributes[1]);
							transaction.setAmount(Double.parseDouble(lineAttributes[2]) * -1);
							transactionList.add(transaction);
						}

						if (lineAttributes[1].equals(account.getAccountName())) {
							TransactionServer transaction = new TransactionServer();
							transaction.setReceiverName(lineAttributes[0]);
							transaction.setAmount(Double.parseDouble(lineAttributes[2]));
							transactionList.add(transaction);
						}
						account.setListTransaction(transactionList);
					}
				}
				accountList.add(account);
				i++;
				in.close();
			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return accountList;
	}
}
