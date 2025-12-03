package CliniBuddySystem.Model;

import java.util.List;

public class DoencaTemplate {
    
    private String nomeDoenca;
    private List<String> sintomasComuns;
    private String descricaoPadrao;
    private String tratamentoPadrao;
    private String riscosPadrao;

    public DoencaTemplate(String nomeDoenca, List<String> sintomasComuns, String descricaoPadrao, 
                          String tratamentoPadrao, String riscosPadrao) { // <-- Tratamento adicionado
        this.nomeDoenca = nomeDoenca;
        this.sintomasComuns = sintomasComuns;
        this.descricaoPadrao = descricaoPadrao;
        this.tratamentoPadrao = tratamentoPadrao;
        this.riscosPadrao = riscosPadrao;
    }

    public String getNomeDoenca() {
        return nomeDoenca;
    }

    public List<String> getSintomasComuns() {
        return sintomasComuns;
    }

    public String getDescricaoPadrao() {
        return descricaoPadrao;
    }
    
    public String getTratamentoPadrao() {
        return tratamentoPadrao;
    }

    public String getRiscosPadrao() {
        return riscosPadrao;
    }
    
    public void setNomeDoenca(String nomeDoenca) { 
        this.nomeDoenca = nomeDoenca;
    }

    public void setDescricaoPadrao(String descricaoPadrao) {
        this.descricaoPadrao = descricaoPadrao;
    }
    
    public void setTratamentoPadrao(String tratamentoPadrao) {
        this.tratamentoPadrao = tratamentoPadrao;
    }
    
    public void setRiscosPadrao(String riscosPadrao) {
        this.riscosPadrao = riscosPadrao;
    }
        
}