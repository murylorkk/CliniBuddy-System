package CliniBuddySystem.Controller;

import java.util.List;

import CliniBuddySystem.Model.*;

public class GerenciadorRegistros{
    private Clinica clinica;

    // --- método construtor de registros (histórico e diagnóstico)
    public GerenciadorRegistros(Clinica clinica){
        this.clinica = clinica; 
    }

    //
    public Historico adicionarHistorico(Paciente paciente, String acompanhante, float temperatura){
        String vet = clinica.getVeterinarioResponsavel();
        Historico novoHistorico = new Historico(vet, acompanhante, temperatura);
        paciente.adicionarHistorico(novoHistorico);
        return novoHistorico;
    }

    //
    public Diagnostico adicionarDiagnostico(Paciente paciente, String doenca, String descricao, String tratamento, String riscos){
        String vet = clinica.getVeterinarioResponsavel();
        Diagnostico novoDiagnostico = new Diagnostico(vet, doenca, descricao, tratamento, riscos);
        paciente.adicionarDiagnostico(novoDiagnostico);
        return novoDiagnostico;
    }

    /**
     * Exclui um registro de Histórico de um paciente.
     * (Geralmente para corrigir erros de entrada)
     */
    public void excluirHistorico(Paciente paciente, Historico registro){
        paciente.removerHistorico(registro);
    }

    /**
     * Exclui um registro de Diagnóstico de um paciente.
     * (Geralmente para corrigir erros de entrada)
     */
    public void excluirDiagnostico(Paciente paciente, Diagnostico registro){
        paciente.removerDiagnostico(registro);
    }

    // -> getters
    public List<Historico> getHistoricosDoPaciente(Paciente paciente){
        return paciente.getHistorico();
    }

    public List<Diagnostico> getDiagnosticosDoPaciente(Paciente paciente){
        return paciente.getDiagnostico();
    }
    /**
     * exclui um histórico de um paciente usando o ÍNDICE da lista.
     */
    public boolean excluirHistoricoPorIndice(Paciente paciente, int indice){
        List<Historico> historicos = paciente.getHistorico();
        if (indice >= 0 && indice < historicos.size()) {
            Historico registroParaExcluir = historicos.get(indice);
            paciente.removerHistorico(registroParaExcluir);
            return true; // Sucesso
            }
        return false; // Falha (índice inválido)
    }

    /*
     * exclui um diagnóstico de um paciente usando o índice da lista
     */
    public boolean excluirDiagnosticoPorIndice(Paciente paciente, int indice){
        List<Diagnostico> diagnosticos = paciente.getDiagnostico();
        if(indice >= 0 && indice < diagnosticos.size()){
            Diagnostico registroParaExcluir = diagnosticos.get(indice);
            paciente.removerDiagnostico(registroParaExcluir);
            return true;
        }
        return false;
    }
}
