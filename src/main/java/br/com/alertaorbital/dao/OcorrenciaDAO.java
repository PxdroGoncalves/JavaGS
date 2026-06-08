package br.com.alertaorbital.dao;

import br.com.alertaorbital.conexoes.ConexaoFactory;
import br.com.alertaorbital.entities.Ocorrencia;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OcorrenciaDAO {

    public void cadastrar(Ocorrencia o) throws ExcecoesConexao {
        String sql = "INSERT INTO OCORRENCIA (data_inicio, data_fim, descricao, status, id_regiao, id_tipo) VALUES (TO_DATE(?,'YYYY-MM-DD'), TO_DATE(?,'YYYY-MM-DD'), ?, ?, ?, ?)";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"id_ocorrencia"})) {
            ps.setString(1, o.getDataInicio());
            if (o.getDataFim() != null && !o.getDataFim().isBlank()) {
                ps.setString(2, o.getDataFim());
            } else {
                ps.setNull(2, Types.VARCHAR);
            }
            ps.setString(3, o.getDescricao());
            ps.setString(4, o.getStatus() != null ? o.getStatus() : "ATIVO");
            ps.setInt(5, o.getIdRegiao());
            ps.setInt(6, o.getIdTipo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) o.setIdOcorrencia(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public List<Ocorrencia> listar() throws ExcecoesConexao {
        String sql = "SELECT o.id_ocorrencia, TO_CHAR(o.data_inicio,'YYYY-MM-DD') AS data_inicio, TO_CHAR(o.data_fim,'YYYY-MM-DD') AS data_fim, o.descricao, o.status, o.id_regiao, o.id_tipo, r.nome AS nome_regiao, r.cidade AS cidade_regiao, td.nome AS nome_tipo FROM OCORRENCIA o INNER JOIN REGIAO r ON r.id_regiao = o.id_regiao INNER JOIN TIPO_DESASTRE td ON td.id_tipo = o.id_tipo ORDER BY o.data_inicio DESC";
        List<Ocorrencia> lista = new ArrayList<>();
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return lista;
    }

    public Ocorrencia buscarPorId(int id) throws ExcecoesConexao {
        String sql = "SELECT o.id_ocorrencia, TO_CHAR(o.data_inicio,'YYYY-MM-DD') AS data_inicio, TO_CHAR(o.data_fim,'YYYY-MM-DD') AS data_fim, o.descricao, o.status, o.id_regiao, o.id_tipo, r.nome AS nome_regiao, r.cidade AS cidade_regiao, td.nome AS nome_tipo FROM OCORRENCIA o INNER JOIN REGIAO r ON r.id_regiao = o.id_regiao INNER JOIN TIPO_DESASTRE td ON td.id_tipo = o.id_tipo WHERE o.id_ocorrencia = ?";
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

    public List<Ocorrencia> listarPorStatus(String status) throws ExcecoesConexao {
        String sql = "SELECT o.id_ocorrencia, TO_CHAR(o.data_inicio,'YYYY-MM-DD') AS data_inicio, TO_CHAR(o.data_fim,'YYYY-MM-DD') AS data_fim, o.descricao, o.status, o.id_regiao, o.id_tipo, r.nome AS nome_regiao, r.cidade AS cidade_regiao, td.nome AS nome_tipo FROM OCORRENCIA o INNER JOIN REGIAO r ON r.id_regiao = o.id_regiao INNER JOIN TIPO_DESASTRE td ON td.id_tipo = o.id_tipo WHERE o.status = ? ORDER BY o.data_inicio DESC";
        List<Ocorrencia> lista = new ArrayList<>();
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status.toUpperCase());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return lista;
    }

    public List<Ocorrencia> listarPorRegiao(int idRegiao) throws ExcecoesConexao {
        String sql = "SELECT o.id_ocorrencia, TO_CHAR(o.data_inicio,'YYYY-MM-DD') AS data_inicio, TO_CHAR(o.data_fim,'YYYY-MM-DD') AS data_fim, o.descricao, o.status, o.id_regiao, o.id_tipo, r.nome AS nome_regiao, r.cidade AS cidade_regiao, td.nome AS nome_tipo FROM OCORRENCIA o INNER JOIN REGIAO r ON r.id_regiao = o.id_regiao INNER JOIN TIPO_DESASTRE td ON td.id_tipo = o.id_tipo WHERE o.id_regiao = ? ORDER BY o.data_inicio DESC";
        List<Ocorrencia> lista = new ArrayList<>();
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRegiao);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return lista;
    }

    public List<Ocorrencia> listarPorSatelite(int idSatelite) throws ExcecoesConexao {
        String sql = "SELECT o.id_ocorrencia, TO_CHAR(o.data_inicio,'YYYY-MM-DD') AS data_inicio, TO_CHAR(o.data_fim,'YYYY-MM-DD') AS data_fim, o.descricao, o.status, o.id_regiao, o.id_tipo, r.nome AS nome_regiao, r.cidade AS cidade_regiao, td.nome AS nome_tipo FROM OCORRENCIA o INNER JOIN REGIAO r ON r.id_regiao = o.id_regiao INNER JOIN TIPO_DESASTRE td ON td.id_tipo = o.id_tipo INNER JOIN OCORRENCIA_SATELITE os ON os.id_ocorrencia = o.id_ocorrencia WHERE os.id_satelite = ? ORDER BY o.data_inicio DESC";
        List<Ocorrencia> lista = new ArrayList<>();
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idSatelite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return lista;
    }

    public void atualizar(Ocorrencia o) throws ExcecoesConexao {
        String sql = "UPDATE OCORRENCIA SET data_inicio = TO_DATE(?,'YYYY-MM-DD'), data_fim = TO_DATE(?,'YYYY-MM-DD'), descricao = ?, status = ?, id_regiao = ?, id_tipo = ? WHERE id_ocorrencia = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, o.getDataInicio());
            if (o.getDataFim() != null && !o.getDataFim().isBlank()) {
                ps.setString(2, o.getDataFim());
            } else {
                ps.setNull(2, Types.VARCHAR);
            }
            ps.setString(3, o.getDescricao());
            ps.setString(4, o.getStatus());
            ps.setInt(5, o.getIdRegiao());
            ps.setInt(6, o.getIdTipo());
            ps.setInt(7, o.getIdOcorrencia());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void atualizarStatus(int id, String novoStatus) throws ExcecoesConexao {
        String sql = "UPDATE OCORRENCIA SET status = ? WHERE id_ocorrencia = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, novoStatus.toUpperCase());
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void deletar(int id) throws ExcecoesConexao {
        String sql = "DELETE FROM OCORRENCIA WHERE id_ocorrencia = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    private Ocorrencia mapear(ResultSet rs) throws SQLException {
        Ocorrencia o = new Ocorrencia();
        o.setIdOcorrencia(rs.getInt("id_ocorrencia"));
        o.setDataInicio(rs.getString("data_inicio"));
        o.setDataFim(rs.getString("data_fim"));
        o.setDescricao(rs.getString("descricao"));
        o.setStatus(rs.getString("status"));
        o.setIdRegiao(rs.getInt("id_regiao"));
        o.setIdTipo(rs.getInt("id_tipo"));
        o.setNomeRegiao(rs.getString("nome_regiao"));
        o.setCidadeRegiao(rs.getString("cidade_regiao"));
        o.setNomeTipo(rs.getString("nome_tipo"));
        return o;
    }
}
