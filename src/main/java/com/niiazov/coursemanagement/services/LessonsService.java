package com.niiazov.coursemanagement.services;

import com.niiazov.coursemanagement.dto.LessonDTO;
import com.niiazov.coursemanagement.exceptions.EntityRelationException;
import com.niiazov.coursemanagement.exceptions.ResourceNotFoundException;
import com.niiazov.coursemanagement.mappers.LessonMapper;
import com.niiazov.coursemanagement.models.Course;
import com.niiazov.coursemanagement.models.Lesson;
import com.niiazov.coursemanagement.repositories.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonsService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final CoursesService coursesService;

    @Transactional
    public void createLesson(LessonDTO lessonDTO,
                             Integer courseId) {

        Course courseToUpdate = coursesService.getCourse(courseId);
        Lesson lesson = lessonMapper.toEntity(lessonDTO);
        lesson.setCourse(courseToUpdate);

        courseToUpdate.getLessons().add(lesson);
        courseToUpdate.setUpdatedAt(LocalDateTime.now());

        lessonRepository.save(lesson);
    }

    public Set<LessonDTO> getAllLessons(Integer courseId) {

        Course course = coursesService.getCourse(courseId);

        Set<Lesson> lessons = new HashSet<>(course.getLessons());
        return lessons.stream().map(lessonMapper::toDTO).collect(Collectors.toSet());
    }

    public LessonDTO getLessonDTO(Integer courseId,
                                  Integer lessonId) {

        Course course = coursesService.getCourse(courseId);

        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() ->
                new ResourceNotFoundException("Lesson with id " + lessonId + " not found."));

        if (!course.getId().equals(lesson.getCourse().getId())) {
            throw new EntityRelationException
                    ("Lesson with id " + lessonId + " does not belong to course " + courseId);
        }

        return lessonMapper.toDTO(lesson);
    }

    public Lesson getLesson(Integer courseId,
                            Integer lessonId) {

        Course course = coursesService.getCourse(courseId);

        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() ->
                new ResourceNotFoundException("Lesson with id " + lessonId + " not found."));

        if (!course.getId().equals(lesson.getCourse().getId())) {
            throw new EntityRelationException
                    ("Lesson with id " + lessonId + " does not belong to course " + courseId);
        }

        return lesson;
    }

    @Transactional
    public void updateLesson(Integer courseId,
                             Integer lessonId,
                             LessonDTO lessonDTO) {

        Lesson lesson = getLesson(courseId, lessonId);

        lesson.setTitle(lessonDTO.getTitle());
        lesson.setDescription(lessonDTO.getDescription());
        lesson.setOrderIndex(lessonDTO.getOrderIndex());
        lesson.setUpdatedAt(LocalDateTime.now());

        lessonRepository.save(lesson);
    }

    @Transactional
    public void deleteLesson(Integer courseId,
                             Integer lessonId) {

        Lesson lesson = getLesson(courseId, lessonId);
        lessonRepository.delete(lesson);
    }
}
