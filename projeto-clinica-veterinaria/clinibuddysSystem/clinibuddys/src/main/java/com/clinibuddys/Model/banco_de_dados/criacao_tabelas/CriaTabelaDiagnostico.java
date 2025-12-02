package com.clinibuddys.Model.banco_de_dados.criacao_tabelas;

import com.clinibuddys.Model.Historico;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.*;

// ==================== Cria Tabela usando comandos SQL ==================== //
public class CriaTabelaDiagnostico {
	
	public static void main(String[] args) {
        // Comando sql para gerar tabela
		final String SQL = "CREATE TABLE diagnostico (" +
						   "id INTEGER PRIMARY KEY, " +
						   "id_paciente INTEGER, " +
						   "tipo TEXT NOT NULL, " +
						   "data TEXT NOT NULL, " +
                           "vet_responsavel TEXT NOT NULL, " +
                           "observacoes TEXT, " +
                           "doença TEXT NOT NULL, " +
                           "descricao TEXT NOT NULL, " +
                           "tratamento_sugerido TEXT NOT NULL, " +
                           "riscos TEXT NOT NULL, " +
						   "FOREIGN KEY (id_paciente) REFERENCES paciente(id) ON DELETE CASCADE" +
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

