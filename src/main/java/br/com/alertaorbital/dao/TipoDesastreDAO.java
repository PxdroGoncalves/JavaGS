package br.com.alertaorbital.dao;

import br.com.alertaorbital.conexoes.ConexaoFactory;
import br.com.alertaorbital.entities.TipoDesastre;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoDesastreDAO {

    // Valida os dados recebidos e realiza o cadastro do registro.
    public void cadastrar(TipoDesastre td) throws ExcecoesConexao {
        String sql = "INSERT INTO TIPO_DESASTRE (nome, descricao, nivel_risco) VALUES (?, ?, ?)";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"id_tipo"})) {
            ps.setString(1, td.getNome());
            ps.setString(2, td.getDescricao());
            ps.setString(3, td.getNivelRisco());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) td.setIdTipo(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    // Busca e retorna os registros solicitados.
    public List<TipoDesastre> listar() throws ExcecoesConexao {
        String sql = "SELECT id_tipo, nome, descricao, nivel_risco FROM TIPO_DESASTRE ORDER BY nome";
        List<TipoDesastre> lista = new ArrayList<>();
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(new TipoDesastre(rs.getInt("id_tipo"), rs.getString("nome"), rs.getString("descricao"), rs.getString("nivel_risco")));
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return lista;
    }

    // Consulta informações com base nos parâmetros recebidos.
    public TipoDesastre buscarPorId(int id) throws ExcecoesConexao {
        String sql = "SELECT id_tipo, nome, descricao, nivel_risco FROM TIPO_DESASTRE WHERE id_tipo = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new TipoDesastre(rs.getInt("id_tipo"), rs.getString("nome"), rs.getString("descricao"), rs.getString("nivel_risco"));
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return null;
    }

    // Atualiza as informações do registro existente.
    public void atualizar(TipoDesastre td) throws ExcecoesConexao {
        String sql = "UPDATE TIPO_DESASTRE SET nome = ?, descricao = ?, nivel_risco = ? WHERE id_tipo = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, td.getNome());
            ps.setString(2, td.getDescricao());
            ps.setString(3, td.getNivelRisco());
            ps.setInt(4, td.getIdTipo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    // Remove o registro correspondente da base de dados.
    public void deletar(int id) throws ExcecoesConexao {
        String sql = "DELETE FROM TIPO_DESASTRE WHERE id_tipo = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }
}