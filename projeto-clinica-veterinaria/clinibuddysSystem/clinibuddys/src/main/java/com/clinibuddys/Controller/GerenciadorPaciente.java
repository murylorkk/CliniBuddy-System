package com.clinibuddys.Controller;

import com.clinibuddys.Model.*;

import com.clinibuddys.Model.Especies.Gato;

import com.clinibuddys.Model.Especies.Cachorro;
import com.clinibuddys.Model.Especies.Cachorro.porteCachorro;
import com.clinibuddys.Model.banco_de_dados.criacao_tabelas.ConexaoDB;
import com.clinibuddys.Model.banco_de_dados.objetosDAO.PacienteDAO;

import java.util.List;

/**
 * CLASSE CONTROLADORA (Controller)
 * contém a lógica de negócio para gerenciar pacientes.
 * ele acessa a Clinica (Model) para salvar ou buscar dados.
 */

 public class GerenciadorPaciente {
    
    private Clinica clinica;

    private PacienteDAO pacienteDAO = new PacienteDAO(ConexaoDB.conectar());

    // O construtor recebe a clinica
    public GerenciadorPaciente(Clinica clinica) {
        this.clinica = clinica;
    }

    /**
     * lógica de negócio para criar e salvar um gato.
     */
    public Paciente cadastrarGato(String nome, String raca, int idade, Double peso) {
        Paciente novoPaciente = new Gato(nome, raca, idade, peso);
        this.clinica.adicionarPaciente(novoPaciente);
        return novoPaciente;
    }

    /**
     * lógica de negócio para criar e salvar um cachorro.
     */
    public Paciente cadastrarCachorro(String nome, String raca, int idade, Double peso, porteCachorro porte) {
        Paciente novoPaciente = new Cachorro(nome, raca, idade, peso, porte);
        this.clinica.adicionarPaciente(novoPaciente);
        return novoPaciente;
    }


    /**
     * atualiza os dados de um paciente existente.
     * usa os setters da classe Paciente.
     */
    public void atualizarPaciente(Paciente paciente, int novaIdade, Double novoPeso) {
        paciente.setIdade(novaIdade); //
        paciente.setPeso(novoPeso); //

        pacienteDAO.atualizarPaciente(paciente.getId(), paciente);
    }

    /**
     * Exclui um paciente da lista principal da clínica.
     */
    public void excluirPaciente(Paciente paciente) {
        this.clinica.removerPaciente(paciente);
    }
    
    /**
     * método para a View (Menu) obter a lista de pacientes.
     */
    public List<Paciente> getPacientes() {
        return this.clinica.getListaDePacientes();
    }

    public Clinica getClinica(){
        return clinica;
    }
}