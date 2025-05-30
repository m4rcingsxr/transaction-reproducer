package org.acme;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/greetings")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class GreetingResource {

    @Inject
    private GreetingService greetingService;

    @POST
    public Uni<List<Greeting>> createGreetings(List<Greeting> greetings) {

        // Create greetings asynchronously with separate transactions
        return Multi.createFrom().iterable(greetings)
                .onItem().transformToUniAndMerge(greeting ->

                    // close session on failure
                    greetingService.saveGreeting(greeting).onFailure().recoverWithNull()
                )
                .collect().asList();
    }

}
