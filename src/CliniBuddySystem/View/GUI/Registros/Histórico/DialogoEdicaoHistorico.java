package CliniBuddySystem.View.GUI.Registros.Histórico;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import CliniBuddySystem.Controller.GerenciadorRegistros;
import CliniBuddySystem.Model.Historico;

public class DialogoEdicaoHistorico extends JDialog {

    private JTextField campoAcompanhante;
    private JTextField campoTemperatura;
    private JTextArea areaObservacoes;
    private JButton botãoSalvar;
    private JButton botãoCancelar; 
    
    private final Historico historicoOriginal;
    private final GerenciadorRegistros gr;
    private final PainelHistorico painelPai;

    public DialogoEdicaoHistorico(JFrame framePai, Historico historico, GerenciadorRegistros gr,
            PainelHistorico painelPai) {
        
        super(framePai, "Editar Histórico: " + historico.getId(), true); 
        
        this.historicoOriginal = historico;
        this.gr = gr;
        this.painelPai = painelPai;
        
        setModal(true);
        setSize(500, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        montarInterface();
        
        campoAcompanhante.setText(historicoOriginal.getAcompanhante());
        campoTemperatura.setText(String.valueOf(historicoOriginal.getTemperatura())); 
        areaObservacoes.setText(historicoOriginal.getObservacoes());
        
        adicionarListeners();
        setLocationRelativeTo(painelPai);
    }

    private void montarInterface() {
        this.setLayout(new BorderLayout(10, 10));
        
        JPanel painelForma = new JPanel(new GridLayout(0, 2, 5, 5));
        
        campoAcompanhante = new JTextField(20);
        campoTemperatura = new JTextField(10);
        areaObservacoes = new JTextArea(5, 40);

        painelForma.add(new JLabel("Acompanhante:"));
        painelForma.add(campoAcompanhante);
        painelForma.add(new JLabel("Temperatura (°C):"));
        painelForma.add(campoTemperatura);
        
        this.add(painelForma, BorderLayout.NORTH);


        areaObservacoes.setBorder(BorderFactory.createTitledBorder("Observações"));
        this.add(new JScrollPane(areaObservacoes), BorderLayout.CENTER);
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botãoSalvar = new JButton("Salvar Alterações");
        botãoCancelar = new JButton("Cancelar");
        botãoCancelar.addActionListener(e -> dispose());
        
        painelBotoes.add(botãoSalvar);
        painelBotoes.add(botãoCancelar);
        this.add(painelBotoes, BorderLayout.SOUTH);
    }

    public void adicionarListeners() {
        botãoSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String strAcomp = campoAcompanhante.getText().trim();
                String strTemp = campoTemperatura.getText().trim();
                String strObs = areaObservacoes.getText().trim();
                
                if (strTemp.isEmpty()) {
                    JOptionPane.showMessageDialog(DialogoEdicaoHistorico.this, "A temperatura é obrigatória!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                float novaTemperatura;
                try {
                    novaTemperatura = Float.parseFloat(strTemp.replace(",", "."));
                    if (novaTemperatura <= 0) {
                        throw new IllegalArgumentException("Temperatura deve ser positiva.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(DialogoEdicaoHistorico.this, "Temperatura deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(DialogoEdicaoHistorico.this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                historicoOriginal.setAcompanhante(strAcomp);
                historicoOriginal.setTemperatura(novaTemperatura); 
                historicoOriginal.setObservacoes(strObs);

                JOptionPane.showMessageDialog(DialogoEdicaoHistorico.this,
                        "Histórico de " + historicoOriginal.getId() + " atualizado com sucesso!");
                
                painelPai.atualizarTabela();
                dispose();
            }
        });
    }
}