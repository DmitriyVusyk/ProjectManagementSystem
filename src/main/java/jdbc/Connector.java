package jdbc;

import java.sql.*;

public class Connector {

    private String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    private String DB_PATH = "localhost";
    private String DB_NAME = "MODULE1_DB";
    private String DB_LOGIN = "root";
    private String DB_PASSWORD = "admin12345";

    private Connection connection;
    private Statement statement;

    public Statement getStatement() {
        return statement;
    }

    public Connector() {
        initDBDriver();
        initConnection();
    }

    private void initDBDriver() {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initConnection() {
        String connectionURL = "jdbc:mysql://" + DB_PATH + "/" + DB_NAME;
        connectionURL += "?verifyServerCertificate=false&useSSL=true";
        connectionURL += "&";
        connectionURL += "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        try {
            connection = DriverManager.getConnection(connectionURL, DB_LOGIN, DB_PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
