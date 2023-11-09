package pl.kurs.zadanie1;



import pl.kurs.zadanie1.model.Lekarz;
import pl.kurs.zadanie1.model.Pacjent;
import pl.kurs.zadanie1.model.Wizyta;
import pl.kurs.zadanie1.services.LekarzService;
import pl.kurs.zadanie1.services.PacjentService;
import pl.kurs.zadanie1.services.WizytaService;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Lekarz> lekarze = LekarzService.wczytajLekarzyZPliku("lekarze.txt");
        List<Pacjent> pacjenci = PacjentService.wczytajPacjentowZPliku("pacjenci.txt");
        List<Wizyta> wizyty = WizytaService.wczytajWizytyZPliku("wizyty.txt", lekarze, pacjenci);



        System.out.println(PacjentService.znajdzPacjentowZNajwiekszaLiczbaWizyt(pacjenci));
        System.out.println(LekarzService.znajdzLekarzyZNajwiekszaLiczbaWizyt(lekarze));
        System.out.println(LekarzService.znajdzNajpopularniejszeSpecjalizacje(lekarze));
        System.out.println(WizytaService.znajdzRokZNajwiekszaLiczbaWizyt(wizyty));
        System.out.println(LekarzService.znajdzTopNNajstarszychLekarzy(lekarze, 5));
        System.out.println(PacjentService.znajdzPacjentowZMinNRoznymiLekarzami(wizyty, 5));
        System.out.println(LekarzService.znajdzLekarzyExclusive(wizyty));

    }
}
