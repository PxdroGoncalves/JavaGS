package br.com.alertaorbital.dao;

import br.com.alertaorbital.conexoes.ConexaoFactory;
import br.com.alertaorbital.entities.Satelite;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SateliteDAO {

    // Valida os dados recebidos e realiza o cadastro do registro.
    public void cadastrar(Satelite satelite) throws ExcecoesConexao {
        String sql = "INSERT INTO SATELITE (nome, agencia, operacional) VALUES (?, ?, ?)";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"id_satelite"})) {
            ps.setString(1, satelite.getNome());
            ps.setString(2, satelite.getAgencia());
            ps.setString(3, satelite.getOperacional() != null ? satelite.getOperacional() : "S");
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) satelite.setIdSatelite(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    // Busca e retorna os registros solicitados.
    public List<Satelite> listar() throws ExcecoesConexao {
        String sql = "SELECT id_satelite, nome, agencia, operacional FROM SATELITE ORDER BY nome";
        List<Satelite> lista = new ArrayList<>();
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(new Satelite(rs.getInt("id_satelite"), rs.getString("nome"), rs.getString("agencia"), rs.getString("operacional")));
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return lista;
    }

    // Busca e retorna os registros solicitados.
    public List<Satelite> listarOperacionais() throws ExcecoesConexao {
        String sql = "SELECT id_satelite, nome, agencia, operacional FROM SATELITE WHERE operacional = 'S' ORDER BY nome";
        List<Satelite> lista = new ArrayList<>();
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(new Satelite(rs.getInt("id_satelite"), rs.getString("nome"), rs.getString("agencia"), rs.getString("operacional")));
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return lista;
    }

    // Consulta informações com base nos parâmetros recebidos.
    public Satelite buscarPorId(int id) throws ExcecoesConexao {
        String sql = "SELECT id_satelite, nome, agencia, operacional FROM SATELITE WHERE id_satelite = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Satelite(rs.getInt("id_satelite"), rs.getString("nome"), rs.getString("agencia"), rs.getString("operacional"));
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return null;
    }

    // Atualiza as informações do registro existente.
    public void atualizar(Satelite satelite) throws ExcecoesConexao {
        String sql = "UPDATE SATELITE SET nome = ?, agencia = ?, operacional = ? WHERE id_satelite = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, satelite.getNome());
            ps.setString(2, satelite.getAgencia());
            ps.setString(3, satelite.getOperacional());
            ps.setInt(4, satelite.getIdSatelite());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    // Remove o registro correspondente da base de dados.
    public void deletar(int id) throws ExcecoesConexao {
        String sql = "DELETE FROM SATELITE WHERE id_satelite = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }
}