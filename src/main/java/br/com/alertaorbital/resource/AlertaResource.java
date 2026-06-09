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

    private String erroJson(ExcecoesConexao e) {
        String msg = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
        return "{\"erro\":\"" + msg + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // Busca e retorna os registros solicitados.
    public Response listar() {
        try {
            AlertaBO bo = new AlertaBO();
            return Response.ok(bo.listar()).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erroJson(e)).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    // Consulta informações com base nos parâmetros recebidos.
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            AlertaBO bo = new AlertaBO();
            Alerta a = bo.buscarPorId(id);
            if (a == null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erro\":\"Alerta nao encontrado para o id: " + id + "\"}").build();
            return Response.ok(a).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erroJson(e)).build();
        }
    }

    @GET
    @Path("/usuario/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    // Busca e retorna os registros solicitados.
    public Response listarPorUsuario(@PathParam("idUsuario") int idUsuario) {
        try {
            AlertaBO bo = new AlertaBO();
            return Response.ok(bo.listarPorUsuario(idUsuario)).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erroJson(e)).build();
        }
    }

    @GET
    @Path("/ocorrencia/{idOcorrencia}")
    @Produces(MediaType.APPLICATION_JSON)
    // Busca e retorna os registros solicitados.
    public Response listarPorOcorrencia(@PathParam("idOcorrencia") int idOcorrencia) {
        try {
            AlertaBO bo = new AlertaBO();
            return Response.ok(bo.listarPorOcorrencia(idOcorrencia)).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erroJson(e)).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Valida os dados recebidos e realiza o cadastro do registro.
    public Response cadastrar(Alerta alerta) {
        try {
            AlertaBO bo = new AlertaBO();
            return Response.status(Response.Status.CREATED).entity(bo.cadastrar(alerta)).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(erroJson(e)).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Atualiza as informações do registro existente.
    public Response atualizar(@PathParam("id") int id, Alerta alerta) {
        try {
            alerta.setIdAlerta(id);
            AlertaBO bo = new AlertaBO();
            return Response.ok(bo.atualizar(alerta)).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(erroJson(e)).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    // Remove o registro correspondente da base de dados.
    public Response deletar(@PathParam("id") int id) {
        try {
            new AlertaBO().deletar(id);
            return Response.noContent().build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erroJson(e)).build();
        }
    }
}