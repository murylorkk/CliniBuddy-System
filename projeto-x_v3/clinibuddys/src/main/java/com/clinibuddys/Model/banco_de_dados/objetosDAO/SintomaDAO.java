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

public class SintomaDAO {
    /* =================== Insere uma lista de sintomas de um paciente na tabela sintomas ===================== */
    public static void inserir(int id, Paciente p, Connection conn) {
        List<String> sintomas = p.getSintomas();
        String sql = "INSERT INTO sintomas (id_paciente, tipo, sintoma) VALUES (?, ?, ?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            for(String sintoma : sintomas) {
                stmt.setInt(1, id);
                //stmt.setString(2, "Cachorro");
                stmt.setString(2, p.getEspecie());
                stmt.setString(3, sintoma);

                stmt.executeUpdate();
                System.out.println("Dados inseridos na tabela sintomas!");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        
    }

    /* =================== Retorna uma lista de sintomas através do id ===================== */
    public static List<String> consultaPorId(int id, Connection conn) {
        String comandoSQL = "SELECT * FROM sintomas WHERE id_paciente = ?";
        List<String> sintomas = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(comandoSQL)) {
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    sintomas.add(rs.getString("sintoma"));
                }
                return sintomas;
            }
        }
        catch(SQLException e) {
            System.out.println("Erro ao consultar por id na tabela sintomas.");
            e.printStackTrace();
        }
        return null;
    }
    /* ==================== Deleta sintomas através do id ==================== */
    public static void deletarPoIdPaciente(int id_paciente, Connection conn) {
        String comandoSQL = "DELETE FROM sintomas WHERE id_paciente = ?";

        try(PreparedStatement stmt = conn.prepareStatement(comandoSQL)) {
            stmt.setInt(1, id_paciente);
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
