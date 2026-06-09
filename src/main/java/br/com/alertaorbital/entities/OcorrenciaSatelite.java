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

    // Executa a operação relacionada a getIdOcorrencia.
    public int getIdOcorrencia() { return idOcorrencia; }
    // Executa a operação relacionada a setIdOcorrencia.
    public void setIdOcorrencia(int idOcorrencia) { this.idOcorrencia = idOcorrencia; }

    // Executa a operação relacionada a getIdSatelite.
    public int getIdSatelite() { return idSatelite; }
    // Executa a operação relacionada a setIdSatelite.
    public void setIdSatelite(int idSatelite) { this.idSatelite = idSatelite; }

    // Executa a operação relacionada a getDataDeteccao.
    public String getDataDeteccao() { return dataDeteccao; }
    // Executa a operação relacionada a setDataDeteccao.
    public void setDataDeteccao(String dataDeteccao) { this.dataDeteccao = dataDeteccao; }

    // Executa a operação relacionada a getNomeSatelite.
    public String getNomeSatelite() { return nomeSatelite; }
    // Executa a operação relacionada a setNomeSatelite.
    public void setNomeSatelite(String nomeSatelite) { this.nomeSatelite = nomeSatelite; }

    // Executa a operação relacionada a getAgenciaSatelite.
    public String getAgenciaSatelite() { return agenciaSatelite; }
    // Executa a operação relacionada a setAgenciaSatelite.
    public void setAgenciaSatelite(String agenciaSatelite) { this.agenciaSatelite = agenciaSatelite; }
}