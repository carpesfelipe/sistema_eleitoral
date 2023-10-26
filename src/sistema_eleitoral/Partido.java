package sistema_eleitoral;

import java.util.HashMap;
import java.util.Map;

public class Partido {
    private Map<String,Candidato> mapaCandidatos= new HashMap<String, Candidato>();
    private int qtVotos = 0;
    private String nrPartido;
    private String sigla;
    public String getSigla() {
        return sigla;
    }
    public Map<String, Candidato> getMapaCandidatos() {
        return new HashMap<String, Candidato>(mapaCandidatos);
    }
    public Partido(String nrPartido, String sigla) {
        this.nrPartido = nrPartido;
    }
    public String getNrPartido() {
        return nrPartido;
    }
    public void setNrPartido(String nrPartido) {
        this.nrPartido = nrPartido;
    }
    public int getQtVotos() {
        return qtVotos;
    }
    public void incrementaVoto(int qtVotos) {
        this.qtVotos += qtVotos;
    }
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}
