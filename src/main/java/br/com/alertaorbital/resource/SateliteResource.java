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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        try {
            SateliteBO bo = new SateliteBO();
            List<Satelite> lista = bo.listar();
            return Response.ok(lista).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName() + "\"}").build();
        }
    }

    @GET
    @Path("/operacionais")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarOperacionais() {
        try {
            SateliteBO bo = new SateliteBO();
            List<Satelite> lista = bo.listarOperacionais();
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
            SateliteBO bo = new SateliteBO();
            Satelite s = bo.buscarPorId(id);
            if (s == null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erro\":\"Satelite nao encontrado para o id: " + id + "\"}").build();
            return Response.ok(s).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName() + "\"}").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(Satelite satelite) {
        try {
            SateliteBO bo = new SateliteBO();
            Satelite criado = bo.cadastrar(satelite);
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
    public Response atualizar(@PathParam("id") int id, Satelite satelite) {
        try {
            satelite.setIdSatelite(id);
            SateliteBO bo = new SateliteBO();
            Satelite atualizado = bo.atualizar(satelite);
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
            SateliteBO bo = new SateliteBO();
            bo.deletar(id);
            return Response.noContent().build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName() + "\"}").build();
        }
    }
}
