package sistema_eleitoral;

import java.util.HashMap;
import java.util.Map;

public class Partido {
    private Map<String,Candidato> mapaCandidatos= new HashMap<String, Candidato>();
    private int qt_votos_nominais = 0;
    private int qt_votos_legenda = 0;
    private String nrPartido;
    private String sigla;
    public String getSigla() {
        return sigla;
    }
    public int getQtdCandidatosEleitos(){
       int qtd=0;
        for(Candidato c: mapaCandidatos.values()){
            if(c.ehCandidatoEleito()){
                qtd++;
            }
       }
       return qtd;
    }
    public Map<String, Candidato> getMapaCandidatos() {
        return new HashMap<String, Candidato>(mapaCandidatos);
    }
    public void addCandidato(Candidato candidato) {
        if(!mapaCandidatos.containsKey(candidato.getNr_candidato())){
            mapaCandidatos.put(candidato.getNr_candidato(), candidato);
        }
    }
    public Candidato getCandidato(String nrVotavel) throws RuntimeException {
        Candidato candidato = mapaCandidatos.get(nrVotavel);
        if (candidato == null) {
            throw new RuntimeException("Candidato não encontrado!");
        }
        return candidato;
    }

    public Partido(String nrPartido, String sigla) {
        this.nrPartido = nrPartido;
        this.sigla = sigla;
    }

    public String getNrPartido() {
        return nrPartido;
    }
    public void setNrPartido(String nrPartido) {
        this.nrPartido = nrPartido;
    }
    public int getQVotosNominais() {
        int qtd=0;
        for(Candidato c: mapaCandidatos.values()){
           qtd+=c.getQt_votos();
       }
       return qtd;
    }
        
    public int getQtVotosLegenda() {
        return qt_votos_legenda;
    }
    public int getQtVotosTotal(){
        return this.qt_votos_legenda+this.qt_votos_nominais;
    }
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    public void incrementaVoto(int qt_voto,boolean ehVotoLegenda){
        if(ehVotoLegenda){
            this.qt_votos_legenda+=qt_voto;
        }else{
            this.qt_votos_nominais+=qt_voto;
        }

    }
    @Override public String toString(){
        String result="";
        int total=this.qt_votos_legenda+this.qt_votos_nominais;
        return result+=this.sigla+" - "+this.nrPartido+", "+total+" votos ("+this.qt_votos_nominais
        +" nominais e "+this.qt_votos_legenda+" de legenda), "+this.getQtdCandidatosEleitos()+" candidatos eleitos";
    }
}
