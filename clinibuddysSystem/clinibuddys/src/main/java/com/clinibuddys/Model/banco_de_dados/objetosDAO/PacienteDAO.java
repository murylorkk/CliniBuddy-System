package com.clinibuddys.Model.banco_de_dados.objetosDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.clinibuddys.Model.Diagnostico;
import com.clinibuddys.Model.Historico;
import com.clinibuddys.Model.Paciente;
import com.clinibuddys.Model.Especies.Cachorro;
import com.clinibuddys.Model.Especies.Gato;
import com.clinibuddys.Model.Especies.Cachorro.porteCachorro;

public class PacienteDAO {
    Connection conn;

    public PacienteDAO(Connection conn) {
        this.conn = conn;
    }

    /* ======================= Inseri um paciente no banco de dados ======================= */
    public void inserir(Paciente p) {
        String SQL = "INSERT INTO paciente (nome, especie, raça, idade, peso, porte) VALUES (?, ?, ?, ?, ?, ?)";

        try(PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, p.getNome());
            if(p instanceof Cachorro) {
                Cachorro c = (Cachorro) p;
                stmt.setString(2, c.getEspecie());
                stmt.setString(6, String.valueOf(c.getPorte())); 
            }
            else if(p instanceof Gato) {
                Gato g = (Gato) p;
                stmt.setString(2, g.getEspecie());
                stmt.setNull(6, Types.VARCHAR);
            }

            stmt.setString(3, p.getRaca());
            stmt.setInt(4, p.getIdade());
            stmt.setDouble(5, p.getPeso());
            stmt.executeUpdate();
            System.out.println("Dados inseridos na tabela paciente!");

            try(ResultSet rs = stmt.getGeneratedKeys()) {
                int id = rs.getInt(1);
                DiagnosticoDAO.inserir(id, p, conn);
                HistoricoDAO.inserir(id, p, conn);
                SintomaDAO.inserir(id, p, conn);
                DoencaDAO.inserir(id, p, conn);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
    /*================== Retorna um paciente atráves do seu id ================== */ 
    public Paciente consultaPorId(int id) {
        String SQL = "SELECT * FROM paciente WHERE id = ?";

        try(PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    List<Historico> hs = HistoricoDAO.consultaPorId(id, conn);
                    List<Diagnostico> ds = DiagnosticoDAO.consultaPorId(id, conn);
                    List<String> doencas = DoencaDAO.consultaPorId(id, conn);
                    List<String> sintomas = SintomaDAO.consultaPorId(id, conn);

                    Paciente p = setPaciente(rs);

                    ds.forEach(diagnostico -> p.adicionarDiagnostico(diagnostico));
                    hs.forEach(historico -> p.adicionarHistorico(historico));
                    doencas.forEach(doenca -> p.adicionarDoenca(doenca));
                    sintomas.forEach(sintoma -> p.adicionarSintomas(sintoma));

                    return p;
                }
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* ============== Deleta Paciente atráves do seu id. ================ */
    public void deletarPorId(int id) {
        String comandoSQL = "DELETE FROM paciente WHERE id = ?";

        try(PreparedStatement stmt = conn.prepareStatement(comandoSQL)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Dados deletados com sucesso !");
        }
        catch(SQLException e) {
            System.out.println("Erro ao deletar paciente por id.");
            e.printStackTrace();
        }
    }

    /* ================= Retorna uma lista com todos os paciente do banco de dados ==================== */
    public List<Paciente> listarTodos() {
        String SQL = "SELECT * FROM paciente";
        List<Paciente> ps = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery()) {
            
            while(rs.next()) {

                int id = rs.getInt("id");
                List<String> doencas = DoencaDAO.consultaPorId(id, conn);
                List<String> sintomas = SintomaDAO.consultaPorId(id, conn);
                List<Diagnostico> ds = DiagnosticoDAO.consultaPorId(id, conn);
                List<Historico> hs = HistoricoDAO.consultaPorId(id, conn);

                Paciente p = setPaciente(rs);
                
                doencas.forEach(doenca -> p.adicionarDoenca(doenca));
                sintomas.forEach(sintoma -> p.adicionarSintomas(sintoma));
                ds.forEach(d -> p.adicionarDiagnostico(d));
                hs.forEach(h -> p.adicionarHistorico(h));

                ps.add(p);
            }
            return ps;
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void atualizarPaciente(int id, Paciente p) {
        String sql = "UPDATE paciente SET nome = ?, especie = ?, raça = ?, idade = ?, peso = ?, porte = ? WHERE id = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getEspecie());
            stmt.setString(3, p.getRaca());
            stmt.setInt(4, p.getIdade());
            stmt.setDouble(5, p.getPeso());
            
            if(p instanceof Cachorro) {
                Cachorro c = (Cachorro) p;
                stmt.setString(6, String.valueOf(c.getPorte())); 
            }
            else if(p instanceof Gato) {
                Gato g = (Gato) p;
                stmt.setNull(6, Types.VARCHAR);
            }

            stmt.setInt(7, p.getId());

            stmt.executeUpdate();

        }
        catch(SQLException e) {
            System.out.println("Erro ao atualizar registro !");
        }
    }

    /* ===================== Preenche os campos de paciente de acordo com seu tipo (Cachorro, Gato, ......). =========================*/
    private static Paciente setPaciente(ResultSet rs) throws SQLException{

        if(rs.getString("especie").equals("Cachorro")) {
                Cachorro c = new Cachorro();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setRaca(rs.getString("raça"));
                c.setIdade(rs.getInt("idade"));
                c.setPeso(rs.getDouble("peso"));
                c.setPorte(porteCachorro.valueOf(rs.getString("porte")));

                return c;

        }
        else if(rs.getString("especie").equals("Gato")) {
            Gato g = new Gato();
            g.setId(rs.getInt("id"));
            g.setNome(rs.getString("nome"));
            g.setRaca(rs.getString("raça"));
            g.setIdade(rs.getInt("idade"));
            g.setPeso(rs.getDouble("peso"));

            return g;
        }
        return null;
    }
}
