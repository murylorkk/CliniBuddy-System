package CliniBuddySystem.Model.Especies;

import CliniBuddySystem.Model.*;

public class Gato extends Paciente{

    public Gato(String nome, String raca, int idade, float peso) {
        // A primeira linha DEVE ser a chamada ao construtor da superclasse
        super(nome, raca, idade, peso);
        
    }
    @Override
    public String getEspecie() {
        return "Gato";
    }
}

