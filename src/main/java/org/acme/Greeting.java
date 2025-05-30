package org.acme;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
class Greeting {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "greeting_seq")
    @SequenceGenerator(name = "greeting_seq", sequenceName = "greeting_seq", allocationSize = 1)
    Long id;
    String message;

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
