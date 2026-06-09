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

    public int getIdAlerta() { return idAlerta; }
    public void setIdAlerta(int idAlerta) { this.idAlerta = idAlerta; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public String getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(String dataEmissao) { this.dataEmissao = dataEmissao; }

    public int getIdOcorrencia() { return idOcorrencia; }
    public void setIdOcorrencia(int idOcorrencia) { this.idOcorrencia = idOcorrencia; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }

    public String getDescricaoOcorrencia() { return descricaoOcorrencia; }
    public void setDescricaoOcorrencia(String descricaoOcorrencia) { this.descricaoOcorrencia = descricaoOcorrencia; }

    public String getStatusOcorrencia() { return statusOcorrencia; }
    public void setStatusOcorrencia(String statusOcorrencia) { this.statusOcorrencia = statusOcorrencia; }

    @Override
    public String toString() {
        return "Alerta{idAlerta=" + idAlerta + ", mensagem='" + mensagem + "', dataEmissao='" + dataEmissao + "'}";
    }
}
