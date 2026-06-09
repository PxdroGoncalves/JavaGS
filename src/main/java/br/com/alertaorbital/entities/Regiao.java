package br.com.alertaorbital.entities;

public class Regiao {

    private int idRegiao;
    private String cidade;
    private String pais;

    public Regiao() {
        super();
    }

    public Regiao(int idRegiao, String cidade, String pais) {
        super();
        this.idRegiao = idRegiao;
        this.cidade = cidade;
        this.pais = pais;
    }

    public int getIdRegiao() { return idRegiao; }
    public void setIdRegiao(int idRegiao) { this.idRegiao = idRegiao; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    @Override
    public String toString() {
        return "Regiao{idRegiao=" + idRegiao + ", cidade='" + cidade + "', pais='" + pais + "'}";
    }
}
