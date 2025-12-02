package com.clinibuddys.Model.banco_de_dados.criacao_tabelas;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
	private static final String URL_JDBC_PADRAO = "jdbc:sqlite:pacientes.db";

	// Método que estabelece conexão com o banco de dados "pacientes".
	public static Connection conectar() {

		try{
			//return DriverManager.getConnection(URL_JDBC_PADRAO); // método que retorna a conexão com "pacientes.db"
			Connection conn = DriverManager.getConnection(URL_JDBC_PADRAO);
			try(Statement stmt = conn.createStatement()) {
				stmt.execute("PRAGMA foreign_keys = ON;"); 
				return conn;
			}
		} catch (SQLException e) { // Retorna erro caso algum problema ocorra no driver.
			System.err.println("Erro ao conectar a um banco de dados: " + e.getMessage());
			return null;
		}
	}
}