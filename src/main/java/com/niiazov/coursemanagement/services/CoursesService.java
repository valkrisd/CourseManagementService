package com.niiazov.coursemanagement.services;

import com.niiazov.coursemanagement.dto.CourseDTO;
import com.niiazov.coursemanagement.exceptions.ResourceNotFoundException;
import com.niiazov.coursemanagement.mappers.CourseMapper;
import com.niiazov.coursemanagement.models.Course;
import com.niiazov.coursemanagement.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoursesService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Transactional
    @CachePut(value = "courses", cacheManager = "redisCacheManager", key = "#result.id")
    public Course createCourse(CourseDTO courseDTO) {

        Course course = courseMapper.toEntity(courseDTO);

        return courseRepository.save(course);
    }

    @Cacheable(value = "courses", cacheManager = "redisCacheManager", unless = "#result.isEmpty()")
    public List<CourseDTO> getAllCourses() {
        List<Course> courseList = courseRepository.findAll();
        return courseList.stream()
                .map(courseMapper::toDTO)
                .toList();
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
    @CachePut(value = "courses", cacheManager = "redisCacheManager", key = "#courseId")
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
    @CacheEvict(value = "courses", cacheManager = "redisCacheManager", key = "#courseId")
    public void deleteCourse(Integer courseId) {

        Course courseToDelete = getCourse(courseId);
        courseRepository.delete(courseToDelete);
    }
}
