package com.clinibuddys.View;

import com.clinibuddys.Controller.GerenciadorAgendamento;
import com.clinibuddys.Controller.GerenciadorPaciente;
import com.clinibuddys.Controller.GerenciadorRegistros;
import com.clinibuddys.Model.Clinica;
import com.clinibuddys.View.GUI.JanelaBoasVindas;

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