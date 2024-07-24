package com.niiazov.coursemanagement.repositories;

import com.niiazov.coursemanagement.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    Set<Enrollment> getEnrollmentsByUserId(Integer userId);

    Set<Enrollment> getEnrollmentsByCourseId(Integer courseId);
}
