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
import com.clinibuddys.Model.Paciente;

import java.util.ArrayList;
import java.util.List;

public class DiagnosticoDAO {
    /* ===================== Insere uma lista de diagnostico de um paciente na tabela diagnostico ====================== */
    public static void inserir(int id, Paciente p, Connection conn) {
        List<Diagnostico> diagnosticos = p.getDiagnostico();
        String sql = "INSERT INTO diagnostico (id_paciente, tipo, data, vet_responsavel, observacoes, doença, descricao, tratamento_sugerido, riscos) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            for(Diagnostico diagnostico : diagnosticos) {
                stmt.setInt(1, id);
                //stmt.setString(2, "Cachorro");
                stmt.setString(2, p.getEspecie());
                stmt.setString(3, diagnostico.getDataFormatada());
                stmt.setString(4, diagnostico.getVeterinarioResponsavel());
                stmt.setString(5, diagnostico.getObservacoes());
                stmt.setString(6, diagnostico.getDoenca());
                stmt.setString(7, diagnostico.getDescricao());
                stmt.setString(8, diagnostico.getTratamentoSug());
                stmt.setString(9, diagnostico.getRiscos());

                stmt.executeUpdate();
                System.out.println("Dados Inseridos na tabela Diagnostico!");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /* ==================== Retorna uma lista de diagnosticos atráves do id ===================== */
    public static List<Diagnostico> consultaPorId(int id, Connection conn) {
        String comandoSQL = "SELECT * FROM diagnostico WHERE id_paciente = ?";
        List<Diagnostico> diagnosticos = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(comandoSQL)) {
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    Diagnostico diagnostico = new Diagnostico();
                    diagnostico.setVeterinarioResponsavel(rs.getString("vet_responsavel"));
                    diagnostico.setObservacoes(rs.getString("observacoes"));
                    diagnostico.setDoenca(rs.getString("doença"));
                    diagnostico.setDescricao(rs.getString("descricao"));
                    diagnostico.setTratamento(rs.getString("tratamento_sugerido"));
                    diagnostico.setRiscos(rs.getString("riscos"));

                    DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    String dataString = rs.getString("data");
                    LocalDateTime data = LocalDateTime.parse(dataString, FORMATADOR);
                    diagnostico.setData(data);
                    
                    diagnosticos.add(diagnostico);
                }

                return diagnosticos;
            }
        }
        catch(SQLException e) {
            System.out.println("Erro ao consultar por id na tabela diagnostico");
            e.printStackTrace();
        }
        return null;
    }

    /* ==================== Deleta diagnosticos através do id ===================== */
    public static void deletarPoIdPaciente(int id_paciente, Connection conn) {
        String comandoSQL = "DELETE FROM diagnostico WHERE id_paciente = ?";

        try(PreparedStatement stmt = conn.prepareStatement(comandoSQL)) {
            stmt.setInt(1, id_paciente);
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
