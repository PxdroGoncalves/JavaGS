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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        try {
            UsuarioBO bo = new UsuarioBO();
            List<Usuario> lista = bo.listar();
            return Response.ok(lista).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName() + "\"}").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            UsuarioBO bo = new UsuarioBO();
            Usuario u = bo.buscarPorId(id);
            if (u == null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erro\":\"Usuario nao encontrado para o id: " + id + "\"}").build();
            return Response.ok(u).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName() + "\"}").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(Usuario usuario) {
        try {
            UsuarioBO bo = new UsuarioBO();
            Usuario criado = bo.cadastrar(usuario);
            return Response.status(Response.Status.CREATED).entity(criado).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\":\"" + e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName() + "\"}").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizar(@PathParam("id") int id, Usuario usuario) {
        try {
            usuario.setIdUsuario(id);
            UsuarioBO bo = new UsuarioBO();
            Usuario atualizado = bo.atualizar(usuario);
            return Response.ok(atualizado).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\":\"" + e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName() + "\"}").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletar(@PathParam("id") int id) {
        try {
            UsuarioBO bo = new UsuarioBO();
            bo.deletar(id);
            return Response.noContent().build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName() + "\"}").build();
        }
    }
}
