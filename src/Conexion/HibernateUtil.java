/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Alan
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static HibernateUtil instance;
    private Session session = null;
    
    public HibernateUtil(){
        
    }
    
    public static HibernateUtil getInstance(){
        if( instance == null ){
            instance = new HibernateUtil();
        }
        
        return instance;
    }
    
    public Session getSession(){
        
        if( this.session == null || !this.session.isOpen() ){
            this.session = sessionFactory.openSession();
        }
        
        return this.session;
    }
 
    private static SessionFactory buildSessionFactory() {
        
        /*try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new AnnotationConfiguration().configure().buildSessionFactory();
 
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }*/
        
        try{

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
            
            String url = properties.getProperty("hibernate.connection.url.base") + 
                    properties.getProperty("database.host") + ":" +
                    properties.getProperty("database.port") + "/" + 
                    properties.getProperty("database.schema");
            
            properties.setProperty("hibernate.connection.url", url);

            Configuration configuration = new Configuration();

            configuration.configure("hibernate.cfg.xml").addProperties(properties);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties()).build();

            //SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            
            return configuration.buildSessionFactory(serviceRegistry);

        }catch (IOException ex) {
            Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
  
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
 
    public void shutdown() {
    	// Close caches and connection pools
    	getSessionFactory().close();
    }
}
