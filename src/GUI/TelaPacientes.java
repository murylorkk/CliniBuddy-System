import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaPacientes extends JFrame {

    private JTextField txtNome, txtEspecie, txtRaca;
    private JButton btnAdicionar, btnCarregar, btnExcluir, btnLimpar;
    private JTable tabelaPacientes;
    private DefaultTableModel tableModel;

    public TelaPacientes() {
        setTitle("Gestão de Pacientes");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // FORMULÁRIO
        JPanel painelFormulario = new JPanel(new GridLayout(4, 2, 5, 5));
        painelFormulario.setBorder(BorderFactory.createTitledBorder("Dados do Paciente"));

        painelFormulario.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painelFormulario.add(txtNome);

        painelFormulario.add(new JLabel("Espécie:"));
        txtEspecie = new JTextField();
        painelFormulario.add(txtEspecie);

        painelFormulario.add(new JLabel("Raça:"));
        txtRaca = new JTextField();
        painelFormulario.add(txtRaca);

        btnAdicionar = new JButton("Adicionar");
        btnLimpar = new JButton("Limpar");
        painelFormulario.add(btnAdicionar);
        painelFormulario.add(btnLimpar);

        add(painelFormulario, BorderLayout.NORTH);

        // TABELA
        String[] colunas = {"Nome", "Espécie", "Raça"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaPacientes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaPacientes);
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
            String nome = txtNome.getText().trim();
            String especie = txtEspecie.getText().trim();
            String raca = txtRaca.getText().trim();

            if (nome.isEmpty() || especie.isEmpty() || raca.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                return;
            }

            tableModel.addRow(new Object[]{nome, especie, raca});
            limparCampos();
        });

        btnLimpar.addActionListener(e -> limparCampos());

        btnCarregar.addActionListener(e -> {
            int linha = tabelaPacientes.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um paciente na tabela.");
                return;
            }

            txtNome.setText((String) tableModel.getValueAt(linha, 0));
            txtEspecie.setText((String) tableModel.getValueAt(linha, 1));
            txtRaca.setText((String) tableModel.getValueAt(linha, 2));
            tableModel.removeRow(linha); // simples: remove e depois adiciona de novo
        });

        btnExcluir.addActionListener(e -> {
            int linha = tabelaPacientes.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um paciente para excluir.");
                return;
            }
            tableModel.removeRow(linha);
        });
    }

    private void limparCampos() {
        txtNome.setText("");
        txtEspecie.setText("");
        txtRaca.setText("");
        txtNome.requestFocus();
    }
}
