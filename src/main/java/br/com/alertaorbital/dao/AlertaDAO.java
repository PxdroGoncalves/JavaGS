package br.com.alertaorbital.dao;

import br.com.alertaorbital.conexoes.ConexaoFactory;
import br.com.alertaorbital.entities.Alerta;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertaDAO {

    // Valida os dados recebidos e realiza o cadastro do registro.
    public void cadastrar(Alerta alerta) throws ExcecoesConexao {
        String sql = "INSERT INTO ALERTA (mensagem, data_emissao, id_ocorrencia, id_usuario) VALUES (?, TO_DATE(?,'YYYY-MM-DD'), ?, ?)";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"id_alerta"})) {
            ps.setString(1, alerta.getMensagem());
            ps.setString(2, alerta.getDataEmissao() != null ? alerta.getDataEmissao() : new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
            ps.setInt(3, alerta.getIdOcorrencia());
            ps.setInt(4, alerta.getIdUsuario());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) alerta.setIdAlerta(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    // Busca e retorna os registros solicitados.
    public List<Alerta> listar() throws ExcecoesConexao {
        String sql = "SELECT a.id_alerta, a.mensagem, TO_CHAR(a.data_emissao,'YYYY-MM-DD') AS data_emissao, a.id_ocorrencia, a.id_usuario, u.nome AS nome_usuario, o.descricao AS descricao_ocorrencia, o.status AS status_ocorrencia FROM ALERTA a INNER JOIN USUARIO u ON u.id_usuario = a.id_usuario INNER JOIN OCORRENCIA o ON o.id_ocorrencia = a.id_ocorrencia ORDER BY a.data_emissao DESC";
        List<Alerta> lista = new ArrayList<>();
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return lista;
    }

    // Consulta informações com base nos parâmetros recebidos.
    public Alerta buscarPorId(int id) throws ExcecoesConexao {
        String sql = "SELECT a.id_alerta, a.mensagem, TO_CHAR(a.data_emissao,'YYYY-MM-DD') AS data_emissao, a.id_ocorrencia, a.id_usuario, u.nome AS nome_usuario, o.descricao AS descricao_ocorrencia, o.status AS status_ocorrencia FROM ALERTA a INNER JOIN USUARIO u ON u.id_usuario = a.id_usuario INNER JOIN OCORRENCIA o ON o.id_ocorrencia = a.id_ocorrencia WHERE a.id_alerta = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return null;
    }

    // Busca e retorna os registros solicitados.
    public List<Alerta> listarPorUsuario(int idUsuario) throws ExcecoesConexao {
        String sql = "SELECT a.id_alerta, a.mensagem, TO_CHAR(a.data_emissao,'YYYY-MM-DD') AS data_emissao, a.id_ocorrencia, a.id_usuario, u.nome AS nome_usuario, o.descricao AS descricao_ocorrencia, o.status AS status_ocorrencia FROM ALERTA a INNER JOIN USUARIO u ON u.id_usuario = a.id_usuario INNER JOIN OCORRENCIA o ON o.id_ocorrencia = a.id_ocorrencia WHERE a.id_usuario = ? ORDER BY a.data_emissao DESC";
        List<Alerta> lista = new ArrayList<>();
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return lista;
    }

    // Busca e retorna os registros solicitados.
    public List<Alerta> listarPorOcorrencia(int idOcorrencia) throws ExcecoesConexao {
        String sql = "SELECT a.id_alerta, a.mensagem, TO_CHAR(a.data_emissao,'YYYY-MM-DD') AS data_emissao, a.id_ocorrencia, a.id_usuario, u.nome AS nome_usuario, o.descricao AS descricao_ocorrencia, o.status AS status_ocorrencia FROM ALERTA a INNER JOIN USUARIO u ON u.id_usuario = a.id_usuario INNER JOIN OCORRENCIA o ON o.id_ocorrencia = a.id_ocorrencia WHERE a.id_ocorrencia = ? ORDER BY a.data_emissao DESC";
        List<Alerta> lista = new ArrayList<>();
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idOcorrencia);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return lista;
    }

    // Atualiza as informações do registro existente.
    public void atualizar(Alerta alerta) throws ExcecoesConexao {
        String sql = "UPDATE ALERTA SET mensagem = ?, data_emissao = TO_DATE(?,'YYYY-MM-DD'), id_ocorrencia = ?, id_usuario = ? WHERE id_alerta = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, alerta.getMensagem());
            ps.setString(2, alerta.getDataEmissao());
            ps.setInt(3, alerta.getIdOcorrencia());
            ps.setInt(4, alerta.getIdUsuario());
            ps.setInt(5, alerta.getIdAlerta());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    // Remove o registro correspondente da base de dados.
    public void deletar(int id) throws ExcecoesConexao {
        String sql = "DELETE FROM ALERTA WHERE id_alerta = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
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