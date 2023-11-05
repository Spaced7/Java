package task2;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class Doctor {
    private int id;
    private String surname;
    private String firstName;
    private String specialization;
    private LocalDate birthDate;
    private String nip;
    private String pesel;
    private static List<Doctor> doctors = new ArrayList<>();
    private List<Visit> visits = new ArrayList<>();

    public Doctor(int id, String surname, String firstName, String specialization, LocalDate birthDate, String nip, String pesel) {

        if (id < 0) {
            throw new IllegalArgumentException("Id lekarza nie może być ujemne.");
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data urodzenia nie może być z przyszłości.");
        }

        this.id = id;
        this.surname = surname;
        this.firstName = firstName;
        this.specialization = specialization;
        this.birthDate = birthDate;
        this.nip = nip;
        this.pesel = pesel;
        doctors.add(this);
    }

    public static List<Doctor> readDoctorsFromFile(String fileName) throws IOException {
        List<Doctor> doctorsList = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-M-d");


        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");
                int id = Integer.parseInt(data[0]);
                String surname = data[1];
                String firstName = data[2];
                String specialization = data[3];
                LocalDate birthDate = LocalDate.parse(data[4], dateFormatter);
                String nip = data[5];
                String pesel = data[6];

                Doctor doctor = new Doctor(id, surname, firstName, specialization, birthDate, nip, pesel);
                doctorsList.add(doctor);
            }
        }
        return doctorsList;
    }
    public static List<Doctor> mostPopularDoctors(List<Doctor>doctors, int countOfDoctors){
        return doctors.stream()
                .sorted(Comparator.comparing(Doctor::countOfVisits).reversed())
                .limit(countOfDoctors)
                .collect(Collectors.toList());
    }

    public static String mostPopularSpecialization(List<Doctor> doctors) {
        Map<String, Integer> specializationCounts = doctors.stream()
                .collect(Collectors.groupingBy(
                        Doctor::getSpecialization,
                        Collectors.summingInt(doctor -> doctor.countOfVisits())
                ));

        return specializationCounts.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);

    }
    public static List<Doctor> getOldestDoctors(List<Doctor> doctors, int x) {
        return doctors.stream()
                .sorted(Comparator.comparing(Doctor::getBirthDate))
                .limit(x)
                .collect(Collectors.toList());
    }
    public static Map<Doctor, List<Integer>> exclusiveDoctors(List<Doctor> doctors, int countOfPatients) {
        return doctors.stream()
                .filter(doctor -> doctor.countOfVisits() == countOfPatients)
                .collect(Collectors.toMap(
                        doctor -> doctor,
                        doctor -> doctor.getVisits().stream()
                                .map(visit -> visit.getPatient().getId())
                                .collect(Collectors.toList())
                ));
    }


    public int countOfVisits() {
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public static void setDoctors(List<Doctor> doctors) {
        Doctor.doctors = doctors;
    }

    public static List<Doctor> getDoctors() {
        return doctors;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", firstName='" + firstName + '\'' +
                ", specialization='" + specialization + '\'' +
                ", birthDate=" + birthDate +
                ", nip='" + nip + '\'' +
                ", pesel='" + pesel + '\'' +
                ", count od visits=" + visits.size() + '\'' +
                '}';
    }
}