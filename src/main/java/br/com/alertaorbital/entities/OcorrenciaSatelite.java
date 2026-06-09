package br.com.alertaorbital.entities;

public class OcorrenciaSatelite {

    private int idOcorrencia;
    private int idSatelite;
    private String dataDeteccao;

    // Campos extras para leitura via JOIN
    private String nomeSatelite;
    private String agenciaSatelite;

    public OcorrenciaSatelite() {
        super();
    }

    public OcorrenciaSatelite(int idOcorrencia, int idSatelite, String dataDeteccao) {
        super();
        this.idOcorrencia = idOcorrencia;
        this.idSatelite = idSatelite;
        this.dataDeteccao = dataDeteccao;
    }

    public int getIdOcorrencia() { return idOcorrencia; }
    public void setIdOcorrencia(int idOcorrencia) { this.idOcorrencia = idOcorrencia; }

    public int getIdSatelite() { return idSatelite; }
    public void setIdSatelite(int idSatelite) { this.idSatelite = idSatelite; }

    public String getDataDeteccao() { return dataDeteccao; }
    public void setDataDeteccao(String dataDeteccao) { this.dataDeteccao = dataDeteccao; }

    public String getNomeSatelite() { return nomeSatelite; }
    public void setNomeSatelite(String nomeSatelite) { this.nomeSatelite = nomeSatelite; }

    public String getAgenciaSatelite() { return agenciaSatelite; }
    public void setAgenciaSatelite(String agenciaSatelite) { this.agenciaSatelite = agenciaSatelite; }
}
