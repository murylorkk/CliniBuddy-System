package CliniBuddySystem.View;

import CliniBuddySystem.Controller.GerenciadorAgendamento;
import CliniBuddySystem.Controller.GerenciadorPaciente;
import CliniBuddySystem.Controller.GerenciadorRegistros;
import CliniBuddySystem.Model.Clinica;
import CliniBuddySystem.View.GUI.JanelaBoasVindas;

public class Main{
    public static void main(String[] args) {

        // ---> 1. criação da instância da clinica
        Clinica minhaClinica = new Clinica();

        // ---> 2. criação das instância das classes de controle
        GerenciadorPaciente gp = new GerenciadorPaciente(minhaClinica);
        GerenciadorAgendamento ga = new GerenciadorAgendamento(minhaClinica);
        GerenciadorRegistros gr = new GerenciadorRegistros(minhaClinica);

        new JanelaBoasVindas(minhaClinica);
    }
}