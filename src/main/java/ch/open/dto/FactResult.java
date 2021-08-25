package ch.open.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class FactResult {

    public long id;
    public String fact;
    public LocalDateTime timestamp;

    private FactResult() {
        // Jackson
    }

    public FactResult(long id, String fact, LocalDateTime timestamp) {
        this.id = id;
        this.fact = fact;
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FactResult that = (FactResult) o;
        return id == that.id && Objects.equals(fact, that.fact) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fact, timestamp);
    }

    @Override
    public String toString() {
        return "FactResult{" +
            "id=" + id +
            ", fact='" + fact + '\'' +
            ", timestamp=" + timestamp +
            '}';
    }
}
