package com.clinibuddys.Model.banco_de_dados.criacao_tabelas;

import com.clinibuddys.Model.Historico;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.*;

// ==================== Cria Tabela usando comandos SQL ==================== //
public class CriaTabelaPaciente {
	
	public static void main(String[] args) {
        // Comando sql para gerar tabela
		final String SQL = "CREATE TABLE paciente (" +
						   "id INTEGER PRIMARY KEY, " +
						   "nome TEXT NOT NULL, " +
                           "especie TEXT NOT NULL," +
						   "raça TEXT NOT NULL, " +
                           "idade INTEGER, " +
                           "peso REAL, " +
                           "porte TEXT" +
						   ");";
							
		try{
		Connection conn = ConexaoDB.conectar(); // estabelece conexão com "pacientes.db"
		Statement stmt = conn.createStatement(); // classe "statement responsável por executar comando SQL."
		stmt.execute(SQL); // Execução do comando sql e criação da tabela.
		
		System.out.println("Comando SQL: " + SQL); // Exibe o comando sql no terminal.
		} 
		catch (SQLException e) {
			e.printStackTrace(); // imprimi quais funções causaram erros.
		}
	}

}
