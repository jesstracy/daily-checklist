package com.tiy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.h2.tools.Server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class DailyChecklistApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyChecklistApplication.class, args);
		try {
			Server.createWebServer().start();

			Connection conn = DriverManager.getConnection("jdbc:h2:./main");
			Statement stmt = conn.createStatement();
			stmt.execute("CREATE TABLE IF NOT EXISTS users (id IDENTITY, firstName VARCHAR, lastName VARCHAR, email VARCHAR, password VARCHAR)");
			stmt.execute("CREATE TABLE IF NOT EXISTS todos (id IDENTITY, description VARCHAR, isDone BOOLEAN, userId INT)");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
