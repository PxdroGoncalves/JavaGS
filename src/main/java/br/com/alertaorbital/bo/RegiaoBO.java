package br.com.alertaorbital.bo;

import br.com.alertaorbital.dao.RegiaoDAO;
import br.com.alertaorbital.entities.Regiao;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.util.List;

public class RegiaoBO {

    public Regiao cadastrar(Regiao regiao) throws ExcecoesConexao {
        if (regiao.getNome() == null || regiao.getNome().isBlank())
            throw new ExcecoesConexao("nome da regiao é obrigatorio");
        if (regiao.getEstado() == null || regiao.getEstado().length() != 2)
            throw new ExcecoesConexao("estado deve ter 2 caracteres (sigla UF)");
        if (regiao.getPais() == null || regiao.getPais().isBlank())
            regiao.setPais("Brasil");

        RegiaoDAO dao = new RegiaoDAO();
        dao.cadastrar(regiao);
        return regiao;
    }

    public List<Regiao> listar() throws ExcecoesConexao {
        RegiaoDAO dao = new RegiaoDAO();
        return dao.listar();
    }

    public Regiao buscarPorId(int id) throws ExcecoesConexao {
        RegiaoDAO dao = new RegiaoDAO();
        return dao.buscarPorId(id);
    }

    public Regiao atualizar(Regiao regiao) throws ExcecoesConexao {
        if (regiao.getNome() == null || regiao.getNome().isBlank())
            throw new ExcecoesConexao("nome da regiao é obrigatorio");
        if (regiao.getEstado() == null || regiao.getEstado().length() != 2)
            throw new ExcecoesConexao("estado deve ter 2 caracteres (sigla UF)");

        RegiaoDAO dao = new RegiaoDAO();
        dao.atualizar(regiao);
        return dao.buscarPorId(regiao.getIdRegiao());
    }

    public void deletar(int id) throws ExcecoesConexao {
        RegiaoDAO dao = new RegiaoDAO();
        dao.deletar(id);
    }
}
