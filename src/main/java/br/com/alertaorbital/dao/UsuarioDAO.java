package br.com.alertaorbital.dao;

import br.com.alertaorbital.conexoes.ConexaoFactory;
import br.com.alertaorbital.entities.Usuario;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // -------------------------------------------------------------------------
    // Mapper auxiliar
    // -------------------------------------------------------------------------
    private Usuario mapRow(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setNome(rs.getString("nome"));
        u.setCargo(rs.getString("cargo"));
        u.setEmail(rs.getString("email"));
        return u;
    }

    // -------------------------------------------------------------------------
    // CADASTRO — salva nome, cargo, email e senha_hash
    // -------------------------------------------------------------------------
    public void cadastrar(Usuario usuario) throws ExcecoesConexao {
        String sql = "INSERT INTO USUARIO (nome, cargo, email, senha_hash) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"id_usuario"})) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCargo());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getSenhaHash());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) usuario.setIdUsuario(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    // -------------------------------------------------------------------------
    // LOGIN — busca por email + senha_hash
    // -------------------------------------------------------------------------
    public Usuario buscarPorEmailESenha(String email, String senhaHash) throws ExcecoesConexao {
        String sql = "SELECT id_usuario, nome, cargo, email FROM USUARIO " +
                     "WHERE email = ? AND senha_hash = ? AND ativo = 'S'";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senhaHash);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return null;
    }

    // -------------------------------------------------------------------------
    // LISTAR
    // -------------------------------------------------------------------------
    public List<Usuario> listar() throws ExcecoesConexao {
        String sql = "SELECT id_usuario, nome, cargo, email FROM USUARIO ORDER BY nome";
        List<Usuario> lista = new ArrayList<>();
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return lista;
    }

    // -------------------------------------------------------------------------
    // BUSCAR POR ID
    // -------------------------------------------------------------------------
    public Usuario buscarPorId(int id) throws ExcecoesConexao {
        String sql = "SELECT id_usuario, nome, cargo, email FROM USUARIO WHERE id_usuario = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return null;
    }

    // -------------------------------------------------------------------------
    // ATUALIZAR
    // -------------------------------------------------------------------------
    public void atualizar(Usuario usuario) throws ExcecoesConexao {
        // Se vier novo hash de senha, atualiza ela também; senão mantém a existente
        String sql = usuario.getSenhaHash() != null
                ? "UPDATE USUARIO SET nome = ?, cargo = ?, email = ?, senha_hash = ? WHERE id_usuario = ?"
                : "UPDATE USUARIO SET nome = ?, cargo = ?, email = ? WHERE id_usuario = ?";

        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCargo());
            ps.setString(3, usuario.getEmail());
            if (usuario.getSenhaHash() != null) {
                ps.setString(4, usuario.getSenhaHash());
                ps.setInt(5, usuario.getIdUsuario());
            } else {
                ps.setInt(4, usuario.getIdUsuario());
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    // -------------------------------------------------------------------------
    // DELETAR
    // -------------------------------------------------------------------------
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
