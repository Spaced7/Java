package task2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;


public class Visit {
    private Doctor doctor;
    private Patient patient;
    private LocalDate date;

    private static List<Visit> visits = new ArrayList<>();

    public Visit(Doctor doctor, Patient patient, LocalDate visitDate) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = visitDate;
        doctor.getVisits().add(this);
        patient.getVisits().add(this);
    }

    public static void readVisitsFromFile(String fileName, List<Doctor> doctorsList, List<Patient> patientsList) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-M-d");
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");
                int doctorId = Integer.parseInt(data[0]);
                int patientId = Integer.parseInt(data[1]);

                Doctor doctor = findDoctorById(doctorId, doctorsList);
                Patient patient = findPatientById(patientId, patientsList);

                if (doctor != null && patient != null) {
                    try {
                        LocalDate visitDate = LocalDate.parse(data[2], dateFormatter);
                        Visit visit = new Visit(doctor, patient, visitDate);
                        visits.add(visit);
                    } catch (DateTimeParseException e) {
                        System.err.println("Błąd parsowania daty w wierszu: " + line);
                    }
                }
            }
        }
    }

    public static List<Map.Entry<Integer, Long>> mostPopularYear(List<Visit> visits, int countOfYears) {
        List<Visit> validVisits = Optional.ofNullable(visits)
                .orElseGet(Collections::emptyList);

        Map<Integer, Long> visitsByYear = validVisits.stream()
                .collect(Collectors.groupingBy(
                        visit -> visit.getDate().getYear(),
                        Collectors.counting()
                ));

        List<Map.Entry<Integer, Long>> sortedByVisit = visitsByYear.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue(Comparator.reverseOrder()))
                .limit(countOfYears)
                .toList();

        return sortedByVisit;
    }

    private static Doctor findDoctorById(int id, List<Doctor> doctorsList) {
        return doctorsList.stream().filter(doctor -> doctor.getId() == id).findFirst().orElse(null);
    }

    private static Patient findPatientById(int id, List<Patient> patientsList) {
        return patientsList.stream().filter(patient -> patient.getId() == id).findFirst().orElse(null);
    }


    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public static List<Visit> getVisits() {
        return visits;
    }

    public static void setVisits(List<Visit> visits) {
        Visit.visits = visits;
    }
}