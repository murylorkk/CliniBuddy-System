package CliniBuddySystem.View.GUI.Agendamento;

import CliniBuddySystem.Controller.GerenciadorAgendamento;
import CliniBuddySystem.Controller.GerenciadorPaciente;
import CliniBuddySystem.Model.Agendamento.statusAgendamento;
import CliniBuddySystem.Model.Agendamento;
import CliniBuddySystem.Model.Paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PainelAgendamentos extends JPanel {

    private final GerenciadorAgendamento ga;
    private final GerenciadorPaciente gp;
    private JComboBox comboPaciente;
    private JSpinner spinnerData, spinnerHora;
    private JTextField campoTipo;
    private JButton botãoAdicionar, botãoLimpar, botãoExcluir, botãoEditar;
    private JTable tabelaAgendamentos;
    private DefaultTableModel tabelaModelo;
    private DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

    public PainelAgendamentos(GerenciadorAgendamento ga, GerenciadorPaciente gp) {
        this.ga = ga;
        this.gp = gp;
        this.setLayout(new BorderLayout(10, 10));
        montarComponentes();
        adicionarListeners();
        atualizarTabela();
    }

    private void carregarPacientesNoCombo() {
        comboPaciente.removeAllItems();

        List<Paciente> pacientes = gp.getPacientes();

        for (Paciente p : pacientes) {
            comboPaciente.addItem(p);
        }
    }

    private void limparCampos() {
        campoTipo.setText("");
    }

    private DefaultTableModel criarModeloTabela() {
        String[] colunas = { "Paciente", "Data", "Hora", "Tipo", "Status" };

        List<Agendamento> listaAgendamentos = ga.getAgendamentos();

        Object[][] dados = new Object[listaAgendamentos.size()][colunas.length];

        for (int i = 0; i < listaAgendamentos.size(); i++) {
            Agendamento a = listaAgendamentos.get(i);
            dados[i][0] = a.getPaciente().getNome() + " (" + a.getPaciente().getEspecie() + ")";
            dados[i][1] = a.getData();
            dados[i][2] = a.getHorario();
            dados[i][3] = a.getMotivo();
            dados[i][4] = a.getStatus();
        }

        return new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };
    }

    public void atualizarTabela() {
        String[] colunas = { "Paciente", "Data", "Hora", "Motivo/Tipo", "Status" };

        List<Agendamento> listaAgendamentos = ga.getAgendamentos();

        Object[][] dados = new Object[listaAgendamentos.size()][colunas.length];

        for (int i = 0; i < listaAgendamentos.size(); i++) {
            Agendamento a = listaAgendamentos.get(i);
            dados[i][0] = a.getPaciente().getNome() + " (" + a.getPaciente().getEspecie() + ")";
            dados[i][1] = a.getData();
            dados[i][2] = a.getHorario();
            dados[i][3] = a.getMotivo();
            dados[i][4] = a.getStatus();
        }
        tabelaModelo.setDataVector(dados, colunas);
        tabelaModelo.fireTableDataChanged();
    }

    private void montarComponentes() {
        JPanel painelFormulario = new JPanel(new BorderLayout(5, 5));
        painelFormulario.setBorder(BorderFactory.createTitledBorder("Dados do Agendamento"));

        JPanel painelCampos = new JPanel(new GridLayout(0, 3, 5, 5));

        comboPaciente = new JComboBox<>();
        carregarPacientesNoCombo();

        SpinnerDateModel dateModel = new SpinnerDateModel();
        spinnerData = new JSpinner(dateModel);
        String dateFormat = "dd/MM/yyyy";
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerData, dateFormat);

        SpinnerDateModel timeModel = new SpinnerDateModel();
        spinnerHora = new JSpinner(timeModel);
        String timeFormat = "HH:mm";
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinnerHora, timeFormat);

        spinnerHora.setEditor(timeEditor);
        spinnerData.setEditor(dateEditor);
        painelCampos.add(new JLabel("Paciente: "));
        painelCampos.add(comboPaciente);
        painelCampos.add(new JLabel(""));

        painelCampos.add(new JLabel("Data (dd/mm/aaaa): "));
        painelCampos.add(spinnerData);
        painelCampos.add(new JLabel(""));

        botãoAdicionar = new JButton("Adicionar");
        botãoLimpar = new JButton("Limpar");

        painelCampos.add(new JLabel("Hora (HH:mm): "));
        painelCampos.add(spinnerHora);
        painelCampos.add(new JLabel(""));
        painelCampos.add(new JLabel("Motivo/tipo: "));
        campoTipo = new JTextField();
        painelCampos.add(campoTipo);
        painelCampos.add(new JLabel(""));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        painelBotoes.add(botãoAdicionar);
        painelBotoes.add(botãoLimpar);

        painelFormulario.add(painelCampos, BorderLayout.CENTER);
        painelFormulario.add(painelBotoes, BorderLayout.SOUTH);

        this.add(painelFormulario, BorderLayout.NORTH);

        tabelaModelo = criarModeloTabela();
        tabelaAgendamentos = new JTable(tabelaModelo);
        JScrollPane scrollPane = new JScrollPane(tabelaAgendamentos);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel painelAcoes = new JPanel();
        botãoEditar = new JButton("Editar Selecionado");
        botãoExcluir = new JButton("Excluir Selecionado");
        painelAcoes.add(botãoEditar);
        painelAcoes.add(botãoExcluir);

        carregarPacientesNoCombo();
        atualizarTabela();
        this.add(painelAcoes, BorderLayout.SOUTH);
    }

    private Agendamento getAgendamentoSelecionado() {
        int indiceLinha = tabelaAgendamentos.getSelectedRow();
        if (indiceLinha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento na lista primeiro.",
                    "Erro de Seleção", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        List<Agendamento> lista = ga.getAgendamentos();
        return lista.get(indiceLinha);
    }

    public void recarregarPacientes() {
        carregarPacientesNoCombo();
    }

    private void adicionarListeners() {
        botãoAdicionar.addActionListener(e -> {

            Paciente paciente = (Paciente) comboPaciente.getSelectedItem();
            java.util.Date dateValue = (java.util.Date) spinnerData.getValue(); 
            java.util.Date timeValue = (java.util.Date) spinnerHora.getValue();
            String motivo = campoTipo.getText().trim();

            if (paciente == null) {
                JOptionPane.showMessageDialog(this, "Paciente é obrigatório.");
                return;
            }

            LocalDateTime dataHoraAgendada = LocalDateTime.of(
                    dateValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    timeValue.toInstant().atZone(ZoneId.systemDefault()).toLocalTime());

            LocalDate data = dataHoraAgendada.toLocalDate();
            LocalTime hora = dataHoraAgendada.toLocalTime();

            try {
                LocalDateTime agora = LocalDateTime.now();

                if (dataHoraAgendada.isBefore(agora)) {
                    throw new IllegalArgumentException("Agendamentos não podem ser marcados no passado ou no agora.");
                }

                ga.cadastrarAgendamento(paciente, data, hora, motivo, statusAgendamento.AGENDADO);

                atualizarTabela();
                limparCampos();

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Regra de Negócio",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        botãoLimpar.addActionListener(e -> limparCampos());

        botãoExcluir.addActionListener(e -> {
            Agendamento agendamento = getAgendamentoSelecionado();
            if (agendamento != null) {
                int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que quer cancelar este agendamento?",
                        "Confirmar Cancelamento", JOptionPane.YES_NO_OPTION);
                if (confirmacao == JOptionPane.YES_OPTION) {
                    ga.cancelarAgendamento(agendamento);
                    atualizarTabela();
                }
            }
        });

        botãoEditar.addActionListener(e -> {
            Agendamento agendamento = getAgendamentoSelecionado();
            if (agendamento != null) {
                JFrame dashboardFrame = (JFrame) SwingUtilities.getWindowAncestor(PainelAgendamentos.this);
                DialogoEdicaoAgendamento dialogo = new DialogoEdicaoAgendamento(
                        dashboardFrame,
                        agendamento,
                        ga,
                        PainelAgendamentos.this);

                dialogo.setModal(true);
                dialogo.setLocationRelativeTo(PainelAgendamentos.this);
                dialogo.setVisible(true);
            }
        });
    }
}