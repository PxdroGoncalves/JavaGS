package br.com.alertaorbital.bo;

import br.com.alertaorbital.dao.TipoDesastreDAO;
import br.com.alertaorbital.entities.TipoDesastre;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.util.List;

public class TipoDesastreBO {

    public TipoDesastre cadastrar(TipoDesastre td) throws ExcecoesConexao {
        if (td.getNome() == null || td.getNome().isBlank())
            throw new ExcecoesConexao("nome do tipo de desastre é obrigatorio");
        if (td.getNivelRisco() == null || td.getNivelRisco().isBlank())
            throw new ExcecoesConexao("nivel_risco é obrigatorio");

        td.setNivelRisco(td.getNivelRisco().toUpperCase());
        TipoDesastreDAO dao = new TipoDesastreDAO();
        dao.cadastrar(td);
        return td;
    }

    public List<TipoDesastre> listar() throws ExcecoesConexao {
        return new TipoDesastreDAO().listar();
    }

    public TipoDesastre buscarPorId(int id) throws ExcecoesConexao {
        return new TipoDesastreDAO().buscarPorId(id);
    }

    public TipoDesastre atualizar(TipoDesastre td) throws ExcecoesConexao {
        if (td.getNome() == null || td.getNome().isBlank())
            throw new ExcecoesConexao("nome do tipo de desastre é obrigatorio");
        if (td.getNivelRisco() == null || td.getNivelRisco().isBlank())
            throw new ExcecoesConexao("nivel_risco é obrigatorio");

        td.setNivelRisco(td.getNivelRisco().toUpperCase());
        TipoDesastreDAO dao = new TipoDesastreDAO();
        dao.atualizar(td);
        return dao.buscarPorId(td.getIdTipo());
    }

    public void deletar(int id) throws ExcecoesConexao {
        new TipoDesastreDAO().deletar(id);
    }
}