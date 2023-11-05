package task2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class Patient {
    private int id;
    private String surname;
    private String firstName;
    private String pesel;
    private LocalDate birthDate;
    private static List<Patient> patientsList = new ArrayList<>();
    private List<Visit> visits = new ArrayList<>();


    public Patient(int id, String surname, String firstName, String pesel, LocalDate birthDate) {
        if (id < 0) {
            throw new IllegalArgumentException("Id pacjenta nie może być ujemne.");
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data urodzenia nie może być z przyszłości.");
        }

        this.id = id;
        this.surname = surname;
        this.firstName = firstName;
        this.pesel = pesel;
        this.birthDate = birthDate;
        patientsList.add(this);
    }

    public static List<Patient> readPatientsFromFile(String fileName) throws IOException {
        List<Patient> patientsList = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");
                int id = Integer.parseInt(data[0]);
                String surname = data[1];
                String firstName = data[2];
                String pesel = data[3];
                LocalDate birthDate = LocalDate.parse(data[4], dateFormatter);

                Patient patient = new Patient(id, surname, firstName, pesel, birthDate);
                patientsList.add(patient);
            }
        }
        return patientsList;
    }


    public static List<Patient> patientsWithMostVisits(List<Patient> patients) {
        int maxVisits = patients.stream()
                .mapToInt(Patient::countOfVisits)
                .max()
                .orElse(0);

        return patients.stream()
                .filter(patient -> patient.countOfVisits() == maxVisits)
                .collect(Collectors.toList());
    }
    public static List<Patient> patientsWihXDifferentDoc(List<Patient>patients, int countOfDoctors){
        return Optional.ofNullable(patients)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .filter(p->p.getVisits().stream()
                        .map(m->m.getDoctor())
                        .distinct()
                        .count()>countOfDoctors)
                .toList();

    }

public int countOfVisits(){
        return visits.size();
}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public static List<Patient> getPatientsList() {
        return patientsList;
    }

    public static void setPatientsList(List<Patient> patientsList) {
        Patient.patientsList = patientsList;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", firstName='" + firstName + '\'' +
                ", pesel='" + pesel + '\'' +
                ", birthDate=" + birthDate +
                ", visits=" + visits +
                '}';
    }
}