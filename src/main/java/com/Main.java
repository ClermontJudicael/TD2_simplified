package com;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = new DataSource();
        System.out.println("DATABASE_HOST: " + System.getenv("DATABASE_HOST"));
        System.out.println("DATABASE_USER: " + System.getenv("DATABASE_USER"));
        System.out.println("DATABASE_PASSWORD: " + System.getenv("DATABASE_PASSWORD"));
        System.out.println("DATABASE_NAME: " + System.getenv("DB_R"));

        try (Connection connection = dataSource.getConnection()) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connection established successfully!");
            } else {
                System.out.println("Failed to establish connection.");
            }
        } catch (SQLException e) {
            System.err.println("Error while checking connection status: " + e.getMessage());
            e.printStackTrace();
        } catch (RuntimeException re) {
            System.err.println("RuntimeException: " + re.getMessage());
            re.printStackTrace();
        }
    }
}