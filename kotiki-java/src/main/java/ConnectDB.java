import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB
{
    public static void main(String[] args) throws SQLException
    {
        Connection connection;
        connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/kotiki-db", "postgres", "4228");
        if (connection == null) {
            System.out.println("Connection failed");
        } else{
            System.out.println("Connected");
        }

    }
}
