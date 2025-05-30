package org.acme;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;

@ApplicationScoped
class GreetingService {

    @Inject
    private GreetingRepository greetingRepository;

    @WithTransaction
    public Uni<Greeting> saveGreeting(Greeting greeting) {
        validate(greeting);
        return greetingRepository.persist(greeting);
    }

    private void validate(Greeting greeting) {
        if (greeting.message.equals("Good night")) {
            throw new BadRequestException("It's morning!");
        }
    }
}
