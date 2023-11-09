package pl.kurs.zadanie1.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pacjent {

    private int identyfikator;
    private String nazwisko;
    private String imie;
    private String pesel;
    private LocalDate dataUrodzenia;
    private List<Wizyta> wizyty = new ArrayList<>();

    public Pacjent(int identyfikator, String nazwisko, String imie, String pesel, LocalDate dataUrodzenia) {
        this.identyfikator = identyfikator;
        this.nazwisko = nazwisko;
        this.imie = imie;
        this.pesel = pesel;
        this.dataUrodzenia = dataUrodzenia;
    }

    public int getIdentyfikator() {
        return identyfikator;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public String getPesel() {
        return pesel;
    }

    public LocalDate getDataUrodzenia() {
        return dataUrodzenia;
    }

    public List<Wizyta> getWizyty() {
        return wizyty;
    }

    @Override
    public String toString() {
        return "Pacjent{" +
                "identyfikator=" + identyfikator +
                ", nazwisko='" + nazwisko + '\'' +
                ", imie='" + imie + '\'' +
                ", pesel='" + pesel + '\'' +
                ", dataUrodzenia=" + dataUrodzenia +
                '}';
    }


}
