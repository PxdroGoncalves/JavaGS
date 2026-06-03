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

    public int getIdRegiao() { return idRegiao; }
    public void setIdRegiao(int idRegiao) { this.idRegiao = idRegiao; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    @Override
    public String toString() {
        return "Regiao{idRegiao=" + idRegiao + ", nome='" + nome + "', estado='" + estado + "', pais='" + pais + "'}";
    }
}
