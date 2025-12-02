package com.clinibuddys.Model;

public class Historico extends Info{
    // atributos únicos da classe histórico
    private String acompanhante;
    private Double temperatura;


    // -> método construtor COM observacoes
    public Historico(String veterinarioResponsavel, String acompanhante, Double temperatura, String observacoes){
        super(veterinarioResponsavel, observacoes); //corrigir métodos construtores
        this.acompanhante = acompanhante;
        this.temperatura = temperatura;
    }
    
    // -> método construtor auxiliar SEM observações
    public Historico(String veterinarioResponsavel, String acompanhante, Double temperatura){
        this(veterinarioResponsavel, acompanhante, temperatura,"");
    }

    public Historico() {

    }

    // -> método toString() para exibição limpa em listas.
    @Override
    public String toString() {
        return "Consulta em " + super.getDataFormatada();
    }

    // getters especifícos
    public String getAcompanhante(){
        return acompanhante;
    }

    public Double getTemperatura(){
        return temperatura;
    }

    // -> sets especifícos
    public void setAcompanhante(String acompanhante) {
        this.acompanhante = acompanhante;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }
}


