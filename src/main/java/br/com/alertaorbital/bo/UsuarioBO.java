package br.com.alertaorbital.bo;

import br.com.alertaorbital.dao.UsuarioDAO;
import br.com.alertaorbital.entities.Usuario;
import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Pattern;

public class UsuarioBO {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // -------------------------------------------------------------------------
    // Utilitário: SHA-256
    // -------------------------------------------------------------------------
    public static String sha256(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(texto.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 indisponivel", e);
        }
    }

    // -------------------------------------------------------------------------
    // CADASTRO
    // -------------------------------------------------------------------------
    public Usuario cadastrar(Usuario usuario) throws ExcecoesConexao {
        if (usuario.getNome() == null || usuario.getNome().isBlank())
            throw new ExcecoesConexao("nome é obrigatorio");
        if (usuario.getCargo() == null || usuario.getCargo().isBlank())
            throw new ExcecoesConexao("cargo é obrigatorio");
        if (usuario.getEmail() == null || !EMAIL_REGEX.matcher(usuario.getEmail()).matches())
            throw new ExcecoesConexao("email invalido");
        if (usuario.getSenha() == null || usuario.getSenha().length() < 6)
            throw new ExcecoesConexao("senha deve ter no minimo 6 caracteres");

        usuario.setSenhaHash(sha256(usuario.getSenha()));
        usuario.setSenha(null); // limpa campo raw antes de persistir

        new UsuarioDAO().cadastrar(usuario);
        return usuario;
    }

    // -------------------------------------------------------------------------
    // LOGIN
    // -------------------------------------------------------------------------
    public Usuario login(String email, String senha) throws ExcecoesConexao {
        if (email == null || email.isBlank())
            throw new ExcecoesConexao("email é obrigatorio");
        if (senha == null || senha.isBlank())
            throw new ExcecoesConexao("senha é obrigatoria");

        String hash = sha256(senha);
        Usuario usuario = new UsuarioDAO().buscarPorEmailESenha(email, hash);
        if (usuario == null)
            throw new ExcecoesConexao("Email ou senha invalidos");
        return usuario;
    }

    // -------------------------------------------------------------------------
    // CRUD
    // -------------------------------------------------------------------------
    public List<Usuario> listar() throws ExcecoesConexao {
        return new UsuarioDAO().listar();
    }

    public Usuario buscarPorId(int id) throws ExcecoesConexao {
        return new UsuarioDAO().buscarPorId(id);
    }

    public Usuario atualizar(Usuario usuario) throws ExcecoesConexao {
        if (usuario.getNome() == null || usuario.getNome().isBlank())
            throw new ExcecoesConexao("nome é obrigatorio");
        if (usuario.getCargo() == null || usuario.getCargo().isBlank())
            throw new ExcecoesConexao("cargo é obrigatorio");
        if (usuario.getEmail() == null || !EMAIL_REGEX.matcher(usuario.getEmail()).matches())
            throw new ExcecoesConexao("email invalido");

        // Se vier nova senha no body, gera novo hash; senão mantém a existente no banco
        if (usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
            if (usuario.getSenha().length() < 6)
                throw new ExcecoesConexao("senha deve ter no minimo 6 caracteres");
            usuario.setSenhaHash(sha256(usuario.getSenha()));
            usuario.setSenha(null);
        }

        UsuarioDAO dao = new UsuarioDAO();
        dao.atualizar(usuario);
        return dao.buscarPorId(usuario.getIdUsuario());
    }

    public void deletar(int id) throws ExcecoesConexao {
        new UsuarioDAO().deletar(id);
    }
}
