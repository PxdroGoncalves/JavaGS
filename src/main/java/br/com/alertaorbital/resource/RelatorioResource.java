package br.com.alertaorbital.resource;

import br.com.alertaorbital.dao.AlertaDAO;
import br.com.alertaorbital.dao.OcorrenciaDAO;
import br.com.alertaorbital.dao.OcorrenciaSateliteDAO;
import br.com.alertaorbital.entities.Alerta;
import br.com.alertaorbital.entities.Ocorrencia;
import br.com.alertaorbital.entities.Satelite;
import br.com.alertaorbital.excecoes.ExcecoesConexao;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;

@Path("/relatorio")
public class RelatorioResource {

    private static final String NASA_EONET_URL = "https://eonet.gsfc.nasa.gov/api/v3/events?status=open&limit=20";

    private String erroJson(Exception e) {
        String msg = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
        return "{\"erro\":\"" + msg + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response gerarRelatorio() {
        try {
            OcorrenciaDAO ocorrenciaDAO   = new OcorrenciaDAO();
            AlertaDAO alertaDAO           = new AlertaDAO();
            OcorrenciaSateliteDAO osDAO   = new OcorrenciaSateliteDAO();

            List<Ocorrencia> todas   = ocorrenciaDAO.listar();
            List<Alerta> alertas     = alertaDAO.listar();

            long totalAtivo      = todas.stream().filter(o -> "ATIVO".equals(o.getStatus())).count();
            long totalControlado = todas.stream().filter(o -> "CONTROLADO".equals(o.getStatus())).count();
            long totalResolvido  = todas.stream().filter(o -> "RESOLVIDO".equals(o.getStatus())).count();

            List<Map<String, Object>> detalhes = new ArrayList<>();
            for (Ocorrencia o : todas) {
                List<Satelite> satelites = osDAO.listarSatelitesPorOcorrencia(o.getIdOcorrencia());
                List<String> nomes = new ArrayList<>();
                for (Satelite s : satelites)
                    nomes.add(s.getNome() + " (" + s.getAgencia() + ")");

                long totalAl = alertas.stream().filter(a -> a.getIdOcorrencia() == o.getIdOcorrencia()).count();

                Map<String, Object> item = new LinkedHashMap<>();
                item.put("id_ocorrencia",       o.getIdOcorrencia());
                item.put("descricao",            o.getDescricao());
                item.put("status",               o.getStatus());
                item.put("data_inicio",          o.getDataInicio());
                item.put("data_fim",             o.getDataFim());
                item.put("regiao",               o.getNomeRegiao());
                item.put("estado",               o.getEstadoRegiao());
                item.put("tipo_desastre",        o.getNomeTipo());
                item.put("nivel_risco",          o.getNivelRisco());
                item.put("satelites_detectores", nomes);
                item.put("total_alertas",        totalAl);
                detalhes.add(item);
            }

            Map<String, Object> rel = new LinkedHashMap<>();
            rel.put("gerado_em",         new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));
            rel.put("total_ocorrencias", todas.size());
            rel.put("total_ativo",       totalAtivo);
            rel.put("total_controlado",  totalControlado);
            rel.put("total_resolvido",   totalResolvido);
            rel.put("total_alertas",     alertas.size());
            rel.put("ocorrencias",       detalhes);

            return Response.ok(rel).build();

        } catch (ExcecoesConexao e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erroJson(e)).build();
        }
    }

    @GET
    @Path("/nasa-eonet")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarNasaEonet() {
        try {
            HttpURLConnection conn = (HttpURLConnection) URI.create(NASA_EONET_URL).toURL().openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10000);

            int status = conn.getResponseCode();
            if (status != 200)
                return Response.status(Response.Status.BAD_GATEWAY)
                        .entity("{\"erro\":\"NASA EONET retornou status " + status + "\"}").build();

            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String linha;
                while ((linha = br.readLine()) != null) sb.append(linha);
            }
            return Response.ok(sb.toString()).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erroJson(e)).build();
        }
    }
}
