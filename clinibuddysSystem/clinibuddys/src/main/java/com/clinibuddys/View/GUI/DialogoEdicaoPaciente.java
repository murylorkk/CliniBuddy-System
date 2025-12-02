package com.clinibuddys.View.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.clinibuddys.Controller.GerenciadorPaciente;
import com.clinibuddys.Model.Paciente;

public class DialogoEdicaoPaciente extends JDialog {

    private JTextField campoNovaIdade;
    private JTextField campoNovoPeso;
    private JButton botãoSalvar;

    private final Paciente pacienteOriginal;
    private final GerenciadorPaciente gp;
    private final PainelPacientes painelPai;

    public DialogoEdicaoPaciente(JFrame framePai, Paciente paciente, GerenciadorPaciente gp, PainelPacientes painelPai) {
        super(framePai, "Editar Paciente: " + paciente.getNome(), true);
        this.pacienteOriginal = paciente;
        this.gp = gp;
        this.painelPai = painelPai;
        setModal(true);
        setSize(350, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        montarInterface();
        adicionarListeners();
        setLocationRelativeTo(painelPai);
    }

    private void montarInterface() {
        this.setLayout(new BorderLayout(10, 10));

        JPanel painelForma = new JPanel(new GridLayout(0, 2, 5, 5));
        campoNovaIdade = new JTextField(5);
        campoNovoPeso = new JTextField(5);
        painelForma.add(new JLabel("Nova Idade (anos):"));
        painelForma.add(campoNovaIdade);
        painelForma.add(new JLabel("Novo Peso (kg):"));
        painelForma.add(campoNovoPeso);
        this.add(painelForma, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botãoSalvar = new JButton("Salvar Alterações");
        JButton botãoCancelar = new JButton("Cancelar");
        botãoCancelar.addActionListener(e -> dispose());
        painelBotoes.add(botãoSalvar);
        painelBotoes.add(botãoCancelar);
        this.add(painelBotoes, BorderLayout.SOUTH);
    }

    public void adicionarListeners() {
        botãoSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String textoNovaIdade = campoNovaIdade.getText().trim();
                String textoNovoPeso = campoNovoPeso.getText().trim();

                if (textoNovaIdade.isEmpty() || textoNovoPeso.isEmpty()) {
                    JOptionPane.showMessageDialog(DialogoEdicaoPaciente.this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int novaIdade;
                Double novoPeso;
                try {
                    novaIdade = Integer.parseInt(textoNovaIdade);
                    novoPeso = Double.parseDouble(textoNovoPeso.replace(",", "."));

                    if (novaIdade <= 0 || novoPeso <= 0) {
                        throw new IllegalArgumentException("Idade e Peso devem ser valores positivos.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(DialogoEdicaoPaciente.this, "Idade e Peso devem ser números válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(DialogoEdicaoPaciente.this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                gp.atualizarPaciente(pacienteOriginal, novaIdade, novoPeso);
                JOptionPane.showMessageDialog(DialogoEdicaoPaciente.this,
                        "Paciente " + pacienteOriginal.getNome() + " atualizado com sucesso!");
                painelPai.atualizarTabela();
                dispose();
            }
        });
    }
}
