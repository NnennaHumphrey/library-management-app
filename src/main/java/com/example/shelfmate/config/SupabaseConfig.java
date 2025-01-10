package com.example.shelfmate.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class SupabaseConfig implements CommandLineRunner {

    // JDBC URL with placeholders filled in
    private static final String JDBC_URL = "jdbc:postgresql://aws-0-eu-west-2.pooler.supabase.com:6543/postgres";
    private static final String DB_USER = "postgres.hjxtqidftooykyhtzmxd";
    private static final String DB_PASSWORD = "RES-ponsible2020";

    @Override
    public void run(String... args) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Connected to the Supabase PostgreSQL database!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
