package exportfile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import model.AccountServer;
import model.FileType;
import model.TransactionServer;

public abstract class AbstractExport {

	protected String path;
	protected String fileName;
	protected FileType fileType;

	public abstract boolean exportResource(List<TransactionServer> transactions, String tableName)
			throws IOException, ClassNotFoundException, SQLException;

	public abstract boolean exportResourceBalance(List<AccountServer> accounts, String tableName)
			throws IOException, ClassNotFoundException, SQLException;

	public AbstractExport() {

	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String name) {
		this.fileName = name;
	}

	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}
}
