package br.com.alertaorbital.resource;

import br.com.alertaorbital.bo.UsuarioBO;
import br.com.alertaorbital.entities.Usuario;
import br.com.alertaorbital.excecoes.ExcecoesConexao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/usuarios")
public class UsuarioResource {

    private String erroJson(ExcecoesConexao e) {
        String msg = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
        return "{\"erro\":\"" + msg + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        try {
            return Response.ok(new UsuarioBO().listar()).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erroJson(e)).build();
        }
    }

    @GET
    @Path("/{id}")
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(Usuario usuario) {
        try {
            return Response.status(Response.Status.CREATED).entity(new UsuarioBO().cadastrar(usuario)).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(erroJson(e)).build();
        }
    }

    @PUT
    @Path("/{id}")
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
    @Path("/{id}")
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
