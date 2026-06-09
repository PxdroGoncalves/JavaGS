package br.com.alertaorbital.bo;

import br.com.alertaorbital.dao.AlertaDAO;
import br.com.alertaorbital.entities.Alerta;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AlertaBO {

    public Alerta cadastrar(Alerta alerta) throws ExcecoesConexao {
        if (alerta.getMensagem() == null || alerta.getMensagem().isBlank())
            throw new ExcecoesConexao("mensagem é obrigatoria");
        if (alerta.getIdOcorrencia() <= 0)
            throw new ExcecoesConexao("id_ocorrencia é obrigatorio");
        if (alerta.getIdUsuario() <= 0)
            throw new ExcecoesConexao("id_usuario é obrigatorio");
        if (alerta.getDataEmissao() == null || alerta.getDataEmissao().isBlank())
            alerta.setDataEmissao(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        AlertaDAO dao = new AlertaDAO();
        dao.cadastrar(alerta);
        return dao.buscarPorId(alerta.getIdAlerta());
    }

    public List<Alerta> listar() throws ExcecoesConexao {
        AlertaDAO dao = new AlertaDAO();
        return dao.listar();
    }

    public Alerta buscarPorId(int id) throws ExcecoesConexao {
        AlertaDAO dao = new AlertaDAO();
        return dao.buscarPorId(id);
    }

    public List<Alerta> listarPorUsuario(int idUsuario) throws ExcecoesConexao {
        AlertaDAO dao = new AlertaDAO();
        return dao.listarPorUsuario(idUsuario);
    }

    public List<Alerta> listarPorOcorrencia(int idOcorrencia) throws ExcecoesConexao {
        AlertaDAO dao = new AlertaDAO();
        return dao.listarPorOcorrencia(idOcorrencia);
    }

    public Alerta atualizar(Alerta alerta) throws ExcecoesConexao {
        if (alerta.getMensagem() == null || alerta.getMensagem().isBlank())
            throw new ExcecoesConexao("mensagem é obrigatoria");
        if (alerta.getIdOcorrencia() <= 0)
            throw new ExcecoesConexao("id_ocorrencia é obrigatorio");
        if (alerta.getIdUsuario() <= 0)
            throw new ExcecoesConexao("id_usuario é obrigatorio");
        if (alerta.getDataEmissao() == null || alerta.getDataEmissao().isBlank())
            alerta.setDataEmissao(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        AlertaDAO dao = new AlertaDAO();
        dao.atualizar(alerta);
        return dao.buscarPorId(alerta.getIdAlerta());
    }

    public void deletar(int id) throws ExcecoesConexao {
        AlertaDAO dao = new AlertaDAO();
        dao.deletar(id);
    }
}
