package org.sp.tproject.main.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler {
    private static final String DATABASE_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String DATABASE_USERNAME = "pomodoro";
    private static final String DATABASE_PASSWORD = "1234";
    private Connection connection;

    public DatabaseHandler() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        this.connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
    }

    public ResultSet getItems() throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery("SELECT * FROM todolist");
    }

    public void addItem(TodoItem item) throws SQLException {
        String sql = "INSERT INTO todolist (task_idx, yy, mm, dd, todo, complete) VALUES (task_idx.nextval, ?, ?, ?, ?, ?)";
        //String sql = "INSERT INTO todolist (task_idx, yy, mm, dd, task, complete, client) VALUES (task_idx.nextval, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, item.getYear());
        statement.setInt(2, item.getMonth());
        statement.setInt(3, item.getDay());
        statement.setString(4, item.getTask());
        statement.setInt(5, item.isComplete() ? 1 : 0);
        //statement.setString(6, item.getClient());
        statement.executeUpdate();
    }


    public void updateItem(TodoItem item) throws SQLException {
        //String sql = "UPDATE todolist SET yy = ?, mm = ?, dd = ?, complete = ? WHERE task = ? AND client = ?";
        String sql = "UPDATE todolist SET yy = ?, mm = ?, dd = ?, complete = ? WHERE todo = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, item.getYear());
        statement.setInt(2, item.getMonth());
        statement.setInt(3, item.getDay());
        statement.setInt(4, item.isComplete() ? 1 : 0);
        statement.setString(5, item.getTask());
        //statement.setString(6, item.getClient());
        statement.executeUpdate();
    }
    
    public void deleteItem(TodoItem item) throws SQLException {
        //String sql = "DELETE FROM todolist WHERE task = ? AND client = ?";
        String sql = "DELETE FROM todolist WHERE todo = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, item.getTask());
            //pstmt.setString(2, item.getClient());
            pstmt.executeUpdate();
        }
    }

    public void clearItems() throws SQLException {
        String sql = "DELETE FROM todolist";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.executeUpdate();
        }
    }
    
}