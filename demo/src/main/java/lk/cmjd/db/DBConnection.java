package lk.cmjd.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;

    private DBConnection() throws Exception {
        Properties props = new Properties();

        // Java AUTOMATICALLY closes 'input' here, even if an error
        // occurred(try-with-resources)
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find db.properties");
            }
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading database configuration", e);
        }

        // Use the properties to establish the connection
        Class.forName(props.getProperty("db.driver"));
        connection = DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.username"),
                props.getProperty("db.password"));
    }

    public static DBConnection getInstance() throws Exception {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }

        return dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}
