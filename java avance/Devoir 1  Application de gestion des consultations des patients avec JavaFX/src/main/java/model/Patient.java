package model;

import java.time.LocalDate;

public class Patient {
    private String name;
    private int age;
    private LocalDate consultationDate;

    public Patient() {}

    public Patient(String name, int age, LocalDate consultationDate) {
        this.name = name;
        this.age = age;
        this.consultationDate = consultationDate;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public LocalDate getConsultationDate() { return consultationDate; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setConsultationDate(LocalDate consultationDate) { this.consultationDate = consultationDate; }

    @Override
    public String toString() {
        return name + " | Âge: " + age + " | Date: " + consultationDate;
    }
}