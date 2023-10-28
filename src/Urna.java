
import java.util.LinkedList;
import java.util.Map;

import reader.CSVreader;
import sistema_eleitoral.Candidato;
import sistema_eleitoral.Partido;

public class Urna {
    public static void main(String[] args) {
        CSVreader csvReader = new CSVreader();
        int i = 1;
        int nr_vagas = 0;
        String tipo = "";
        if (args[0].equals("--federal")) {
            tipo = "6";
        } else {
            tipo = "7";
        }
        try {
            Map<String, Candidato> mapaCandidatos = csvReader.processaArquivoCandatos(
                    "C:\\Users\\felip\\trabalho-poo\\sistema_eleitoral\\src\\reader\\candidatos.csv");
            Map<String, Partido> mapaPartidos = csvReader.processaArquivoPartidos(
                    "C:\\Users\\felip\\trabalho-poo\\sistema_eleitoral\\src\\reader\\votacao.csv", args[0]);
            for (Candidato c : mapaCandidatos.values()) {
                if (c.getCd_cargo().equals(tipo)) {
                    if (c.getCd_sit_tot_turno() != null) {
                        if (c.getCd_sit_tot_turno().contains("2") || c.getCd_sit_tot_turno().contains("3")) {
                            nr_vagas++;
                        }
                    }
                }
            }
            System.out.println("Numero de vagas:\n" + nr_vagas);
            if (tipo.equals("7")) {
                System.out.println("Deputados estaduais eleitos:");
            } else {
                System.out.println("Deputados federais eleitos:");
            }
            LinkedList<Candidato> listaCandidatos = new LinkedList<Candidato>(mapaCandidatos.values());
            listaCandidatos.sort((Candidato c1, Candidato c2) -> c2.getQt_votos() - c1.getQt_votos());
            for (Candidato c : listaCandidatos) {
                if (c.getCd_cargo().equals(tipo)) {

                    if (c.getCd_sit_tot_turno() != null) {
                        if (c.getCd_sit_tot_turno().contains("2") || c.getCd_sit_tot_turno().contains("3")) {
                            System.out.println(i + "- " + c);
                            i++;
                        }
                    }
                }
            }
            System.out.println(
                    "Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
            int cont = 0;
            for (Candidato c : listaCandidatos) {
                if (cont == nr_vagas) {
                    break;
                }
                if (c.getCd_cargo().equals(tipo)) {
                    System.out.println(cont + 1 + "- " + c);
                    cont++;
                }
            }
            System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");
            cont = 0;
            for (Candidato c : listaCandidatos) {
                if (cont == nr_vagas) {
                    break;
                }
                if(c.getCd_cargo().equals(tipo)){
                   
                    if (c.getCd_sit_tot_turno() != null) {
                        if (!(c.getCd_sit_tot_turno().contains("2") || c.getCd_sit_tot_turno().contains("3"))) {
                            System.out.println(cont+1 + "- " + c);
                        }
                    }
                    cont++;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
