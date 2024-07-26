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

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentsService {
    private final CoursesService coursesService;
    private final EnrollmentMapper enrollmentMapper;

    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public void createEnrollment(Integer courseId, EnrollmentDTO enrollmentDTO) {
        Course courseToUpdate = coursesService.getCourse(courseId);
        Enrollment enrollment = enrollmentMapper.toEntity(enrollmentDTO);

        enrollment.setCourse(courseToUpdate);
        courseToUpdate.getEnrollments().add(enrollment);

        enrollmentRepository.save(enrollment);
    }

    public Set<EnrollmentDTO> getEnrollmentsByUserId(Integer userId) {

        if (userId == null || userId < 0) {
            throw new IllegalArgumentException("User id cannot be empty or less than 0");
        }

        Set<Enrollment> enrollments = enrollmentRepository.getEnrollmentsByUserId(userId);
        return enrollments.stream().map(enrollmentMapper::toDTO).collect(Collectors.toSet());
    }

    public Set<EnrollmentDTO> getEnrollmentsByCourseId(Integer courseId) {

        if (courseId == null || courseId < 0) {
            throw new IllegalArgumentException("Course id cannot be empty or less than 0");
        }

        Set<Enrollment> enrollments = enrollmentRepository.getEnrollmentsByCourseId(courseId);
        return enrollments.stream().map(enrollmentMapper::toDTO).collect(Collectors.toSet());
    }

    @Transactional
    public void updateEnrollment(Integer enrollmentId, EnrollmentDTO enrollmentDTO) {
        enrollmentRepository.findById(enrollmentId).map(existingEnrollment -> {
            existingEnrollment.setStatus(enrollmentDTO.getStatus());
            existingEnrollment.setCompleted(enrollmentDTO.getCompleted());
            existingEnrollment.setEnrollmentDate(enrollmentDTO.getEnrollmentDate());

            return enrollmentRepository.save(existingEnrollment);
        }).orElseThrow(() -> new ResourceNotFoundException("Enrollment with id " + enrollmentId + " does not exist"));
    }

    @Transactional
    public void deleteEnrollment(Integer enrollmentId) {
        Enrollment enrollmentToDelete = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Enrollment with id " + enrollmentId + " does not exist"));
        enrollmentRepository.delete(enrollmentToDelete);
    }
}
