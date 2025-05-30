package org.acme;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.hibernate.reactive.mutiny.Mutiny;

@ApplicationScoped
class GreetingService {

    @Inject
    private Mutiny.SessionFactory sessionFactory;

    public Uni<Greeting> saveGreeting(Greeting greeting) {
        return sessionFactory.openSession().flatMap(session ->
                Uni.createFrom().item(() -> {
                    validate(greeting);
                    return greeting;
                }).flatMap(validated ->
                        session.withTransaction(tx ->
                                session.persist(validated).replaceWith(validated)
                        )
                ).eventually(() -> session.close())
        );
    }

    private void validate(Greeting greeting) {
        if (greeting.message.equals("Good night")) {
            throw new BadRequestException("It's morning!");
        }
    }
}
