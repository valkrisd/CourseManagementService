package com.niiazov.coursemanagement.services;

import com.niiazov.coursemanagement.dto.LessonDTO;
import com.niiazov.coursemanagement.enums.CourseStatus;
import com.niiazov.coursemanagement.mappers.LessonMapper;
import com.niiazov.coursemanagement.models.Course;
import com.niiazov.coursemanagement.models.Lesson;
import com.niiazov.coursemanagement.repositories.CourseRepository;
import com.niiazov.coursemanagement.repositories.LessonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class LessonsServiceTests {

    @Autowired
    private LessonsService lessonsService;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private CourseRepository courseRepository;
    @MockBean
    private LessonMapper lessonMapper;
    @MockBean
    private CoursesService coursesService;

    private Course course;
    private Lesson lesson;
    private LessonDTO lessonDTO;
    private Integer courseId;

    @BeforeEach
    void setup() {

        courseId = 1;

        lessonDTO = LessonDTO.builder()
                .title("Test lesson")
                .description("Test description")
                .orderIndex(1)
                .build();

        lesson = Lesson.builder()
                .id(1)
                .title("Test lesson")
                .description("Test description")
                .orderIndex(1)
                .build();

        lessonRepository.save(lesson);

        course = Course.builder()
                .id(1)
                .title("Test course")
                .description("Test description")
                .author("Test author")
                .duration(10)
                .courseStatus(CourseStatus.ACTIVE)
                .price(BigDecimal.valueOf(99.99))
                .startDate(LocalDate.of(2023, 1, 1))
                .endDate(LocalDate.of(2023, 12, 31))
                .lessons(new HashSet<>(Set.of(lesson)))
                .build();

        courseRepository.save(course);
    }

    @Test
    void gettingAllLessons_storesInCache() {

        when(coursesService.getCourse(courseId)).thenReturn(course);
        when(lessonMapper.toEntity(lessonDTO)).thenReturn(lesson);

        Set<LessonDTO> lessons = lessonsService.getAllLessons(courseId);
        assertNotNull(lessons);

        Set<LessonDTO> cachedLessons = lessonsService.getAllLessons(courseId);
        assertSame(lessons, cachedLessons);

        verify(coursesService, times(1)).getCourse(courseId);
    }

    @Test
    void creatingLesson_storesInCache() {

        when(coursesService.getCourse(courseId)).thenReturn(course);
        when(lessonMapper.toEntity(lessonDTO)).thenReturn(lesson);

        Lesson createdLesson = lessonsService.createLesson(lessonDTO, courseId);
        Lesson cachedLesson = lessonsService.getLesson(courseId, lesson.getId());

        assertNotNull(cachedLesson);
        assertSame(createdLesson, cachedLesson);
        verify(coursesService, times(1)).getCourse(courseId);
        verify(lessonMapper, times(1)).toEntity(lessonDTO);
    }

}
