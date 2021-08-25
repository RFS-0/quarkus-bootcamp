package ch.open.repository;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Fact extends PanacheEntity {

    public String fact;
    public LocalDateTime timestamp;

    public Fact() {
        // Hibernate
    }

    public Fact(String fact, LocalDateTime timestamp) {
        this.fact = fact;
        this.timestamp = timestamp;
    }
}
