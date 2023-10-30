package sistema_eleitoral;

import java.time.LocalDate;
import java.util.LinkedList;

public class Relatorio {
     public void RelatorioIdade(LinkedList<Candidato> listaCandidatos, String tipo, LocalDate dataEleicao) {
        int qtdMenor30 = 0;
        int qtdMenor40 = 0;
        int qtdMenor50 = 0;
        int qtdMenor60 = 0;
        int qtdMaior60=0;
        for (Candidato c : listaCandidatos) {
            if (c.ehDoTipoDeEleicao(tipo)) {
                if (c.getCd_sit_tot_turno() != null) {
                    if (c.ehCandidatoEleito()) {
                        if (c.calculaIdade(dataEleicao) <= 30) {
                            qtdMenor30++;
                        } else if (c.calculaIdade(dataEleicao) <= 40) {
                            qtdMenor40++;

                        } else if (c.calculaIdade(dataEleicao) <= 50) {
                            qtdMenor50++;
                        } else if (c.calculaIdade(dataEleicao) <= 60) {
                            qtdMenor60++;
                        }else{
                            qtdMaior60++;}
                    }
                }
            }
        }
        float result=(float)qtdMenor30/(float)listaCandidatos.size();
        System.out.println("    Idade < 30: ("+result+")%");
        result=(float)qtdMenor40/(float)listaCandidatos.size();
        System.out.println("30 <= Idade < 40: ("+result+")%");
        result=(float)qtdMenor50/(float)listaCandidatos.size();
        System.out.println("40 <= Idade < 50: ("+result+")%");
        result=(float)qtdMenor60/(float)listaCandidatos.size();
        System.out.println("50 <= Idade < 60: ("+result+")%");
        result=(float)qtdMaior60/(float)listaCandidatos.size();
        System.out.println("    Idade >= 60: ("+result+")%");
    }
}
