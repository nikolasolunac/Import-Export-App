package database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Util {

	private Properties props;
    private static Util instance;
    
    public Util() throws IOException{
        props = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");
        props.load(input);   
    }
    
    public static Util getInstance() throws IOException{
        if(instance==null){
            instance = new Util();
        }
        return instance;
    }
    
    public String get (String key){
        return props.getProperty(key, "");
    }
}
