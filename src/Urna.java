
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Map;

import reader.CSVreader;
import sistema_eleitoral.Candidato;
import sistema_eleitoral.Partido;

public class Urna {
    public static void main(String[] args) {
        CSVreader csvReader = new CSVreader();
        LocalDate ld = LocalDate.parse(args[3], java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        int i = 1;
        int nr_vagas = 0;
        String tipo = "";
        if (args[0].equals("--federal")) {
            tipo = "6";
        } else {
            tipo = "7";
        }
        try {
            Map<String, Candidato> mapaCandidatos = csvReader.processaArquivoCandatos(args[1],tipo);
            Map<String, Partido> mapaPartidos = csvReader.processaArquivoPartidos(args[2], tipo);
            System.out.println("Número de vagas: " + mapaCandidatos.size());
            for (Candidato c : mapaCandidatos.values()) {
                if (c.ehDoTipoDeEleicao(tipo)) {
                    if (c.getCd_sit_tot_turno() != null) {
                        if (c.ehCandidatoEleito()) {
                            nr_vagas++;
                        }
                    }
                }
            }
            System.out.println("Numero de vagas: " + nr_vagas);
            if (tipo.equals("7")) {
                System.out.println("Deputados estaduais eleitos:");
            } else {
                System.out.println("Deputados federais eleitos:");
            }
            LinkedList<Candidato> listaCandidatos = new LinkedList<Candidato>(mapaCandidatos.values());
            listaCandidatos.sort((Candidato c1, Candidato c2) -> c2.getQt_votos() - c1.getQt_votos());
            for (Candidato c : listaCandidatos) {
                if (c.ehDoTipoDeEleicao(tipo)) {

                    if (c.getCd_sit_tot_turno() != null) {
                       if (c.ehCandidatoEleito()) {
                            System.out.println(i + "- " + c);
                            i++;
                        }
                    }
                }
            }
            System.out.println(
                    "Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
            int cont = 0;
            int menor_num_votos = 0;
            for (Candidato c : listaCandidatos) {
                
                if (c.ehDoTipoDeEleicao(tipo)) {
                    System.out.println(cont + 1 + "- " + c);
                    cont++;
                    if(cont==nr_vagas){
                        menor_num_votos = c.getQt_votos();
                        System.out.println("Número mínimo de votos para se eleger: "+menor_num_votos);
                        break;
                    }
                }
            }
            System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");
            cont = 1;
            for (Candidato c : listaCandidatos) {
                if (cont == nr_vagas) {
                    break;
                }
                if(c.ehDoTipoDeEleicao(tipo)){
                   
                    if (c.getCd_sit_tot_turno() != null) {
                        if (!(c.ehCandidatoEleito())) {
                            System.out.println(cont+ "- " + c);
                        }
                    }
                    cont++;
                }
            }
            cont=0;
            System.out.println("Eleitos, que se beneficiaram do sistema proporcional:");
            for (Candidato c : listaCandidatos) {
                if(c.ehDoTipoDeEleicao(tipo)){
                    if (c.getCd_sit_tot_turno() != null) {
                        if (c.ehCandidatoEleito()) {
                            if(c.getQt_votos()<menor_num_votos){
                                System.out.println(cont+1 + "- " + c);
                            }
                        }
                    }
                    cont++;
                }
            }
            cont=1;
            System.out.println("Votação dos partidos e número de candidatos eleitos:");
            LinkedList<Partido> listaPartidos = new LinkedList<Partido>(mapaPartidos.values());
            listaPartidos.sort((Partido p1, Partido p2) -> p2.getQtVotosTotal() - p1.getQtVotosTotal());
            for(Partido p: listaPartidos){
                System.out.println(cont+" - "+p);
                cont++;
            }
            System.out.println("Eleitos, por faixa etária (na data de eleição):");
            RelatorioIdade(listaCandidatos, tipo, ld);
            System.out.println("Eleitos, por gênero:");
            RelatorioGenero(listaCandidatos, tipo);
        } catch (Exception e) {
            System.out.println("Erro ao processar arquivo");
            System.out.println(e.getMessage());
        }

    }

    public static void RelatorioIdade(LinkedList<Candidato> listaCandidatos, String tipo, LocalDate dataEleicao) {
        int qtdMenor30 = 0;
        int qtdMenor40 = 0;
        int qtdMenor50 = 0;
        int qtdMenor60 = 0;
        int qtdMaior60=0;
        int eleitos=0;
        for (Candidato c : listaCandidatos) {
            if (c.ehDoTipoDeEleicao(tipo)) {
                if (c.getCd_sit_tot_turno() != null) {
                    if (c.ehCandidatoEleito()) {
                        if (c.calculaIdade(dataEleicao) < 30) {
                            qtdMenor30++;
                        } else if (c.calculaIdade(dataEleicao)>=30 && c.calculaIdade(dataEleicao) < 40) {
                            qtdMenor40++;

                        } else if (c.calculaIdade(dataEleicao)>=40 && c.calculaIdade(dataEleicao) < 50) {
                            qtdMenor50++;
                        } else if (c.calculaIdade(dataEleicao)>=50 && c.calculaIdade(dataEleicao) < 60) {
                            qtdMenor60++;
                        }else{
                            qtdMaior60++;
                        }
                        eleitos++;
                        
                    }
                }
            }
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
        float result=((float)qtdMenor30/(float)eleitos);
        result*=100;
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        System.out.println("      Idade < 30: "+ qtdMenor30+" ("+ nf.format(result)+"%)");
        result=(float)qtdMenor40/eleitos;
        result*=100;
        System.out.println("30 <= Idade < 40: "+ qtdMenor40+" ("+nf.format(result)+"%)");
        result=(float)qtdMenor50/eleitos;
        result*=100;
        System.out.println("40 <= Idade < 50: "+qtdMenor50+" ("+nf.format(result)+"%)");
        result=(float)qtdMenor60/eleitos;
        result*=100;
        System.out.println("50 <= Idade < 60: "+qtdMenor60+" ("+nf.format(result)+"%)");
        result=(float)qtdMaior60/eleitos;
        result*=100;
        System.out.println("60 <= Idade:      "+qtdMaior60+" ("+nf.format(result)+"%)");
    }
    public static void RelatorioGenero(LinkedList<Candidato> listaCandidatos, String tipo){
        int qtdMasculino=0;
        int qtdFeminino=0;
        int eleitos=0;
        for(Candidato c: listaCandidatos){
            if(c.ehDoTipoDeEleicao(tipo)){
                if(c.getCd_sit_tot_turno()!=null){
                    if(c.ehCandidatoEleito()){
                        if(c.getCd_genero().equals("4")){
                            qtdFeminino++;
                        }else{
                            qtdMasculino++;
                        }
                        eleitos++;
                    }
                }
            }
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
        float result=((float)qtdMasculino/(float)eleitos);
        result*=100;
        
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        System.out.println("Feminino:  "+qtdFeminino+" ("+nf.format(result)+"%)");
        result=(float)qtdFeminino/eleitos;
        result*=100;
        System.out.println("Masculino: "+qtdMasculino+" ("+nf.format(result)+"%)");
    }
    // public void RelatorioVotos(LinkedList<>){

    // }
}
