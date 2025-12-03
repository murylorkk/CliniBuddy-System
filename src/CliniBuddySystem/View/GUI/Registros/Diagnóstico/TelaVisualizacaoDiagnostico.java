package CliniBuddySystem.View.GUI.Registros.Diagnóstico;

import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.*;

import CliniBuddySystem.Model.Diagnostico;
import CliniBuddySystem.Model.Paciente;
public class TelaVisualizacaoDiagnostico extends JFrame {
    private final Paciente p;

    public TelaVisualizacaoDiagnostico(Diagnostico diagnostico, Paciente p) {
        super("Diagnóstico #"+ diagnostico.getId());
        this.p = p;
        this.setSize(500, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.setLayout(new GridLayout(15,4,5,5));
        this.add(new Label("Veterinário responsável: " + diagnostico.getVeterinarioResponsavel()));
        this.add(new JLabel("Paciente: " + p.getNome()));
        this.add(new JLabel("ID: " + diagnostico.getId()));
        this.add(new JLabel("Data: " + diagnostico.getDataFormatada()));
        this.add(new JLabel("Doença: " + diagnostico.getDoenca()));
        this.add(new JLabel("Descrição: " + diagnostico.getDescricao()));
        this.add(new JLabel("Tratamento: " + diagnostico.getTratamentoSug()));
        this.add(new JLabel("Riscos: " + diagnostico.getRiscos()));
        this.setVisible(true);
    }
}