package CliniBuddySystem.View.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import CliniBuddySystem.Controller.GerenciadorAgendamento;
import CliniBuddySystem.Controller.GerenciadorPaciente;
import CliniBuddySystem.Controller.GerenciadorRegistros;
import CliniBuddySystem.Model.Clinica;

public class JanelaBoasVindas extends JFrame {
    private JTextField campoVeterinarioNome;
    private Clinica clinica;

    public JanelaBoasVindas(Clinica clinica) {
        super("CliniBuddy System");
        this.clinica = clinica;

        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JLabel bemVindo = new JLabel("Bem-vindo ao CliniBuddy System");
        JLabel bemVindo2 = new JLabel("Sistema de Gestão Clínica");
        bemVindo.setBounds(100, 30, 400, 25);
        bemVindo2.setBounds(120, 45, 400, 25);
        this.add(bemVindo);
        this.add(bemVindo2);

        campoVeterinarioNome = new JTextField(20);
        campoVeterinarioNome.setBounds(110, 120, 150, 25);
        this.add(campoVeterinarioNome);

        JLabel nomeVet = new JLabel("Nome do veterinário: ");
        nomeVet.setBounds(125, 100, 400, 25);
        this.add(nomeVet);

        JButton botaoEnter = new JButton("Entrar");
        botaoEnter.setBounds(145, 150, 75, 20);
        this.add(botaoEnter);

        this.getRootPane().setDefaultButton(botaoEnter);
        this.setVisible(true);
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