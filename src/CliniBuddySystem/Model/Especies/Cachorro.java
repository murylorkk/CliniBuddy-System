package CliniBuddySystem.Model.Especies;

import CliniBuddySystem.Model.*;

public class Cachorro extends Paciente {
    public enum porteCachorro {
        PEQUENO,
        MEDIO,
        GRANDE
    };

    porteCachorro porte;

    public Cachorro(String nome, String raca, int idade, float peso, porteCachorro porte) {
        super(nome, raca, idade, peso);
        this.porte = porte;
    }

    // -> getters
    @Override
    public String getEspecie() {
        return "Cachorro";
    }

    public porteCachorro getPorte() {
        return porte;
    }
}
