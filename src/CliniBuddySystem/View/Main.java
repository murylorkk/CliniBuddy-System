package CliniBuddySystem.View;

import CliniBuddySystem.Model.*;
import CliniBuddySystem.Controller.*;

public class Main{
    public static void main(String[] args) {

        // ---> 1. criação da instância da clinica
        Clinica minhaClinica = new Clinica();

        // ---> 2. criação das instância das classes de controle
        GerenciadorPaciente gp = new GerenciadorPaciente(minhaClinica);
        GerenciadorAgendamento ga = new GerenciadorAgendamento(minhaClinica);
        GerenciadorRegistros gr = new GerenciadorRegistros(minhaClinica);

        // ---> 3.criação da instância do menu
        Menu meuMenu = new Menu(minhaClinica, gp, ga, gr);

        //inicia e exibe o menu
        meuMenu.exibir();
    }
}