package br.com.alertaorbital.dao;

import br.com.alertaorbital.conexoes.ConexaoFactory;
import br.com.alertaorbital.entities.TipoDesastre;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoDesastreDAO {

    public Connection minhaConexao;

    public TipoDesastreDAO() throws ExcecoesConexao {
        try {
            ConexaoFactory factory = new ConexaoFactory();
            this.minhaConexao = factory.conexao();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void cadastrar(TipoDesastre td) throws ExcecoesConexao {
        try {
            String sql = "INSERT INTO TIPO_DESASTRE (nome, descricao, nivel_risco) VALUES (?, ?, ?)";
            PreparedStatement ps = minhaConexao.prepareStatement(sql, new String[]{"id_tipo"});
            ps.setString(1, td.getNome());
            ps.setString(2, td.getDescricao());
            ps.setString(3, td.getNivelRisco());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) td.setIdTipo(rs.getInt(1));
            ps.close();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public List<TipoDesastre> listar() throws ExcecoesConexao {
        try {
            List<TipoDesastre> lista = new ArrayList<>();
            String sql = "SELECT id_tipo, nome, descricao, nivel_risco FROM TIPO_DESASTRE ORDER BY nome";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new TipoDesastre(
                        rs.getInt("id_tipo"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("nivel_risco")
                ));
            }
            ps.close();
            return lista;
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public TipoDesastre buscarPorId(int id) throws ExcecoesConexao {
        try {
            String sql = "SELECT id_tipo, nome, descricao, nivel_risco FROM TIPO_DESASTRE WHERE id_tipo = ?";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                TipoDesastre td = new TipoDesastre(
                        rs.getInt("id_tipo"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("nivel_risco")
                );
                ps.close();
                return td;
            }
            ps.close();
            return null;
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void atualizar(TipoDesastre td) throws ExcecoesConexao {
        try {
            String sql = "UPDATE TIPO_DESASTRE SET nome = ?, descricao = ?, nivel_risco = ? WHERE id_tipo = ?";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setString(1, td.getNome());
            ps.setString(2, td.getDescricao());
            ps.setString(3, td.getNivelRisco());
            ps.setInt(4, td.getIdTipo());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void deletar(int id) throws ExcecoesConexao {
        try {
            String sql = "DELETE FROM TIPO_DESASTRE WHERE id_tipo = ?";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }
}
