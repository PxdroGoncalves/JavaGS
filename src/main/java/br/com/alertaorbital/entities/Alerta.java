package br.com.alertaorbital.entities;

public class Alerta {

    private int idAlerta;
    private String mensagem;
    private String dataEmissao;
    private int idOcorrencia;
    private int idUsuario;

    // Campos extras para leitura via JOIN
    private String nomeUsuario;
    private String descricaoOcorrencia;
    private String statusOcorrencia;

    public Alerta() {
        super();
    }

    public Alerta(int idAlerta, String mensagem, String dataEmissao, int idOcorrencia, int idUsuario) {
        super();
        this.idAlerta = idAlerta;
        this.mensagem = mensagem;
        this.dataEmissao = dataEmissao;
        this.idOcorrencia = idOcorrencia;
        this.idUsuario = idUsuario;
    }

    // Executa a operação relacionada a getIdAlerta.
    public int getIdAlerta() { return idAlerta; }
    // Executa a operação relacionada a setIdAlerta.
    public void setIdAlerta(int idAlerta) { this.idAlerta = idAlerta; }

    // Executa a operação relacionada a getMensagem.
    public String getMensagem() { return mensagem; }
    // Executa a operação relacionada a setMensagem.
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    // Executa a operação relacionada a getDataEmissao.
    public String getDataEmissao() { return dataEmissao; }
    // Executa a operação relacionada a setDataEmissao.
    public void setDataEmissao(String dataEmissao) { this.dataEmissao = dataEmissao; }

    // Executa a operação relacionada a getIdOcorrencia.
    public int getIdOcorrencia() { return idOcorrencia; }
    // Executa a operação relacionada a setIdOcorrencia.
    public void setIdOcorrencia(int idOcorrencia) { this.idOcorrencia = idOcorrencia; }

    // Executa a operação relacionada a getIdUsuario.
    public int getIdUsuario() { return idUsuario; }
    // Executa a operação relacionada a setIdUsuario.
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    // Executa a operação relacionada a getNomeUsuario.
    public String getNomeUsuario() { return nomeUsuario; }
    // Executa a operação relacionada a setNomeUsuario.
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }

    // Executa a operação relacionada a getDescricaoOcorrencia.
    public String getDescricaoOcorrencia() { return descricaoOcorrencia; }
    // Executa a operação relacionada a setDescricaoOcorrencia.
    public void setDescricaoOcorrencia(String descricaoOcorrencia) { this.descricaoOcorrencia = descricaoOcorrencia; }

    // Executa a operação relacionada a getStatusOcorrencia.
    public String getStatusOcorrencia() { return statusOcorrencia; }
    // Executa a operação relacionada a setStatusOcorrencia.
    public void setStatusOcorrencia(String statusOcorrencia) { this.statusOcorrencia = statusOcorrencia; }

    @Override
    // Executa a operação relacionada a toString.
    public String toString() {
        return "Alerta{idAlerta=" + idAlerta + ", mensagem='" + mensagem + "', dataEmissao='" + dataEmissao + "'}";
    }
}