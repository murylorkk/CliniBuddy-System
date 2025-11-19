package CliniBuddySystem.View;

import CliniBuddySystem.Model.*;
import CliniBuddySystem.Model.Agendamento.statusAgendamento;
import CliniBuddySystem.Model.Especies.Cachorro.porteCachorro;
import CliniBuddySystem.Controller.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private Scanner entrada;
    private Clinica clinica;
    private GerenciadorPaciente gerenciadorPaciente;
    private GerenciadorAgendamento gerenciadorAgendamento;
    private GerenciadorRegistros gerenciadorRegistros;

    public Menu(Clinica clinica, GerenciadorPaciente gp, GerenciadorAgendamento ga, GerenciadorRegistros gr) {
        this.entrada = new Scanner(System.in);
        this.clinica = clinica;
        this.gerenciadorPaciente = gp;
        this.gerenciadorAgendamento = ga;
        this.gerenciadorRegistros = gr;
    }

    /* ------ MENU E LÓGICA PRINCIPAL ------ */
    public void exibir() {
        boolean flag = true;
        System.out.println("--- Iniciando... ---\n" + "\n" +
                " ------------------------------------\n" +
                " |  Bem-vindo ao CliniBuddy System  |\n" +
                " ------------------------------------\n");

        System.out.println(" -> Nome do veterinario responsavel: ");
        String vet = entrada.nextLine();
        this.clinica.setVeterinarioResponsavel(vet);
        while (flag) {
            System.out.println("\n--- Menu CliniBuddy ---");
            System.out.println("Digite o número para acessar uma aba: \n" +
                    "-> 1. Pacientes \n" +
                    "-> 2. Históricos\n" +
                    "-> 3. Diagnósticos\n" +
                    "-> 4. Agendamentos\n" +
                    "-> 5. Sair \n");

            int menu = lerInteiroComLimite("Sua opção: ", 1, 5);
            switch (menu) {
                case (1):
                    gerenciarPacientes();
                    break;
                case (2):
                    gerenciarHistorico();
                    break;
                case (3):
                    gerenciarDiagnostico();
                    break;
                case (4):
                    gerenciarAgendamentos();
                    break;
                case (5):
                    System.out.println("--- Encerrando... ---");
                    flag = false;
                    break;
            }
        }
        entrada.close();
    }
    /*------ lista de métodos para gerenciamento geral -------*/

    // método privado para a aba de histórico
    private void gerenciarHistorico() {
        System.out.println("\n--- Histórico ---\n");
        boolean flag = true;
        while (flag) {
            System.out.print("Digite o número para acessar uma aba: \n" +
                    " -> 0. Voltar \n" +
                    " -> 1. Cadastrar novo histórico \n" +
                    " -> 2. Exibir históricos \n" +
                    " -> 3. Excluir registro de histórico \n"); // <-- NOVO

            int subMenu = lerInteiroComLimite("Sua opção: ", 0, 3); // <-- MUDANÇA (limite 3)
            switch (subMenu) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    adicionarHistorico();
                    flag = false;
                    break;
                case 2:
                    exibirHistorico();
                    flag = false;
                    break;
                case 3:
                    excluirHistorico();
                    flag = false;
                    break;
            }
        }
    }

    // método privado para a aba de diagnóstico
    private void gerenciarDiagnostico() {
        System.out.println("\n--- Diagnóstico ---\n");
        boolean flag = true;
        while (flag) {
            System.out.print("Digite o número para acessar uma aba: \n" +
                    " -> 0. Voltar \n" +
                    " -> 1. Cadastrar novo diagnóstico \n" +
                    " -> 2. Exibir diagnóstico \n" +
                    " -> 3. Excluir registro de diagnóstico \n"); // <-- NOVO

            int subMenu = lerInteiroComLimite("Sua opção: ", 0, 3); // <-- MUDANÇA (limite 3)
            switch (subMenu) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    adicionarDiagnostico();
                    flag = false;
                    break;
                case 2:
                    exibirDiagnostico();
                    flag = false;
                    break;
                case 3: // <-- NOVO
                    excluirDiagnostico();
                    flag = false;
                    break;
            }
        }
    }

    // método privado para a aba de pacientes
    private void gerenciarPacientes() {
        System.out.println("\n--- Gerenciamento de pacientes ---\n");
        boolean flag = true;
        while (flag) {
            System.out.print("Digite o número para acessar uma aba: \n" +
                    " -> 0. Voltar \n" +
                    " -> 1. Cadastrar novo paciente \n" +
                    " -> 2. Exibir cadastros \n" +
                    " -> 3. Editar paciente \n" +
                    " -> 4. Excluir paciente \n");

            int subMenu = lerInteiroComLimite("Sua opção: ", 0, 4);
            switch (subMenu) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    realizarCadastro();
                    flag = false;
                    break;
                case 2:
                    exibirCadastro();
                    flag = false;
                    break;
                case 3:
                    editarPaciente();
                    flag = false;
                    break;
                case 4:
                    excluirPaciente();
                    flag = false;
                    break;
            }
        }
    }

    // método privado para a aba de agendamentos
    private void gerenciarAgendamentos() {
        System.out.println("\n--- Gerenciamento de Agendamentos ---\n");
        boolean flag = true;
        while (flag) {
            System.out.print("Digite o número para acessar uma aba: \n" +
                    " -> 0. Voltar \n" +
                    " -> 1. Cadastrar novo agendamento \n" +
                    " -> 2. Exibir agendamentos \n" +
                    " -> 3. Remarcar agendamento \n" +
                    " -> 4. Atualizar status \n" +
                    " -> 5. Cancelar agendamento \n");

            int subMenu = lerInteiroComLimite("Sua opção: ", 0, 5);
            switch (subMenu) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    adicionarAgendamento();
                    flag = false;
                    break;
                case 2:
                    exibirAgendamento();
                    flag = false;
                    break;
                case 3:
                    remarcarAgendamento();
                    flag = false;
                    break;
                case 4:
                    atualizarStatusAgendamento();
                    flag = false;
                    break;
                case 5:
                    cancelarAgendamento();
                    flag = false;
                    break;
            }
        }
    }

    // -> método privado geral para a lógica de cadastro
    private void realizarCadastro() {
        System.out.println("\n--- Cadastro de novo paciente ---");

        System.out.print("--- Digite o nome do paciente: ");
        String nome = entrada.nextLine();

        System.out.print("--- Digite a idade (anos): ");
        int idade = lerInteiro("");

        System.out.print("--- Digite o peso (kg): ");
        float peso = lerFloat("");

        boolean flag = true;
        Paciente novoPaciente = null;
        while (flag) {
           
            System.out.print("--- Qual a espécie do paciente? : \n -> 1. Gato \n -> 2. Cachorro\n");
            int temp = lerInteiroComLimite("Sua opção: ", 1, 2);
            System.out.print("--- Digite a raça: ");
            String raca = entrada.nextLine();

            switch (temp) {
                case 1:
                    novoPaciente = this.gerenciadorPaciente.cadastrarGato(nome, raca, idade, peso);
                    flag = false;
                    break;
                case 2:
                    porteCachorro porteSelecionado = null;
                    System.out.print("--- Qual o porte do cachorro? \n" +
                            "1. Pequeno \n" +
                            "2. Medio \n" +
                            "3. Grande \n");
                    int porteOpcao = lerInteiroComLimite("Sua opção: ", 1, 3);
                    switch (porteOpcao) {
                        case 1:
                            porteSelecionado = porteCachorro.PEQUENO;
                            break;
                        case 2:
                            porteSelecionado = porteCachorro.MEDIO;
                            break;
                        case 3:
                            porteSelecionado = porteCachorro.GRANDE;
                            break;
                    }
                    novoPaciente = this.gerenciadorPaciente.cadastrarCachorro(nome, raca, idade, peso,
                            porteSelecionado);
                    flag = false;
                    break;
            }
        }
        System.out.println("\n--- Paciente: " + nome + " cadastrado com sucesso! --- \n");
    }

    // método para exibir cadastros
    private void exibirCadastro() {
        System.out.println("\n--- Pacientes cadastrados ---\n");
        Paciente PacienteSelecionado = selecionarPaciente();
        if (PacienteSelecionado != null) {
            System.out.println("Nome: " + PacienteSelecionado.getNome() + "\n" +
                    "Especie: " + PacienteSelecionado.getEspecie() + "\n" +
                    "Raca: " + PacienteSelecionado.getRaca() + "\n" +
                    "Peso: " + PacienteSelecionado.getPeso() + "\n");
        }
    }

    // -> método AUXILIAR para escolher um paciente da lista
    private Paciente selecionarPaciente() {
        List<Paciente> pacientes = this.gerenciadorPaciente.getPacientes();
        if (pacientes.isEmpty()) {
            System.out.println("\n-> Nenhum paciente encontrado. Cadastre um primeiro.\n");
            return null;
        }

        System.out.println("\nSelecione um paciente:");
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println("-> " + (i + 1) + ". " + pacientes.get(i));
        }

        int id = lerInteiroComLimite("Digite o número do paciente: ", 1, pacientes.size());
        return pacientes.get(id - 1);
    }

    // --- métodos para adicionar ---

    private void adicionarDiagnostico() {
        System.out.println("--- Cadastro de Diagnostico ---");
        Paciente paciente = selecionarPaciente();
        if (paciente != null) {
            System.out.print("Informe a doença: ");
            String doença = entrada.nextLine();

            System.out.print("Faça uma breve descrição da doença: ");
            String descricao = entrada.nextLine();

            System.out.print("Tratamento Sugerido: ");
            String tratamento = entrada.nextLine();

            System.out.print("Quais são os riscos: ");
            String riscos = entrada.nextLine();

            this.gerenciadorRegistros.adicionarDiagnostico(paciente, doença, descricao, tratamento, riscos);
            System.out.println("--- Registro adicionado a lista de diagnostico de " + paciente.getNome() + "---\n");
        }
    }

    private void adicionarHistorico() {
        System.out.println("--- Adicionar histórico ---\n");
        Paciente paciente = selecionarPaciente();
        if (paciente != null) {

            System.out.print(" ->Nome do acompanhante: ");
            String acomp = entrada.nextLine();

            System.out.print(" ->Temperatura aferida (°C): ");
            float temp = lerFloat("");

            this.gerenciadorRegistros.adicionarHistorico(paciente, acomp, temp);
            System.out.println("--- Registro adicionado a lista de histórico de " + paciente.getNome() + " ---\n");
        }
    }

    private void adicionarAgendamento() {
        System.out.println("--- Adicionar agendamento ---\n");
        Paciente paciente = selecionarPaciente();
        if (paciente != null) {
            System.out.print("Informe o motivo do agendamento: ");
            String motivo = entrada.nextLine();

            LocalDate data = lerData("Informe a data (dd/MM/yyyy): ");
            LocalTime horario = lerHorario("Informe o horário (HH:mm): ");

            System.out.print("Informe o status: \n" +
                    "1. Agendado \n" +
                    "2. Confirmado \n" +
                    "3. Cancelado \n" +
                    "4. Realizado \n");
            statusAgendamento status = null;
            int opçãoStatus = lerInteiroComLimite("Sua opção: ", 1, 4);
            switch (opçãoStatus) {
                case 1:
                    status = statusAgendamento.AGENDADO;
                    break;
                case 2:
                    status = statusAgendamento.CONFIRMADO;
                    break;
                case 3:
                    status = statusAgendamento.CANCELADO;
                    break;
                case 4:
                    status = statusAgendamento.REALIZADO;
                    break;
            }
            this.gerenciadorAgendamento.cadastrarAgendamento(paciente, data, horario, motivo, status);
        }
    }

    // --- métodos de exibição
    private void exibirHistorico() {
        Paciente paciente = selecionarPaciente();
        if (paciente != null) {
            List<Historico> HistoricoPaciente = this.gerenciadorRegistros.getHistoricosDoPaciente(paciente);
            if (HistoricoPaciente.isEmpty()) {
                System.out.println(" -> Nenhum histórico encontrado. Cadastre um primeiro.\n");
                return;
            }
            System.out.println("Selecione um registro histórico: ");
            for (int i = 0; i < HistoricoPaciente.size(); i++) {
                System.out.println("    -> " + (i + 1) + ". " + HistoricoPaciente.get(i) + "( "
                        + HistoricoPaciente.get(i).getDataFormatada() + " )");
            }

            System.out.print("Digite o número do histórico: ");
            int id = lerInteiro("");

            if (id > 0 && id <= HistoricoPaciente.size()) {
                Historico atual = HistoricoPaciente.get(id - 1);
                System.out.println(" ---> Histórico: " + atual + "\n" +
                        "Paciente:  " + paciente.getNome() + "\n" +
                        "ID: " + atual.getId() + "\n" +
                        "Acompanhante: " + atual.getAcompanhante() + "\n" +
                        "Temperatura (°C): " + atual.getTemperatura() + "\n" +
                        "Veterinario Responsável: " + atual.getVeterinarioResponsavel() + "\n");
            } else {
                System.out.println("--- Número de paciente inválido. ---\n");
                return;
            }
        }
    }

    private void exibirDiagnostico() {
        Paciente paciente = selecionarPaciente();
        if (paciente != null) {
            List<Diagnostico> DiagnosticosPaciente = this.gerenciadorRegistros.getDiagnosticosDoPaciente(paciente);
            if (DiagnosticosPaciente.isEmpty()) {
                System.out.println(" -> Nenhum diagnóstico encontrado. Cadastre um primeiro.\n");
                return;
            }

            for (int i = 0; i < DiagnosticosPaciente.size(); i++) {
                System.out.println("-> " + (i + 1) + ". " + DiagnosticosPaciente.get(i));
            }

            int id = lerInteiro("Digite o número do diagnostico: \"");

            // validando a entrada e ajustando o índice
            if (id > 0 && id <= DiagnosticosPaciente.size()) {
                Diagnostico atual = DiagnosticosPaciente.get(id - 1);
                System.out.println(" ---> Diagnostico: " + atual + "\n" +
                        "Paciente: " + paciente.getNome() + "\n" +
                        "Raca/Especie: " + paciente.getRaca() + "/" + paciente.getEspecie() + "\n" +
                        "ID: " + atual.getId() + "\n" +
                        "Descricao: " + atual.getDescricao() + "\n" +
                        "Riscos associados: " + atual.getRiscos() + "\n" +
                        "Tratamento sugerido: " + atual.getTratamentoSug() + "\n" +
                        "Observacoes: " + atual.getObservacoes() + "\n");
            } else {
                System.out.println("--- Número de paciente inválido. ---\n");
                return;
            }
        }
    }

    private void exibirAgendamento() {
        List<Agendamento> agendamentos = clinica.getListaDeAgendamentos();
        if (agendamentos.isEmpty()) {
            System.out.println("\n-> Nenhum agendamento encontrado.\n");
            return;
        }

        System.out.println("\n--- Agendamentos Registrados ---");
        for (int i = 0; i < agendamentos.size(); i++) {
            // Usa o toString() do Agendamento
            System.out.println("-> " + (i + 1) + ". " + agendamentos.get(i));
        }

        System.out.println("---------------------------------");
        System.out.print("Digite o número do agendamento para ver detalhes (ou 0 para voltar): ");
        int id = lerInteiroComLimite("", 0, agendamentos.size());

        if (id > 0) {
            Agendamento selecionado = agendamentos.get(id - 1);
            System.out.println(" ---> Agendamento: " + selecionado + "\n" +
                    "Paciente: " + selecionado.getPaciente() + "\n" +
                    "Raca/Especie: " + selecionado.getPaciente().getRaca() + "/"
                    + selecionado.getPaciente().getEspecie() + "\n" +
                    "ID: " + selecionado.getId() + "\n" +
                    "Data: " + selecionado.getData() + "\n" +
                    "Motivo: " + selecionado.getMotivo() + "\n" +
                    "Status: " + selecionado.getStatus() + "\n");
        }
    }

    private void editarPaciente() {
        System.out.println("\n--- Editar Paciente ---");
        Paciente paciente = selecionarPaciente();
        if (paciente == null) {
            return;
        }

        System.out.println("Dados atuais: Idade=" + paciente.getIdade() + " anos, Peso=" + paciente.getPeso() + " kg");

        System.out.print(" -> Digite a nova idade: ");
        int novaIdade = lerInteiro("");

        System.out.print(" -> Digite o novo peso (kg): ");
        float novoPeso = lerFloat("");

        // Chama o controlador para fazer a lógica
        this.gerenciadorPaciente.atualizarPaciente(paciente, novaIdade, novoPeso);

        System.out.println("--- Paciente " + paciente.getNome() + " atualizado com sucesso! ---");
    }

    private void excluirPaciente() {
        System.out.println("\n--- Excluir Paciente ---");
        Paciente paciente = selecionarPaciente();
        if (paciente == null) {
            return;
        }

        System.out.println("ATENÇÃO: Isso excluirá permanentemente o paciente " + paciente.getNome() + ".");
        System.out.print("Tem certeza que deseja continuar? (S/N): ");
        String confirmacao = entrada.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            // Chama o controlador para fazer a lógica
            this.gerenciadorPaciente.excluirPaciente(paciente);
            System.out.println("--- Paciente excluído com sucesso. ---");
        } else {
            System.out.println("--- Exclusão cancelada. ---");
        }
    }

    private void remarcarAgendamento() {
        System.out.println("\n--- Remarcar Agendamento ---");
        Agendamento agendamento = selecionarAgendamento();
        if (agendamento == null) {
            return;
        }

        System.out.println("Data/Hora atual: " + agendamento.getData() + " às " + agendamento.getHorario());

        LocalDate novaData = lerData(" -> Informe a nova data (dd/MM/yyyy): ");
        LocalTime novoHorario = lerHorario(" -> Informe o novo horário (HH:mm): ");

        // Chama o controlador
        this.gerenciadorAgendamento.remarcarAgendamento(agendamento, novaData, novoHorario);

        System.out.println("--- Agendamento de " + agendamento.getPaciente().getNome() + " remarcado com sucesso! ---");
    }

    private void atualizarStatusAgendamento() {
        System.out.println("\n--- Atualizar Status do Agendamento ---");
        Agendamento agendamento = selecionarAgendamento();
        if (agendamento == null) {
            return;
        }

        System.out.println("Status atual: " + agendamento.getStatus());
        System.out.print("Informe o novo Status: \n" +
                "1. Agendado \n" +
                "2. Confirmado \n" +
                "3. Cancelado \n" +
                "4. Realizado \n");
        int opçãoStatus = lerInteiroComLimite("Sua opção: ", 1, 4);
        statusAgendamento novoStatus = null;
        switch (opçãoStatus) {
            case 1:
                novoStatus = statusAgendamento.AGENDADO;
                break;
            case 2:
                novoStatus = statusAgendamento.CONFIRMADO;
                break;
            case 3:
                novoStatus = statusAgendamento.CANCELADO;
                break;
            case 4:
                novoStatus = statusAgendamento.REALIZADO;
                break;
        }

        this.gerenciadorAgendamento.atualizarStatus(agendamento, novoStatus);

        System.out.println("--- Status atualizado com sucesso! ---");
    }

    private void cancelarAgendamento() {
        System.out.println("\n--- Cancelar Agendamento ---");
        Agendamento agendamento = selecionarAgendamento();
        if (agendamento == null) {
            return;
        }

        System.out.println("ATENÇÃO: Isso excluirá o agendamento de " + agendamento.getPaciente().getNome() + ".");
        System.out.print("Tem certeza que deseja continuar? (S/N): ");
        String confirmacao = entrada.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            // Chama o controlador
            this.gerenciadorAgendamento.cancelarAgendamento(agendamento);
            System.out.println("--- Agendamento cancelado com sucesso. ---");
        } else {
            System.out.println("--- Cancelamento abortado. ---");
        }
    }

    private void excluirHistorico() {
        System.out.println("\n--- Excluir Registro de Histórico ---");
        Paciente paciente = selecionarPaciente();
        if (paciente == null) {
            return;
        }

        List<Historico> historicos = this.gerenciadorRegistros.getHistoricosDoPaciente(paciente);
        if (historicos.isEmpty()) {
            System.out.println(" -> Nenhum histórico encontrado para este paciente.\n");
            return;
        }

        for (int i = 0; i < historicos.size(); i++) {
            System.out.println("    -> " + (i + 1) + ". " + historicos.get(i));
        }

        int id = lerInteiroComLimite("Digite o número do histórico: ", 1, historicos.size());
        int indice = id - 1;

        System.out.println("ATENÇÃO: Isso excluirá o registro: " + historicos.get(indice));
        System.out.print("Tem certeza que deseja continuar? (S/N): ");
        String confirmacao = entrada.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            this.gerenciadorRegistros.excluirHistoricoPorIndice(paciente, indice);
            System.out.println("--- Registro de histórico excluído com sucesso. ---");
        } else {
            System.out.println("--- Exclusão cancelada. ---");
        }
    }

    private void excluirDiagnostico() {
        System.out.println("\n--- Excluir Registro de Diagnóstico ---");
        Paciente paciente = selecionarPaciente();
        if (paciente == null) {
            return;
        }

        List<Diagnostico> diagnosticos = this.gerenciadorRegistros.getDiagnosticosDoPaciente(paciente);
        if (diagnosticos.isEmpty()) {
            System.out.println(" -> Nenhum diagnóstico encontrado para este paciente.\n");
            return;
        }

        for (int i = 0; i < diagnosticos.size(); i++) {
            System.out.println("    -> " + (i + 1) + ". " + diagnosticos.get(i));
        }

        int id = lerInteiroComLimite("Digite o número do diagnóstico: ", 1, diagnosticos.size());
        int indice = id - 1;
        // 2. Confirmar a exclusão
        System.out.println("ATENÇÃO: Isso excluirá o registro: " + diagnosticos.get(indice));
        System.out.print("Tem certeza que deseja continuar? (S/N): ");
        String confirmacao = entrada.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            // 3. Chamar o controlador
            this.gerenciadorRegistros.excluirDiagnosticoPorIndice(paciente, indice);
            System.out.println("--- Registro de diagnóstico excluído com sucesso. ---");
        } else {
            System.out.println("--- Exclusão cancelada. ---");
        }
    }

    private Agendamento selecionarAgendamento() {
        List<Agendamento> agendamentos = this.gerenciadorAgendamento.getAgendamentos();

        if (agendamentos.isEmpty()) {
            System.out.println("\n-> Nenhum agendamento encontrado.\n");
            return null;
        }

        System.out.println("\nSelecione um agendamento:");
        for (int i = 0; i < agendamentos.size(); i++) {
            // Usa o toString() do Agendamento
            System.out.println("-> " + (i + 1) + ". " + agendamentos.get(i));
        }

        int id = lerInteiroComLimite("Digite o número do agendamento: ", 1, agendamentos.size());
        return agendamentos.get(id - 1);
    }

    // --- métodos auxiliares de leitura segura ---

    /**
     * lê um número inteiro do usuário, garantindo que a entrada seja válida.
     * continua pedindo até que um número inteiro seja digitado.
     * 
     * @param mensagem A mensagem a ser exibida para o usuário.
     * @return O número inteiro lido.
     */
    private int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(entrada.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("--- Erro: Por favor, digite um número inteiro válido. ---");
            }
        }
    }

    /**
     * Lê um número float do usuário, garantindo que a entrada seja válida.
     * Continua pedindo até que um número seja digitado (ex: 10 ou 10.5).
     * 
     * @param mensagem A mensagem a ser exibida para o usuário.
     * @return O número float lido.
     */

    private float lerFloat(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                // Substitui vírgula por ponto para aceitar ambos os formatos
                return Float.parseFloat(entrada.nextLine().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.err.println("--- Erro: Por favor, digite um número válido. ---");
            }
        }
    }

    /**
     * Lê um número inteiro dentro de um intervalo específico (min e max).
     * Continua pedindo até que um número válido dentro do intervalo seja digitado.
     * 
     * @param mensagem A mensagem para o usuário.
     * @param min      O valor mínimo aceitável.
     * @param max      O valor máximo aceitável.
     * @return O número inteiro lido e validado.
     */
    private int lerInteiroComLimite(String mensagem, int min, int max) {
        while (true) {
            int numero = lerInteiro(mensagem);
            if (numero >= min && numero <= max) {
                return numero;
            } else {
                System.err.println("--- Erro: O número deve estar entre " + min + " e " + max + ". ---");
            }
        }
    }

    // --- método auxiliar para ler data ---
    /**
     * Lê uma string em formato de data e verifica se está formatada
     * 
     * @param mensagem a mensagem para o usuário
     * @return um objeto formatado que representa uma data
     */
    private LocalDate lerData(String mensagem) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.print(mensagem);
            String texto = entrada.nextLine();
            try {
                return LocalDate.parse(texto, formatador);
            } catch (DateTimeParseException e) {
                System.err.println("--- Erro: Formato de data inválido. Use dd/MM/yyyy (ex: 01/10/2025) ---");
            }
        }
    }

    // --- método auxiliar para ler hora ---
    /**
     * Lê uma string em formato de data e verifica se está formatada
     * 
     * @param mensagem a mensagem para o usuário
     * @return um objeto formatado que representa um horario
     */
    private LocalTime lerHorario(String mensagem) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm");
        while (true) {
            System.out.print(mensagem);
            String texto = entrada.nextLine();
            try {
                return LocalTime.parse(texto, formatador);
            } catch (DateTimeParseException e) {
                System.err.println("--- Erro: Formato de hora inválido. Use HH:mm (ex: 14:30) ---");
            }
        }
    }
}
