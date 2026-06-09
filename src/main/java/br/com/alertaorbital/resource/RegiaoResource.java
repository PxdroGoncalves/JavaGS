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

    private String erroJson(ExcecoesConexao e) {
        String msg = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
        return "{\"erro\":\"" + msg + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // Busca e retorna os registros solicitados.
    public Response listar() {
        try {
            return Response.ok(new RegiaoBO().listar()).build();
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
            Regiao r = new RegiaoBO().buscarPorId(id);
            if (r == null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erro\":\"Regiao nao encontrada para o id: " + id + "\"}").build();
            return Response.ok(r).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erroJson(e)).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Valida os dados recebidos e realiza o cadastro do registro.
    public Response cadastrar(Regiao regiao) {
        try {
            return Response.status(Response.Status.CREATED).entity(new RegiaoBO().cadastrar(regiao)).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(erroJson(e)).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Atualiza as informações do registro existente.
    public Response atualizar(@PathParam("id") int id, Regiao regiao) {
        try {
            regiao.setIdRegiao(id);
            return Response.ok(new RegiaoBO().atualizar(regiao)).build();
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
            new RegiaoBO().deletar(id);
            return Response.noContent().build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erroJson(e)).build();
        }
    }
}