package br.com.alertaorbital.bo;

import br.com.alertaorbital.dao.TipoDesastreDAO;
import br.com.alertaorbital.entities.TipoDesastre;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.util.Arrays;
import java.util.List;

public class TipoDesastreBO {

    private static final List<String> NIVEIS = Arrays.asList("BAIXO", "MEDIO", "ALTO", "CRITICO");

    public TipoDesastre cadastrar(TipoDesastre td) throws ExcecoesConexao {
        if (td.getNome() == null || td.getNome().isBlank())
            throw new ExcecoesConexao("nome do tipo de desastre é obrigatorio");
        if (td.getNivelRisco() == null || !NIVEIS.contains(td.getNivelRisco().toUpperCase()))
            throw new ExcecoesConexao("nivel_risco invalido. Use: BAIXO, MEDIO, ALTO ou CRITICO");

        td.setNivelRisco(td.getNivelRisco().toUpperCase());
        TipoDesastreDAO dao = new TipoDesastreDAO();
        dao.cadastrar(td);
        return td;
    }

    public List<TipoDesastre> listar() throws ExcecoesConexao {
        TipoDesastreDAO dao = new TipoDesastreDAO();
        return dao.listar();
    }

    public TipoDesastre buscarPorId(int id) throws ExcecoesConexao {
        TipoDesastreDAO dao = new TipoDesastreDAO();
        return dao.buscarPorId(id);
    }

    public TipoDesastre atualizar(TipoDesastre td) throws ExcecoesConexao {
        if (td.getNome() == null || td.getNome().isBlank())
            throw new ExcecoesConexao("nome do tipo de desastre é obrigatorio");
        if (td.getNivelRisco() == null || !NIVEIS.contains(td.getNivelRisco().toUpperCase()))
            throw new ExcecoesConexao("nivel_risco invalido. Use: BAIXO, MEDIO, ALTO ou CRITICO");

        td.setNivelRisco(td.getNivelRisco().toUpperCase());
        TipoDesastreDAO dao = new TipoDesastreDAO();
        dao.atualizar(td);
        return dao.buscarPorId(td.getIdTipo());
    }

    public void deletar(int id) throws ExcecoesConexao {
        TipoDesastreDAO dao = new TipoDesastreDAO();
        dao.deletar(id);
    }
}
