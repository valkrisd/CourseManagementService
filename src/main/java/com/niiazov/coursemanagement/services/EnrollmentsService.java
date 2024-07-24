package com.niiazov.coursemanagement.services;

import com.niiazov.coursemanagement.dto.EnrollmentDTO;
import com.niiazov.coursemanagement.exceptions.ResourceNotFoundException;
import com.niiazov.coursemanagement.mappers.EnrollmentMapper;
import com.niiazov.coursemanagement.models.Course;
import com.niiazov.coursemanagement.models.Enrollment;
import com.niiazov.coursemanagement.repositories.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentsService {
    private final CoursesService coursesService;
    private final EnrollmentMapper enrollmentMapper;

    private final EnrollmentRepository enrollmentRepository;

    public void createEnrollment(EnrollmentDTO enrollmentDTO) {
        Course courseToUpdate = coursesService.getCourse(enrollmentDTO.getUserId());
        Enrollment enrollment = enrollmentMapper.toEntity(enrollmentDTO);
        enrollment.setCourse(courseToUpdate);

        courseToUpdate.getEnrollments().add(enrollment);
        courseToUpdate.setUpdatedAt(LocalDateTime.now());

        enrollmentRepository.save(enrollment);
    }

    public Set<EnrollmentDTO> getEnrollmentsByUserId(Integer userId) {

        if (userId == null || userId < 0) {
            throw new IllegalArgumentException("User id cannot be empty or less than 0");
        }

        Set<Enrollment> enrollments = enrollmentRepository.getEnrollmentsByUserId(userId);

        if (enrollments.isEmpty()) {
            throw new ResourceNotFoundException("User with id " + userId + " has no enrollments");
        } else return enrollments.stream().map(enrollmentMapper::toDTO).collect(Collectors.toSet());
    }

    public Set<EnrollmentDTO> getEnrollmentsByCourseId(Integer courseId) {

        if (courseId == null || courseId < 0) {
            throw new IllegalArgumentException("Course id cannot be empty or less than 0");
        }

        Set<Enrollment> enrollments = enrollmentRepository.getEnrollmentsByCourseId(courseId);

        if (enrollments.isEmpty()) {
            throw new ResourceNotFoundException("There are no enrollments for course with id " + courseId);
        } else return enrollments.stream().map(enrollmentMapper::toDTO).collect(Collectors.toSet());
    }

    @Transactional
    public void updateEnrollment(Integer enrollmentId,
                                 EnrollmentDTO enrollmentDTO) {

        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(enrollmentId);

        if (optionalEnrollment.isEmpty()) {
            throw new ResourceNotFoundException("Enrollment with id " + enrollmentId + " does not exist");
        }

        Enrollment enrollmentToUpdate = optionalEnrollment.get();

        enrollmentToUpdate.setStatus(enrollmentDTO.getStatus());
        enrollmentToUpdate.setCompleted(enrollmentDTO.getCompleted());
        enrollmentToUpdate.setEnrollmentDate(enrollmentDTO.getEnrollmentDate());
        enrollmentToUpdate.setUpdatedAt(LocalDateTime.now());

        enrollmentRepository.save(enrollmentToUpdate);
    }

    @Transactional
    public void deleteEnrollment(Integer enrollmentId) {

        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(enrollmentId);

        if (optionalEnrollment.isEmpty()) {
            throw new ResourceNotFoundException("Enrollment with id " + enrollmentId + " does not exist");
        }

        Enrollment enrollmentToDelete = optionalEnrollment.get();
        enrollmentRepository.delete(enrollmentToDelete);
    }
}
