package CliniBuddySystem.View.GUI.Registros.Histórico;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import java.awt.Dimension;

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
import CliniBuddySystem.Model.Historico;
import CliniBuddySystem.Model.Paciente;

public class PainelHistorico extends JPanel {
    private final GerenciadorRegistros gr;
    private final GerenciadorPaciente gp;

    private JComboBox<Paciente> comboPaciente;
    private JTextField campoAcompanhante;
    private JTextField campoTemperatura;
    private JTextArea areaObservacoes;

    private DefaultTableModel tabelaModelo;
    private JTable tabelaHistoricos;
    private JButton botãoAdicionar, botãoLimpar, botãoExcluir, botãoEditar, botãoExibir;

    public PainelHistorico(GerenciadorRegistros gr, GerenciadorPaciente gp) {
        this.gr = gr;
        this.gp = gp;
        montarComponentes();
        adicionarListeners();
    }

    private void montarComponentes() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelFormulario = new JPanel(new BorderLayout(5, 5));
        painelFormulario.setBorder(BorderFactory.createTitledBorder("Dados do Histórico"));
        painelFormulario.setPreferredSize(new Dimension(450, 0));

        JPanel painelCampos = new JPanel(new GridLayout(0, 3, 5, 5)); 
        this.comboPaciente = new JComboBox<>();
        carregarPacientesNoCombo();
        painelCampos.add(new JLabel("Paciente: "));
        painelCampos.add(this.comboPaciente);
        painelCampos.add(new JLabel(""));

      
        painelCampos.add(new JLabel("Acompanhante: "));
        this.campoAcompanhante = new JTextField();
        painelCampos.add(this.campoAcompanhante);
        painelCampos.add(new JLabel("")); 


        painelCampos.add(new JLabel("Temperatura (°C): "));
        this.campoTemperatura = new JTextField();
        painelCampos.add(this.campoTemperatura);
        painelCampos.add(new JLabel("")); 
        
        this.areaObservacoes = new JTextArea(5, 20); 
        this.areaObservacoes.setBorder(BorderFactory.createTitledBorder("Observações/Detalhes da Consulta"));
        JScrollPane scrollObservacoes = new JScrollPane(this.areaObservacoes); 

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        this.botãoAdicionar = new JButton("Adicionar Registro");
        this.botãoLimpar = new JButton("Limpar Campos");
        painelBotoes.add(this.botãoAdicionar);
        painelBotoes.add(this.botãoLimpar);


        painelFormulario.add(painelCampos, BorderLayout.NORTH);
        painelFormulario.add(scrollObservacoes, BorderLayout.CENTER);  
        painelFormulario.add(painelBotoes, BorderLayout.SOUTH);
        this.add(painelFormulario, BorderLayout.WEST); 

        tabelaModelo = criarModeloTabela();
        tabelaHistoricos = new JTable(tabelaModelo);
        JScrollPane scrollTabela = new JScrollPane(tabelaHistoricos);
        

        JPanel painelAcoesTabela = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.botãoEditar = new JButton("Editar Selecionado");
        this.botãoExcluir = new JButton("Excluir Selecionado");
        this.botãoExibir = new JButton("Exibir Selecionado");
        painelAcoesTabela.add(botãoExibir);
        painelAcoesTabela.add(botãoEditar);
        painelAcoesTabela.add(botãoExcluir);

        JPanel painelListagem = new JPanel(new BorderLayout());
        painelListagem.setBorder(BorderFactory.createTitledBorder("Históricos do Paciente Selecionado"));
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

    private void limparCampos() {
        campoTemperatura.setText("");
        campoAcompanhante.setText("");
        areaObservacoes.setText("");
    }

    public void atualizarTabela() {
        String[] colunas = { "ID", "Data", "Temp (°C)", "Acompanhante", "Observações" };

        Paciente pacienteSelecionado = (Paciente) comboPaciente.getSelectedItem();

        if (pacienteSelecionado == null) {
            tabelaModelo.setDataVector(new Object[0][colunas.length], colunas);
            tabelaModelo.fireTableDataChanged();
            return;
        }

        List<Historico> lista = gr.getHistoricosDoPaciente(pacienteSelecionado);
        Object[][] dados = new Object[lista.size()][colunas.length];

        for (int i = 0; i < lista.size(); i++) {
            Historico h = lista.get(i);
            dados[i][0] = h.getId();
            dados[i][1] = h.getDataFormatada();
            dados[i][2] = h.getTemperatura();
            dados[i][3] = h.getAcompanhante();
            dados[i][4] = h.getObservacoes();
        }
        
        tabelaModelo.setDataVector(dados, colunas);
        tabelaModelo.fireTableDataChanged();
    }

    private DefaultTableModel criarModeloTabela() {
        String[] colunas = { "ID", "Data", "Temp (°C)", "Acompanhante", "Observações" };
        return new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };
    }

    private Historico getHistoricoSelecionado() {
        int indiceLinha = tabelaHistoricos.getSelectedRow();
        if (indiceLinha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um histórico na lista primeiro.",
                    "Erro de Seleção", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        
        Paciente paciente = (Paciente) comboPaciente.getSelectedItem();
        if (paciente == null) return null; 

        List<Historico> lista = gr.getHistoricosDoPaciente(paciente);
        return lista.get(indiceLinha);
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
            String acompanhante = campoAcompanhante.getText().trim();
            String obs = areaObservacoes.getText().trim();
            String tempStr = campoTemperatura.getText().trim();

            if (paciente == null || tempStr.isEmpty() || acompanhante.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um paciente, informe a temperatura e o acompanhante.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                float temperatura = Float.parseFloat(tempStr.replace(",", "."));
                
                gr.adicionarHistorico(paciente, acompanhante, temperatura, obs);
                
                limparCampos();
                atualizarTabela();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Temperatura deve ser um número válido!", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        
        botãoLimpar.addActionListener(e -> limparCampos());

        botãoExcluir.addActionListener(e -> {
            Historico historico = getHistoricoSelecionado();
            if (historico != null) {
                Paciente paciente = (Paciente) comboPaciente.getSelectedItem();
                
                int confirmacao = JOptionPane.showConfirmDialog(this, 
                    "Tem certeza que deseja excluir este registro de histórico?", 
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

                if (confirmacao == JOptionPane.YES_OPTION) {
                    gr.excluirHistorico(paciente, historico); 
                    atualizarTabela();
                    JOptionPane.showMessageDialog(this, "Registro excluído com sucesso!");
                }
            }
        });
    
        botãoEditar.addActionListener(e -> {
            Historico historico = getHistoricoSelecionado();
            if (historico != null) {
                JFrame dashboardFrame = (JFrame) SwingUtilities.getWindowAncestor(PainelHistorico.this);
                DialogoEdicaoHistorico dialogo = new DialogoEdicaoHistorico(
                        dashboardFrame,
                        historico,
                        gr,
                        PainelHistorico.this);

                dialogo.setModal(true);
                dialogo.setLocationRelativeTo(PainelHistorico.this);
                dialogo.setVisible(true);
            }
        });
        
        botãoExibir.addActionListener(e ->{
            Historico historico = getHistoricoSelecionado();
            Paciente p = (Paciente) comboPaciente.getSelectedItem();
            if(historico != null && p != null){
                new TelaVisualizacaoHistorico(historico, p).setVisible(true);
            }
        });

    }

    
}