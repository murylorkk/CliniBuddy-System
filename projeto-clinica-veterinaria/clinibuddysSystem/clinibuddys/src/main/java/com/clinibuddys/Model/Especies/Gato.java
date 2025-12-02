package com.clinibuddys.Model.Especies;

import com.clinibuddys.Model.*;

public class Gato extends Paciente{

    public Gato(String nome, String raca, int idade, Double peso) {
        // A primeira linha DEVE ser a chamada ao construtor da superclasse
        super(nome, raca, idade, peso);
        
    }

    public Gato(){ 
    }
    @Override
    public String getEspecie() {
        return "Gato";
    }
}

