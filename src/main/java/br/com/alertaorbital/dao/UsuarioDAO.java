package br.com.alertaorbital.dao;

import br.com.alertaorbital.conexoes.ConexaoFactory;
import br.com.alertaorbital.entities.Usuario;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public Connection minhaConexao;

    public UsuarioDAO() throws ExcecoesConexao {
        try {
            ConexaoFactory factory = new ConexaoFactory();
            this.minhaConexao = factory.conexao();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void cadastrar(Usuario usuario) throws ExcecoesConexao {
        try {
            String sql = "INSERT INTO USUARIO (nome, cargo, email) VALUES (?, ?, ?)";
            PreparedStatement ps = minhaConexao.prepareStatement(sql, new String[]{"id_usuario"});
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCargo());
            ps.setString(3, usuario.getEmail());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) usuario.setIdUsuario(rs.getInt(1));
            ps.close();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public List<Usuario> listar() throws ExcecoesConexao {
        try {
            List<Usuario> lista = new ArrayList<>();
            String sql = "SELECT id_usuario, nome, cargo, email FROM USUARIO ORDER BY nome";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nome"),
                        rs.getString("cargo"),
                        rs.getString("email")
                ));
            }
            ps.close();
            return lista;
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public Usuario buscarPorId(int id) throws ExcecoesConexao {
        try {
            String sql = "SELECT id_usuario, nome, cargo, email FROM USUARIO WHERE id_usuario = ?";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nome"),
                        rs.getString("cargo"),
                        rs.getString("email")
                );
                ps.close();
                return u;
            }
            ps.close();
            return null;
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void atualizar(Usuario usuario) throws ExcecoesConexao {
        try {
            String sql = "UPDATE USUARIO SET nome = ?, cargo = ?, email = ? WHERE id_usuario = ?";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCargo());
            ps.setString(3, usuario.getEmail());
            ps.setInt(4, usuario.getIdUsuario());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void deletar(int id) throws ExcecoesConexao {
        try {
            String sql = "DELETE FROM USUARIO WHERE id_usuario = ?";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }
}
