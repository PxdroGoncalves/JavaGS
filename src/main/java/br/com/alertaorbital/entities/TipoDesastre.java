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

    // Executa a operação relacionada a getIdTipo.
    public int getIdTipo() { return idTipo; }
    // Executa a operação relacionada a setIdTipo.
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    // Executa a operação relacionada a getNome.
    public String getNome() { return nome; }
    // Executa a operação relacionada a setNome.
    public void setNome(String nome) { this.nome = nome; }

    // Executa a operação relacionada a getDescricao.
    public String getDescricao() { return descricao; }
    // Executa a operação relacionada a setDescricao.
    public void setDescricao(String descricao) { this.descricao = descricao; }

    // Executa a operação relacionada a getNivelRisco.
    public String getNivelRisco() { return nivelRisco; }
    // Executa a operação relacionada a setNivelRisco.
    public void setNivelRisco(String nivelRisco) { this.nivelRisco = nivelRisco; }

    @Override
    // Executa a operação relacionada a toString.
    public String toString() {
        return "TipoDesastre{idTipo=" + idTipo + ", nome='" + nome + "', nivelRisco='" + nivelRisco + "'}";
    }
}