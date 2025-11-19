package CliniBuddySystem.Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Agendamento extends Info{
    private Paciente paciente;
    private String motivo;
    private LocalDate data;
    private LocalTime horario;

    public enum statusAgendamento{
        AGENDADO,
        CONFIRMADO,
        CANCELADO,
        REALIZADO
    }
    statusAgendamento status;

    public Agendamento(Paciente paciente, LocalDate data, LocalTime horario, String veterinarioResponsavel, String motivo, statusAgendamento status) {
        super(veterinarioResponsavel, "");
        this.paciente = paciente;
        this.data = data;
        this.horario = horario;
        this.motivo = motivo;
        this.status = status;
    }

    // -> getters
    public Paciente getPaciente(){
        return paciente;
    }
    
    public String getMotivo(){
        return motivo; 
    }
    
    public LocalTime getHorario(){
        return horario;
    }
    
    public statusAgendamento getStatus(){
        return status;
    }

    public LocalDate getData(){
        return data;
    }

    // -> setters
    public void setData(LocalDate data){
        this.data = data;
    }

    public void setHorario(LocalTime horario){
        this.horario = horario;
    }
    
    public void setStatus(statusAgendamento status){
        this.status = status;
    }

    public void setPaciente(Paciente paciente){
        this.paciente = paciente;
    }
    
    public void setMotivo(String motivo){
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return "AGENDADO para " + data + " às " + horario +
                " | Paciente: " + paciente.getNome() +
                " | Espécie: " + paciente.getEspecie() +
                " | Raça: " + paciente.getRaca() +
                " | Motivo: " + motivo +
                " | Status: " + status;
    }
}
