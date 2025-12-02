package com.clinibuddys.Model.banco_de_dados.objetosDAO;
import com.clinibuddys.Model.Paciente;
import com.clinibuddys.Model.Especies.Cachorro;
import com.clinibuddys.Model.Especies.Gato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoencaDAO {
    /* ===================== Insere uma lista de doenças de um paciente na tabela "doencas" ====================== */
    public static void inserir(int id, Paciente p, Connection conn) {
        List<String> doencas = p.getDoencas();
        String sql = "INSERT INTO doencas (id_paciente, tipo, doença) VALUES (?, ?, ?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            for(String doenca : doencas) {
                stmt.setInt(1, id);
                stmt.setString(2, p.getEspecie());
                //stmt.setString(2, "Cachorro");
                stmt.setString(3, doenca);

                stmt.executeUpdate();
                System.out.println("Dados inseridos na tabela doenças!");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        
    }
    /* ===================== Retorna uma lista de doenças através do id ===================== */
    public static List<String> consultaPorId(int id, Connection conn) {
        String comandoSQL = "SELECT * FROM doencas WHERE id_paciente = ?";
        List<String> doencas = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(comandoSQL)) {
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    doencas.add(rs.getString("doença"));
                }
                return doencas;
            }
        }
        catch(SQLException e) {
            System.out.println("Erro ao consultar por id na tabela doencas.");
            e.printStackTrace();
        }
        return null;
    }

    /* ======================== Deleta doenças através do id ======================= */
    public static void deletarPoIdPaciente(int id_paciente, Connection conn) {
        String comandoSQL = "DELETE FROM doencas WHERE id_paciente = ?";

        try(PreparedStatement stmt = conn.prepareStatement(comandoSQL)) {
            stmt.setInt(1, id_paciente);
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
