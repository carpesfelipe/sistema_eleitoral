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

    public Map<String, Candidato> processaArquivoCandatos(String arquivo_candidato, String tipo_eleicao)
            throws FileNotFoundException, IOException {
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
        try (FileInputStream fin = new FileInputStream(arquivo_candidato);
                Scanner s = new Scanner(fin, "ISO-8859-1")) {
            int coluna = 0;
            s.nextLine();
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
                            if (!token.equals(tipo_eleicao)) {
                                coluna++;
                                break;
                            }
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
                                candidato.setDt_nascimento(null);
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
                if (candidato.ehDeferidoCandidato() || candidato.ehVotoLegenda()) {
                    mapaCandidatos.put(candidato.getNr_candidato(), candidato);
                }
            }
            return mapaCandidatos;
        }
    }

    public Map<String, Partido> processaArquivoPartidos(String fileName, String tipo_eleicao) {
        final int CD_CARGO = 17;
        final int NR_VOTAVEL = 19;
        final int QT_VOTOS = 21;
 
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
                    int qt_votos = 0;
                    while (lineScanner.hasNext()) {
                        String token = lineScanner.next();
                        if (coluna == CD_CARGO || coluna == NR_VOTAVEL || coluna == QT_VOTOS) {
                            token = token.substring(1, token.length() - 1);

                            if (coluna == CD_CARGO) {
                                cd_cargo = token;
                                if (!cd_cargo.equals(tipo_eleicao)) {
                                    coluna++;
                                    break;
                                }
                            } else if (coluna == QT_VOTOS) {
                                qt_votos = Integer.parseInt(token);
                            } else if (coluna == NR_VOTAVEL) {
                                nr_votavel = token;
                            }
                        }
                        coluna++;
                    }
                    String key_partido = null;
                    if (cd_cargo.equals(tipo_eleicao)) {
                        if (Voto.ehVotoBrancoOuNulo(nr_votavel)) {
                            continue;
                        } else {
                            // candidatos deferidos e com votos na legenda
                            if (mapaCandidatos.containsKey(nr_votavel)) {
                                Candidato c = mapaCandidatos.get(nr_votavel);
                                key_partido = c.getNr_partido();
                                String sg_partido = c.getSg_partido();
                                // verificando se o partido ja foi criado;
                                if (!mapaPartidos.containsKey(key_partido)) {
                                    Partido partido = new Partido(key_partido, sg_partido);
                                    mapaPartidos.put(key_partido, partido);
                                }
                                mapaPartidos.get(key_partido).incrementaVoto(qt_votos, c.ehVotoLegenda());
                                mapaCandidatos.get(nr_votavel).incrementaVoto(qt_votos);
                                mapaPartidos.get(key_partido).addCandidato(c);
                            }else{
                                if(mapaPartidos.get(nr_votavel)!=null){
                                    mapaPartidos.get(nr_votavel).incrementaVoto(qt_votos,true);
                                }
                            } 
                        }
                    }
                }
            }
            return mapaPartidos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapaPartidos;
    }
}