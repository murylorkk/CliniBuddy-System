package CliniBuddySystem.View.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import CliniBuddySystem.Controller.GerenciadorRegistros;
import CliniBuddySystem.Model.DoencaTemplate;

public class PainelAuxilioDiagnostico extends JPanel {

    private final GerenciadorRegistros gr; // Controller para a Base de Conhecimento

    private JTextField campoSintoma;
    private JButton botãoBuscar;

    // Tabela para exibir os resultados (Doença, Tratamento, Riscos)
    private JTable tabelaSugestoes;
    private DefaultTableModel tabelaModelo;

    public PainelAuxilioDiagnostico(GerenciadorRegistros gr) {
        this.gr = gr;
        montarComponentes();
        adicionarListeners();
    }

    public void montarComponentes() {
        this.setLayout(new BorderLayout(10, 10));

        // --- 1. PAINEL DE BUSCA (NORTH) ---
        // FlowLayout para organizar os elementos lado a lado
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        campoSintoma = new JTextField(30);
        botãoBuscar = new JButton("Buscar Sugestões");

        painelBusca.add(new JLabel("Sintoma-chave:"));
        painelBusca.add(campoSintoma);
        painelBusca.add(botãoBuscar);

        this.add(painelBusca, BorderLayout.NORTH);

        // --- 2. TABELA DE RESULTADOS (CENTER) ---

        // Inicialização do Modelo (cria as colunas sem dados iniciais)
        tabelaModelo = new DefaultTableModel(new String[] { "Doença Sugerida", "Tratamento Padrão", "Riscos" }, 0);
        tabelaSugestoes = new JTable(tabelaModelo);

        JScrollPane scrollPane = new JScrollPane(tabelaSugestoes);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Sugestões de Diagnóstico"));

        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void adicionarListeners() {
        botãoBuscar.addActionListener(e -> {
            String sintoma = campoSintoma.getText().trim();

            if (sintoma.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite um sintoma para iniciar a busca.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // --- 1. CHAMADA AO CONTROLLER ---
            // Chama o método que busca no 'índiceSintomas' do GerenciadorRegistros
            List<DoencaTemplate> sugestoes = gr.sugerirDiagnosticosPorSintoma(sintoma);

            // --- 2. TRATAMENTO E EXIBIÇÃO ---
            if (sugestoes.isEmpty()) {
                // Se não encontrou, limpa a tabela e notifica
                tabelaModelo.setRowCount(0); // Limpa o conteúdo
                tabelaModelo.fireTableDataChanged();
                JOptionPane.showMessageDialog(this, "Nenhum diagnóstico sugerido para o sintoma: " + sintoma,
                        "Sem Resultados", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Converte e exibe os resultados na JTable
                exibirSugestoes(sugestoes);
            }
        });
    }

    // Método auxiliar para converter o resultado do Controller para a View (JTable)
    private void exibirSugestoes(List<DoencaTemplate> sugestoes) {
        String[] colunas = { "Doença Sugerida", "Tratamento Padrão", "Riscos" };
        Object[][] dados = new Object[sugestoes.size()][colunas.length];

        for (int i = 0; i < sugestoes.size(); i++) {
            DoencaTemplate d = sugestoes.get(i);
            dados[i][0] = d.getNomeDoenca();
            dados[i][1] = d.getTratamentoPadrao();
            dados[i][2] = d.getRiscosPadrao();
        }

        tabelaModelo.setDataVector(dados, colunas);
        tabelaModelo.fireTableDataChanged();
    }
}