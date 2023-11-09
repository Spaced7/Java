package pl.kurs.zadanie1.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Lekarz {

    private int identyfikator;
    private String nazwisko;
    private String imie;
    private String specjalnosc;
    private LocalDate dataUrodzenia;
    private String nip;
    private String pesel;
    private List<Wizyta> wizyty = new ArrayList<>();

    public Lekarz(int identyfikator, String nazwisko, String imie, String specjalnosc, LocalDate dataUrodzenia, String nip, String pesel) {
        this.identyfikator = identyfikator;
        this.nazwisko = nazwisko;
        this.imie = imie;
        this.specjalnosc = specjalnosc;
        this.dataUrodzenia = dataUrodzenia;
        this.nip = nip;
        this.pesel = pesel;
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

    public String getSpecjalnosc() {
        return specjalnosc;
    }

    public LocalDate getDataUrodzenia() {
        return dataUrodzenia;
    }

    public String getNip() {
        return nip;
    }

    public String getPesel() {
        return pesel;
    }

    public List<Wizyta> getWizyty() {
        return wizyty;
    }

    @Override
    public String toString() {
        return "Lekarz{" +
                "identyfikator=" + identyfikator +
                ", nazwisko='" + nazwisko + '\'' +
                ", imie='" + imie + '\'' +
                ", specjalnosc='" + specjalnosc + '\'' +
                ", dataUrodzenia=" + dataUrodzenia +
                ", nip='" + nip + '\'' +
                ", pesel='" + pesel + '\'' +
                '}';
    }

}
