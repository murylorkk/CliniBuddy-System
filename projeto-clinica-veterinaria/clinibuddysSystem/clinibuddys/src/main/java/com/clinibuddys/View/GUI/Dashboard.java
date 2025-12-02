package com.clinibuddys.View.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.clinibuddys.Controller.GerenciadorAgendamento;
import com.clinibuddys.Controller.GerenciadorPaciente;
import com.clinibuddys.Controller.GerenciadorRegistros;

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
        this.setSize(900, 600);
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

        PainelPacientes painelPacientes = new PainelPacientes(gp);        
        tabbedPane.addTab("Pacientes", painelPacientes);
        
        // Adiciona as futuras classes (ex: Agendamentos)
        // 
        // PainelAgendamentos painelAgendamentos = new PainelAgendamentos(ga);
        // tabbedPane.addTab("Agendamentos", painelAgendamentos);

        return tabbedPane;
    }
    

}