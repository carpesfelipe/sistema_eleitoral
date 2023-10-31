package sistema_eleitoral;

import java.text.NumberFormat;
import java.time.LocalDate;

public class Candidato {
    // 6 para deputado federal e 7 para deputado estadual;
    private String cd_cargo;
    private String cd_situacao_candidato_tot;
    // processar apenas aqueles com os valores 2 ou 16 que representam candidatos
    // com candidatura deferida;
    private String nr_candidato;
    // nome do candidato na urna;
    private String nm_urna_candidato;
    private String nr_partido;
    private String sg_partido;
    private String nr_federacao;
    private String cd_sit_tot_turno;
    private LocalDate dt_nascimento;
    private String cd_genero;
    private String nm_tipo_destinacao_votos;
    private int votos_nominais = 0;

    public void setCd_cargo(String cd_cargo) {
        this.cd_cargo = cd_cargo;
    }

    public String getCd_cargo() {
        return this.cd_cargo;
    }

    public void setCd_situacao_candidato_tot(String cd_situacao_candidato_tot) {
        this.cd_situacao_candidato_tot = cd_situacao_candidato_tot;
    }

    public String getCd_situacao_candidato_tot() {
        return cd_situacao_candidato_tot;
    }

    public void setNr_candidato(String nr_candidato) {
        this.nr_candidato = nr_candidato;
    }

    public String getNr_candidato() {
        return nr_candidato;
    }

    public void setNm_urna_candidato(String nm_urna_candidato) {
        this.nm_urna_candidato = nm_urna_candidato;
    }

    public String getNm_urna_candidato() {
        return nm_urna_candidato;
    }

    public void setNr_partido(String nr_partido) {
        this.nr_partido = nr_partido;
    }

    public String getNr_partido() {
        return nr_partido;
    }

    public void setSg_partido(String sg_partido) {
        this.sg_partido = sg_partido;
    }

    public String getSg_partido() {
        return sg_partido;
    }

    public void setNr_federacao(String nr_federacao) {
        this.nr_federacao = nr_federacao;
    }

    public String getNr_federacao() {
        return nr_federacao;
    }

    public void setCd_sit_tot_turno(String cd_sit_tot_turno) {
        this.cd_sit_tot_turno = cd_sit_tot_turno;
    }

    public String getCd_sit_tot_turno() {
        return cd_sit_tot_turno;
    }

    public void setDt_nascimento(LocalDate dt_nascimento) {
        this.dt_nascimento = dt_nascimento;
    }

    public LocalDate getDt_nascimento() {
        return dt_nascimento;
    }

    public void setCd_genero(String cd_genero) {
        this.cd_genero = cd_genero;
    }

    public String getCd_genero() {
        return cd_genero;
    }

    public void setNm_tipo_destinacao_votos(String nm_tipo_destinacao_votos) {
        this.nm_tipo_destinacao_votos = nm_tipo_destinacao_votos;
    }

    public String getNm_tipo_destinacao_votos() {
        return nm_tipo_destinacao_votos;
    }

    public void computaVoto(String nr_candidato) {
        if (nr_candidato == this.nr_candidato) {
            this.votos_nominais++;
        }
    }

    public boolean ehDoTipoDeEleicao(String tipo) {
        if (cd_cargo.equals(tipo))
            return true;
        return false;
    }

    public boolean ehCandidatoEleito() {
        if(cd_sit_tot_turno!=null){
            if (cd_sit_tot_turno.contains("2") || cd_sit_tot_turno.contains("3")) {
                return true;
            }
        }
        return false;
    }

    public boolean ehVotoLegenda(){
        if(nm_tipo_destinacao_votos!=null){
            if(nm_tipo_destinacao_votos.contains("Válido (legenda)")){
                return true;
            }
        }
        return false;
    }

    public void incrementaVoto(int qtd_votos){
        this.votos_nominais+=qtd_votos;
    }

    public int calculaIdade(LocalDate data_atual){
        if(this.dt_nascimento==null)return 0;
        int idade=data_atual.getYear()-this.dt_nascimento.getYear();
        return idade;
    }

    @Override public String toString(){
        String result="";
        NumberFormat nf = NumberFormat.getNumberInstance();
        result+=this.nm_urna_candidato+" ("+this.sg_partido+", "+nf.format(this.votos_nominais)+" votos)\n";

        return result;
    }

    public boolean ehDeferidoCandidato(){
        if(cd_situacao_candidato_tot!=null){
            if(cd_situacao_candidato_tot.equals("2") || cd_situacao_candidato_tot.equals("16")){
                return true;
            }
        }
        return false;
       
    }

    public int getQt_votos() {
        return this.votos_nominais;
    }
}
