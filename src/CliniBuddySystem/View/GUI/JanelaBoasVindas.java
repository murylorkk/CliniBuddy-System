package CliniBuddySystem.View.GUI;

import CliniBuddySystem.Controller.*;
import CliniBuddySystem.Model.Clinica;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class JanelaBoasVindas extends JFrame {
    private JTextField campoVeterinarioNome;
    private Clinica clinica;

    public JanelaBoasVindas(Clinica clinica) {
        super("CliniBuddy System - Gerenciamento Veterinário");
        this.clinica = clinica;

        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);

        JLabel bemVindo = new JLabel("Bem-vindo ao CliniBuddy System");
        bemVindo.setBounds(100, 30, 400, 25);
        this.add(bemVindo);

        campoVeterinarioNome = new JTextField(20);
        campoVeterinarioNome.setBounds(110, 120, 150, 25);
        this.add(campoVeterinarioNome);
        campoVeterinarioNome.setLocale(bemVindo.getLocale());

        JLabel nomeVet = new JLabel("Nome do veterinário: ");
        nomeVet.setBounds(125, 100, 400, 25);
        this.add(nomeVet);

        JButton botaoEnter = new JButton("Entrar");
        botaoEnter.setBounds(145, 150, 75, 20);
        this.add(botaoEnter);
        this.getRootPane().setDefaultButton(botaoEnter);
        botaoEnter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String nomeVet = campoVeterinarioNome.getText().trim();
                
                if (nomeVet.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O nome do veterinário não pode ser vazio.",
                            "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                clinica.setVeterinarioResponsavel(nomeVet); 

                GerenciadorPaciente gp = new GerenciadorPaciente(clinica);
                GerenciadorAgendamento ga = new GerenciadorAgendamento(clinica);
                GerenciadorRegistros gr = new GerenciadorRegistros(clinica);

                new Dashboard(gp, ga, gr);

                dispose();
            }
        });
    }
}