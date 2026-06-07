package br.com.alertaorbital.dao;

import br.com.alertaorbital.conexoes.ConexaoFactory;
import br.com.alertaorbital.entities.Usuario;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void cadastrar(Usuario usuario) throws ExcecoesConexao {
        String sql = "INSERT INTO USUARIO (nome, cargo, email) VALUES (?, ?, ?)";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"id_usuario"})) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCargo());
            ps.setString(3, usuario.getEmail());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) usuario.setIdUsuario(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public List<Usuario> listar() throws ExcecoesConexao {
        String sql = "SELECT id_usuario, nome, cargo, email FROM USUARIO ORDER BY nome";
        List<Usuario> lista = new ArrayList<>();
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(new Usuario(rs.getInt("id_usuario"), rs.getString("nome"), rs.getString("cargo"), rs.getString("email")));
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return lista;
    }

    public Usuario buscarPorId(int id) throws ExcecoesConexao {
        String sql = "SELECT id_usuario, nome, cargo, email FROM USUARIO WHERE id_usuario = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Usuario(rs.getInt("id_usuario"), rs.getString("nome"), rs.getString("cargo"), rs.getString("email"));
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return null;
    }

    public void atualizar(Usuario usuario) throws ExcecoesConexao {
        String sql = "UPDATE USUARIO SET nome = ?, cargo = ?, email = ? WHERE id_usuario = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCargo());
            ps.setString(3, usuario.getEmail());
            ps.setInt(4, usuario.getIdUsuario());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void deletar(int id) throws ExcecoesConexao {
        String sql = "DELETE FROM USUARIO WHERE id_usuario = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }
}
