package exportfile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import database.SQLiteDB;
import model.AccountServer;
import model.FileType;
import model.TransactionServer;

public class SQLite3Export extends AbstractExport {

	public SQLite3Export(FileType fileType, String path, String fileName) {
        this.fileType = fileType;
        this.fileName = fileName;
        this.path = path;
    }

    @Override
    public boolean exportResource(List<TransactionServer> transactions, String tableName) throws IOException, ClassNotFoundException, SQLException {
    	
    	SQLiteDB.getInstance().insertData(transactions, getPath(), getFileName(), tableName);
        
    	return true;
    }

	@Override
	public boolean exportResourceBalance(List<AccountServer> accounts, String tableName) throws IOException, ClassNotFoundException, SQLException {

		SQLiteDB.getInstance().insertDataBalance(accounts, getPath(), getFileName(), tableName);
		
		return true;
	}
}
