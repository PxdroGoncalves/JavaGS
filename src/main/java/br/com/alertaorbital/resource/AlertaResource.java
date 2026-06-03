package br.com.alertaorbital.resource;

import br.com.alertaorbital.bo.AlertaBO;
import br.com.alertaorbital.entities.Alerta;
import br.com.alertaorbital.excecoes.ExcecoesConexao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/alertas")
public class AlertaResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        try {
            AlertaBO bo = new AlertaBO();
            List<Alerta> lista = bo.listar();
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
            AlertaBO bo = new AlertaBO();
            Alerta a = bo.buscarPorId(id);
            if (a == null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erro\":\"Alerta nao encontrado para o id: " + id + "\"}").build();
            return Response.ok(a).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName() + "\"}").build();
        }
    }

    @GET
    @Path("/usuario/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPorUsuario(@PathParam("idUsuario") int idUsuario) {
        try {
            AlertaBO bo = new AlertaBO();
            List<Alerta> lista = bo.listarPorUsuario(idUsuario);
            return Response.ok(lista).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName() + "\"}").build();
        }
    }

    @GET
    @Path("/ocorrencia/{idOcorrencia}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPorOcorrencia(@PathParam("idOcorrencia") int idOcorrencia) {
        try {
            AlertaBO bo = new AlertaBO();
            List<Alerta> lista = bo.listarPorOcorrencia(idOcorrencia);
            return Response.ok(lista).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName() + "\"}").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(Alerta alerta) {
        try {
            AlertaBO bo = new AlertaBO();
            Alerta criado = bo.cadastrar(alerta);
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
    public Response atualizar(@PathParam("id") int id, Alerta alerta) {
        try {
            alerta.setIdAlerta(id);
            AlertaBO bo = new AlertaBO();
            Alerta atualizado = bo.atualizar(alerta);
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
            AlertaBO bo = new AlertaBO();
            bo.deletar(id);
            return Response.noContent().build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName() + "\"}").build();
        }
    }
}
