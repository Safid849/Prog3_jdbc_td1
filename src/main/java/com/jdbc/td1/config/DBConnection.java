package com.jdbc.td1.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/product_management_db";
    private static final String USER = "product_manager_user";
    private static final String PASSWORD = "123456";

    public Connection getDBConnection() {
        try {
            Class.forName("org.postgresql.Driver");

            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (SQLException e) {
            throw new RuntimeException(" Erreur de connexion à la base PostgreSQL : " + e.getMessage(), e);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(" Driver PostgreSQL introuvable. Assure-toi d'avoir le driver JDBC.", e);
        }
    }
}
