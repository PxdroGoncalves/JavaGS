package br.com.alertaorbital.entities;

public class Regiao {

    private int idRegiao;
    private String nome;
    private String estado;
    private String pais;

    public Regiao() {
        super();
    }

    public Regiao(int idRegiao, String nome, String estado, String pais) {
        super();
        this.idRegiao = idRegiao;
        this.nome = nome;
        this.estado = estado;
        this.pais = pais;
    }

    // Executa a operação relacionada a getIdRegiao.
    public int getIdRegiao() { return idRegiao; }
    // Executa a operação relacionada a setIdRegiao.
    public void setIdRegiao(int idRegiao) { this.idRegiao = idRegiao; }

    // Executa a operação relacionada a getNome.
    public String getNome() { return nome; }
    // Executa a operação relacionada a setNome.
    public void setNome(String nome) { this.nome = nome; }

    // Executa a operação relacionada a getEstado.
    public String getEstado() { return estado; }
    // Executa a operação relacionada a setEstado.
    public void setEstado(String estado) { this.estado = estado; }

    // Executa a operação relacionada a getPais.
    public String getPais() { return pais; }
    // Executa a operação relacionada a setPais.
    public void setPais(String pais) { this.pais = pais; }

    @Override
    // Executa a operação relacionada a toString.
    public String toString() {
        return "Regiao{idRegiao=" + idRegiao + ", nome='" + nome + "', estado='" + estado + "', pais='" + pais + "'}";
    }
}