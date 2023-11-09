package pl.kurs.zadanie1.services;

import pl.kurs.zadanie1.model.Lekarz;
import pl.kurs.zadanie1.model.Pacjent;
import pl.kurs.zadanie1.model.Wizyta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WizytaService {

    public static List<Wizyta> wczytajWizytyZPliku(String nazwaPliku, List<Lekarz> lekarze, List<Pacjent> pacjenci) {
        List<Wizyta> wizyty = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nazwaPliku))) {
            String linia;
            reader.readLine();
            while ((linia = reader.readLine()) != null) {
                String[] wycinek = linia.split("\t");
                int lekarzId = Integer.parseInt(wycinek[0]);
                int pacjentId = Integer.parseInt(wycinek[1]);
                String poprawnaData = PacjentService.poprawnyFormatDaty(wycinek[2]);
                LocalDate data = LocalDate.parse(poprawnaData);
                Lekarz lekarz = LekarzService.znajdzLekarzaPoId(lekarze, lekarzId);
                Pacjent pacjent = PacjentService.znajdzPacjentaPoId(pacjenci, pacjentId);
                if (lekarz != null && pacjent != null) {
                    wizyty.add(new Wizyta(lekarz, pacjent, data));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wizyty;
    }

    public static List<Integer> znajdzRokZNajwiekszaLiczbaWizyt(List<Wizyta> wizyty) {
        Map<Integer, Integer> rokZliczenia = zliczWizytyLata(wizyty);
        return znajdzLataZNajwiekszaLiczbaWizyt(rokZliczenia);
    }

    private static Map<Integer, Integer> zliczWizytyLata(List<Wizyta> wizyty) {
        return wizyty.stream()
                .collect(Collectors.groupingBy(wizyta -> wizyta.getData().getYear(), Collectors.summingInt(ign -> 1)));
    }

    private static List<Integer> znajdzLataZNajwiekszaLiczbaWizyt(Map<Integer, Integer> rokZliczenia) {
        int maxLiczbaWizyt = rokZliczenia.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1);

        return rokZliczenia.entrySet().stream()
                .filter(entry -> entry.getValue() == maxLiczbaWizyt)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
