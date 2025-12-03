package CliniBuddySystem.View.GUI;

import CliniBuddySystem.Model.Clinica;
import javax.swing.*;
import java.awt.*;

public class PainelOpcoes extends JPanel {

    // --- DEPENDÊNCIAS ---
    private final Clinica clinica;
    private final JFrame dashboardFrame; 
    
    // --- COMPONENTES ---
    private JButton botãoModoEscuro;
    private JButton botãoAlterarNome;
    private JButton botãoSair;
    private JLabel lblNomeVetAtual;
    private JLabel lblTemaAtual; 

    public PainelOpcoes(Clinica clinica, JFrame dashboardFrame) {
        this.clinica = clinica;
        this.dashboardFrame = dashboardFrame;
        montarComponentes();
        adicionarListeners();
    }

    private void montarComponentes() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); 

        JPanel painelCentral = new JPanel(new GridLayout(4, 2, 15, 15));
        painelCentral.setBorder(BorderFactory.createTitledBorder("Configurações do Sistema"));

        lblNomeVetAtual = new JLabel("Vet. Responsável: " + clinica.getVeterinarioResponsavel());
        botãoAlterarNome = new JButton("Alterar Nome");
        
        painelCentral.add(lblNomeVetAtual);
        painelCentral.add(botãoAlterarNome);

        lblTemaAtual = new JLabel("Tema Atual: " + UIManager.getLookAndFeel().getName());
        botãoModoEscuro = new JButton("Alternar Tema");
        
        painelCentral.add(lblTemaAtual);
        painelCentral.add(botãoModoEscuro);
        
        botãoSair = new JButton("Sair do Sistema");
        painelCentral.add(new JLabel("--- Encerrar ---")); 
        painelCentral.add(botãoSair);

        this.add(painelCentral);
    }

    private void adicionarListeners() {
        botãoAlterarNome.addActionListener(e -> {
            String novoNome = JOptionPane.showInputDialog(dashboardFrame, 
                "Digite o novo nome do Veterinário Responsável:", 
                clinica.getVeterinarioResponsavel());
            
            if (novoNome != null && !novoNome.trim().isEmpty()) {
                clinica.setVeterinarioResponsavel(novoNome.trim()); 
                lblNomeVetAtual.setText("Vet. Responsável: " + novoNome.trim()); 
                
                dashboardFrame.setTitle("Dashboard - CliniBuddy System | Veterinário: " + novoNome.trim());
                JOptionPane.showMessageDialog(dashboardFrame, "Nome atualizado com sucesso!");
            }
        });

        botãoModoEscuro.addActionListener(e -> {
            try {
                String temaAtual = UIManager.getLookAndFeel().getName();
                String novoLafClass;

                if (temaAtual.contains("Nimbus")) {
                    novoLafClass = UIManager.getSystemLookAndFeelClassName();
                } else {
                    novoLafClass = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
                }

                UIManager.setLookAndFeel(novoLafClass);
                SwingUtilities.updateComponentTreeUI(dashboardFrame);
                
                lblTemaAtual.setText("Tema Atual: " + UIManager.getLookAndFeel().getName());

            } catch (Exception ex) {
                try { 
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    SwingUtilities.updateComponentTreeUI(dashboardFrame);
                } catch (Exception ignored) {} 
                JOptionPane.showMessageDialog(dashboardFrame, "Erro ao trocar tema.", "Erro de UI", JOptionPane.WARNING_MESSAGE);
            }
        });

        botãoSair.addActionListener(e -> {
            int confirmacao = JOptionPane.showConfirmDialog(dashboardFrame, 
                "Tem certeza que deseja sair do sistema?", 
                "Confirmar Saída", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}