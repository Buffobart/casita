/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;
import java.sql.Connection;
import com.mysql.jdbc.jdbc2.optional.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClsConexion {
    
    private static Connection conection=null;
    
    public Connection getConection(){
        
        if( conection == null ){
            try {
            
                Properties properties = new Properties();
                //properties.load(new FileInputStream("db.properties"));
                FileInputStream file;

                //the base folder is ./, the root of the main.properties file  
                String path = "./database.properties";
                
                //load the file handle for main.properties
                file = new FileInputStream(path);
                
                //load all the properties from this file
                properties.load(file);

                //we have loaded the properties, so close the file handle
                file.close();
                
                MysqlConnectionPoolDataSource ds=new MysqlConnectionPoolDataSource();
                //ds.setServerName("192.168.1.70");
                ds.setServerName(properties.getProperty("database.host"));
                ds.setPort(Integer.parseInt(properties.getProperty("database.port")));
                ds.setDatabaseName(properties.getProperty("database.schema"));
                
                String user = properties.getProperty("hibernate.connection.username");
                String pass = (properties.getProperty("hibernate.connection.password"));
                
                conection=ds.getConnection(user,pass);
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ClsConexion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ClsConexion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ClsConexion.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        return conection;
    }
    
}
