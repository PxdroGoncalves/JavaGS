package br.com.alertaorbital.dao;

import br.com.alertaorbital.conexoes.ConexaoFactory;
import br.com.alertaorbital.entities.Regiao;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegiaoDAO {

    public void cadastrar(Regiao regiao) throws ExcecoesConexao {
        String sql = "INSERT INTO REGIAO (nome, estado, pais) VALUES (?, ?, ?)";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"id_regiao"})) {
            ps.setString(1, regiao.getNome());
            ps.setString(2, regiao.getEstado());
            ps.setString(3, regiao.getPais() != null ? regiao.getPais() : "Brasil");
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) regiao.setIdRegiao(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public List<Regiao> listar() throws ExcecoesConexao {
        String sql = "SELECT id_regiao, nome, estado, pais FROM REGIAO ORDER BY estado, nome";
        List<Regiao> lista = new ArrayList<>();
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Regiao(
                        rs.getInt("id_regiao"),
                        rs.getString("nome"),
                        rs.getString("estado"),
                        rs.getString("pais")
                ));
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return lista;
    }

    public Regiao buscarPorId(int id) throws ExcecoesConexao {
        String sql = "SELECT id_regiao, nome, estado, pais FROM REGIAO WHERE id_regiao = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Regiao(rs.getInt("id_regiao"), rs.getString("nome"), rs.getString("estado"), rs.getString("pais"));
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return null;
    }

    public void atualizar(Regiao regiao) throws ExcecoesConexao {
        String sql = "UPDATE REGIAO SET nome = ?, estado = ?, pais = ? WHERE id_regiao = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, regiao.getNome());
            ps.setString(2, regiao.getEstado());
            ps.setString(3, regiao.getPais());
            ps.setInt(4, regiao.getIdRegiao());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void deletar(int id) throws ExcecoesConexao {
        String sql = "DELETE FROM REGIAO WHERE id_regiao = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }
}
