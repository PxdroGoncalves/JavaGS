package br.com.alertaorbital.resource;

import br.com.alertaorbital.bo.OcorrenciaBO;
import br.com.alertaorbital.entities.Ocorrencia;
import br.com.alertaorbital.entities.OcorrenciaSatelite;
import br.com.alertaorbital.entities.Satelite;
import br.com.alertaorbital.excecoes.ExcecoesConexao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@Path("/ocorrencias")
public class OcorrenciaResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // Busca e retorna os registros solicitados.
    public Response listar() {
        try {
            OcorrenciaBO bo = new OcorrenciaBO();
            List<Ocorrencia> lista = bo.listar();
            return Response.ok(lista).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()) + "\"}").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    // Consulta informações com base nos parâmetros recebidos.
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            OcorrenciaBO bo = new OcorrenciaBO();
            Ocorrencia o = bo.buscarPorId(id);
            if (o == null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erro\":\"Ocorrencia nao encontrada para o id: " + id + "\"}").build();
            return Response.ok(o).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()) + "\"}").build();
        }
    }

    @GET
    @Path("/status/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    // Busca e retorna os registros solicitados.
    public Response listarPorStatus(@PathParam("status") String status) {
        try {
            OcorrenciaBO bo = new OcorrenciaBO();
            List<Ocorrencia> lista = bo.listarPorStatus(status);
            return Response.ok(lista).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\":\"" + (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()) + "\"}").build();
        }
    }

    @GET
    @Path("/regiao/{idRegiao}")
    @Produces(MediaType.APPLICATION_JSON)
    // Busca e retorna os registros solicitados.
    public Response listarPorRegiao(@PathParam("idRegiao") int idRegiao) {
        try {
            OcorrenciaBO bo = new OcorrenciaBO();
            List<Ocorrencia> lista = bo.listarPorRegiao(idRegiao);
            return Response.ok(lista).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()) + "\"}").build();
        }
    }

    @GET
    @Path("/satelite/{idSatelite}")
    @Produces(MediaType.APPLICATION_JSON)
    // Busca e retorna os registros solicitados.
    public Response listarPorSatelite(@PathParam("idSatelite") int idSatelite) {
        try {
            OcorrenciaBO bo = new OcorrenciaBO();
            List<Ocorrencia> lista = bo.listarPorSatelite(idSatelite);
            return Response.ok(lista).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()) + "\"}").build();
        }
    }

    @GET
    @Path("/{id}/satelites")
    @Produces(MediaType.APPLICATION_JSON)
    // Busca e retorna os registros solicitados.
    public Response listarSatelites(@PathParam("id") int id) {
        try {
            OcorrenciaBO bo = new OcorrenciaBO();
            List<Satelite> lista = bo.listarSatelites(id);
            return Response.ok(lista).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()) + "\"}").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Valida os dados recebidos e realiza o cadastro do registro.
    public Response cadastrar(Ocorrencia ocorrencia) {
        try {
            OcorrenciaBO bo = new OcorrenciaBO();
            Ocorrencia criada = bo.cadastrar(ocorrencia);
            return Response.status(Response.Status.CREATED).entity(criada).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\":\"" + (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()) + "\"}").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Atualiza as informações do registro existente.
    public Response atualizar(@PathParam("id") int id, Ocorrencia ocorrencia) {
        try {
            ocorrencia.setIdOcorrencia(id);
            OcorrenciaBO bo = new OcorrenciaBO();
            Ocorrencia atualizada = bo.atualizar(ocorrencia);
            return Response.ok(atualizada).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\":\"" + (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()) + "\"}").build();
        }
    }

    // PUT /api/ocorrencias/{id}/status — atualiza apenas o status
    // Body: {"status": "CONTROLADO"}
    @PUT
    @Path("/{id}/status")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Atualiza as informações do registro existente.
    public Response atualizarStatus(@PathParam("id") int id, Map<String, String> body) {
        try {
            String novoStatus = body.get("status");
            if (novoStatus == null || novoStatus.isBlank())
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"erro\":\"Campo status é obrigatorio\"}").build();
            OcorrenciaBO bo = new OcorrenciaBO();
            Ocorrencia atualizada = bo.atualizarStatus(id, novoStatus);
            return Response.ok(atualizada).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\":\"" + (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()) + "\"}").build();
        }
    }

    // POST /api/ocorrencias/{id}/satelites — vincular satelite
    // Body: {"idSatelite": 1, "dataDeteccao": "2026-06-01"}
    @POST
    @Path("/{id}/satelites")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Executa a operação relacionada a vincularSatelite.
    public Response vincularSatelite(@PathParam("id") int id, Map<String, Object> body) {
        try {
            Object idSateliteObj = body.get("idSatelite");
            if (idSateliteObj == null)
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"erro\":\"Campo idSatelite é obrigatorio\"}").build();
            // FIX: Jackson desserializa números como Integer, não String
            int idSatelite;
            try {
                idSatelite = Integer.parseInt(idSateliteObj.toString());
            } catch (NumberFormatException ex) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"erro\":\"idSatelite deve ser um numero inteiro\"}").build();
            }
            Object dataDeteccaoObj = body.get("dataDeteccao");
            String dataDeteccao = dataDeteccaoObj != null ? dataDeteccaoObj.toString() : null;
            OcorrenciaBO bo = new OcorrenciaBO();
            OcorrenciaSatelite os = bo.vincularSatelite(id, idSatelite, dataDeteccao);
            return Response.status(Response.Status.CREATED).entity(os).build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\":\"" + (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()) + "\"}").build();
        }
    }

    // DELETE /api/ocorrencias/{id}/satelites/{idSatelite} — desvincular satelite
    @DELETE
    @Path("/{id}/satelites/{idSatelite}")
    @Produces(MediaType.APPLICATION_JSON)
    // Executa a operação relacionada a desvincularSatelite.
    public Response desvincularSatelite(@PathParam("id") int id,
                                        @PathParam("idSatelite") int idSatelite) {
        try {
            OcorrenciaBO bo = new OcorrenciaBO();
            bo.desvincularSatelite(id, idSatelite);
            return Response.noContent().build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\":\"" + (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()) + "\"}").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    // Remove o registro correspondente da base de dados.
    public Response deletar(@PathParam("id") int id) {
        try {
            OcorrenciaBO bo = new OcorrenciaBO();
            bo.deletar(id);
            return Response.noContent().build();
        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\":\"" + (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()) + "\"}").build();
        }
    }
}