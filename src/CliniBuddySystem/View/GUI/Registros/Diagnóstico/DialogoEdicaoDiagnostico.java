package CliniBuddySystem.View.GUI.Registros.Diagnóstico;

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
import CliniBuddySystem.Model.Diagnostico;

public class DialogoEdicaoDiagnostico extends JDialog {

    private JTextField campoDoenca;
    private JTextArea areaDescricao;
    private JTextArea areaTratamento;
    private JTextArea areaRiscos;
    private JButton botãoSalvar;
    private JButton botãoCancelar;

    private final Diagnostico diagnosticoOriginal;
    private final GerenciadorRegistros gr;
    private final PainelDiagnostico painelPai;

    public DialogoEdicaoDiagnostico(JFrame framePai, Diagnostico diagnostico, GerenciadorRegistros gr,
            PainelDiagnostico painelPai) {
        
        super(framePai, "Editar Diagnóstico #" + diagnostico.getId(), true);
        
        this.diagnosticoOriginal = diagnostico;
        this.gr = gr;
        this.painelPai = painelPai;
        
        setModal(true);
        setSize(650, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        montarInterface();
        
        campoDoenca.setText(diagnosticoOriginal.getDoenca());
        areaDescricao.setText(diagnosticoOriginal.getDescricao());
        areaTratamento.setText(diagnosticoOriginal.getTratamentoSug());
        areaRiscos.setText(diagnosticoOriginal.getRiscos());
        
        adicionarListeners();
        setLocationRelativeTo(painelPai);
    }

    private void montarInterface() {
        this.setLayout(new BorderLayout(10, 10));

        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.campoDoenca = new JTextField(30);
        painelTopo.add(new JLabel("Doença/Diagnóstico:"));
        painelTopo.add(this.campoDoenca);
        this.add(painelTopo, BorderLayout.NORTH);

        JPanel painelTextAreas = new JPanel(new GridLayout(3, 1, 10, 10));
        painelTextAreas.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Margem

        this.areaDescricao = new JTextArea(5, 50);
        areaDescricao.setBorder(BorderFactory.createTitledBorder("Descrição"));
        painelTextAreas.add(new JScrollPane(areaDescricao));

        this.areaTratamento = new JTextArea(5, 50);
        areaTratamento.setBorder(BorderFactory.createTitledBorder("Tratamento Sugerido"));
        painelTextAreas.add(new JScrollPane(areaTratamento));

        this.areaRiscos = new JTextArea(5, 50);
        areaRiscos.setBorder(BorderFactory.createTitledBorder("Riscos Associados"));
        painelTextAreas.add(new JScrollPane(areaRiscos));

        this.add(painelTextAreas, BorderLayout.CENTER);

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
                String novaDoenca = campoDoenca.getText().trim();
                String novaDescricao = areaDescricao.getText().trim();
                String novoTratamento = areaTratamento.getText().trim();
                String novosRiscos = areaRiscos.getText().trim();

                if (novaDoenca.isEmpty() || novaDescricao.isEmpty()) {
                    JOptionPane.showMessageDialog(DialogoEdicaoDiagnostico.this, 
                        "Doença e Descrição são campos obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                diagnosticoOriginal.setDoenca(novaDoenca); 
                diagnosticoOriginal.setDescricao(novaDescricao);
                diagnosticoOriginal.setTratamento(novoTratamento);
                diagnosticoOriginal.setRiscos(novosRiscos);

                JOptionPane.showMessageDialog(DialogoEdicaoDiagnostico.this,
                        "Diagnóstico #" + diagnosticoOriginal.getId() + " atualizado com sucesso!");
                
                painelPai.atualizarTabela(); 
                dispose();
            }
        });
    }
}