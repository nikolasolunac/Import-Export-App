package importfile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import model.AccountServer;
import model.FileType;

public abstract class AbstractImport {

	protected String path;
	protected String fileName;
	protected FileType fileType;

	public abstract List<AccountServer> prepareSource() throws IOException, ClassNotFoundException, SQLException;

	public AbstractImport() {

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

	protected File getFullFileName() throws IOException {
		File dir = new File(getPath() + "/" + getFileName());
		return dir;

	}
}
