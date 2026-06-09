package br.com.alertaorbital.bo;

import br.com.alertaorbital.dao.SateliteDAO;
import br.com.alertaorbital.entities.Satelite;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.util.List;

public class SateliteBO {

    // Valida os dados recebidos e realiza o cadastro do registro.
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

    // Busca e retorna os registros solicitados.
    public List<Satelite> listar() throws ExcecoesConexao {
        SateliteDAO dao = new SateliteDAO();
        return dao.listar();
    }

    // Busca e retorna os registros solicitados.
    public List<Satelite> listarOperacionais() throws ExcecoesConexao {
        SateliteDAO dao = new SateliteDAO();
        return dao.listarOperacionais();
    }

    // Consulta informações com base nos parâmetros recebidos.
    public Satelite buscarPorId(int id) throws ExcecoesConexao {
        SateliteDAO dao = new SateliteDAO();
        return dao.buscarPorId(id);
    }

    // Atualiza as informações do registro existente.
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

    // Remove o registro correspondente da base de dados.
    public void deletar(int id) throws ExcecoesConexao {
        SateliteDAO dao = new SateliteDAO();
        dao.deletar(id);
    }
}