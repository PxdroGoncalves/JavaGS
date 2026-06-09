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
    public Response gdacs() throws Exception {
        LocalDate hoje = LocalDate.now();
        LocalDate ha365dias = hoje.minusDays(365);

        String url = "https://www.gdacs.org/gdacsapi/api/events/geteventlist/SEARCH"
                + "?fromDate=" + ha365dias
                + "&toDate=" + hoje
                + "&alertlevel=Green,Orange,Red"
                + "&country=Brazil,Argentina,Chile,Peru,Colombia,Bolivia,Ecuador,Paraguay,Uruguay,Venezuela,Suriname,Guyana"
                + "&pagesize=100";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return Response.ok(response.body()).build();
    }
}
