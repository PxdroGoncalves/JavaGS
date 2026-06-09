package br.com.alertaorbital.bo;

import br.com.alertaorbital.dao.OcorrenciaDAO;
import br.com.alertaorbital.dao.OcorrenciaSateliteDAO;
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

    // Valida os dados recebidos e realiza o cadastro do registro.
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

    // Busca e retorna os registros solicitados.
    public List<Ocorrencia> listar() throws ExcecoesConexao {
        OcorrenciaDAO dao = new OcorrenciaDAO();
        return dao.listar();
    }

    // Consulta informações com base nos parâmetros recebidos.
    public Ocorrencia buscarPorId(int id) throws ExcecoesConexao {
        OcorrenciaDAO dao = new OcorrenciaDAO();
        return dao.buscarPorId(id);
    }

    // Busca e retorna os registros solicitados.
    public List<Ocorrencia> listarPorStatus(String status) throws ExcecoesConexao {
        if (!STATUS_VALIDOS.contains(status.toUpperCase()))
            throw new ExcecoesConexao("status invalido. Use: ATIVO, CONTROLADO ou RESOLVIDO");
        OcorrenciaDAO dao = new OcorrenciaDAO();
        return dao.listarPorStatus(status.toUpperCase());
    }

    // Busca e retorna os registros solicitados.
    public List<Ocorrencia> listarPorRegiao(int idRegiao) throws ExcecoesConexao {
        OcorrenciaDAO dao = new OcorrenciaDAO();
        return dao.listarPorRegiao(idRegiao);
    }

    // Busca e retorna os registros solicitados.
    public List<Ocorrencia> listarPorSatelite(int idSatelite) throws ExcecoesConexao {
        OcorrenciaDAO dao = new OcorrenciaDAO();
        return dao.listarPorSatelite(idSatelite);
    }

    // Atualiza as informações do registro existente.
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
    // Atualiza as informações do registro existente.
    public Ocorrencia atualizarStatus(int id, String novoStatus) throws ExcecoesConexao {
        if (!STATUS_VALIDOS.contains(novoStatus.toUpperCase()))
            throw new ExcecoesConexao("status invalido. Use: ATIVO, CONTROLADO ou RESOLVIDO");

        OcorrenciaDAO dao = new OcorrenciaDAO();
        Ocorrencia atual = dao.buscarPorId(id);
        if (atual == null)
            throw new ExcecoesConexao("Ocorrencia nao encontrada para o id: " + id);

        String statusAtual = atual.getStatus();
        String statusNovo  = novoStatus.toUpperCase();

        if (statusAtual.equals("RESOLVIDO"))
            throw new ExcecoesConexao("Ocorrencia ja esta RESOLVIDA e nao pode ser alterada");
        if (statusAtual.equals("CONTROLADO") && statusNovo.equals("ATIVO"))
            throw new ExcecoesConexao("Nao e permitido regredir o status de CONTROLADO para ATIVO");

        dao.atualizarStatus(id, statusNovo);
        return dao.buscarPorId(id);
    }

    // Remove o registro correspondente da base de dados.
    public void deletar(int id) throws ExcecoesConexao {
        OcorrenciaDAO dao = new OcorrenciaDAO();
        dao.deletar(id);
    }

    // Executa a operação relacionada a vincularSatelite.
    public OcorrenciaSatelite vincularSatelite(int idOcorrencia, int idSatelite, String dataDeteccao) throws ExcecoesConexao {
        OcorrenciaSateliteDAO osDAO = new OcorrenciaSateliteDAO();
        if (osDAO.vinculoExiste(idOcorrencia, idSatelite))
            throw new ExcecoesConexao("Satelite ja esta vinculado a esta ocorrencia");
        if (dataDeteccao == null || dataDeteccao.isBlank())
            dataDeteccao = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        OcorrenciaSatelite os = new OcorrenciaSatelite(idOcorrencia, idSatelite, dataDeteccao);
        osDAO.vincular(os);
        return os;
    }

    // Executa a operação relacionada a desvincularSatelite.
    public void desvincularSatelite(int idOcorrencia, int idSatelite) throws ExcecoesConexao {
        OcorrenciaSateliteDAO osDAO = new OcorrenciaSateliteDAO();
        if (!osDAO.vinculoExiste(idOcorrencia, idSatelite))
            throw new ExcecoesConexao("Vinculo nao encontrado para ocorrencia " + idOcorrencia + " e satelite " + idSatelite);
        osDAO.desvincular(idOcorrencia, idSatelite);
    }

    // Busca e retorna os registros solicitados.
    public List<Satelite> listarSatelites(int idOcorrencia) throws ExcecoesConexao {
        OcorrenciaSateliteDAO osDAO = new OcorrenciaSateliteDAO();
        return osDAO.listarSatelitesPorOcorrencia(idOcorrencia);
    }
}