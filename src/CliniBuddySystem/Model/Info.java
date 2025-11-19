package CliniBuddySystem.Model;

import  java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Info{
    // atributos comuns
    private static int proximoID = 1;
    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    protected final int id; //identificador único e imutável
    protected LocalDateTime data; 
    protected String veterinarioResponsavel; 
    protected String observacoes; //campo genérico para anotações gerais;

// -> métodos construtores

    // -> construtor principal: permite que a subclasse defina a data.
public Info(LocalDateTime data, String veterinarioResponsavel, String observacoes){
    this.id = proximoID++;
    this.data = data;
    this.veterinarioResponsavel = veterinarioResponsavel;
    this.observacoes = observacoes;
}

    // -> construtor secundáiro: para registros que ocorrem no "agora"
    public Info(String veterinarioResponsavel, String observacoes){
        this(LocalDateTime.now(), veterinarioResponsavel, observacoes);
    }

    // --- Métodos ---
    // -> getters
    public int getId() {
        return id;
    }

    public String getDataFormatada(){
        return this.data.format(FORMATADOR);
    }
    
    public String getVeterinarioResponsavel() {
        return veterinarioResponsavel;
    }

    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes){
        this.observacoes = observacoes;
    }
    
    @Override
    public String toString() {
        // fornece uma representação útil para listas
        return "Registro #" + id + " (" + data + ")";
    }
}
