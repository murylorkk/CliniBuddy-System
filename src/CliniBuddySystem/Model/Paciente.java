package CliniBuddySystem.Model;

import java.util.ArrayList;
import java.util.List;

// reformulação da classe paciente, pois ela se tornou uma classe abstrata
public abstract class Paciente {
    // -> atributos gerais
    protected String nome;
    protected String raca;
    protected int idade;
    protected float peso;

    // -> atributos específicos
    private List<Historico> historico;
    private List<Diagnostico> diagnostico;
    private List<String> sintomas;
    private List<String> doencas;

    // -> método construtor
    public Paciente(String nome, String raca, int idade, float peso) {
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.peso = peso;

        // -> inicializa todas as listas como vazias
        this.historico = new ArrayList<>();
        this.diagnostico = new ArrayList<>();
        this.sintomas = new ArrayList<>();
        this.doencas = new ArrayList<>();
    }

    // -> getters para acessar informações
    public String getNome() {
        return nome;
    }

    public String getRaca() {
        return raca;
    }

    public int getIdade() {
        return idade;
    }

    public float getPeso() {
        return peso;
    }

    // -> getters para as listas
    public List<Historico> getHistorico() {
        return this.historico;
    }

    public List<Diagnostico> getDiagnostico() {
        return this.diagnostico;
    }

    public List<String> getSintomas() {
        return this.sintomas;
    }

    public List<String> getDoencas() {
        return this.doencas;
    }

    // -> get abstrato
    public abstract String getEspecie();

    // -> métodos que atualizam o cadastro do paciente com infomações que mudam com
    // o tempo
    public void setIdade(int NovaIdade) {
        this.idade = NovaIdade;
    }

    public void setPeso(float NovoPeso) {
        this.peso = NovoPeso;
    }

    // -> métodos para adicionar elementos no atríbutos lista
    public void adicionarDoenca(String doenca) {
        this.doencas.add(doenca);
    }

    public void adicionarSintomas(String sintoma) {
        this.sintomas.add(sintoma);
    }

    public void adicionarHistorico(Historico historico) {
        this.historico.add(historico);
    }

    public void adicionarDiagnostico(Diagnostico diagnostico) {
        this.diagnostico.add(diagnostico);
    }

    // -> método toString() atualizado para usar o método abstrato
    @Override
    public String toString() {
        return this.nome + " (" + this.getEspecie() + ")";
    }

    /**
     * Remove um registro de histórico da lista deste paciente.
     */
    public void removerHistorico(Historico registro) {
        this.historico.remove(registro);
    }

    /**
     * Remove um sintoma da lista de sintomas do paciente
     */
    public void removerSintoma(String sintoma) {
        this.sintomas.remove(sintoma);
    }

    /**
     * Remove uma doença da lista de doenças do paciente.
     */
    public void removerDoenca(String doenca) {
        this.doencas.remove(doenca);
    }

    /**
     * Remove um registro de diagnóstico da lista deste paciente.
     */
    public void removerDiagnostico(Diagnostico registro) {
        this.diagnostico.remove(registro);
    }
}
