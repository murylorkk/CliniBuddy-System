package CliniBuddySystem.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import CliniBuddySystem.Model.*;

public class GerenciadorRegistros {
    private Clinica clinica;
    private Map<String, DoencaTemplate> baseDeConhecimento;
    private Map<String, List<DoencaTemplate>> indiceSintomas;

    // --- método construtor de registros (histórico e diagnóstico)
    public GerenciadorRegistros(Clinica clinica) {
        this.clinica = clinica;
        this.baseDeConhecimento = new HashMap<>();
        this.indiceSintomas = new HashMap<>();
        inicializarBaseDeConhecimento();
    }

    public Historico adicionarHistorico(Paciente paciente, String acompanhante, float temperatura, String observacoes) {
        String vet = clinica.getVeterinarioResponsavel();
        Historico novoHistorico = new Historico(vet, acompanhante, temperatura, observacoes);
        paciente.adicionarHistorico(novoHistorico);
        return novoHistorico;
    }

    public Diagnostico adicionarDiagnostico(Paciente paciente, String doenca, String descricao, String tratamento,
            String riscos) {
        String vet = clinica.getVeterinarioResponsavel();
        Diagnostico novoDiagnostico = new Diagnostico(vet, doenca, descricao, tratamento, riscos);
        paciente.adicionarDiagnostico(novoDiagnostico);
        return novoDiagnostico;
    }

    /**
     * Exclui um registro de Histórico de um paciente.
     * (Geralmente para corrigir erros de entrada)
     */
    public void excluirHistorico(Paciente paciente, Historico registro) {
        paciente.removerHistorico(registro);
    }

    /**
     * Exclui um registro de Diagnóstico de um paciente.
     * (Geralmente para corrigir erros de entrada)
     */
    public void excluirDiagnostico(Paciente paciente, Diagnostico registro) {
        paciente.removerDiagnostico(registro);
    }

    // -> getters
    public List<Historico> getHistoricosDoPaciente(Paciente paciente) {
        return paciente.getHistorico();
    }

    public List<Diagnostico> getDiagnosticosDoPaciente(Paciente paciente) {
        return paciente.getDiagnostico();
    }

