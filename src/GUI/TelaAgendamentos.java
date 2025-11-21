import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaAgendamentos extends JFrame {

    private JTextField txtPaciente, txtData, txtHora, txtTipo;
    private JButton btnAdicionar, btnCarregar, btnExcluir, btnLimpar;
    private JTable tabelaAgendamentos;
    private DefaultTableModel tableModel;

    public TelaAgendamentos() {
        setTitle("Agendamentos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // FORMULÁRIO
        JPanel painelFormulario = new JPanel(new GridLayout(3, 4, 5, 5));
        painelFormulario.setBorder(BorderFactory.createTitledBorder("Dados do Agendamento"));

        painelFormulario.add(new JLabel("Paciente:"));
        txtPaciente = new JTextField();
        painelFormulario.add(txtPaciente);

        painelFormulario.add(new JLabel("Data (dd/mm/aaaa):"));
        txtData = new JTextField();
        painelFormulario.add(txtData);

        painelFormulario.add(new JLabel("Hora (hh:mm):"));
        txtHora = new JTextField();
        painelFormulario.add(txtHora);

        painelFormulario.add(new JLabel("Tipo (consulta, retorno, etc.):"));
        txtTipo = new JTextField();
        painelFormulario.add(txtTipo);

        btnAdicionar = new JButton("Adicionar");
        btnLimpar = new JButton("Limpar");
        painelFormulario.add(btnAdicionar);
        painelFormulario.add(btnLimpar);

        add(painelFormulario, BorderLayout.NORTH);

        // TABELA
        String[] colunas = {"Paciente", "Data", "Hora", "Tipo"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaAgendamentos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaAgendamentos);
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
            String hora = txtHora.getText().trim();
            String tipo = txtTipo.getText().trim();

            if (paciente.isEmpty() || data.isEmpty() || hora.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Paciente, Data e Hora são obrigatórios.");
                return;
            }

            tableModel.addRow(new Object[]{paciente, data, hora, tipo});
            limparCampos();
        });

        btnLimpar.addActionListener(e -> limparCampos());

        btnCarregar.addActionListener(e -> {
            int linha = tabelaAgendamentos.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um agendamento.");
                return;
            }

            txtPaciente.setText((String) tableModel.getValueAt(linha, 0));
            txtData.setText((String) tableModel.getValueAt(linha, 1));
            txtHora.setText((String) tableModel.getValueAt(linha, 2));
            txtTipo.setText((String) tableModel.getValueAt(linha, 3));
            tableModel.removeRow(linha);
        });

        btnExcluir.addActionListener(e -> {
            int linha = tabelaAgendamentos.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um agendamento para excluir.");
                return;
            }
            tableModel.removeRow(linha);
        });
    }

    private void limparCampos() {
        txtPaciente.setText("");
        txtData.setText("");
        txtHora.setText("");
        txtTipo.setText("");
        txtPaciente.requestFocus();
    }
}
