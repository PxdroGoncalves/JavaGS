package br.com.alertaorbital.bo;

import br.com.alertaorbital.dao.RegiaoDAO;
import br.com.alertaorbital.entities.Regiao;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.util.List;

public class RegiaoBO {

    public Regiao cadastrar(Regiao regiao) throws ExcecoesConexao {
        if (regiao.getNome() == null || regiao.getNome().isBlank())
            throw new ExcecoesConexao("nome da regiao é obrigatorio");
        if (regiao.getCidade() == null || regiao.getCidade().isBlank())
            throw new ExcecoesConexao("cidade da regiao é obrigatoria");
        if (regiao.getPais() == null || regiao.getPais().isBlank())
            throw new ExcecoesConexao("pais da regiao é obrigatorio");

        RegiaoDAO dao = new RegiaoDAO();
        dao.cadastrar(regiao);
        return regiao;
    }

    public List<Regiao> listar() throws ExcecoesConexao {
        return new RegiaoDAO().listar();
    }

    public Regiao buscarPorId(int id) throws ExcecoesConexao {
        return new RegiaoDAO().buscarPorId(id);
    }

    public Regiao atualizar(Regiao regiao) throws ExcecoesConexao {
        if (regiao.getNome() == null || regiao.getNome().isBlank())
            throw new ExcecoesConexao("nome da regiao é obrigatorio");
        if (regiao.getCidade() == null || regiao.getCidade().isBlank())
            throw new ExcecoesConexao("cidade da regiao é obrigatoria");
        if (regiao.getPais() == null || regiao.getPais().isBlank())
            throw new ExcecoesConexao("pais da regiao é obrigatorio");

        RegiaoDAO dao = new RegiaoDAO();
        dao.atualizar(regiao);
        return dao.buscarPorId(regiao.getIdRegiao());
    }

    public void deletar(int id) throws ExcecoesConexao {
        new RegiaoDAO().deletar(id);
    }
}
