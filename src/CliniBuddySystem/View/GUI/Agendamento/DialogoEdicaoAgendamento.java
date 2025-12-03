package CliniBuddySystem.View.GUI.Agendamento;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import CliniBuddySystem.Controller.GerenciadorAgendamento;
import CliniBuddySystem.Model.Agendamento;
import CliniBuddySystem.Model.Agendamento.statusAgendamento;

public class DialogoEdicaoAgendamento extends JDialog {

    private JTextField campoNovoMotivo, campoNovaData, campoNovoHorario;
    private JComboBox<statusAgendamento> comboStatus;
    private JButton botãoSalvar, botãoCancelar;

    private final Agendamento agendamentoOriginal;
    private final GerenciadorAgendamento ga;
    private final PainelAgendamentos painelPai;

    public DialogoEdicaoAgendamento(JFrame framePai, Agendamento agendamento, GerenciadorAgendamento ga,
            PainelAgendamentos painelPai) {
        super(framePai, "Editar Agendamento: " + agendamento.getPaciente(), true);
        this.agendamentoOriginal = agendamento;
        this.ga = ga;
        this.painelPai = painelPai;
        setModal(true);
        setSize(350, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        montarInterface();
        adicionarListeners();
        setLocationRelativeTo(painelPai);

        campoNovoMotivo.setText(agendamentoOriginal.getMotivo());
        campoNovaData.setText(agendamentoOriginal.getData().format(formatoData));
        campoNovoHorario.setText(agendamentoOriginal.getHorario().format(formatoHora));
        comboStatus.setSelectedItem(agendamentoOriginal.getStatus());

    }

    private void montarInterface() {
        this.setLayout(new BorderLayout(10, 10));

        JPanel painelForma = new JPanel(new GridLayout(0, 2, 5, 5));
        campoNovoMotivo = new JTextField(5);
        campoNovaData = new JTextField(5);
        campoNovoHorario = new JTextField(5);
        painelForma.add(new JLabel("Motivo:"));
        painelForma.add(campoNovoMotivo);
        painelForma.add(new JLabel("Data(dd/mm/aaaa): "));
        painelForma.add(campoNovaData);
        painelForma.add(new JLabel("Horário(hh:mm): "));
        painelForma.add(campoNovoHorario);
        comboStatus = new JComboBox<>(statusAgendamento.values());
        painelForma.add(new JLabel("Status:"));
        painelForma.add(comboStatus);
        this.add(painelForma, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botãoSalvar = new JButton("Salvar Alterações");
        botãoCancelar = new JButton("Cancelar");
        botãoCancelar.addActionListener(e -> dispose());

        painelBotoes.add(botãoSalvar);
        painelBotoes.add(botãoCancelar);
        this.add(painelBotoes, BorderLayout.SOUTH);
    }

    DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

    public void adicionarListeners() {
        botãoSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                statusAgendamento agendamentoSts = (statusAgendamento) comboStatus.getSelectedItem();
                String strNovaData = campoNovaData.getText().trim();
                String strNovoHorario = campoNovoHorario.getText().trim();
                String strMotivo = campoNovoMotivo.getText().trim();
                if (agendamentoSts == null || strNovaData.isEmpty() || strNovoHorario.isEmpty()) {
                    JOptionPane.showMessageDialog(DialogoEdicaoAgendamento.this,
                            "Preencha todos os campos!", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                LocalDate novaData;
                LocalTime novaHora;
                try {
                    novaData = LocalDate.parse(strNovaData, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    novaHora = LocalTime.parse(strNovoHorario, DateTimeFormatter.ofPattern("HH:mm"));

                    LocalDateTime dataHoraAgendada = LocalDateTime.of(novaData, novaHora);

                    if (dataHoraAgendada.isBefore(LocalDateTime.now())) {
                        JOptionPane.showMessageDialog(DialogoEdicaoAgendamento.this,
                                "Não é possível remarcar para uma data/hora no passado.",
                                "Data Inválida",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(DialogoEdicaoAgendamento.this,
                            "Os campos devem estar em formatos válidos (Data: dd/MM/yyyy | Hora: HH:mm)!",
                            "Erro de Formatação",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(DialogoEdicaoAgendamento.this, ex.getMessage(), "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                agendamentoOriginal.setMotivo(strMotivo);
                ga.atualizarStatus(agendamentoOriginal, agendamentoSts);
                ga.remarcarAgendamento(agendamentoOriginal, novaData, novaHora);

                JOptionPane.showMessageDialog(DialogoEdicaoAgendamento.this,
                        "Agendamento de " + agendamentoOriginal.getPaciente() + " atualizado com sucesso!");

                painelPai.atualizarTabela();
                dispose();
            }
        });
    }
}
