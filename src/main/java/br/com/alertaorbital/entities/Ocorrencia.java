package br.com.alertaorbital.entities;

public class Ocorrencia {

    private int idOcorrencia;
    private String dataInicio;
    private String dataFim;
    private String descricao;
    private String status;
    private int idRegiao;
    private int idTipo;

    // Objetos aninhados para leitura (preenchidos pelo DAO via JOIN)
    private String nomeRegiao;
    private String estadoRegiao;
    private String nomeTipo;
    private String nivelRisco;

    public Ocorrencia() {
        super();
    }

    public Ocorrencia(int idOcorrencia, String dataInicio, String dataFim,
                      String descricao, String status, int idRegiao, int idTipo) {
        super();
        this.idOcorrencia = idOcorrencia;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.descricao = descricao;
        this.status = status;
        this.idRegiao = idRegiao;
        this.idTipo = idTipo;
    }

    public int getIdOcorrencia() { return idOcorrencia; }
    public void setIdOcorrencia(int idOcorrencia) { this.idOcorrencia = idOcorrencia; }

    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }

    public String getDataFim() { return dataFim; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getIdRegiao() { return idRegiao; }
    public void setIdRegiao(int idRegiao) { this.idRegiao = idRegiao; }

    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    public String getNomeRegiao() { return nomeRegiao; }
    public void setNomeRegiao(String nomeRegiao) { this.nomeRegiao = nomeRegiao; }

    public String getEstadoRegiao() { return estadoRegiao; }
    public void setEstadoRegiao(String estadoRegiao) { this.estadoRegiao = estadoRegiao; }

    public String getNomeTipo() { return nomeTipo; }
    public void setNomeTipo(String nomeTipo) { this.nomeTipo = nomeTipo; }

    public String getNivelRisco() { return nivelRisco; }
    public void setNivelRisco(String nivelRisco) { this.nivelRisco = nivelRisco; }

    @Override
    public String toString() {
        return "Ocorrencia{idOcorrencia=" + idOcorrencia + ", status='" + status + "', descricao='" + descricao + "'}";
    }
}
