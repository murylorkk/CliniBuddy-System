package com.clinibuddys.Test;

import java.sql.Connection;
import java.util.List;

import com.clinibuddys.Model.Paciente;
import com.clinibuddys.Model.Especies.Cachorro;
import com.clinibuddys.Model.Especies.Gato;
import com.clinibuddys.Model.Especies.Cachorro.porteCachorro;
import com.clinibuddys.Model.banco_de_dados.criacao_tabelas.ConexaoDB;
import com.clinibuddys.Model.banco_de_dados.objetosDAO.PacienteDAO;

public class TesteBD {

    public static void main(String[] args) {
        Connection conn = ConexaoDB.conectar();

        PacienteDAO pacienteDAO = new PacienteDAO(conn);

        // Printando lista vazia !
        mostrarDados(pacienteDAO);

        // Inserindo dados !
        Cachorro cachorro = new Cachorro("Spike", "Labrador", 6, 12.6, porteCachorro.MEDIO);
        
        pacienteDAO.inserir(cachorro);

        Gato gato = new Gato("Snowbell", "Siamês", 4, 6.7);

        pacienteDAO.inserir(gato);

        // Printando novamente
        mostrarDados(pacienteDAO);
    }
    
    private static void mostrarDados(PacienteDAO pacienteDAO) {
        List<Paciente> lista = pacienteDAO.listarTodos();
        
        if(lista.isEmpty()) {
            System.out.println("Lista de pacientes vazia !");
        }
        else {
            lista.forEach(System.out::println);

        }
    }

    
}
