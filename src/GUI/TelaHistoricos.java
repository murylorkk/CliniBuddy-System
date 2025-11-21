import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaHistoricos extends JFrame {

    private JTextField txtPaciente, txtData;
    private JTextArea txtDescricao;
    private JButton btnAdicionar, btnCarregar, btnExcluir, btnLimpar;
    private JTable tabelaHistoricos;
    private DefaultTableModel tableModel;

    public TelaHistoricos() {
        setTitle("Históricos Clínicos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // FORMULÁRIO
        JPanel painelFormulario = new JPanel(new BorderLayout(5, 5));
        painelFormulario.setBorder(BorderFactory.createTitledBorder("Dados do Histórico"));

        JPanel painelCampos = new JPanel(new GridLayout(2, 2, 5, 5));
        painelCampos.add(new JLabel("Paciente:"));
        txtPaciente = new JTextField();
        painelCampos.add(txtPaciente);

        painelCampos.add(new JLabel("Data (dd/mm/aaaa):"));
        txtData = new JTextField();
        painelCampos.add(txtData);

        painelFormulario.add(painelCampos, BorderLayout.NORTH);

        txtDescricao = new JTextArea(4, 20);
        txtDescricao.setBorder(BorderFactory.createTitledBorder("Descrição / Observações"));
        painelFormulario.add(new JScrollPane(txtDescricao), BorderLayout.CENTER);

        JPanel painelBotoesForm = new JPanel();
        btnAdicionar = new JButton("Adicionar");
        btnLimpar = new JButton("Limpar");
        painelBotoesForm.add(btnAdicionar);
        painelBotoesForm.add(btnLimpar);
        painelFormulario.add(painelBotoesForm, BorderLayout.SOUTH);

        add(painelFormulario, BorderLayout.NORTH);

        // TABELA
        String[] colunas = {"Paciente", "Data", "Descrição"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaHistoricos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaHistoricos);
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
            String data = txtData.getText().trim();
            String descricao = txtDescricao.getText().trim();

            if (paciente.isEmpty() || data.isEmpty() || descricao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                return;
            }

            tableModel.addRow(new Object[]{paciente, data, descricao});
            limparCampos();
        });

        btnLimpar.addActionListener(e -> limparCampos());

        btnCarregar.addActionListener(e -> {
            int linha = tabelaHistoricos.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um histórico.");
                return;
            }

            txtPaciente.setText((String) tableModel.getValueAt(linha, 0));
            txtData.setText((String) tableModel.getValueAt(linha, 1));
            txtDescricao.setText((String) tableModel.getValueAt(linha, 2));
            tableModel.removeRow(linha);
        });

        btnExcluir.addActionListener(e -> {
            int linha = tabelaHistoricos.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um histórico para excluir.");
                return;
            }
            tableModel.removeRow(linha);
        });
    }

    private void limparCampos() {
        txtPaciente.setText("");
        txtData.setText("");
        txtDescricao.setText("");
        txtPaciente.requestFocus();
    }
}
