package communication;

import java.io.IOException;

public class MainClient {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Communication communication = new Communication();
        communication.connect(args);
	}

}
