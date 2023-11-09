package pl.kurs.zadanie1.model;

import pl.kurs.zadanie1.exceptions.EntityNotFoundException;

import java.time.Instant;
import java.time.LocalDate;

public class Wizyta {

    private Lekarz lekarz;
    private Pacjent pacjent;
    private LocalDate data;

    public Wizyta(Lekarz lekarz, Pacjent pacjent, LocalDate data) {
        if (pacjent == null) {
            throw new EntityNotFoundException(Instant.now(), "pacjent", "identyfikator");
        }
        if (lekarz == null) {
            throw new EntityNotFoundException(Instant.now(), "lakarz", "identyfikator");
        }
        this.lekarz = lekarz;
        this.pacjent = pacjent;
        this.data = data;

        lekarz.getWizyty().add(this);
        pacjent.getWizyty().add(this);
    }

    public Lekarz getLekarz() {
        return lekarz;
    }

    public Pacjent getPacjent() {
        return pacjent;
    }

    public LocalDate getData() {
        return data;
    }


    @Override
    public String toString() {
        return "Wizyta{" +
                "lekarz=" + lekarz.getIdentyfikator() +
                ", pacjent=" + pacjent.getIdentyfikator() +
                ", data=" + data +
                '}';
    }
}
