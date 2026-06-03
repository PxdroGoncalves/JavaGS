package br.com.alertaorbital.dao;

import br.com.alertaorbital.conexoes.ConexaoFactory;
import br.com.alertaorbital.entities.Alerta;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertaDAO {

    public Connection minhaConexao;

    public AlertaDAO() throws ExcecoesConexao {
        try {
            ConexaoFactory factory = new ConexaoFactory();
            this.minhaConexao = factory.conexao();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void cadastrar(Alerta alerta) throws ExcecoesConexao {
        try {
            String sql = "INSERT INTO ALERTA (mensagem, data_emissao, id_ocorrencia, id_usuario) VALUES (?, TO_DATE(?,'YYYY-MM-DD'), ?, ?)";
            PreparedStatement ps = minhaConexao.prepareStatement(sql, new String[]{"id_alerta"});
            ps.setString(1, alerta.getMensagem());
            ps.setString(2, alerta.getDataEmissao() != null ? alerta.getDataEmissao() : new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
            ps.setInt(3, alerta.getIdOcorrencia());
            ps.setInt(4, alerta.getIdUsuario());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) alerta.setIdAlerta(rs.getInt(1));
            ps.close();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public List<Alerta> listar() throws ExcecoesConexao {
        try {
            List<Alerta> lista = new ArrayList<>();
            String sql = "SELECT a.id_alerta, a.mensagem, TO_CHAR(a.data_emissao,'YYYY-MM-DD') AS data_emissao, a.id_ocorrencia, a.id_usuario, u.nome AS nome_usuario, o.descricao AS descricao_ocorrencia, o.status AS status_ocorrencia FROM ALERTA a INNER JOIN USUARIO u ON u.id_usuario = a.id_usuario INNER JOIN OCORRENCIA o ON o.id_ocorrencia = a.id_ocorrencia ORDER BY a.data_emissao DESC";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
            ps.close();
            return lista;
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public Alerta buscarPorId(int id) throws ExcecoesConexao {
        try {
            String sql = "SELECT a.id_alerta, a.mensagem, TO_CHAR(a.data_emissao,'YYYY-MM-DD') AS data_emissao, a.id_ocorrencia, a.id_usuario, u.nome AS nome_usuario, o.descricao AS descricao_ocorrencia, o.status AS status_ocorrencia FROM ALERTA a INNER JOIN USUARIO u ON u.id_usuario = a.id_usuario INNER JOIN OCORRENCIA o ON o.id_ocorrencia = a.id_ocorrencia WHERE a.id_alerta = ?";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Alerta a = mapear(rs);
                ps.close();
                return a;
            }
            ps.close();
            return null;
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public List<Alerta> listarPorUsuario(int idUsuario) throws ExcecoesConexao {
        try {
            List<Alerta> lista = new ArrayList<>();
            String sql = "SELECT a.id_alerta, a.mensagem, TO_CHAR(a.data_emissao,'YYYY-MM-DD') AS data_emissao, a.id_ocorrencia, a.id_usuario, u.nome AS nome_usuario, o.descricao AS descricao_ocorrencia, o.status AS status_ocorrencia FROM ALERTA a INNER JOIN USUARIO u ON u.id_usuario = a.id_usuario INNER JOIN OCORRENCIA o ON o.id_ocorrencia = a.id_ocorrencia WHERE a.id_usuario = ? ORDER BY a.data_emissao DESC";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
            ps.close();
            return lista;
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public List<Alerta> listarPorOcorrencia(int idOcorrencia) throws ExcecoesConexao {
        try {
            List<Alerta> lista = new ArrayList<>();
            String sql = "SELECT a.id_alerta, a.mensagem, TO_CHAR(a.data_emissao,'YYYY-MM-DD') AS data_emissao, a.id_ocorrencia, a.id_usuario, u.nome AS nome_usuario, o.descricao AS descricao_ocorrencia, o.status AS status_ocorrencia FROM ALERTA a INNER JOIN USUARIO u ON u.id_usuario = a.id_usuario INNER JOIN OCORRENCIA o ON o.id_ocorrencia = a.id_ocorrencia WHERE a.id_ocorrencia = ? ORDER BY a.data_emissao DESC";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setInt(1, idOcorrencia);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
            ps.close();
            return lista;
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void atualizar(Alerta alerta) throws ExcecoesConexao {
        try {
            String sql = "UPDATE ALERTA SET mensagem = ?, data_emissao = TO_DATE(?,'YYYY-MM-DD'), id_ocorrencia = ?, id_usuario = ? WHERE id_alerta = ?";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setString(1, alerta.getMensagem());
            ps.setString(2, alerta.getDataEmissao());
            ps.setInt(3, alerta.getIdOcorrencia());
            ps.setInt(4, alerta.getIdUsuario());
            ps.setInt(5, alerta.getIdAlerta());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void deletar(int id) throws ExcecoesConexao {
        try {
            String sql = "DELETE FROM ALERTA WHERE id_alerta = ?";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    private Alerta mapear(ResultSet rs) throws SQLException {
        Alerta a = new Alerta();
        a.setIdAlerta(rs.getInt("id_alerta"));
        a.setMensagem(rs.getString("mensagem"));
        a.setDataEmissao(rs.getString("data_emissao"));
        a.setIdOcorrencia(rs.getInt("id_ocorrencia"));
        a.setIdUsuario(rs.getInt("id_usuario"));
        a.setNomeUsuario(rs.getString("nome_usuario"));
        a.setDescricaoOcorrencia(rs.getString("descricao_ocorrencia"));
        a.setStatusOcorrencia(rs.getString("status_ocorrencia"));
        return a;
    }
}
