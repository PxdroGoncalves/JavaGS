package br.com.alertaorbital.bo;

import br.com.alertaorbital.dao.SateliteDAO;
import br.com.alertaorbital.entities.Satelite;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.util.List;

public class SateliteBO {

    public Satelite cadastrar(Satelite satelite) throws ExcecoesConexao {
        if (satelite.getNome() == null || satelite.getNome().isBlank())
            throw new ExcecoesConexao("nome do satelite e obrigatorio");
        if (satelite.getAgencia() == null || satelite.getAgencia().isBlank())
            throw new ExcecoesConexao("agencia e obrigatoria");
        if (satelite.getOperacional() == null)
            satelite.setOperacional("S");
        if (!satelite.getOperacional().equals("S") && !satelite.getOperacional().equals("N"))
            throw new ExcecoesConexao("operacional invalido. Use S ou N");

        SateliteDAO dao = new SateliteDAO();
        dao.cadastrar(satelite);
        return satelite;
    }

    public List<Satelite> listar() throws ExcecoesConexao {
        SateliteDAO dao = new SateliteDAO();
        return dao.listar();
    }

    public List<Satelite> listarOperacionais() throws ExcecoesConexao {
        SateliteDAO dao = new SateliteDAO();
        return dao.listarOperacionais();
    }

    public Satelite buscarPorId(int id) throws ExcecoesConexao {
        SateliteDAO dao = new SateliteDAO();
        return dao.buscarPorId(id);
    }

    public Satelite atualizar(Satelite satelite) throws ExcecoesConexao {
        if (satelite.getNome() == null || satelite.getNome().isBlank())
            throw new ExcecoesConexao("nome do satelite e obrigatorio");
        if (satelite.getAgencia() == null || satelite.getAgencia().isBlank())
            throw new ExcecoesConexao("agencia e obrigatoria");
        // FIX: null-check antes do .equals() para evitar NullPointerException
        if (satelite.getOperacional() == null)
            satelite.setOperacional("S");
        if (!satelite.getOperacional().equals("S") && !satelite.getOperacional().equals("N"))
            throw new ExcecoesConexao("operacional invalido. Use S ou N");

        SateliteDAO dao = new SateliteDAO();
        dao.atualizar(satelite);
        return dao.buscarPorId(satelite.getIdSatelite());
    }

    public void deletar(int id) throws ExcecoesConexao {
        SateliteDAO dao = new SateliteDAO();
        dao.deletar(id);
    }
}
