package reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import sistema_eleitoral.*;

public class CSVreader {
    private Map<String, Candidato> mapaCandidatos = new HashMap<String, Candidato>();
    private Map<String, Partido> mapaPartidos = new HashMap<String, Partido>();

    public Map<String, Candidato> processaArquivoCandatos(String fileName) throws FileNotFoundException, IOException {
        final int CD_CARGOcand = 13;
        final int CD_SITUACAO_CANDIDATO_TOT = 68;
        final int NR_CANDIDATO = 16;
        final int NM_URNA_CANDIDATO = 18;
        final int NR_PARTIDO = 27;
        final int SG_PARTIDO = 28;
        final int NR_FEDERACAO = 30;
        final int DT_NASCIMENTO = 42;
        final int SIT_TOT_TURNO = 56;
        final int CD_GENERO = 45;
        final int NM_TIPO_DESTINACAO_VOTOS = 67;
        try (FileInputStream fin = new FileInputStream(fileName);
                Scanner s = new Scanner(fin, "ISO-8859-1")) {
            int coluna = 0;
            //s.nextLine();
            while (s.hasNextLine()) {
                coluna = 0;
                String line = s.nextLine();
                Candidato candidato = new Candidato();
                try (Scanner lineScanner = new Scanner(line)) {
                    lineScanner.useDelimiter(";");
                    while (lineScanner.hasNext()) {
                        String token = lineScanner.next();
                        token = token.substring(1, token.length() - 1);
                        if (coluna == CD_CARGOcand) {
                            candidato.setCd_cargo(token);
                        } else if (coluna == CD_SITUACAO_CANDIDATO_TOT) {
                            candidato.setCd_situacao_candidato_tot(token);
                        } else if (coluna == NR_CANDIDATO) {
                            candidato.setNr_candidato(token);
                        } else if (coluna == NM_URNA_CANDIDATO) {
                            candidato.setNm_urna_candidato(token);
                        } else if (coluna == NR_PARTIDO) {
                            candidato.setNr_partido(token);
                        } else if (coluna == SG_PARTIDO) {
                            candidato.setSg_partido(token);
                        } else if (coluna == NR_FEDERACAO) {
                            candidato.setNr_federacao(token);
                        } else if (coluna == DT_NASCIMENTO) {

                            try {
                                LocalDate ld = LocalDate.parse(token, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                candidato.setDt_nascimento(ld);
                            } catch (Exception e) {
                                continue;
                            }
                        } else if (coluna == SIT_TOT_TURNO) {
                            candidato.setCd_sit_tot_turno(token);
                        } else if (coluna == CD_GENERO) {
                            candidato.setCd_genero(token);
                        } else if (coluna == NM_TIPO_DESTINACAO_VOTOS) {
                            candidato.setNm_tipo_destinacao_votos(token);
                        }
                        coluna++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mapaCandidatos.put(candidato.getNr_candidato(), candidato);
            }
            return mapaCandidatos;
        }
    }

    public Map<String, Partido> processaArquivoPartidos(String fileName) {
        final int CD_CARGO = 17;
        final int NR_VOTAVEL = 19;
        final int QTVOTOS = 21;
        // lendo arquivo de partidos
        try (FileInputStream fin = new FileInputStream(fileName);
                Scanner s = new Scanner(fin, "ISO-8859-1")) {
            int coluna = 0;
            s.nextLine();
            while (s.hasNextLine()) {
                coluna = 0;
                String line = s.nextLine();
                
                try (Scanner lineScanner = new Scanner(line)) {
                    lineScanner.useDelimiter(";");
                    String nr_votavel = "";
                    String cd_cargo = "";
                    int qtVotos=0;
                    while (lineScanner.hasNext()) {
                        if (coluna == CD_CARGO || coluna == NR_VOTAVEL || coluna == QTVOTOS) {
                            String token = lineScanner.next();
                            token = token.substring(1, token.length() - 1);
                            if (coluna == QTVOTOS) {
                                qtVotos+= Integer.parseInt(token);
                            } else if (coluna == CD_CARGO) {
                                cd_cargo = token;
                            } else if (coluna == NR_VOTAVEL) {
                                nr_votavel = token;                          
                            }
                        } else {
                            lineScanner.next();
                        }
                        coluna++;
                    }
                    mapaCandidatos.get(nr_votavel).incrementaVoto(qtVotos);;
                    
                }
            }
          
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapaPartidos;
    }
}