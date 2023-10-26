package sistema_eleitoral;

import java.util.Map;

import reader.CSVreader;

public class Urna {
    public static void main(String[] args) {
        CSVreader csvReader = new CSVreader();
        int i = 0;
        try {
            Map<String, Candidato> mapaCandidatos = csvReader.processaArquivoCandatos(
                    "C:\\Users\\felip\\Desktop\\sistema_eleitoral\\sistema_eleitoral\\src\\reader\\candidatos.csv");
            Map<String, Partido> mapaPartidos = csvReader.processaArquivoPartidos();
                    for (Candidato candidato : mapaCandidatos.values()) {
                   
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
