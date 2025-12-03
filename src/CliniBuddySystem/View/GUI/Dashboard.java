package CliniBuddySystem.View.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import CliniBuddySystem.Controller.GerenciadorAgendamento;
import CliniBuddySystem.Controller.GerenciadorPaciente;
import CliniBuddySystem.Controller.GerenciadorRegistros;
import CliniBuddySystem.View.GUI.Agendamento.PainelAgendamentos;
import CliniBuddySystem.View.GUI.Paciente.PainelPacientes;
import CliniBuddySystem.View.GUI.Registros.Diagnóstico.PainelDiagnostico;
import CliniBuddySystem.View.GUI.Registros.Histórico.PainelHistorico;

public class Dashboard extends JFrame {
    
    private GerenciadorAgendamento ga;
    private GerenciadorPaciente gp;
    private GerenciadorRegistros gr;

    public Dashboard(GerenciadorPaciente gp, GerenciadorAgendamento ga, GerenciadorRegistros gr) {
        this.ga = ga;
        this.gp = gp;
        this.gr = gr;

        setTitle("Dashboard - CliniBuddy System | Veterinário: " + gp.getClinica().getVeterinarioResponsavel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        this.setLayout(new BorderLayout());
        
        this.add(criarCabecalho(), BorderLayout.NORTH);
        this.add(criarAbasPrincipais(), BorderLayout.CENTER);
        
        this.setLocationRelativeTo(null);
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
    
    private JTabbedPane criarAbasPrincipais() {
        JTabbedPane tabbedPane = new JTabbedPane();

        PainelAgendamentos painelAgendamentos = new PainelAgendamentos(ga, gp);
        PainelHistorico painelHistorico = new PainelHistorico(gr, gp);
        PainelDiagnostico painelDiagnostico = new PainelDiagnostico(gr, gp);
        PainelPacientes painelPacientes = new PainelPacientes(gp,painelAgendamentos, painelHistorico, painelDiagnostico);
        PainelOpcoes painelOpcoes = new PainelOpcoes(gp.getClinica(), this);
        PainelAuxilioDiagnostico painelAuxilioDiagnostico = new PainelAuxilioDiagnostico(gr);
        tabbedPane.addTab("Pacientes", painelPacientes);
        tabbedPane.addTab("Agendamentos", painelAgendamentos);
        tabbedPane.addTab("Históricos", painelHistorico);
        tabbedPane.addTab("Diagnósticos", painelDiagnostico);
        tabbedPane.addTab("Auxilio ao Diagnóstico", painelAuxilioDiagnostico);
        tabbedPane.addTab("Opções", painelOpcoes);
        return tabbedPane;
    }
    

}