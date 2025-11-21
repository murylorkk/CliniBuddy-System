import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaDiagnosticos extends JFrame {

    private JTextField txtPaciente, txtDiagnostico;
    private JTextArea txtObservacoes;
    private JButton btnAdicionar, btnCarregar, btnExcluir, btnLimpar;
    private JTable tabelaDiagnosticos;
    private DefaultTableModel tableModel;

    public TelaDiagnosticos() {
        setTitle("Diagnósticos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // FORMULÁRIO
        JPanel painelFormulario = new JPanel(new BorderLayout(5, 5));
        painelFormulario.setBorder(BorderFactory.createTitledBorder("Dados do Diagnóstico"));

        JPanel painelCampos = new JPanel(new GridLayout(2, 2, 5, 5));
        painelCampos.add(new JLabel("Paciente:"));
        txtPaciente = new JTextField();
        painelCampos.add(txtPaciente);

        painelCampos.add(new JLabel("Diagnóstico:"));
        txtDiagnostico = new JTextField();
        painelCampos.add(txtDiagnostico);

        painelFormulario.add(painelCampos, BorderLayout.NORTH);

        txtObservacoes = new JTextArea(4, 20);
        txtObservacoes.setBorder(BorderFactory.createTitledBorder("Observações / Laudo"));
        painelFormulario.add(new JScrollPane(txtObservacoes), BorderLayout.CENTER);

        JPanel painelBotoesForm = new JPanel();
        btnAdicionar = new JButton("Adicionar");
        btnLimpar = new JButton("Limpar");
        painelBotoesForm.add(btnAdicionar);
        painelBotoesForm.add(btnLimpar);
        painelFormulario.add(painelBotoesForm, BorderLayout.SOUTH);

        add(painelFormulario, BorderLayout.NORTH);

        // TABELA
        String[] colunas = {"Paciente", "Diagnóstico", "Observações"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaDiagnosticos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaDiagnosticos);
        add(scrollPane, BorderLayout.CENTER);

        // AÇÕES
        JPanel painelAcoes = new JPanel();
        btnCarregar = new JButton("Carregar para Edição");
        btnExcluir = new JButton("Excluir Selecionado");
        painelAcoes.add(btnCarregar);
        painelAcoes.add(btnExcluir);
        add(painelAcoes, BorderLayout.SOUTH);

        // LISTENERS
        btnAdicionar.addActionListener(e -> {
            String paciente = txtPaciente.getText().trim();
            String diagnostico = txtDiagnostico.getText().trim();
            String observacoes = txtObservacoes.getText().trim();

            if (paciente.isEmpty() || diagnostico.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Paciente e Diagnóstico são obrigatórios.");
                return;
            }

            tableModel.addRow(new Object[]{paciente, diagnostico, observacoes});
            limparCampos();
        });

        btnLimpar.addActionListener(e -> limparCampos());

        btnCarregar.addActionListener(e -> {
            int linha = tabelaDiagnosticos.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um diagnóstico.");
                return;
            }

            txtPaciente.setText((String) tableModel.getValueAt(linha, 0));
            txtDiagnostico.setText((String) tableModel.getValueAt(linha, 1));
            txtObservacoes.setText((String) tableModel.getValueAt(linha, 2));
            tableModel.removeRow(linha);
        });

        btnExcluir.addActionListener(e -> {
            int linha = tabelaDiagnosticos.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um diagnóstico para excluir.");
                return;
            }
            tableModel.removeRow(linha);
        });
    }

    private void limparCampos() {
        txtPaciente.setText("");
        txtDiagnostico.setText("");
        txtObservacoes.setText("");
        txtPaciente.requestFocus();
    }
}
