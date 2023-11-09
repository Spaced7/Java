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

public class LekarzService {

    public static List<Lekarz> wczytajLekarzyZPliku(String nazwaPliku) {
        List<Lekarz> lekarze = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nazwaPliku))) {
            String linia;
            reader.readLine();
            while ((linia = reader.readLine()) != null) {
                String[] wycinek = linia.split("\t");
                int identyfikator = Integer.parseInt(wycinek[0]);
                String nazwisko = wycinek[1];
                String imie = wycinek[2];
                String specjalnosc = wycinek[3];
                LocalDate dataUrodzenia = LocalDate.parse(wycinek[4]);
                String nip = wycinek[5];
                String pesel = wycinek[6];
                lekarze.add(new Lekarz(identyfikator, nazwisko, imie, specjalnosc, dataUrodzenia, nip, pesel));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lekarze;
    }

    public static Lekarz znajdzLekarzaPoId(List<Lekarz> lekarze, int identyfikator) {
        return lekarze.stream()
                .filter(lekarz -> lekarz.getIdentyfikator() == identyfikator)
                .findFirst()
                .orElse(null);
    }

    public static List<Lekarz> znajdzLekarzyZNajwiekszaLiczbaWizyt(List<Lekarz> lekarze) {
        List<Lekarz> lekarzeZNajwiecejWizyt = new ArrayList<>();
        int maxLiczbaWizyt = znajdzMaxLiczbaWizyt(lekarze);
        dodajLekarzyZNajwiekszaLiczbaWizyt(lekarze, lekarzeZNajwiecejWizyt, maxLiczbaWizyt);
        return lekarzeZNajwiecejWizyt;
    }

    public static List<String> znajdzNajpopularniejszeSpecjalizacje(List<Lekarz> lekarze) {
        Map<String, Integer> specjalizacjeLiczniki = obliczSpecjalizacjeLiczniki(lekarze);
        return znajdzNajpopularniejszeSpecjalizacjeZLicznikami(specjalizacjeLiczniki);
    }

    public static List<Lekarz> znajdzTopNNajstarszychLekarzy(List<Lekarz> lekarze, int n) {
        return lekarze.stream()
                .sorted(Comparator.comparing(Lekarz::getDataUrodzenia))
                .limit(n)
                .collect(Collectors.toList());
    }

    public static List<Lekarz> znajdzLekarzyExclusive(List<Wizyta> wizyty) {
        Map<Lekarz, Set<Pacjent>> lekarzPacjenciMapa = utworzMapeLekarzPacjenci(wizyty);
        return znajdzLekarzyExclusiveZMapy(lekarzPacjenciMapa);
    }

    private static Map<Lekarz, Set<Pacjent>> utworzMapeLekarzPacjenci(List<Wizyta> wizyty) {
        return wizyty.stream()
                .collect(Collectors.groupingBy(Wizyta::getLekarz,
                        Collectors.mapping(Wizyta::getPacjent, Collectors.toSet())));
    }

    private static List<Lekarz> znajdzLekarzyExclusiveZMapy(Map<Lekarz, Set<Pacjent>> lekarzPacjenciMapa) {
        return lekarzPacjenciMapa.entrySet().stream()
                .filter(entry -> entry.getValue().size() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private static int znajdzMaxLiczbaWizyt(List<Lekarz> lekarze) {
        return lekarze.stream()
                .mapToInt(lekarz -> lekarz.getWizyty().size())
                .max()
                .orElse(0);
    }

    private static void dodajLekarzyZNajwiekszaLiczbaWizyt(List<Lekarz> lekarze, List<Lekarz> lekarzeZNajwiecejWizyt, int maxLiczbaWizyt) {
        lekarze.stream()
                .filter(lekarz -> lekarz.getWizyty().size() == maxLiczbaWizyt)
                .forEach(lekarzeZNajwiecejWizyt::add);
    }

    private static Map<String, Integer> obliczSpecjalizacjeLiczniki(List<Lekarz> lekarze) {
        return lekarze.stream()
                .collect(Collectors.toMap(Lekarz::getSpecjalnosc, lekarz -> 1, Integer::sum));
    }

    private static List<String> znajdzNajpopularniejszeSpecjalizacjeZLicznikami(Map<String, Integer> specjalizacjeLiczniki) {
        List<String> najpopularniejszeSpecjalizacje = new ArrayList<>();
        int maxLicznik = znajdzMaxLicznik(specjalizacjeLiczniki);
        dodajNajpopularniejszeSpecjalizacje(specjalizacjeLiczniki, najpopularniejszeSpecjalizacje, maxLicznik);
        return najpopularniejszeSpecjalizacje;
    }

    private static int znajdzMaxLicznik(Map<String, Integer> specjalizacjeLiczniki) {
        return specjalizacjeLiczniki.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1);
    }

    private static void dodajNajpopularniejszeSpecjalizacje(Map<String, Integer> specjalizacjeLiczniki, List<String> najpopularniejszeSpecjalizacje, int maxLicznik) {
        specjalizacjeLiczniki.entrySet().stream()
                .filter(entry -> entry.getValue() == maxLicznik)
                .map(Map.Entry::getKey)
                .forEach(najpopularniejszeSpecjalizacje::add);
    }
}
