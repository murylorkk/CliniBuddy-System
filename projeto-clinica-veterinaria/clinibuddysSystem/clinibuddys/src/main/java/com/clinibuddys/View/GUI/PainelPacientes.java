package com.clinibuddys.View.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.clinibuddys.Controller.GerenciadorPaciente;
import com.clinibuddys.Model.Especies.Cachorro.porteCachorro;
import com.clinibuddys.Model.banco_de_dados.criacao_tabelas.ConexaoDB;
import com.clinibuddys.Model.banco_de_dados.objetosDAO.PacienteDAO;
import com.clinibuddys.Model.Paciente;

public class PainelPacientes extends JPanel {

    private final GerenciadorPaciente gp;

    private JTextField campoNome;
    private JTextField campoIdade;
    private JTextField campoPeso;
    private JTextField campoRaça;
    private JRadioButton radioGato;
    private JRadioButton radioCachorro;
    private JLabel porteDog;
    private JComboBox<porteCachorro> porte;
    private JButton botãoCadastrar;
    private JButton botãoExcluirPaciente;
    private JButton botãoEditarPaciente;
    private DefaultTableModel tabelaModelo;
    private JTable tabelaPacientes;

    private PacienteDAO pacienteDAO = new PacienteDAO(ConexaoDB.conectar());

    public PainelPacientes(GerenciadorPaciente gp) {
        this.gp = gp;
        this.setLayout(new BorderLayout());
        montarComponentes();
        adicionarListeners();
    }

    private void montarComponentes() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelEsquerda = new JPanel(new BorderLayout());
        painelEsquerda.setPreferredSize(new Dimension(350, 0));

