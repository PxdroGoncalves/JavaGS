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

    // Executa a operação relacionada a getIdSatelite.
    public int getIdSatelite() { return idSatelite; }
    // Executa a operação relacionada a setIdSatelite.
    public void setIdSatelite(int idSatelite) { this.idSatelite = idSatelite; }

    // Executa a operação relacionada a getNome.
    public String getNome() { return nome; }
    // Executa a operação relacionada a setNome.
    public void setNome(String nome) { this.nome = nome; }

    // Executa a operação relacionada a getAgencia.
    public String getAgencia() { return agencia; }
    // Executa a operação relacionada a setAgencia.
    public void setAgencia(String agencia) { this.agencia = agencia; }

    // Executa a operação relacionada a getOperacional.
    public String getOperacional() { return operacional; }
    // Executa a operação relacionada a setOperacional.
    public void setOperacional(String operacional) { this.operacional = operacional; }

    @Override
    // Executa a operação relacionada a toString.
    public String toString() {
        return "Satelite{idSatelite=" + idSatelite + ", nome='" + nome + "', agencia='" + agencia + "', operacional='" + operacional + "'}";
    }
}