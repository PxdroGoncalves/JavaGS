package br.com.alertaorbital.entities;

public class Satelite {

    private int idSatelite;
    private String nome;
    private String agencia;
    private String operacional;

    public Satelite() {
        super();
    }

    public Satelite(int idSatelite, String nome, String agencia, String operacional) {
        super();
        this.idSatelite = idSatelite;
        this.nome = nome;
        this.agencia = agencia;
        this.operacional = operacional;
    }

    public int getIdSatelite() { return idSatelite; }
    public void setIdSatelite(int idSatelite) { this.idSatelite = idSatelite; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getAgencia() { return agencia; }
    public void setAgencia(String agencia) { this.agencia = agencia; }

    public String getOperacional() { return operacional; }
    public void setOperacional(String operacional) { this.operacional = operacional; }

    @Override
    public String toString() {
        return "Satelite{idSatelite=" + idSatelite + ", nome='" + nome + "', agencia='" + agencia + "', operacional='" + operacional + "'}";
    }
}
