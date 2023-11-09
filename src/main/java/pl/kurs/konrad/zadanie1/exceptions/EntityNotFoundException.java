package pl.kurs.zadanie1.exceptions;

import java.time.Instant;

public class EntityNotFoundException extends IllegalArgumentException {

    private Instant occurredAt;
    private String entity;
    private String key;

    public EntityNotFoundException(Instant occurredAt, String entity, String key) {
        this.occurredAt = occurredAt;
        this.entity = entity;
        this.key = key;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "EntityNotFoundException{" +
                "occurredAt=" + occurredAt +
                ", entity='" + entity + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public String getMessage() {
        return super.getMessage();
    }
}
