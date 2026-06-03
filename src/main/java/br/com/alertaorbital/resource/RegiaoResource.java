package br.com.alertaorbital.resource;

import br.com.alertaorbital.bo.RegiaoBO;
import br.com.alertaorbital.entities.Regiao;
import br.com.alertaorbital.excecoes.ExcecoesConexao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/regioes")
public class RegiaoResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        try {
            RegiaoBO bo = new RegiaoBO();
            List<Regiao> lista = bo.listar();
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
            RegiaoBO bo = new RegiaoBO();
            Regiao r = bo.buscarPorId(id);
            if (r == null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erro\":\"Regiao nao encontrada para o id: " + id + "\"}").build();
            return Response.ok(r).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName() + "\"}").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(Regiao regiao) {
        try {
            RegiaoBO bo = new RegiaoBO();
            Regiao criada = bo.cadastrar(regiao);
            return Response.status(Response.Status.CREATED).entity(criada).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\":\"" + e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName() + "\"}").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizar(@PathParam("id") int id, Regiao regiao) {
        try {
            regiao.setIdRegiao(id);
            RegiaoBO bo = new RegiaoBO();
            Regiao atualizada = bo.atualizar(regiao);
            return Response.ok(atualizada).build();
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
            RegiaoBO bo = new RegiaoBO();
            bo.deletar(id);
            return Response.noContent().build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName() + "\"}").build();
        }
    }
}
