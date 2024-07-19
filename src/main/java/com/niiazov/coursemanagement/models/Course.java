package com.niiazov.coursemanagement.models;

import com.niiazov.coursemanagement.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
@RequiredArgsConstructor
@Getter
@Setter
public class Course {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "author")
    private String author;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "status", length = 20)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "course")
    private Set<CourseMaterial> courseMaterials = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<Lesson> lessons = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<Enrollment> enrollments = new HashSet<>();
}
