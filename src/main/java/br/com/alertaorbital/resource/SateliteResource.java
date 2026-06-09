package br.com.alertaorbital.resource;

import br.com.alertaorbital.bo.SateliteBO;
import br.com.alertaorbital.entities.Satelite;
import br.com.alertaorbital.excecoes.ExcecoesConexao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/satelites")
public class SateliteResource {

    private String erroJson(ExcecoesConexao e) {
        String msg = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
        return "{\"erro\":\"" + msg + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // Busca e retorna os registros solicitados.
    public Response listar() {
        try {
            return Response.ok(new SateliteBO().listar()).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erroJson(e)).build();
        }
    }

    @GET
    @Path("/operacionais")
    @Produces(MediaType.APPLICATION_JSON)
    // Busca e retorna os registros solicitados.
    public Response listarOperacionais() {
        try {
            return Response.ok(new SateliteBO().listarOperacionais()).build();
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
            Satelite s = new SateliteBO().buscarPorId(id);
            if (s == null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erro\":\"Satelite nao encontrado para o id: " + id + "\"}").build();
            return Response.ok(s).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erroJson(e)).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Valida os dados recebidos e realiza o cadastro do registro.
    public Response cadastrar(Satelite satelite) {
        try {
            return Response.status(Response.Status.CREATED).entity(new SateliteBO().cadastrar(satelite)).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(erroJson(e)).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Atualiza as informações do registro existente.
    public Response atualizar(@PathParam("id") int id, Satelite satelite) {
        try {
            satelite.setIdSatelite(id);
            return Response.ok(new SateliteBO().atualizar(satelite)).build();
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
            new SateliteBO().deletar(id);
            return Response.noContent().build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erroJson(e)).build();
        }
    }
}