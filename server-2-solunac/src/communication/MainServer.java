package communication;

import java.io.IOException;
import java.sql.SQLException;

public class MainServer {

	public static void main(String[] args) throws IOException {
		Communication communication = new Communication();
        try {
			communication.connect(args);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
