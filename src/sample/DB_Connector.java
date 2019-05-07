package sample;

import java.sql.*;

public class DB_Connector {

    String url = "jdbc:mysql://den1.mysql6.gear.host:3306/pc2fma2?user=pc2fma2&password=r00tr00t!";

    Connection connection;
    Statement statement;
    ResultSet resultSet;

    void connect() {
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            System.err.print("Connection could not be established");
        }

    }

    public void update(String table, String column, String value) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate("'UPDATE " + table + " SET " + column + " = " + value + "'");

        } catch (SQLException ex) {
            System.err.print("Update failed");
        }
    }

    public void updateWhere(String table, String column, String value, String condition) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate("'UPDATE " + table + " SET " + column + " = " + value + " WHERE " + condition + "'");

        } catch (SQLException ex) {
            System.err.print("Update failed");
        }
    }


}

