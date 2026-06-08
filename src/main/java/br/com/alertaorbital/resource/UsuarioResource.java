package br.com.alertaorbital.resource;

import br.com.alertaorbital.bo.UsuarioBO;
import br.com.alertaorbital.entities.Usuario;
import br.com.alertaorbital.excecoes.ExcecoesConexao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@Path("/")
public class UsuarioResource {

    private String erroJson(ExcecoesConexao e) {
        String msg = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
        return "{\"erro\":\"" + msg + "\"}";
    }

    // =========================================================================
    // AUTH
    // =========================================================================

    /**
     * POST /auth/cadastro
     * Body: { "nome": "...", "cargo": "...", "email": "...", "senha": "..." }
     * Retorna 201 + usuario criado (sem dados sensíveis)
     */
    @POST
    @Path("/auth/cadastro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(Usuario usuario) {
        try {
            Usuario criado = new UsuarioBO().cadastrar(usuario);
            return Response.status(Response.Status.CREATED).entity(criado).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(erroJson(e)).build();
        }
    }

    /**
     * POST /auth/login
     * Body: { "email": "...", "senha": "..." }
     * Retorna 200 + usuario (sem dados sensíveis) ou 401
     */
    @POST
    @Path("/auth/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Map<String, String> credenciais) {
        try {
            String email = credenciais.get("email");
            String senha = credenciais.get("senha");
            Usuario usuario = new UsuarioBO().login(email, senha);
            return Response.ok(usuario).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(erroJson(e)).build();
        }
    }

    // =========================================================================
    // USUARIOS (CRUD)
    // =========================================================================

    @GET
    @Path("/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        try {
            List<Usuario> lista = new UsuarioBO().listar();
            return Response.ok(lista).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erroJson(e)).build();
        }
    }

    @GET
    @Path("/usuarios/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            Usuario u = new UsuarioBO().buscarPorId(id);
            if (u == null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erro\":\"Usuario nao encontrado para o id: " + id + "\"}").build();
            return Response.ok(u).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erroJson(e)).build();
        }
    }

    @PUT
    @Path("/usuarios/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizar(@PathParam("id") int id, Usuario usuario) {
        try {
            usuario.setIdUsuario(id);
            return Response.ok(new UsuarioBO().atualizar(usuario)).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(erroJson(e)).build();
        }
    }

    @DELETE
    @Path("/usuarios/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletar(@PathParam("id") int id) {
        try {
            new UsuarioBO().deletar(id);
            return Response.noContent().build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erroJson(e)).build();
        }
    }
}
