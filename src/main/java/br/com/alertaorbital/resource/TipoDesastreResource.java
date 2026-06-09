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

    private String erroJson(ExcecoesConexao e) {
        String msg = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
        return "{\"erro\":\"" + msg + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // Busca e retorna os registros solicitados.
    public Response listar() {
        try {
            return Response.ok(new TipoDesastreBO().listar()).build();
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
            TipoDesastre td = new TipoDesastreBO().buscarPorId(id);
            if (td == null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erro\":\"TipoDesastre nao encontrado para o id: " + id + "\"}").build();
            return Response.ok(td).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erroJson(e)).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Valida os dados recebidos e realiza o cadastro do registro.
    public Response cadastrar(TipoDesastre td) {
        try {
            return Response.status(Response.Status.CREATED).entity(new TipoDesastreBO().cadastrar(td)).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(erroJson(e)).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Atualiza as informações do registro existente.
    public Response atualizar(@PathParam("id") int id, TipoDesastre td) {
        try {
            td.setIdTipo(id);
            return Response.ok(new TipoDesastreBO().atualizar(td)).build();
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
            new TipoDesastreBO().deletar(id);
            return Response.noContent().build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erroJson(e)).build();
        }
    }
}