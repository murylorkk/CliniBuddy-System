package CliniBuddySystem.View.GUI.Registros.Diagnóstico;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import CliniBuddySystem.Controller.GerenciadorPaciente;
import CliniBuddySystem.Controller.GerenciadorRegistros;
import CliniBuddySystem.Model.Diagnostico;
import CliniBuddySystem.Model.Paciente;

public class PainelDiagnostico extends JPanel {

    private final GerenciadorRegistros gr;
    private final GerenciadorPaciente gp;

    private JComboBox<Paciente> comboPaciente;
    private JTextField campoDoenca;
    private JTextArea areaDescricao;
    private JTextArea areaTratamento;
    private JTextArea areaRiscos;

    private DefaultTableModel tabelaModelo;
    private JTable tabelaDiagnosticos;
    private JButton botãoAdicionar, botãoLimpar, botãoExcluir, botãoEditar, botãoExibir;

    public PainelDiagnostico(GerenciadorRegistros gr, GerenciadorPaciente gp) {
        this.gr = gr;
        this.gp = gp;
        montarComponentes();
        adicionarListeners();
        atualizarTabela();
    }

    private void montarComponentes() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelFormulario = new JPanel(new BorderLayout(5, 5));
        painelFormulario.setBorder(BorderFactory.createTitledBorder("Registro de Diagnóstico"));
        painelFormulario.setPreferredSize(new Dimension(500, 0));

        JPanel painelCamposSimples = new JPanel(new GridLayout(0, 2, 5, 5));
        this.comboPaciente = new JComboBox<>();
        carregarPacientesNoCombo();
        this.campoDoenca = new JTextField();

        painelCamposSimples.add(new JLabel("Paciente: "));
        painelCamposSimples.add(this.comboPaciente);
        painelCamposSimples.add(new JLabel("Doença: "));
        painelCamposSimples.add(this.campoDoenca);

        JPanel painelTextAreas = new JPanel(new GridLayout(3, 1, 5, 5));

        this.areaDescricao = new JTextArea(4, 20);
        this.areaTratamento = new JTextArea(4, 20);
        this.areaRiscos = new JTextArea(4, 20);

        areaDescricao.setBorder(BorderFactory.createTitledBorder("Descrição"));
        areaTratamento.setBorder(BorderFactory.createTitledBorder("Tratamento Sugerido"));
        areaRiscos.setBorder(BorderFactory.createTitledBorder("Riscos"));

        painelTextAreas.add(new JScrollPane(areaDescricao));
        painelTextAreas.add(new JScrollPane(areaTratamento));
        painelTextAreas.add(new JScrollPane(areaRiscos));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        this.botãoAdicionar = new JButton("Adicionar Diagnóstico");
        this.botãoLimpar = new JButton("Limpar Campos");
        painelBotoes.add(this.botãoAdicionar);
        painelBotoes.add(this.botãoLimpar);

        painelFormulario.add(painelCamposSimples, BorderLayout.NORTH);
        painelFormulario.add(painelTextAreas, BorderLayout.CENTER);
        painelFormulario.add(painelBotoes, BorderLayout.SOUTH);
        this.add(painelFormulario, BorderLayout.WEST); // Adiciona o formulário à esquerda

        tabelaModelo = criarModeloTabela();
        tabelaDiagnosticos = new JTable(tabelaModelo);
        JScrollPane scrollTabela = new JScrollPane(tabelaDiagnosticos);

        JPanel painelAcoesTabela = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botãoExibir = new JButton("Visualizar Detalhes");
        botãoEditar = new JButton("Editar Selecionado");
        botãoExcluir = new JButton("Excluir Selecionado");

        painelAcoesTabela.add(botãoExibir);
        painelAcoesTabela.add(botãoEditar);
        painelAcoesTabela.add(botãoExcluir);

        JPanel painelListagem = new JPanel(new BorderLayout());
        painelListagem.setBorder(BorderFactory.createTitledBorder("Diagnósticos do Paciente Selecionado"));
        painelListagem.add(scrollTabela, BorderLayout.CENTER);
        painelListagem.add(painelAcoesTabela, BorderLayout.SOUTH);

        this.add(painelListagem, BorderLayout.CENTER);

