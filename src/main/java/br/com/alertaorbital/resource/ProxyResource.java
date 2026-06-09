package br.com.alertaorbital.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

@Path("/proxy")
public class ProxyResource {

    @GET
    @Path("/gdacs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response gdacs() {

        try {
            LocalDate hoje = LocalDate.now();
            LocalDate ha365dias = hoje.minusDays(365);

            String url =
                    "https://www.gdacs.org/gdacsapi/api/events/geteventlist/SEARCH"
                            + "?fromDate=" + ha365dias
                            + "&toDate=" + hoje
                            + "&alertlevel=Green,Orange,Red"
                            + "&pagesize=100";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            String body = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            ).body();

            return Response.ok(body).build();

        } catch (Exception e) {
            return Response.serverError()
                    .entity("{\"erro\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}