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

    // Executa a operação relacionada a getIdOcorrencia.
    public int getIdOcorrencia() { return idOcorrencia; }
    // Executa a operação relacionada a setIdOcorrencia.
    public void setIdOcorrencia(int idOcorrencia) { this.idOcorrencia = idOcorrencia; }

    // Executa a operação relacionada a getDataInicio.
    public String getDataInicio() { return dataInicio; }
    // Executa a operação relacionada a setDataInicio.
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }

    // Executa a operação relacionada a getDataFim.
    public String getDataFim() { return dataFim; }
    // Executa a operação relacionada a setDataFim.
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }

    // Executa a operação relacionada a getDescricao.
    public String getDescricao() { return descricao; }
    // Executa a operação relacionada a setDescricao.
    public void setDescricao(String descricao) { this.descricao = descricao; }

    // Executa a operação relacionada a getStatus.
    public String getStatus() { return status; }
    // Executa a operação relacionada a setStatus.
    public void setStatus(String status) { this.status = status; }

    // Executa a operação relacionada a getIdRegiao.
    public int getIdRegiao() { return idRegiao; }
    // Executa a operação relacionada a setIdRegiao.
    public void setIdRegiao(int idRegiao) { this.idRegiao = idRegiao; }

    // Executa a operação relacionada a getIdTipo.
    public int getIdTipo() { return idTipo; }
    // Executa a operação relacionada a setIdTipo.
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    // Executa a operação relacionada a getNomeRegiao.
    public String getNomeRegiao() { return nomeRegiao; }
    // Executa a operação relacionada a setNomeRegiao.
    public void setNomeRegiao(String nomeRegiao) { this.nomeRegiao = nomeRegiao; }

    // Executa a operação relacionada a getEstadoRegiao.
    public String getEstadoRegiao() { return estadoRegiao; }
    // Executa a operação relacionada a setEstadoRegiao.
    public void setEstadoRegiao(String estadoRegiao) { this.estadoRegiao = estadoRegiao; }

    // Executa a operação relacionada a getNomeTipo.
    public String getNomeTipo() { return nomeTipo; }
    // Executa a operação relacionada a setNomeTipo.
    public void setNomeTipo(String nomeTipo) { this.nomeTipo = nomeTipo; }

    // Executa a operação relacionada a getNivelRisco.
    public String getNivelRisco() { return nivelRisco; }
    // Executa a operação relacionada a setNivelRisco.
    public void setNivelRisco(String nivelRisco) { this.nivelRisco = nivelRisco; }

    @Override
    // Executa a operação relacionada a toString.
    public String toString() {
        return "Ocorrencia{idOcorrencia=" + idOcorrencia + ", status='" + status + "', descricao='" + descricao + "'}";
    }
}