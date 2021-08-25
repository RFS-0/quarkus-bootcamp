package ch.open.resource;

import ch.open.dto.FactResult;
import ch.open.dto.NewFact;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Path("/facts")
public class FactResource {

    private final List<FactResult> facts = new CopyOnWriteArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FactResult> getFacts() {
        return facts;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addFact(NewFact newFact) {
        var fact = new FactResult(facts.size(), newFact.fact, LocalDateTime.now());
        facts.add(fact);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFactById(@PathParam("id") int id) {
        return facts.stream()
                .filter(g -> id == g.id)
                .findAny()
                .map(g -> Response.ok(g).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
