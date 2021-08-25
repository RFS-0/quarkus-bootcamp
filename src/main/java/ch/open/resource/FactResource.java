package ch.open.resource;

import ch.open.dto.FactResult;
import ch.open.dto.NewFact;
import ch.open.service.FactService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

@Path("/facts")
public class FactResource {

    @Inject
    FactService factService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FactResult> getFacts(@DefaultValue("3") @QueryParam("limit") int limit) {
        return factService.getFacts(limit);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addFact(NewFact newFact) {
        var factResult = factService.add(newFact);

        var location = UriBuilder.fromPath(Long.toString(factResult.id)).build();
        return Response.created(location).entity(factResult).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFactById(@PathParam("id") int id) {
        return factService.getFactFor(id)
                .map(fact -> Response.ok(fact).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
