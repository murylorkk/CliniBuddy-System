package com.clinibuddys.Model.Especies;

import com.clinibuddys.Model.Paciente;

public class Cachorro extends Paciente {
    public enum porteCachorro {
        PEQUENO,
        MEDIO,
        GRANDE
    };

    porteCachorro porte;

    public Cachorro(String nome, String raca, int idade, Double peso, porteCachorro porte) {
        super(nome, raca, idade, peso);
        this.porte = porte;
    }

    public Cachorro() {
        
    }
    // -> getters
    @Override
    public String getEspecie() {
        return "Cachorro";
    }

    public porteCachorro getPorte() {
        return porte;
    }

    public void setPorte(porteCachorro porte) {
        this.porte = porte;
    }
}
