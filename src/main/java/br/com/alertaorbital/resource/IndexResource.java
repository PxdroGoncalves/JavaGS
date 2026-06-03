package br.com.alertaorbital.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Path("/")
public class IndexResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response index() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("mensagem", "API AlertaOrbital rodando com sucesso");
        response.put("swagger", "/q/swagger-ui");
        response.put("endpoints", List.of(
                ep("/regioes",                                 "lista regioes monitoradas"),
                ep("/satelites",                               "lista satelites cadastrados"),
                ep("/satelites/operacionais",                  "lista apenas satelites ativos"),
                ep("/tipos-desastre",                          "lista tipos de desastre e niveis de risco"),
                ep("/usuarios",                                "lista operadores e analistas"),
                ep("/ocorrencias",                             "lista e cadastra ocorrencias"),
                ep("/ocorrencias/status/{status}",             "filtra ocorrencias por status"),
                ep("/ocorrencias/{id}/status",                 "atualiza status da ocorrencia"),
                ep("/ocorrencias/{id}/satelites",              "vincula e lista satelites da ocorrencia"),
                ep("/ocorrencias/{id}/satelites/{idSatelite}", "desvincula satelite da ocorrencia"),
                ep("/alertas",                                 "lista e emite alertas"),
                ep("/alertas/usuario/{idUsuario}",             "alertas por operador"),
                ep("/alertas/ocorrencia/{idOcorrencia}",       "alertas por ocorrencia"),
                ep("/relatorio",                               "resumo geral das ocorrencias"),
                ep("/relatorio/nasa-eonet",                    "eventos abertos em tempo real via NASA")
        ));
        return Response.ok(response).build();
    }

    private Map<String, String> ep(String path, String descricao) {
        Map<String, String> m = new LinkedHashMap<>();
        m.put(path, descricao);
        return m;
    }
}