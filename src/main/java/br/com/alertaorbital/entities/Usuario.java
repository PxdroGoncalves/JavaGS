package br.com.alertaorbital.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Usuario {

    private int idUsuario;
    private String nome;
    private String cargo;
    private String email;

    @JsonIgnore
    private String senhaHash;

    // Campo write-only: aceita no JSON de entrada mas nunca serializa na resposta
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    public Usuario() { super(); }

    public Usuario(int idUsuario, String nome, String cargo, String email) {
        super();
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.cargo = cargo;
        this.email = email;
    }

    // Executa a operação relacionada a getIdUsuario.
    public int getIdUsuario() { return idUsuario; }
    // Executa a operação relacionada a setIdUsuario.
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    // Executa a operação relacionada a getNome.
    public String getNome() { return nome; }
    // Executa a operação relacionada a setNome.
    public void setNome(String nome) { this.nome = nome; }

    // Executa a operação relacionada a getCargo.
    public String getCargo() { return cargo; }
    // Executa a operação relacionada a setCargo.
    public void setCargo(String cargo) { this.cargo = cargo; }

    // Executa a operação relacionada a getEmail.
    public String getEmail() { return email; }
    // Executa a operação relacionada a setEmail.
    public void setEmail(String email) { this.email = email; }

    // Executa a operação relacionada a getSenhaHash.
    public String getSenhaHash() { return senhaHash; }
    // Executa a operação relacionada a setSenhaHash.
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }

    // Executa a operação relacionada a getSenha.
    public String getSenha() { return senha; }
    // Executa a operação relacionada a setSenha.
    public void setSenha(String senha) { this.senha = senha; }

    @Override
    // Executa a operação relacionada a toString.
    public String toString() {
        return "Usuario{idUsuario=" + idUsuario + ", nome='" + nome + "', cargo='" + cargo + "', email='" + email + "'}";
    }
}