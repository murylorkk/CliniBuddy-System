package CliniBuddySystem.Controller;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import CliniBuddySystem.Model.*;
import CliniBuddySystem.Model.Agendamento.statusAgendamento;

/**
 * CLASSE CONTROLADORA (Controller)
 * contém a lógica de negócio para gerenciar agendamentos.
 * ele acessa a Clinica (Model) para salvar ou buscar dados.
 */
public class GerenciadorAgendamento{

    private Clinica clinica;

    public GerenciadorAgendamento(Clinica clinica){
        this.clinica = clinica;
    }
    
    /**
     * Lógica de negócio para criar um novo agendamento.
     */
    public Agendamento cadastrarAgendamento(Paciente paciente, LocalDate data, LocalTime horario, String motivo, statusAgendamento status) {
        String vetResponsavel = this.clinica.getVeterinarioResponsavel();
        Agendamento novoAgendamento = new Agendamento(paciente, data, horario, vetResponsavel, motivo, status);
        this.clinica.getListaDeAgendamentos().add(novoAgendamento);
        return novoAgendamento;
    }

    /**
     * Método para a View (Menu) obter a lista de agendamentos.
     */
    public List<Agendamento> getAgendamentos() {
        return this.clinica.getListaDeAgendamentos();
    }

    /**
     * atualiza a data e hora de um agendamento.
     * usa os setters da classe Agendamento.
     */
    public void remarcarAgendamento(Agendamento agendamento, LocalDate novaData, LocalTime novoHorario) {
        agendamento.setData(novaData); //
        agendamento.setHorario(novoHorario); //
    }

    /**
     *
     * atualiza o status de um agendamento.
     * usa o setter da classe Agendamento.
     */
    public void atualizarStatus(Agendamento agendamento, statusAgendamento novoStatus) {
        agendamento.setStatus(novoStatus); //
    }

    /**
     * Remove um agendamento da lista principal da clínica.
     */
    public void cancelarAgendamento(Agendamento agendamento) {
        this.clinica.getListaDeAgendamentos().remove(agendamento);
    }
}