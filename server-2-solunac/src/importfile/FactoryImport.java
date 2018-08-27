package importfile;

import model.FileType;
import importfile.AbstractImport;
import importfile.TXTImport;

public class FactoryImport {

	public AbstractImport getImport(FileType fileType, String path, String fileName, int numberOfAccounts) {
        switch (fileType) {
        	case XLSX:
        		return new XLSXImport(fileType, path, fileName, numberOfAccounts);    
        	case TXT:
                return new TXTImport(fileType, path, fileName, numberOfAccounts); 
            default:
                return null;
        }
    }
}
