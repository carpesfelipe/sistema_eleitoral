package sistema_eleitoral;

import java.util.Map;

import reader.CSVreader;

public class Urna {
    public static void main(String[] args) {
        CSVreader csvReader = new CSVreader();
        int i = 0;
        int nr_vagas=0;
        try {
            Map<String, Candidato> mapaCandidatos = csvReader.processaArquivoCandatos(
                    "C:\\Users\\felip\\Desktop\\sistema_eleitoral\\sistema_eleitoral\\src\\reader\\candidatos.csv");
            // Map<String, Partido> mapaPartidos = csvReader.processaArquivoPartidos();
            for (Candidato c : mapaCandidatos.values()) {
                if(c.ehEleito(c)){
                    nr_vagas++;
                }
            }
            System.out.println("Numero de vagas: "+nr_vagas);
             for (Candidato c : mapaCandidatos.values()) {
                if(c.ehEleito(c)){
                   System.out.println(c);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
