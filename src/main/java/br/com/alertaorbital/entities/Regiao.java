package br.com.alertaorbital.entities;

public class Regiao {

    private int idRegiao;
    private String nome;
    private String cidade;
    private String pais;

    public Regiao() {
        super();
    }

    public Regiao(int idRegiao, String nome, String cidade, String pais) {
        super();
        this.idRegiao = idRegiao;
        this.nome = nome;
        this.cidade = cidade;
        this.pais = pais;
    }

    public int getIdRegiao() { return idRegiao; }
    public void setIdRegiao(int idRegiao) { this.idRegiao = idRegiao; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    @Override
    public String toString() {
        return "Regiao{idRegiao=" + idRegiao + ", nome='" + nome + "', cidade='" + cidade + "', pais='" + pais + "'}";
    }
}
