package br.com.alertaorbital.dao;

import br.com.alertaorbital.conexoes.ConexaoFactory;
import br.com.alertaorbital.entities.Satelite;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SateliteDAO {

    public Connection minhaConexao;

    public SateliteDAO() throws ExcecoesConexao {
        try {
            ConexaoFactory factory = new ConexaoFactory();
            this.minhaConexao = factory.conexao();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void cadastrar(Satelite satelite) throws ExcecoesConexao {
        try {
            String sql = "INSERT INTO SATELITE (nome, agencia, operacional) VALUES (?, ?, ?)";
            PreparedStatement ps = minhaConexao.prepareStatement(sql, new String[]{"id_satelite"});
            ps.setString(1, satelite.getNome());
            ps.setString(2, satelite.getAgencia());
            ps.setString(3, satelite.getOperacional() != null ? satelite.getOperacional() : "S");
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) satelite.setIdSatelite(rs.getInt(1));
            ps.close();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public List<Satelite> listar() throws ExcecoesConexao {
        try {
            List<Satelite> lista = new ArrayList<>();
            String sql = "SELECT id_satelite, nome, agencia, operacional FROM SATELITE ORDER BY nome";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Satelite(
                        rs.getInt("id_satelite"),
                        rs.getString("nome"),
                        rs.getString("agencia"),
                        rs.getString("operacional")
                ));
            }
            ps.close();
            return lista;
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public List<Satelite> listarOperacionais() throws ExcecoesConexao {
        try {
            List<Satelite> lista = new ArrayList<>();
            String sql = "SELECT id_satelite, nome, agencia, operacional FROM SATELITE WHERE operacional = 'S' ORDER BY nome";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Satelite(
                        rs.getInt("id_satelite"),
                        rs.getString("nome"),
                        rs.getString("agencia"),
                        rs.getString("operacional")
                ));
            }
            ps.close();
            return lista;
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public Satelite buscarPorId(int id) throws ExcecoesConexao {
        try {
            String sql = "SELECT id_satelite, nome, agencia, operacional FROM SATELITE WHERE id_satelite = ?";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Satelite s = new Satelite(
                        rs.getInt("id_satelite"),
                        rs.getString("nome"),
                        rs.getString("agencia"),
                        rs.getString("operacional")
                );
                ps.close();
                return s;
            }
            ps.close();
            return null;
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void atualizar(Satelite satelite) throws ExcecoesConexao {
        try {
            String sql = "UPDATE SATELITE SET nome = ?, agencia = ?, operacional = ? WHERE id_satelite = ?";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setString(1, satelite.getNome());
            ps.setString(2, satelite.getAgencia());
            ps.setString(3, satelite.getOperacional());
            ps.setInt(4, satelite.getIdSatelite());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void deletar(int id) throws ExcecoesConexao {
        try {
            String sql = "DELETE FROM SATELITE WHERE id_satelite = ?";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }
}
