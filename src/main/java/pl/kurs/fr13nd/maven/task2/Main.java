package task2;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) {

        try {
            List<Doctor> doctorsList = Doctor.readDoctorsFromFile("lekarze.txt");
            List<Patient> patientsList = Patient.readPatientsFromFile("pacjenci.txt");
            Visit.readVisitsFromFile("wizyty.txt", doctorsList, patientsList);
        } catch (IOException e) {
            System.err.println("Błąd odczytu plików: " + e.getMessage());
        }

        List<Doctor> doctorsList = Doctor.getDoctors();
        List<Patient> patientList = Patient.getPatientsList();
        List<Visit> visitList = Visit.getVisits();

        System.out.println("Ilość doktorów: " + doctorsList.size());

        System.out.println("Ilość pac jentów: " + patientList.size());

        System.out.println("Ilość wizyt: " + visitList.size());

        System.out.println("Ilość pacjentów: " + patientList);


        System.out.println("No.01------------------------------------------------------------------------------------");
        // Znajdź lekarza, który miał najwięcej wizyt
        System.out.println("Lekarz/e z najwieksza ilością wizyt: ");

        List<Doctor>popularDoctor = Doctor.mostPopularDoctors(doctorsList,1);
        for (Doctor doctor : popularDoctor) {
            System.out.println(doctor.getFirstName() + " " + doctor.getSurname() + ", ilość wizyt: "
                    + doctor.countOfVisits());
        }


        System.out.println("No.02------------------------------------------------------------------------------------");
        // Znajdź pacjenta, który miał najwięcej wizyt
        System.out.println("Pacjent/ci z najwieksza ilością wizyt: ");

        List<Patient> patientsWithTheMostVisits = Patient.patientsWithMostVisits(patientList);
        for (Patient patient : patientsWithTheMostVisits) {
            System.out.println("Imie i nazwisko: " + patient.getFirstName() + " " + patient.getSurname() +
                    ", ilość wizyt: " + patient.countOfVisits());
        }


        System.out.println("No.03------------------------------------------------------------------------------------");
        // Znajdź specjalizację, która cieszy się największym powodzeniem (najwięcej wizyt)
        System.out.println("Najpopularniejsza specjalizacja wśród lekarzy to: ");

        String mostPopularSpecialization = Doctor.mostPopularSpecialization(doctorsList);
        System.out.println(mostPopularSpecialization);


        System.out.println("No.04------------------------------------------------------------------------------------");
        // Znajdź rok, w którym było najwięcej wizyt
        List<Map.Entry<Integer, Long>> mostPopularYear = Visit.mostPopularYear(visitList,1);
        for (Map.Entry<Integer, Long> integerLongEntry : mostPopularYear) {
            System.out.println("Rok w którym było najwięcej wizyt:  " + integerLongEntry.getKey() + ", ilość wizyt: "
            + integerLongEntry.getValue());
        }

        System.out.println("No.05------------------------------------------------------------------------------------");
        // Wypisz top 5 najstarszych lekarzy
        System.out.println("Top 5 najstarszych lekarzy: ");
        List<Doctor> topXOldestDoctors = Doctor.getOldestDoctors(doctorsList, 5);
        for (Doctor doctor : topXOldestDoctors) {
            System.out.println("Imie i nazwisko lekarza: " + doctor.getFirstName() + " " + doctor.getSurname() +
                    ", data urodzenia: " + doctor.getBirthDate());

        }

        System.out.println("No.06------------------------------------------------------------------------------------");
        // Zwroc pacjentow, ktorzy byli u minimum 5 różnych lekarzy
        System.out.println("Pacjenci ktorzy byli u 5 roznych lekarzy: ");
        List<Patient>patientWithXDifferentDoc = Patient.patientsWihXDifferentDoc(patientList,5);
        for (Patient patient : patientWithXDifferentDoc) {
            System.out.println("Imie i nazwisko: " + patient.getFirstName()+ " " + patient.getSurname());
        }

        System.out.println("No.07------------------------------------------------------------------------------------");
        // Zwroc lekarzy exclusive - czyli takich, ktorzy przyjmowali tylko jednego pacjenta
        System.out.println("Lekarze exclusive: ");

        Map<Doctor, List<Integer>> exclusiveDoctorsWithPatients = Doctor.exclusiveDoctors(doctorsList,1);
        for (Map.Entry<Doctor, List<Integer>> entry : exclusiveDoctorsWithPatients.entrySet()) {
            Doctor doctor = entry.getKey();
            List<Integer> patientIds = entry.getValue();

            System.out.println("Imie i nazwisko: " + doctor.getFirstName() + " " + doctor.getSurname() +
                    ", ilość wizyt: " + doctor.countOfVisits() +
                    ", lista pacjentów: " + patientIds);
        }

    }
}
