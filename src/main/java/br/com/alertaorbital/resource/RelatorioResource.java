package br.com.alertaorbital.resource;

import br.com.alertaorbital.conexoes.ConexaoFactory;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

@Path("/relatorio")
public class RelatorioResource {

    private static final String NASA_EONET_URL =
            "https://eonet.gsfc.nasa.gov/api/v3/events?status=open&limit=20";

    private String erroJson(Exception e) {
        String msg = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
        return "{\"erro\":\"" + msg + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response gerarRelatorio() {
        try (Connection con = ConexaoFactory.getConnection()) {

            // 1. Busca todas as ocorrências com JOIN em uma query
            String sqlOcorrencias =
                "SELECT o.id_ocorrencia, o.descricao, o.status, " +
                "TO_CHAR(o.data_inicio,'YYYY-MM-DD') AS data_inicio, " +
                "TO_CHAR(o.data_fim,'YYYY-MM-DD') AS data_fim, " +
                "r.cidade AS cidade_regiao, " +
                "td.nome AS nome_tipo, td.nivel_risco " +
                "FROM OCORRENCIA o " +
                "INNER JOIN REGIAO r ON r.id_regiao = o.id_regiao " +
                "INNER JOIN TIPO_DESASTRE td ON td.id_tipo = o.id_tipo " +
                "ORDER BY o.data_inicio DESC";

            List<Map<String, Object>> ocorrencias = new ArrayList<>();
            Map<Integer, Map<String, Object>> ocorrenciaMap = new LinkedHashMap<>();

            try (PreparedStatement ps = con.prepareStatement(sqlOcorrencias);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id_ocorrencia");
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("id_ocorrencia",       id);
                    item.put("descricao",            rs.getString("descricao"));
                    item.put("status",               rs.getString("status"));
                    item.put("data_inicio",          rs.getString("data_inicio"));
                    item.put("data_fim",             rs.getString("data_fim"));
                    item.put("cidade",               rs.getString("cidade_regiao"));
                    item.put("tipo_desastre",        rs.getString("nome_tipo"));
                    item.put("nivel_risco",          rs.getString("nivel_risco"));
                    item.put("satelites_detectores", new ArrayList<String>());
                    item.put("total_alertas",        0L);
                    ocorrencias.add(item);
                    ocorrenciaMap.put(id, item);
                }
            }

            // 2. Busca satélites de todas as ocorrências de uma vez
            if (!ocorrenciaMap.isEmpty()) {
                String sqlSatelites =
                    "SELECT os.id_ocorrencia, s.nome, s.agencia " +
                    "FROM OCORRENCIA_SATELITE os " +
                    "INNER JOIN SATELITE s ON s.id_satelite = os.id_satelite";

                try (PreparedStatement ps = con.prepareStatement(sqlSatelites);
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int idOc = rs.getInt("id_ocorrencia");
                        if (ocorrenciaMap.containsKey(idOc)) {
                            @SuppressWarnings("unchecked")
                            List<String> nomes = (List<String>) ocorrenciaMap.get(idOc).get("satelites_detectores");
                            nomes.add(rs.getString("nome") + " (" + rs.getString("agencia") + ")");
                        }
                    }
                }
            }

            // 3. Conta alertas por ocorrência de uma vez
            String sqlAlertas =
                "SELECT id_ocorrencia, COUNT(*) AS total " +
                "FROM ALERTA GROUP BY id_ocorrencia";

            long totalAlertas = 0;
            try (PreparedStatement ps = con.prepareStatement(sqlAlertas);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idOc = rs.getInt("id_ocorrencia");
                    long total = rs.getLong("total");
                    totalAlertas += total;
                    if (ocorrenciaMap.containsKey(idOc)) {
                        ocorrenciaMap.get(idOc).put("total_alertas", total);
                    }
                }
            }

            // 4. Monta sumário
            long totalAtivo      = ocorrencias.stream().filter(o -> "ATIVO".equals(o.get("status"))).count();
            long totalControlado = ocorrencias.stream().filter(o -> "CONTROLADO".equals(o.get("status"))).count();
            long totalResolvido  = ocorrencias.stream().filter(o -> "RESOLVIDO".equals(o.get("status"))).count();

            Map<String, Object> rel = new LinkedHashMap<>();
            rel.put("gerado_em",         new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));
            rel.put("total_ocorrencias", ocorrencias.size());
            rel.put("total_ativo",       totalAtivo);
            rel.put("total_controlado",  totalControlado);
            rel.put("total_resolvido",   totalResolvido);
            rel.put("total_alertas",     totalAlertas);
            rel.put("ocorrencias",       ocorrencias);

            return Response.ok(rel).build();

        } catch (ExcecoesConexao | java.sql.SQLException e) {
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
