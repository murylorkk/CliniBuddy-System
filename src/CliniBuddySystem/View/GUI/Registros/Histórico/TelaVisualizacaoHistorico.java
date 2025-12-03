package CliniBuddySystem.View.GUI.Registros.Histórico;

import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.*;

import CliniBuddySystem.Model.Historico;
import CliniBuddySystem.Model.Paciente;
public class TelaVisualizacaoHistorico extends JFrame {
    private final Paciente p;

    public TelaVisualizacaoHistorico(Historico historico, Paciente p) {
        super("Histórico #"+ historico.getId());
        this.p = p;
        this.setSize(500, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.setLayout(new GridLayout(15,4,5,5));
        this.add(new Label("Veterinário responsável: " + historico.getVeterinarioResponsavel()));
        this.add(new JLabel("Paciente: " + p.getNome()));
        this.add(new JLabel("ID: " + historico.getId()));
        this.add(new JLabel("Data: " + historico.getDataFormatada()));
        this.add(new JLabel("Temperatura (°C): " + historico.getAcompanhante()));
        this.add(new JLabel("Observações: " + historico.getObservacoes()));

        this.setVisible(true);
    }
}