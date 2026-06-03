package br.com.alertaorbital.resource;

import br.com.alertaorbital.bo.TipoDesastreBO;
import br.com.alertaorbital.entities.TipoDesastre;
import br.com.alertaorbital.excecoes.ExcecoesConexao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/tipos-desastre")
public class TipoDesastreResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        try {
            TipoDesastreBO bo = new TipoDesastreBO();
            List<TipoDesastre> lista = bo.listar();
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
            TipoDesastreBO bo = new TipoDesastreBO();
            TipoDesastre td = bo.buscarPorId(id);
            if (td == null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erro\":\"TipoDesastre nao encontrado para o id: " + id + "\"}").build();
            return Response.ok(td).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName() + "\"}").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(TipoDesastre td) {
        try {
            TipoDesastreBO bo = new TipoDesastreBO();
            TipoDesastre criado = bo.cadastrar(td);
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
    public Response atualizar(@PathParam("id") int id, TipoDesastre td) {
        try {
            td.setIdTipo(id);
            TipoDesastreBO bo = new TipoDesastreBO();
            TipoDesastre atualizado = bo.atualizar(td);
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
            TipoDesastreBO bo = new TipoDesastreBO();
            bo.deletar(id);
            return Response.noContent().build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName() + "\"}").build();
        }
    }
}
