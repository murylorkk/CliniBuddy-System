
package CliniBuddySystem.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe MODELO (Model)
 * * esta classe armazena todos os dados de negócio da clínica.
 * ela não sabe como exibir menus ou ler dados do usuário.
 * ela apenas guarda e gerencia as listas de pacientes e agendamentos.
 */
public class Clinica {

    private List<Paciente> listaDePacientes;
    private List<Agendamento> listaDeAgendamentos;
    private String veterinarioResponsavel;

    public Clinica() {
        this.listaDePacientes = new ArrayList<>();
        this.listaDeAgendamentos = new ArrayList<>();
        this.veterinarioResponsavel = "";
    }

    // --- getters e setters ---
    public List<Paciente> getListaDePacientes() {
        return this.listaDePacientes;
    }

    public List<Agendamento> getListaDeAgendamentos() {
        return this.listaDeAgendamentos;
    }

    public String getVeterinarioResponsavel() {
        return this.veterinarioResponsavel;
    }

    public void setVeterinarioResponsavel(String veterinarioResponsavel) {
        this.veterinarioResponsavel = veterinarioResponsavel;
    }

    public void adicionarPaciente(Paciente paciente) {
        listaDePacientes.add(paciente);
    }

    public void removerPaciente(Paciente paciente) {
        listaDePacientes.remove(paciente);
    }

    // no futuro, métodos como salvarDados() e carregarDados() poderiam vir aqui
}
