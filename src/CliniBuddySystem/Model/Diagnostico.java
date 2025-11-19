package CliniBuddySystem.Model;

public class Diagnostico extends Info{
    private String doenca;
    private String descricao;
    private String tratamentoSugerido;
    private String riscos;

    // -> método construtor COM observações
    public Diagnostico(String veterinarioResponsavel, String doenca, String descricao, String tratamentoSugerido, String riscos, String observacoes){
        super(veterinarioResponsavel, observacoes);
        this.doenca = doenca;
        this.descricao = descricao;
        this.tratamentoSugerido = tratamentoSugerido;
        this.riscos = riscos;
    }

    // -> método construtor auxiliar SEM observações
    public Diagnostico(String veterinarioResponsavel, String doenca, String descricao, String tratamentoSugerido, String riscos){
        this(veterinarioResponsavel, doenca, descricao, tratamentoSugerido, riscos, "");
    }
    
    // -> getters
    public String getDescricao(){
        return descricao;
    }
    
    public String getTratamentoSug(){
        return tratamentoSugerido;
    }

    public String getRiscos(){
        return riscos;
    }
    
    // -> setters
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public void setTratamento(String tratamento){
        this.tratamentoSugerido = tratamento;
    }

    public void setRiscos(String riscos){
        this.riscos = riscos;
    }
    
    // -> método toString() para facilitar a visualização em listas.
    @Override
    public String toString() {
        return "Diagnóstico: " + this.doenca + "(em: "+ super.getDataFormatada() + ")";
    }

}