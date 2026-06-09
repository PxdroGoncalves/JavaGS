package br.com.alertaorbital.bo;

import br.com.alertaorbital.dao.OcorrenciaDAO;
import br.com.alertaorbital.dao.OcorrenciaSateliteDAO;
import br.com.alertaorbital.dao.SateliteDAO;
import br.com.alertaorbital.entities.Ocorrencia;
import br.com.alertaorbital.entities.OcorrenciaSatelite;
import br.com.alertaorbital.entities.Satelite;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class OcorrenciaBO {

    private static final List<String> STATUS_VALIDOS = Arrays.asList("ATIVO", "CONTROLADO", "RESOLVIDO");

    public Ocorrencia cadastrar(Ocorrencia o) throws ExcecoesConexao {
        if (o.getDataInicio() == null || o.getDataInicio().isBlank())
            throw new ExcecoesConexao("data_inicio e obrigatoria");
        if (o.getDescricao() == null || o.getDescricao().isBlank())
            throw new ExcecoesConexao("descricao e obrigatoria");
        if (o.getIdRegiao() <= 0)
            throw new ExcecoesConexao("id_regiao e obrigatorio");
        if (o.getIdTipo() <= 0)
            throw new ExcecoesConexao("id_tipo e obrigatorio");
        if (o.getStatus() == null || o.getStatus().isBlank())
            o.setStatus("ATIVO");
        if (!STATUS_VALIDOS.contains(o.getStatus().toUpperCase()))
            throw new ExcecoesConexao("status invalido. Use: ATIVO, CONTROLADO ou RESOLVIDO");

        o.setStatus(o.getStatus().toUpperCase());
        OcorrenciaDAO dao = new OcorrenciaDAO();
        dao.cadastrar(o);
        return dao.buscarPorId(o.getIdOcorrencia());
    }

    public List<Ocorrencia> listar() throws ExcecoesConexao {
        OcorrenciaDAO dao = new OcorrenciaDAO();
        return dao.listar();
    }

    public Ocorrencia buscarPorId(int id) throws ExcecoesConexao {
        OcorrenciaDAO dao = new OcorrenciaDAO();
        return dao.buscarPorId(id);
    }

    public List<Ocorrencia> listarPorStatus(String status) throws ExcecoesConexao {
        if (status == null || status.isBlank())
            throw new ExcecoesConexao("status e obrigatorio");
        if (!STATUS_VALIDOS.contains(status.toUpperCase()))
            throw new ExcecoesConexao("status invalido. Use: ATIVO, CONTROLADO ou RESOLVIDO");
        OcorrenciaDAO dao = new OcorrenciaDAO();
        return dao.listarPorStatus(status.toUpperCase());
    }

    public List<Ocorrencia> listarPorRegiao(int idRegiao) throws ExcecoesConexao {
        OcorrenciaDAO dao = new OcorrenciaDAO();
        return dao.listarPorRegiao(idRegiao);
    }

    public List<Ocorrencia> listarPorSatelite(int idSatelite) throws ExcecoesConexao {
        OcorrenciaDAO dao = new OcorrenciaDAO();
        return dao.listarPorSatelite(idSatelite);
    }

    public Ocorrencia atualizar(Ocorrencia o) throws ExcecoesConexao {
        if (o.getDataInicio() == null || o.getDataInicio().isBlank())
            throw new ExcecoesConexao("data_inicio e obrigatoria");
        if (o.getDescricao() == null || o.getDescricao().isBlank())
            throw new ExcecoesConexao("descricao e obrigatoria");
        // FIX: validacoes de idRegiao e idTipo que estavam faltando no atualizar
        if (o.getIdRegiao() <= 0)
            throw new ExcecoesConexao("id_regiao e obrigatorio");
        if (o.getIdTipo() <= 0)
            throw new ExcecoesConexao("id_tipo e obrigatorio");
        if (o.getStatus() == null || !STATUS_VALIDOS.contains(o.getStatus().toUpperCase()))
            throw new ExcecoesConexao("status invalido. Use: ATIVO, CONTROLADO ou RESOLVIDO");

        o.setStatus(o.getStatus().toUpperCase());
        OcorrenciaDAO dao = new OcorrenciaDAO();
        dao.atualizar(o);
        return dao.buscarPorId(o.getIdOcorrencia());
    }

    // Regra de negocio: progressao ATIVO -> CONTROLADO -> RESOLVIDO sem retrocesso
    public Ocorrencia atualizarStatus(int id, String novoStatus) throws ExcecoesConexao {
        if (novoStatus == null || novoStatus.isBlank())
            throw new ExcecoesConexao("status e obrigatorio");
        if (!STATUS_VALIDOS.contains(novoStatus.toUpperCase()))
            throw new ExcecoesConexao("status invalido. Use: ATIVO, CONTROLADO ou RESOLVIDO");

        OcorrenciaDAO dao = new OcorrenciaDAO();
        Ocorrencia atual = dao.buscarPorId(id);
        if (atual == null)
            throw new ExcecoesConexao("Ocorrencia nao encontrada para o id: " + id);

        String statusAtual = atual.getStatus() != null ? atual.getStatus().toUpperCase() : "";
        String statusNovo  = novoStatus.toUpperCase();

        if (statusAtual.equals("RESOLVIDO"))
            throw new ExcecoesConexao("Ocorrencia ja esta RESOLVIDA e nao pode ser alterada");
        if (statusAtual.equals("CONTROLADO") && statusNovo.equals("ATIVO"))
            throw new ExcecoesConexao("Nao e permitido regredir o status de CONTROLADO para ATIVO");

        dao.atualizarStatus(id, statusNovo);
        return dao.buscarPorId(id);
    }

    public void deletar(int id) throws ExcecoesConexao {
        OcorrenciaDAO dao = new OcorrenciaDAO();
        dao.deletar(id);
    }

    public OcorrenciaSatelite vincularSatelite(int idOcorrencia, int idSatelite, String dataDeteccao) throws ExcecoesConexao {
        OcorrenciaSateliteDAO osDAO = new OcorrenciaSateliteDAO();
        if (osDAO.vinculoExiste(idOcorrencia, idSatelite))
            throw new ExcecoesConexao("Satelite ja esta vinculado a esta ocorrencia");

        // Valida que o satelite existe e recupera nome/agencia para devolver na resposta
        Satelite satelite = new SateliteDAO().buscarPorId(idSatelite);
        if (satelite == null)
            throw new ExcecoesConexao("Satelite nao encontrado para o id: " + idSatelite);

        if (dataDeteccao == null || dataDeteccao.isBlank())
            dataDeteccao = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        OcorrenciaSatelite os = new OcorrenciaSatelite(idOcorrencia, idSatelite, dataDeteccao);
        osDAO.vincular(os);

        // Preenche os campos extras (JOIN) para que nao retornem null
        os.setNomeSatelite(satelite.getNome());
        os.setAgenciaSatelite(satelite.getAgencia());
        return os;
    }

    public void desvincularSatelite(int idOcorrencia, int idSatelite) throws ExcecoesConexao {
        OcorrenciaSateliteDAO osDAO = new OcorrenciaSateliteDAO();
        if (!osDAO.vinculoExiste(idOcorrencia, idSatelite))
            throw new ExcecoesConexao("Vinculo nao encontrado para ocorrencia " + idOcorrencia + " e satelite " + idSatelite);
        osDAO.desvincular(idOcorrencia, idSatelite);
    }

    public List<Satelite> listarSatelites(int idOcorrencia) throws ExcecoesConexao {
        OcorrenciaSateliteDAO osDAO = new OcorrenciaSateliteDAO();
        return osDAO.listarSatelitesPorOcorrencia(idOcorrencia);
    }
}
