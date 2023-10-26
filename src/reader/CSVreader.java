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
    // private Map<String, Partido> mapaPartidos = new HashMap<String, Partido>();

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
            int linha = 0;
            while (s.hasNextLine()) {
                coluna = 0;
                if (linha == 0) {
                    linha++;
                    s.nextLine();
                    continue;
                }
                String line = s.nextLine();
                Candidato candidato = new Candidato();
                try (Scanner lineScanner = new Scanner(line)) {
                    lineScanner.useDelimiter(";");
                    while (lineScanner.hasNext()) {
                        String token = lineScanner.next();
                        if (coluna == CD_CARGOcand) {
                            token = token.substring(1, token.length() - 1);
                            candidato.setCd_cargo(token);
                        } else if (coluna == CD_SITUACAO_CANDIDATO_TOT) {
                            token = token.substring(1, token.length() - 1);
                            candidato.setCd_situacao_candidato_tot(token);
                        } else if (coluna == NR_CANDIDATO) {
                            token = token.substring(1, token.length() - 1);
                            candidato.setNr_candidato(token);
                        } else if (coluna == NM_URNA_CANDIDATO) {
                            token = token.substring(1, token.length() - 1);
                            candidato.setNm_urna_candidato(token);
                        } else if (coluna == NR_PARTIDO) {
                            token = token.substring(1, token.length() - 1);
                            candidato.setNr_partido(token);
                        } else if (coluna == SG_PARTIDO) {
                            token = token.substring(1, token.length() - 1);
                            candidato.setSg_partido(token);
                        } else if (coluna == NR_FEDERACAO) {
                            token = token.substring(1, token.length() - 1);
                            candidato.setNr_federacao(token);
                        } else if (coluna == DT_NASCIMENTO) {

                            token = token.substring(1, token.length() - 1);
                            try {
                                LocalDate ld = LocalDate.parse(token, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                candidato.setDt_nascimento(ld);
                            } catch (Exception e) {
                                continue;
                            }
                        } else if (coluna == SIT_TOT_TURNO) {
                            token = token.substring(1, token.length() - 1);
                            candidato.setCd_sit_tot_turno(token);
                        } else if (coluna == CD_GENERO) {
                            token = token.substring(1, token.length() - 1);
                            candidato.setCd_genero(token);
                        } else if (coluna == NM_TIPO_DESTINACAO_VOTOS) {
                            token = token.substring(1, token.length() - 1);
                            candidato.setNm_tipo_destinacao_votos(token);
                        }
                        coluna++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mapaCandidatos.put(candidato.getNr_candidato(), candidato);
                linha++;
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

            while (s.hasNextLine()) {
                coluna = 0;
                String line = s.nextLine();
                try (Scanner lineScanner = new Scanner(line)) {
                    lineScanner.useDelimiter(";");
                    String nr_votavel = "";
                    String cd_cargo = "";
                    while (lineScanner.hasNext()) {
                        int qtVotos;
                        if (coluna == CD_CARGO || coluna == NR_VOTAVEL || coluna == QTVOTOS) {
                            String token = lineScanner.next();
                            if (coluna == QTVOTOS) {
                                qtVotos = Integer.parseInt(token);
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

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}