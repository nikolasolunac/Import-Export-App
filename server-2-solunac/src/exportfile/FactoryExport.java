package exportfile;

import model.FileType;

public class FactoryExport {

	public AbstractExport getExport(FileType fileType, String path, String fileName) {
        switch (fileType) {
            case SQLITE3:
                return new SQLite3Export(fileType, path, fileName);
            default:
                return null;
        }
    }
}
