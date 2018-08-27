package importfile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.AccountServer;
import model.FileType;
import model.TransactionServer;

public class XLSXImport extends AbstractImport {

	private int numberOfAccounts;

	public XLSXImport(FileType fileType, String path, String fileName, int numberOfAccounts) {
		this.fileType = fileType;
		this.fileName = fileName;
		this.path = path;
		this.numberOfAccounts = numberOfAccounts;
	}

	@Override
	public List<AccountServer> prepareSource() throws IOException, ClassNotFoundException, SQLException {

		List<AccountServer> accountList = new LinkedList<>();

		try {

			FileInputStream excelFile = new FileInputStream(getFullFileName());

			Workbook workbook = new XSSFWorkbook(excelFile);

			String accountName;
			for (int s = 0; s < numberOfAccounts; s++) {
				Sheet sheet = workbook.getSheetAt(s);
				accountName = workbook.getSheetName(s);
				AccountServer account = new AccountServer();
				
				List<TransactionServer> transactionList = new LinkedList<>();

				Row row;
				for (int r = 0; r <= sheet.getLastRowNum(); r++) {
					row = (Row) sheet.getRow(r);
					TransactionServer transaction = new TransactionServer();
					String receiverName = row.getCell(0) == null ? "null" : row.getCell(0).toString();

					String amount = row.getCell(1) == null ? "0" : row.getCell(1).toString();

					transaction.setReceiverName(receiverName);
					transaction.setAmount(Double.parseDouble(amount));
					transactionList.add(transaction);
				}
				account.setAccountName(accountName);
				account.setListTransaction(transactionList);
				accountList.add(account);
			}
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return accountList;
		
	}
}
