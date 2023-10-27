package sistema_eleitoral;

import java.util.Map;

import reader.CSVreader;

public class Urna {
    public static void main(String[] args) {
        CSVreader csvReader = new CSVreader();
        int i = 1;
        int nr_vagas = 0;
        try {
            Map<String, Candidato> mapaCandidatos = csvReader.processaArquivoCandatos(
                    "C:\\Users\\felip\\trabalho-poo\\sistema_eleitoral\\src\\reader\\candidatos.csv");
            Map<String, Partido> mapaPartidos = csvReader.processaArquivoPartidos(
                    "C:\\Users\\felip\\trabalho-poo\\sistema_eleitoral\\src\\reader\\votacao.csv");
            for (Candidato c : mapaCandidatos.values()) {
                if (c.getCd_sit_tot_turno() != null) {
                    if (c.getCd_sit_tot_turno().contains("2") || c.getCd_sit_tot_turno().contains("3")) {
                        nr_vagas++;
                    }
                }
            }
            System.out.println("Numero de vagas: " + nr_vagas);
            for (Candidato c : mapaCandidatos.values()) {
                if (c.getCd_sit_tot_turno() != null) {
                    if (c.getCd_sit_tot_turno().contains("2") || c.getCd_sit_tot_turno().contains("3")) {
                        System.out.println(i+"- "+c);
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    
}
}