    /**
     * exclui um histórico de um paciente usando o ÍNDICE da lista.
     */
    public boolean excluirHistoricoPorIndice(Paciente paciente, int indice) {
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
    public boolean excluirDiagnosticoPorIndice(Paciente paciente, int indice) {
        List<Diagnostico> diagnosticos = paciente.getDiagnostico();
        if (indice >= 0 && indice < diagnosticos.size()) {
            Diagnostico registroParaExcluir = diagnosticos.get(indice);
            paciente.removerDiagnostico(registroParaExcluir);
            return true;
        }
        return false;
    }

    // --- LÓGICA DE NEGÓCIO DA BASE DE CONHECIMENTO ---

    /**
     * popula os mapas com dados iniciais e constrói o índice de busca.
     */

    // Em GerenciadorRegistros.java

    private void inicializarBaseDeConhecimento() {
        // Definicoes de Sintomas Comuns (para simplificar a entrada)
        List<String> SINT_CINOMOSE = List.of("febre", "letargia", "tosse", "secreções", "vômitos", "convulsão");
        List<String> SINT_PARVO = List.of("diarreia e vômitos", "sangramentos", "letargia", "perda de apetite",
                "febre");
        List<String> SINT_FIV = List.of("perda de peso", "anemia", "gengivite", "infecções secundárias");
        List<String> SINT_FELV = List.of("anemia", "perda de peso", "febre", "linfonodos inchados");
        List<String> SINT_DRC = List.of("aumento de sede", "alterações na urina", "perda de apetite", "perda de peso");
        List<String> SINT_OTITE = List.of("coceira", "secreção", "dor abdominal");
        List<String> SINT_DIABETES = List.of("aumento de sede", "aumento de apetite", "perda de peso", "hálito doce");

        // 1. CRIAÇÃO DOS TEMPLATES DE DOENÇAS (Gatos)
        baseDeConhecimento.put("fiv", new DoencaTemplate(
                "Vírus da Imunodeficiência (FIV)", SINT_FIV,
                "Vírus crônico que compromete o sistema imunológico.",
                "Suporte nutricional, tratamento agressivo de infecções secundárias.",
                "Imunodeficiência, aumento de risco de câncer."));

        baseDeConhecimento.put("felv", new DoencaTemplate(
                "Vírus da Leucemia Felina (FeLV)", SINT_FELV,
                "Retrovírus que causa imunossupressão, anemia e câncer.",
                "Cuidados de suporte, tratamento quimioterápico (se câncer).",
                "Falência de medula óssea, morte."));

        baseDeConhecimento.put("fpv", new DoencaTemplate(
                "Panleucopenia Felina (FPV)", SINT_PARVO, // Compartilha sintomas com Parvovirose Canina
                "Infecção viral grave que destrói células de rápida divisão, como as do intestino.",
                "Fluidoterapia intensiva e antibióticos para sepse.",
                "Morte rápida por desidratação e infecção."));

        baseDeConhecimento.put("drc", new DoencaTemplate(
                "Doença Renal Crônica (DRC)", SINT_DRC,
                "Perda progressiva e irreversível da função renal.",
                "Dieta renal, controle de pressão arterial e fluidoterapia.",
                "Falência renal terminal."));

        // ... (doenças de gatos com dados de placeholder)
        baseDeConhecimento.put("clamidiose",
                new DoencaTemplate("Clamidiose Felina", List.of("conjuntivite", "secreções oculares"),
                        "Infecção bacteriana da conjuntiva.", "Antibióticos tópicos e orais.", "Risco baixo."));

        baseDeConhecimento.put("fhv-1",
                new DoencaTemplate("Herpesvírus Felino (FHV-1)",
                        List.of("problemas respiratórios", "conjuntivite", "espirros"),
                        "Infecção respiratória viral comum (gripe felina).", "Suporte, antiviral e descongestionante.",
                        "Risco médio para filhotes."));

        baseDeConhecimento.put("fcv",
                new DoencaTemplate("Calicivírus Felino (FCV)",
                        List.of("úlcerações orais", "febre", "problemas respiratórios"),
                        "Vírus que causa úlceras na boca e problemas respiratórios.",
                        "Suporte nutricional e manejo da dor.", "Risco baixo a médio."));

        baseDeConhecimento.put("pif",
                new DoencaTemplate("Peritonite Infecciosa Felina (PIF)",
                        List.of("febre", "perda de peso", "acúmulo de líquido abdominal"),
                        "Doença viral fatal complexa.", "Tratamento experimental ou paliativo.", "Risco fatal."));

        baseDeConhecimento.put("asma felina", new DoencaTemplate("Asma Felina",
                List.of("respiração ruidosa", "tosse seca", "dificuldade respiratória"), "Doença respiratória crônica.",
                "Broncodilatadores e corticosteroides.", "Risco de crise respiratória grave."));

        baseDeConhecimento.put("toxoplasmose",
                new DoencaTemplate("Toxoplasmose", List.of("letargia", "cegueira", "sinais neurológicos"),
                        "Infecção parasitária, afeta múltiplos órgãos.", "Antibióticos e anti-inflamatórios.",
                        "Risco médio a alto."));

        // 2. CRIAÇÃO DOS TEMPLATES DE DOENÇAS (Cães)
        baseDeConhecimento.put("cinomose", new DoencaTemplate(
                "Cinomose", SINT_CINOMOSE,
                "Doença viral grave e contagiosa que afeta múltiplos sistemas, incluindo o nervoso.",
                "Suporte intensivo, controle de convulsões e antibióticos.",
                "Sequela neurológica permanente, morte."));

        baseDeConhecimento.put("parvovirose", new DoencaTemplate(
                "Parvovirose Canina", SINT_PARVO,
                "Infecção viral severa que ataca as células do intestino e medula óssea.",
                "Fluidoterapia intravenosa e controle de vômitos.",
                "Morte rápida por desidratação e sepse."));

        baseDeConhecimento.put("otite", new DoencaTemplate(
                "Otite", SINT_OTITE,
                "Inflamação do canal auditivo, geralmente por infecção bacteriana ou fúngica.",
                "Limpeza profunda e medicação tópica.",
                "Surdez crônica ou dor crônica."));

        baseDeConhecimento.put("diabetes mellitus", new DoencaTemplate(
                "Diabetes Mellitus", SINT_DIABETES,
                "Distúrbio endócrino caracterizado por deficiência de insulina.",
                "Terapia com insulina e dieta específica.",
                "Cetoacidose diabética, cegueira."));
        // ... (mais 5 doenças de cães com dados de placeholder)

        baseDeConhecimento.put("ehrlichiose", new DoencaTemplate("Ehrlichiose", List.of("febre", "anemia", "letargia"),
                "Doença transmitida por carrapatos, afeta células sanguíneas.", "Antibióticos (Doxiciclina).",
                "Risco de sangramento grave."));

        baseDeConhecimento.put("leptospirose",
                new DoencaTemplate("Leptospirose", List.of("vômitos", "icterícia", "alterações na urina"),
                        "Infecção bacteriana transmitida pela urina de ratos, afeta fígado e rins.",
                        "Terapia intensiva e antibióticos.", "Risco de falência renal aguda."));

        baseDeConhecimento.put("leishmaniose",
                new DoencaTemplate("Leishmaniose Visceral Canina",
                        List.of("perda de peso", "anemia", "problemas de pele"),
                        "Doença parasitária crônica, afeta múltiplos sistemas.", "Tratamento de suporte e antimonial.",
                        "Risco fatal e zoonose."));

        baseDeConhecimento.put("coronavirose",
                new DoencaTemplate("Coronavirose", List.of("diarreia", "vômitos", "apatia"),
                        "Infecção viral gastrointestinal leve.", "Fluidoterapia e suporte.", "Risco baixo."));

        baseDeConhecimento.put("parainfluenza",
                new DoencaTemplate("Parainfluenza", List.of("tosse seca", "febre", "problemas respiratórios"),
                        "Vírus respiratório contagioso (Tosse dos Canis).", "Suporte e controle da tosse.",
                        "Risco baixo."));
        baseDeConhecimento.put("raiva",
                new DoencaTemplate("Raiva", List.of("mudança de comportamento", "paralisia", "hipersalivação"),
                        "Doença viral fatal que ataca o sistema nervoso.",
                        "Não há tratamento após o início dos sintomas.", "Risco fatal e zoonose."));

        // 3. CONSTRUÇÃO DO ÍNDICE INVERTIDO (Não alterada)
        for (DoencaTemplate template : baseDeConhecimento.values()) {
            for (String sintoma : template.getSintomasComuns()) {
                indiceSintomas.putIfAbsent(sintoma.toLowerCase(), new ArrayList<>());
                indiceSintomas.get(sintoma.toLowerCase()).add(template);
            }
        }
    }

    /**
     * Retorna a lista de doenças sugeridas com base em um sintoma pesquisado.
     */
    public List<DoencaTemplate> sugerirDiagnosticosPorSintoma(String sintoma) {
        // Retorna a lista do índice, ou uma lista vazia se o sintoma não for encontrado
        return indiceSintomas.getOrDefault(sintoma.toLowerCase(), new ArrayList<>());
    }

}
