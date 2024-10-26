package org.example.entities;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class Enrollment {

    @Id
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Course course;

    @Column(name = "enrollment_date") // if spring.jpa.hibernate.ddl-auto=update not used, then we have to specify the col name explicitly
    private LocalDate enrollmentDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                ", student=" + student +
                ", course=" + course +
                ", enrollmentDate=" + enrollmentDate +
                '}';
    }
}