        JPanel formulario = new JPanel(new GridLayout(0, 2, 5, 5));

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));

        formulario.add(new JLabel("Nome: "));
        this.campoNome = new JTextField();
        formulario.add(this.campoNome);

        formulario.add(new JLabel("Idade (anos): "));
        this.campoIdade = new JTextField();
        formulario.add(this.campoIdade);

        formulario.add(new JLabel("Peso (kg): "));
        this.campoPeso = new JTextField();
        formulario.add(this.campoPeso);

        formulario.add(new JLabel("Raça: "));
        this.campoRaça = new JTextField();
        formulario.add(this.campoRaça);

        this.radioGato = new JRadioButton("Gato");
        this.radioCachorro = new JRadioButton("Cachorro");

        ButtonGroup especie = new ButtonGroup();
        especie.add(radioGato);
        especie.add(radioCachorro);

        JPanel painelEspecie = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelEspecie.add(radioGato);
        painelEspecie.add(radioCachorro);

        formulario.add(new JLabel("Espécie: "));
        formulario.add(painelEspecie);

        this.porte = new JComboBox<>(porteCachorro.values());
        this.porteDog = new JLabel("Porte: ");
        formulario.add(this.porteDog);
        formulario.add(this.porte);
        porte.setVisible(false);
        porteDog.setVisible(false);

        painelEsquerda.add(formulario, BorderLayout.NORTH);
        painelEsquerda.add(painelBotao, BorderLayout.SOUTH);

        this.botãoCadastrar = new JButton("Cadastrar");
        painelBotao.add(botãoCadastrar);

        this.add(painelEsquerda, BorderLayout.WEST);

        this.add(criarPainelLista(), BorderLayout.CENTER);
    }

    private DefaultTableModel criarModeloTabela() {
        String[] colunas = {"Nome", "Espécie", "Raça", "Idade", "Peso"};

        List<Paciente> lista = gp.getPacientes(); // -> possível carregamento de dados do banco dados ?

        Object[][] dados = new Object[lista.size()][colunas.length];

        for (int i = 0; i < lista.size(); i++) {
            Paciente p = lista.get(i);
            dados[i][0] = p.getNome();
            dados[i][1] = p.getEspecie();
            dados[i][2] = p.getRaca();
            dados[i][3] = p.getIdade();
            dados[i][4] = p.getPeso();
        }

        return new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public void atualizarTabela() {
        String[] colunas = {"Nome", "Espécie", "Raça", "Idade(anos)", "Peso(Kg)"};

        //List<Paciente> lista = gp.getPacientes();
          List<Paciente> lista = pacienteDAO.listarTodos();

        Object[][] dados = new Object[lista.size()][colunas.length];

        for (int i = 0; i < lista.size(); i++) {
            Paciente p = lista.get(i);
            dados[i][0] = p.getNome();
            dados[i][1] = p.getEspecie();
            dados[i][2] = p.getRaca();
            dados[i][3] = p.getIdade();
            dados[i][4] = p.getPeso();
        }
        tabelaModelo.setDataVector(dados, colunas);
        tabelaModelo.fireTableDataChanged();
    }

    private JPanel criarPainelLista() {
        this.tabelaModelo = criarModeloTabela();
        this.tabelaPacientes = new JTable(tabelaModelo);

        JPanel painelLista = new JPanel(new BorderLayout());
        painelLista.setBorder(BorderFactory.createTitledBorder("Lista de Pacientes"));

        JScrollPane scrollPane = new JScrollPane(this.tabelaPacientes);
        painelLista.add(scrollPane, BorderLayout.CENTER);

        JPanel painelAções = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.botãoExcluirPaciente = new JButton("Excluir Paciente");
        this.botãoEditarPaciente = new JButton("Editar Paciente");
        painelAções.add(this.botãoExcluirPaciente);
        painelAções.add(this.botãoEditarPaciente);
        painelLista.add(painelAções, BorderLayout.SOUTH);
        return painelLista;
    }

    private Paciente getPacienteSelecionado() {
        int indiceLinha = tabelaPacientes.getSelectedRow();
        if (indiceLinha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente na lista primeiro.",
                    "Erro de Seleção", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        List<Paciente> lista = gp.getPacientes();
        return lista.get(indiceLinha);
    }

    private void adicionarListeners() {
        ActionListener especieListener = e -> {
            boolean isCachorro = radioCachorro.isSelected();

            porteDog.setVisible(isCachorro);
            porte.setVisible(isCachorro);

            PainelPacientes.this.revalidate();
            PainelPacientes.this.repaint();
        };

        radioGato.addActionListener(especieListener);

        radioCachorro.addActionListener(especieListener);

        botãoCadastrar.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String nome;
                String raça;
                int idade = 0;
                Double peso = 0.0;
                nome = campoNome.getText();
                raça = campoRaça.getText();

                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O nome do paciente não pode ser vazio.",
                            "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    idade = Integer.parseInt(campoIdade.getText());
                    peso = Double.parseDouble(campoPeso.getText().replace(",", "."));

                    if (idade <= 0 || peso <= 0) {
                        // Lançar sua própria exceção para ir direto ao catch, ou usar JOptionPane e
                        // 'return'
                        throw new IllegalArgumentException("Valores não podem ser zero ou negativos.");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Idade e Peso devem ser números válidos!",
                            "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                    return;

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (raça.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O nome da raça não pode ser vazio.",
                            "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (radioGato.isSelected()) {
                    gp.cadastrarGato(nome, raça, idade, peso);
                } else if (radioCachorro.isSelected()) {
                    porteCachorro porteSelecionado = (porteCachorro) porte.getSelectedItem();
                    if (porteSelecionado == null) {
                        JOptionPane.showMessageDialog(null, "Selecione o porte do cachorro.");
                        return;
                    }
                    gp.cadastrarCachorro(nome, raça, idade, peso, porteSelecionado);
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, Selecione uma espécie", "Erro de Cadastro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(null, "Paciente cadastrado com sucesso!");
                campoNome.setText("");
                campoIdade.setText("");
                campoPeso.setText("");
                campoRaça.setText("");
                atualizarTabela();
            }
        }
        );
        botãoExcluirPaciente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                Paciente paciente = getPacienteSelecionado();
                if (paciente != null) {
                    int confirmacao = JOptionPane.showConfirmDialog(PainelPacientes.this,
                            "Tem certeza que deseja excluir " + paciente.getNome() + "?",
                            "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

                    if (confirmacao == JOptionPane.YES_OPTION) {
                        gp.excluirPaciente(paciente);
                        atualizarTabela();
                        JOptionPane.showMessageDialog(PainelPacientes.this, "Paciente excluído com sucesso.");
                    }
                }
            }
        }
        );
        botãoEditarPaciente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                Paciente paciente = getPacienteSelecionado();
                if (paciente != null) {
                    JFrame dashboardFrame = (JFrame) SwingUtilities.getWindowAncestor(PainelPacientes.this);
                    DialogoEdicaoPaciente dialogo = new DialogoEdicaoPaciente(
                            dashboardFrame,
                            paciente,
                            gp,
                            PainelPacientes.this 
                    );

                    dialogo.setModal(true);
                    dialogo.setLocationRelativeTo(PainelPacientes.this);
                    dialogo.setVisible(true);

                }
            }
        });
    }
}
