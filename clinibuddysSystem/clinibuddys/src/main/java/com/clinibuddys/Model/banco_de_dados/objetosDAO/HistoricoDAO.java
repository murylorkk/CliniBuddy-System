package com.clinibuddys.Model.banco_de_dados.objetosDAO;
import com.clinibuddys.Model.Especies.Cachorro;
import com.clinibuddys.Model.Especies.Gato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.clinibuddys.Model.Diagnostico;

import java.util.ArrayList;
import java.util.List;
import com.clinibuddys.Model.Historico;
import com.clinibuddys.Model.Paciente;

public class HistoricoDAO {
    /* ====================== Insere uma lista de historicos de um paciente na tabela historico ====================== */
    public static void inserir(int id, Paciente p, Connection conn) {
        List<Historico> historicos = p.getHistorico();
        String sql = "INSERT INTO historico (id_paciente, tipo, data, vet_responsavel, observacoes, acompanhante, temperatura) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            for(Historico historico : historicos) {
               stmt.setInt(1, id);
               //stmt.setString(2, "Cachorro");
               stmt.setString(2, p.getEspecie());
               stmt.setString(3, historico.getDataFormatada());
               stmt.setString(4, historico.getVeterinarioResponsavel());
               stmt.setString(5, historico.getObservacoes());
               stmt.setString(6, historico.getAcompanhante());
               stmt.setDouble(7, historico.getTemperatura());

               stmt.executeUpdate();
               System.out.println("Dados inseridos na tabela historico!");
        }
        
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        
}
    /* =================== Retorna uma lista de históricos através do id ==================== */
    public static List<Historico> consultaPorId(int id, Connection conn) {
        String comandoSQL = "SELECT * FROM historico WHERE id_paciente = ?";
        List<Historico> historicos = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(comandoSQL)) {
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    Historico historico = new Historico();
                    historico.setAcompanhante(rs.getString("acompanhante"));
                    historico.setObservacoes(rs.getString("observacoes"));
                    historico.setTemperatura(rs.getDouble("temperatura"));
                    historico.setVeterinarioResponsavel(rs.getString("vet_responsavel"));

                    String dataString = rs.getString("data");
                    DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    LocalDateTime data = LocalDateTime.parse(dataString, FORMATADOR);
                    historico.setData(data);

                    historicos.add(historico);
                }
                return historicos;
            }
        }
        catch(SQLException e) {
            System.out.println("Erro ao consultar por id na tabela historicos");
            e.printStackTrace();
        }
        return null;
    }

    /* ==================== Deleta historicos através do id ================== */
    public static void deletarPoIdPaciente(int id_paciente, Connection conn) {
        String comandoSQL = "DELETE FROM historico WHERE id_paciente = ?";

        try(PreparedStatement stmt = conn.prepareStatement(comandoSQL)) {
            stmt.setInt(1, id_paciente);
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    } 
}