        atualizarTabela();
    }

    public void carregarPacientesNoCombo() {
        comboPaciente.removeAllItems();
        List<Paciente> pacientes = gp.getPacientes();
        for (Paciente p : pacientes) {
            comboPaciente.addItem(p);
        }
    }

    public void atualizarTabela() {
        String[] colunas = { "ID", "Doença", "Tratamento", "Data" };

        Paciente pacienteSelecionado = (Paciente) comboPaciente.getSelectedItem();

        if (pacienteSelecionado == null) {
            tabelaModelo.setDataVector(new Object[0][colunas.length], colunas);
            tabelaModelo.fireTableDataChanged();
            return;
        }

        List<Diagnostico> lista = gr.getDiagnosticosDoPaciente(pacienteSelecionado);
        Object[][] dados = new Object[lista.size()][colunas.length];

        for (int i = 0; i < lista.size(); i++) {
            Diagnostico d = lista.get(i);
            dados[i][0] = d.getId();
            dados[i][1] = d.getDoenca();
            dados[i][2] = d.getTratamentoSug();
            dados[i][3] = d.getDataFormatada();
        }

        tabelaModelo.setDataVector(dados, colunas);
        tabelaModelo.fireTableDataChanged();
    }

    private DefaultTableModel criarModeloTabela() {
        String[] colunas = { "ID", "Doença", "Tratamento", "Data" };
        return new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };
    }

    private Diagnostico getDiagnosticoSelecionado() {
        int indiceLinha = tabelaDiagnosticos.getSelectedRow();
        if (indiceLinha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um diagnóstico na lista primeiro.",
                    "Erro de Seleção", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        Paciente paciente = (Paciente) comboPaciente.getSelectedItem();
        if (paciente == null)
            return null;

        List<Diagnostico> lista = gr.getDiagnosticosDoPaciente(paciente);
        return lista.get(indiceLinha);
    }

    private void limparCampos() {
        campoDoenca.setText("");
        areaDescricao.setText("");
        areaTratamento.setText("");
        areaRiscos.setText("");
    }
    public void recarregarPacientes(){
        carregarPacientesNoCombo();
    }
    private void adicionarListeners() {
        comboPaciente.addActionListener(e -> {
            atualizarTabela();
        });

        botãoAdicionar.addActionListener(e -> {
            Paciente paciente = (Paciente) comboPaciente.getSelectedItem();
            String doenca = campoDoenca.getText().trim();
            String descricao = areaDescricao.getText().trim();
            String tratamento = areaTratamento.getText().trim();
            String riscos = areaRiscos.getText().trim();

            if (paciente == null || doenca.isEmpty() || descricao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Paciente, Doença e Descrição são obrigatórios.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            gr.adicionarDiagnostico(paciente, doenca, descricao, tratamento, riscos);

            limparCampos();
            atualizarTabela();
        });

        botãoLimpar.addActionListener(e -> limparCampos());

        botãoExcluir.addActionListener(e -> {
            Diagnostico diagnostico = getDiagnosticoSelecionado();
            if (diagnostico != null) {
                Paciente paciente = (Paciente) comboPaciente.getSelectedItem();

                int confirmacao = JOptionPane.showConfirmDialog(this,
                        "Tem certeza que deseja excluir este diagnóstico?",
                        "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

                if (confirmacao == JOptionPane.YES_OPTION) {
                    gr.excluirDiagnostico(paciente, diagnostico);
                    atualizarTabela();
                    JOptionPane.showMessageDialog(this, "Diagnóstico excluído com sucesso!");
                }
            }
        });

        botãoEditar.addActionListener(e -> {
            Diagnostico diagnostico = getDiagnosticoSelecionado();
            if (diagnostico != null) {
                JFrame dashboardFrame = (JFrame) SwingUtilities.getWindowAncestor(PainelDiagnostico.this);
                DialogoEdicaoDiagnostico dialogo = new DialogoEdicaoDiagnostico(
                        dashboardFrame,
                        diagnostico,
                        gr,
                        PainelDiagnostico.this);

                dialogo.setModal(true);
                dialogo.setLocationRelativeTo(PainelDiagnostico.this);
                dialogo.setVisible(true);
            }
        });

        botãoExibir.addActionListener(e -> {
            Diagnostico diagnostico = getDiagnosticoSelecionado();
            Paciente p = (Paciente) comboPaciente.getSelectedItem();
            if(diagnostico != null && p != null){
                new TelaVisualizacaoDiagnostico(diagnostico, p);
            }
        });
    }
}