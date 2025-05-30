package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

@QuarkusTest
class GreetingResourceE2E {

    @RepeatedTest(100)
    void shouldPersistGreetingsWithCorrectMessage() {
        var goodNight = createGreeting("Good night");
        var heyThere = createGreeting("Hey there");
        var goodMorning = createGreeting("Good morning");

        given()
                .contentType("application/json")
                .body(List.of(goodNight, goodMorning, heyThere))
                .log().all()
                .when().post("/greetings")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", is(2))
                .body("message", containsInAnyOrder("Hey there", "Good morning"));
    }

    private Greeting createGreeting(String message) {
        Greeting greeting = new Greeting();
        greeting.message = message;
        return greeting;
    }
}
