package br.com.alertaorbital.entities;

public class Usuario {

    private int idUsuario;
    private String nome;
    private String cargo;
    private String email;

    public Usuario() {
        super();
    }

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

    @Override
    public String toString() {
        return "Usuario{idUsuario=" + idUsuario + ", nome='" + nome + "', cargo='" + cargo + "', email='" + email + "'}";
    }
}
