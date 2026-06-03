package br.com.alertaorbital.dao;

import br.com.alertaorbital.conexoes.ConexaoFactory;
import br.com.alertaorbital.entities.OcorrenciaSatelite;
import br.com.alertaorbital.entities.Satelite;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OcorrenciaSateliteDAO {

    public Connection minhaConexao;

    public OcorrenciaSateliteDAO() throws ExcecoesConexao {
        try {
            ConexaoFactory factory = new ConexaoFactory();
            this.minhaConexao = factory.conexao();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void vincular(OcorrenciaSatelite os) throws ExcecoesConexao {
        try {
            String sql = "INSERT INTO OCORRENCIA_SATELITE (id_ocorrencia, id_satelite, data_deteccao) VALUES (?, ?, TO_DATE(?,'YYYY-MM-DD'))";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setInt(1, os.getIdOcorrencia());
            ps.setInt(2, os.getIdSatelite());
            ps.setString(3, os.getDataDeteccao());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public void desvincular(int idOcorrencia, int idSatelite) throws ExcecoesConexao {
        try {
            String sql = "DELETE FROM OCORRENCIA_SATELITE WHERE id_ocorrencia = ? AND id_satelite = ?";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setInt(1, idOcorrencia);
            ps.setInt(2, idSatelite);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public boolean vinculoExiste(int idOcorrencia, int idSatelite) throws ExcecoesConexao {
        try {
            String sql = "SELECT COUNT(*) FROM OCORRENCIA_SATELITE WHERE id_ocorrencia = ? AND id_satelite = ?";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setInt(1, idOcorrencia);
            ps.setInt(2, idSatelite);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            ps.close();
            return count > 0;
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }

    public List<Satelite> listarSatelitesPorOcorrencia(int idOcorrencia) throws ExcecoesConexao {
        try {
            List<Satelite> lista = new ArrayList<>();
            String sql = "SELECT s.id_satelite, s.nome, s.agencia, s.operacional FROM SATELITE s INNER JOIN OCORRENCIA_SATELITE os ON os.id_satelite = s.id_satelite WHERE os.id_ocorrencia = ? ORDER BY s.nome";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ps.setInt(1, idOcorrencia);
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

    public List<OcorrenciaSatelite> listar() throws ExcecoesConexao {
        try {
            List<OcorrenciaSatelite> lista = new ArrayList<>();
            String sql = "SELECT os.id_ocorrencia, os.id_satelite, TO_CHAR(os.data_deteccao,'YYYY-MM-DD') AS data_deteccao, s.nome AS nome_satelite, s.agencia FROM OCORRENCIA_SATELITE os INNER JOIN SATELITE s ON s.id_satelite = os.id_satelite ORDER BY os.data_deteccao DESC";
            PreparedStatement ps = minhaConexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
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
            ps.close();
            return lista;
        } catch (SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }
}
