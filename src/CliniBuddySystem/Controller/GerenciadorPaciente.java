package CliniBuddySystem.Controller;

import CliniBuddySystem.Model.*;
import CliniBuddySystem.Model.Especies.*;
import CliniBuddySystem.Model.Especies.Cachorro.*;

import java.util.List;

/**
 * CLASSE CONTROLADORA (Controller)
 * contém a lógica de negócio para gerenciar pacientes.
 * ele acessa a Clinica (Model) para salvar ou buscar dados.
 */

 public class GerenciadorPaciente {
    
    private Clinica clinica;

    // O construtor recebe a clinica
    public GerenciadorPaciente(Clinica clinica) {
        this.clinica = clinica;
    }

    /**
     * lógica de negócio para criar e salvar um gato.
     */
    public Paciente cadastrarGato(String nome, String raca, int idade, float peso) {
        Paciente novoPaciente = new Gato(nome, raca, idade, peso);
        this.clinica.adicionarPaciente(novoPaciente);
        return novoPaciente;
    }

    /**
     * lógica de negócio para criar e salvar um cachorro.
     */
    public Paciente cadastrarCachorro(String nome, String raca, int idade, float peso, porteCachorro porte) {
        Paciente novoPaciente = new Cachorro(nome, raca, idade, peso, porte);
        this.clinica.adicionarPaciente(novoPaciente);
        return novoPaciente;
    }


    /**
     * atualiza os dados de um paciente existente.
     * usa os setters da classe Paciente.
     */
    public void atualizarPaciente(Paciente paciente, int novaIdade, float novoPeso) {
        paciente.setIdade(novaIdade); //
        paciente.setPeso(novoPeso); //
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
}