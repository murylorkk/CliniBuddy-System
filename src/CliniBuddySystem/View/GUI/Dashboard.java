package CliniBuddySystem.View.GUI;

import CliniBuddySystem.Controller.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Dashboard extends JFrame {
    private GerenciadorAgendamento ga;
    private GerenciadorPaciente gp;
    private GerenciadorRegistros gr;
    JRadioButton gatoBotao;
    JRadioButton cachorroBotao;
    public Dashboard(GerenciadorPaciente gp, GerenciadorAgendamento ga, GerenciadorRegistros gr) {
        this.ga = ga;
        this.gp = gp;
        this.gr = gr;

        setTitle("Dashboard - CliniBuddy System | Veterinário: " + gp.getClinica().getVeterinarioResponsavel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.add(criarCabecalho(), BorderLayout.NORTH);
        this.add(criarAbasPrincipais(), BorderLayout.CENTER);
        this.setVisible(true);
    }

    public JPanel criarCabecalho() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String nomeVet = gp.getClinica().getVeterinarioResponsavel();
        JLabel saudacao = new JLabel("Olá, " + nomeVet + ". Este é seu Dashboard.");
        saudacao.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 16));
        painel.add(saudacao);
        return painel;
    }

    public JTabbedPane criarAbasPrincipais() {
        JTabbedPane dashboard = new JTabbedPane();
        dashboard.addTab("Pacientes", null, criarPainelPacientes(), "Gerenciamento de pacientes");
        dashboard.addTab("Diagnóstico", null, criarPainelDiagnostico(), "Gerenciador de diagnósticos");
        dashboard.addTab("Históricos", null, criarPainelHistorico(), "Gerenciador de históricos");
        dashboard.addTab("Agendamento", null, criarPainelAgendamento(), "Gerenciador de Agendamentos");
        dashboard.addTab("Opções", null, criarPainelOpcoes(), "Ajustes no CliniBuddy System");
        return dashboard;
    }

   public JPanel criarPainelPacientes() {
    JPanel painelPacientes = new JPanel(new BorderLayout());

    JPanel containerEsquerda = new JPanel(new BorderLayout());

    containerEsquerda.setPreferredSize(new Dimension(350, 0)); 
    containerEsquerda.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel formulario = new JPanel(new GridLayout(0, 2, 5, 5));

    JLabel nome = new JLabel("Nome: ");
    JTextField nomePaciente = new JTextField();

    JLabel idade = new JLabel("Idade: ");
    JTextField idadePaciente = new JTextField();

    JLabel peso = new JLabel("Peso: ");
    JTextField pesoPaciente = new JTextField();

    gatoBotao = new JRadioButton("Gato");
    cachorroBotao = new JRadioButton("Cachorro");
    gatoBotao.setSelected(true);
    
    ButtonGroup grupoEspecie = new ButtonGroup();
    grupoEspecie.add(cachorroBotao);
    grupoEspecie.add(gatoBotao);

    JPanel painelEspecie = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0 ));
    painelEspecie.add(gatoBotao);
    painelEspecie.add(cachorroBotao);

    JLabel raca = new JLabel("Raça: ");
    JTextField racaPaciente = new JTextField();

    formulario.add(nome);
    formulario.add(nomePaciente);
    formulario.add(idade);
    formulario.add(idadePaciente);
    formulario.add(peso);
    formulario.add(pesoPaciente);
    formulario.add(new JLabel("Espécie: "));
    formulario.add(painelEspecie);
    formulario.add(raca);
    formulario.add(racaPaciente);
    
    JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
    JButton btnCadastrar = new JButton("Cadastrar");
    
    painelBotao.add(btnCadastrar);


    JPanel empilhador = new JPanel();
    empilhador.setLayout(new BoxLayout(empilhador, BoxLayout.Y_AXIS));
    
    containerEsquerda.setBorder(BorderFactory.createTitledBorder(" Cadastro de Pacientes "));
    empilhador.add(Box.createVerticalStrut(10));
    empilhador.add(formulario);
    empilhador.add(Box.createVerticalStrut(10)); 
    empilhador.add(painelBotao);
    
    containerEsquerda.add(empilhador, BorderLayout.NORTH);
    
    painelPacientes.add(containerEsquerda, BorderLayout.WEST);
    
    JPanel areaLista = new JPanel();
    areaLista.setBackground(Color.WHITE);
    areaLista.setBorder(BorderFactory.createTitledBorder("Lista de Pacientes (Futuro)"));
    painelPacientes.add(areaLista, BorderLayout.CENTER);

    return painelPacientes;
}

    public JPanel criarPainelDiagnostico() {
        JPanel painelDiagnostico = new JPanel();

        return painelDiagnostico;
    }

    public JPanel criarPainelHistorico() {
        JPanel painelHistorico = new JPanel();
        setLayout(new BorderLayout());
        return painelHistorico;
    }

    public JPanel criarPainelAgendamento() {
        JPanel painelAgendamento = new JPanel(new GridLayout(10, 2, 5, 5));
        return painelAgendamento;
    }

    public JPanel criarPainelOpcoes() {
        JPanel painelOpcoes = new JPanel(new GridLayout(10, 2, 5, 5));
        return painelOpcoes;
    }
}