package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {

    public static final String URL = "jdbc:postgresql://localhost:5432/kotiki-db";
    public static final String USER = "postgres";
    public static final String PASSWORD = "4228";

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }

    public static void main(String[] args){
        Connection connection = DbConnect.getConnection();
    }

}
