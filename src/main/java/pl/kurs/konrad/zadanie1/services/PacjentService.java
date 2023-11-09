package pl.kurs.zadanie1.services;

import pl.kurs.zadanie1.model.Lekarz;
import pl.kurs.zadanie1.model.Pacjent;
import pl.kurs.zadanie1.model.Wizyta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class PacjentService {

    public static List<Pacjent> wczytajPacjentowZPliku(String nazwaPliku) {
        List<Pacjent> pacjenci = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nazwaPliku))) {
            String linia;
            reader.readLine();
            while ((linia = reader.readLine()) != null) {
                String[] wycinek = linia.split("\t");
                int identyfikator = Integer.parseInt(wycinek[0]);
                String nazwisko = wycinek[1];
                String imie = wycinek[2];
                String pesel = wycinek[3];
                String poprawnaData = poprawnyFormatDaty(wycinek[4]);
                LocalDate dataUrodzenia = LocalDate.parse(poprawnaData);
                pacjenci.add(new Pacjent(identyfikator, nazwisko, imie, pesel, dataUrodzenia));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pacjenci;
    }

    public static Pacjent znajdzPacjentaPoId(List<Pacjent> pacjenci, int identyfikator) {
        return pacjenci.stream()
                .filter(pacjent -> pacjent.getIdentyfikator() == identyfikator)
                .findFirst()
                .orElse(null);
    }

    public static String poprawnyFormatDaty(String data) {
        String[] dataParts = data.split("-");
        String rok = dataParts[0];
        String miesiac = dataParts[1].length() == 1 ? "0" + dataParts[1] : dataParts[1];
        String dzien = dataParts[2].length() == 1 ? "0" + dataParts[2] : dataParts[2];
        return rok + "-" + miesiac + "-" + dzien;
    }

    public static List<Pacjent> znajdzPacjentowZNajwiekszaLiczbaWizyt(List<Pacjent> pacjenci) {
        int maxLiczbaWizyt = pacjenci.stream()
                .mapToInt(pacjent -> pacjent.getWizyty().size())
                .max()
                .orElse(0);

        return pacjenci.stream()
                .filter(pacjent -> pacjent.getWizyty().size() == maxLiczbaWizyt)
                .collect(Collectors.toList());
    }

    public static List<Pacjent> znajdzPacjentowZMinNRoznymiLekarzami(List<Wizyta> wizyty, int n) {
        Map<Pacjent, Set<Lekarz>> pacjentLekarzeMap = utworzMapePacjentLekarze(wizyty);
        return znajdzPacjentowZMinNRoznymiLekarzamiZMapy(pacjentLekarzeMap, n);
    }

    private static Map<Pacjent, Set<Lekarz>> utworzMapePacjentLekarze(List<Wizyta> wizyty) {
        return wizyty.stream()
                .collect(Collectors.groupingBy(Wizyta::getPacjent,
                        Collectors.mapping(Wizyta::getLekarz, Collectors.toSet())));
    }

    private static List<Pacjent> znajdzPacjentowZMinNRoznymiLekarzamiZMapy(Map<Pacjent, Set<Lekarz>> pacjentLekarzeMap, int n) {
        return pacjentLekarzeMap.entrySet().stream()
                .filter(entry -> entry.getValue().size() >= n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
