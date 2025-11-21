import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class MenuPrincipal extends JFrame{
    public MenuPrincipal(){
        setTitle("CliniBuddy System - Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnPacientes = new JButton("Pacientes");
        JButton btnHistoricos = new JButton("Históricos");
        JButton btnDiagnosticos = new JButton("Diagnosticos");
        JButton btnAgendamentos = new JButton("Agendamentos");

        add(btnPacientes);
        add(btnHistoricos);
        add(btnDiagnosticos);
        add(btnAgendamentos);

        btnPacientes.addActionListener(e->gerenciarPacientes());
        btnHistoricos.addActionListener(e->gerenciarHistorico());
        btnDiagnosticos.addActionListener(e->gerenciarDiagnostico());
        btnAgendamentos.addActionListener(e->gerenciarAgendamentos());
 
    }
    private void gerenciarPacientes(){
        new TelaPacientes().setVisible(true);
    }
    private void gerenciarHistorico(){
        new TelaHistoricos().setVisible(true);
    }
    private void gerenciarDiagnostico(){
        new TelaDiagnosticos().setVisible(true);
    }
    private void gerenciarAgendamentos(){
        new TelaAgendamentos().setVisible(true);
    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(()->{
            new MenuPrincipal().setVisible(true);
        });
    }
}