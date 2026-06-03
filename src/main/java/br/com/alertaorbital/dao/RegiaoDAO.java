package br.com.alertaorbital.dao;

import br.com.alertaorbital.conexoes.ConexaoFactory;
import br.com.alertaorbital.entities.Regiao;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegiaoDAO {

    public Connection minhaConexao;

    public RegiaoDAO() throws ExcecoesConexao {
        try {
            ConexaoFactory factory = new ConexaoFactory();
            this.minhaConexao = factory.conexao();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void cadastrar(Regiao regiao) throws ExcecoesConexao {
        try {
            String sql = "INSERT INTO REGIAO (nome, estado, pais) VALUES (?, ?, ?)";
            PreparedStatement ps = minhaConexao.prepareStatement(sql, new String[]{"id_regiao"});
            ps.setString(1, regiao.getNome());
            ps.setString(2, regiao.getEstado());
            ps.setString(3, regiao.getPais() != null ? regiao.getPais() : "Brasil");
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) regiao.setIdRegiao(rs.getInt(1));
            ps.close();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public List<Regiao> listar() throws ExcecoesConexao {
        try {
            List<Regiao> lista = new ArrayList<>();
            String sql = "SELECT id_regiao, nome, estado, pais FROM REGIAO ORDER BY estado, nome";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Regiao r = new Regiao(
                        rs.getInt("id_regiao"),
                        rs.getString("nome"),
                        rs.getString("estado"),
                        rs.getString("pais")
                );
                lista.add(r);
            }
            ps.close();
            return lista;
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public Regiao buscarPorId(int id) throws ExcecoesConexao {
        try {
            String sql = "SELECT id_regiao, nome, estado, pais FROM REGIAO WHERE id_regiao = ?";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Regiao r = new Regiao(
                        rs.getInt("id_regiao"),
                        rs.getString("nome"),
                        rs.getString("estado"),
                        rs.getString("pais")
                );
                ps.close();
                return r;
            }
            ps.close();
            return null;
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void atualizar(Regiao regiao) throws ExcecoesConexao {
        try {
            String sql = "UPDATE REGIAO SET nome = ?, estado = ?, pais = ? WHERE id_regiao = ?";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setString(1, regiao.getNome());
            ps.setString(2, regiao.getEstado());
            ps.setString(3, regiao.getPais());
            ps.setInt(4, regiao.getIdRegiao());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void deletar(int id) throws ExcecoesConexao {
        try {
            String sql = "DELETE FROM REGIAO WHERE id_regiao = ?";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }
}
