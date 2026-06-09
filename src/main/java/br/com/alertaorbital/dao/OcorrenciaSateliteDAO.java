package br.com.alertaorbital.dao;

import br.com.alertaorbital.conexoes.ConexaoFactory;
import br.com.alertaorbital.entities.OcorrenciaSatelite;
import br.com.alertaorbital.entities.Satelite;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OcorrenciaSateliteDAO {

    // Executa a operação relacionada a vincular.
    public void vincular(OcorrenciaSatelite os) throws ExcecoesConexao {
        String sql = "INSERT INTO OCORRENCIA_SATELITE (id_ocorrencia, id_satelite, data_deteccao) VALUES (?, ?, TO_DATE(?,'YYYY-MM-DD'))";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, os.getIdOcorrencia());
            ps.setInt(2, os.getIdSatelite());
            ps.setString(3, os.getDataDeteccao());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    // Executa a operação relacionada a desvincular.
    public void desvincular(int idOcorrencia, int idSatelite) throws ExcecoesConexao {
        String sql = "DELETE FROM OCORRENCIA_SATELITE WHERE id_ocorrencia = ? AND id_satelite = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idOcorrencia);
            ps.setInt(2, idSatelite);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    // Executa a operação relacionada a vinculoExiste.
    public boolean vinculoExiste(int idOcorrencia, int idSatelite) throws ExcecoesConexao {
        String sql = "SELECT COUNT(*) FROM OCORRENCIA_SATELITE WHERE id_ocorrencia = ? AND id_satelite = ?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idOcorrencia);
            ps.setInt(2, idSatelite);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    // Busca e retorna os registros solicitados.
    public List<Satelite> listarSatelitesPorOcorrencia(int idOcorrencia) throws ExcecoesConexao {
        String sql = "SELECT s.id_satelite, s.nome, s.agencia, s.operacional FROM SATELITE s INNER JOIN OCORRENCIA_SATELITE os ON os.id_satelite = s.id_satelite WHERE os.id_ocorrencia = ? ORDER BY s.nome";
        List<Satelite> lista = new ArrayList<>();
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idOcorrencia);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Satelite(
                            rs.getInt("id_satelite"),
                            rs.getString("nome"),
                            rs.getString("agencia"),
                            rs.getString("operacional")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return lista;
    }

    // Busca e retorna os registros solicitados.
    public List<OcorrenciaSatelite> listar() throws ExcecoesConexao {
        String sql = "SELECT os.id_ocorrencia, os.id_satelite, TO_CHAR(os.data_deteccao,'YYYY-MM-DD') AS data_deteccao, s.nome AS nome_satelite, s.agencia FROM OCORRENCIA_SATELITE os INNER JOIN SATELITE s ON s.id_satelite = os.id_satelite ORDER BY os.data_deteccao DESC";
        List<OcorrenciaSatelite> lista = new ArrayList<>();
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                OcorrenciaSatelite os = new OcorrenciaSatelite(
                        rs.getInt("id_ocorrencia"),
                        rs.getInt("id_satelite"),
                        rs.getString("data_deteccao")
                );
                os.setNomeSatelite(rs.getString("nome_satelite"));
                os.setAgenciaSatelite(rs.getString("agencia"));
                lista.add(os);
            }
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
        return lista;
    }
}