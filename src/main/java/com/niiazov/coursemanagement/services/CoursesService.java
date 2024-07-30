package com.niiazov.coursemanagement.services;

import com.niiazov.coursemanagement.dto.CourseDTO;
import com.niiazov.coursemanagement.exceptions.ResourceNotFoundException;
import com.niiazov.coursemanagement.mappers.CourseMapper;
import com.niiazov.coursemanagement.models.Course;
import com.niiazov.coursemanagement.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoursesService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Transactional
    public void createCourse(CourseDTO courseDTO) {

        Course course = courseMapper.toEntity(courseDTO);
        courseRepository.save(course);
    }

    public Set<CourseDTO> getAllCourses() {

        Set<Course> courses = new HashSet<>(courseRepository.findAll());
        return courses.stream().map(courseMapper::toDTO).collect(Collectors.toSet());
    }

    public CourseDTO getCourseDTO(Integer courseId) {

        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isPresent()) {
            return courseMapper.toDTO(course.get());
        } else throw new ResourceNotFoundException("Course with id " + courseId + " not found");
    }

    public Course getCourse(Integer courseId) {

        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isPresent()) return course.get();
        else throw new ResourceNotFoundException("Course with id " + courseId + " not found");
    }

    @Transactional
    public void updateCourse(Integer courseId,
                             CourseDTO courseDTO) {

        Course course = courseMapper.toEntity(courseDTO);
        Course courseToUpdate = getCourse(courseId);

        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setDescription(course.getDescription());
        courseToUpdate.setAuthor(course.getAuthor());
        courseToUpdate.setDuration(course.getDuration());
        courseToUpdate.setCourseStatus(course.getCourseStatus());
        courseToUpdate.setPrice(course.getPrice());
        courseToUpdate.setStartDate(course.getStartDate());
        courseToUpdate.setEndDate(course.getEndDate());

        courseRepository.save(courseToUpdate);
    }

    @Transactional
    public void deleteCourse(Integer courseId) {

        Course courseToDelete = getCourse(courseId);
        courseRepository.delete(courseToDelete);
    }
}
