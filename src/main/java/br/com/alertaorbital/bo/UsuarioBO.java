package br.com.alertaorbital.bo;

import br.com.alertaorbital.dao.UsuarioDAO;
import br.com.alertaorbital.entities.Usuario;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.util.List;
import java.util.regex.Pattern;

public class UsuarioBO {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public Usuario cadastrar(Usuario usuario) throws ExcecoesConexao {
        if (usuario.getNome() == null || usuario.getNome().isBlank())
            throw new ExcecoesConexao("nome é obrigatorio");
        if (usuario.getCargo() == null || usuario.getCargo().isBlank())
            throw new ExcecoesConexao("cargo é obrigatorio");
        if (usuario.getEmail() == null || !EMAIL_REGEX.matcher(usuario.getEmail()).matches())
            throw new ExcecoesConexao("email invalido");

        UsuarioDAO dao = new UsuarioDAO();
        dao.cadastrar(usuario);
        return usuario;
    }

    public List<Usuario> listar() throws ExcecoesConexao {
        UsuarioDAO dao = new UsuarioDAO();
        return dao.listar();
    }

    public Usuario buscarPorId(int id) throws ExcecoesConexao {
        UsuarioDAO dao = new UsuarioDAO();
        return dao.buscarPorId(id);
    }

    public Usuario atualizar(Usuario usuario) throws ExcecoesConexao {
        if (usuario.getNome() == null || usuario.getNome().isBlank())
            throw new ExcecoesConexao("nome é obrigatorio");
        if (usuario.getCargo() == null || usuario.getCargo().isBlank())
            throw new ExcecoesConexao("cargo é obrigatorio");
        if (usuario.getEmail() == null || !EMAIL_REGEX.matcher(usuario.getEmail()).matches())
            throw new ExcecoesConexao("email invalido");

        UsuarioDAO dao = new UsuarioDAO();
        dao.atualizar(usuario);
        return dao.buscarPorId(usuario.getIdUsuario());
    }

    public void deletar(int id) throws ExcecoesConexao {
        UsuarioDAO dao = new UsuarioDAO();
        dao.deletar(id);
    }
}
