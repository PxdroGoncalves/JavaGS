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

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    @Override
    public String toString() {
        return "Usuario{idUsuario=" + idUsuario + ", nome='" + nome + "', cargo='" + cargo + "', email='" + email + "'}";
    }
}
