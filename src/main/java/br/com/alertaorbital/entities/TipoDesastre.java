package br.com.alertaorbital.entities;

public class TipoDesastre {

    private int idTipo;
    private String nome;
    private String descricao;
    private String nivelRisco;

    public TipoDesastre() {
        super();
    }

    public TipoDesastre(int idTipo, String nome, String descricao, String nivelRisco) {
        super();
        this.idTipo = idTipo;
        this.nome = nome;
        this.descricao = descricao;
        this.nivelRisco = nivelRisco;
    }

    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getNivelRisco() { return nivelRisco; }
    public void setNivelRisco(String nivelRisco) { this.nivelRisco = nivelRisco; }

    @Override
    public String toString() {
        return "TipoDesastre{idTipo=" + idTipo + ", nome='" + nome + "', nivelRisco='" + nivelRisco + "'}";
    }
}
