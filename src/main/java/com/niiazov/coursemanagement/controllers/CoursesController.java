package com.niiazov.coursemanagement.controllers;

import com.niiazov.coursemanagement.dto.CourseDTO;
import com.niiazov.coursemanagement.exceptions.ResourceNotCreatedException;
import com.niiazov.coursemanagement.exceptions.ResourceNotUpdatedException;
import com.niiazov.coursemanagement.services.CoursesService;
import com.niiazov.coursemanagement.util.ErrorsUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CoursesController {

    private final CoursesService coursesService;

    @PostMapping
    public ResponseEntity<HttpStatus> createCourse(@RequestBody @Valid CourseDTO courseDTO,
                                                   BindingResult bindingResult) {
        log.info("Попытка создания нового курса");
        if (bindingResult.hasErrors()) {
            String errorMsg = ErrorsUtil.getErrorMessage(bindingResult);
            log.error("Ошибка валидации при создании курса: {}", errorMsg);
            throw new ResourceNotCreatedException(errorMsg);
        }

        coursesService.createCourse(courseDTO);
        log.info("Курс успешно создан: {}", courseDTO);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<CourseDTO>> getCourses() {
        log.info("Запрос списка всех курсов");
        Set<CourseDTO> courseDTOs = coursesService.getAllCourses();
        log.debug("Список всех курсов успешно получен: {}", courseDTOs);

        return ResponseEntity.ok(courseDTOs);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable Integer courseId) {
        log.info("Запрос данных о курсе с id: {}", courseId);
        CourseDTO userDTO = coursesService.getCourseDTO(courseId);
        log.info("Курс с id: {} успешно найден", courseId);

        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<HttpStatus> updateCourse(@PathVariable Integer courseId,
                                                   @RequestBody @Valid CourseDTO courseDTO,
                                                   BindingResult bindingResult) {
        log.info("Обновление данных о курсе с id: {}", courseId);
        if (bindingResult.hasErrors()) {
            String errorMsg = ErrorsUtil.getErrorMessage(bindingResult);
            log.error("Ошибка валидации при обновлении курса с id: {}: {}", courseId, errorMsg);
            throw new ResourceNotUpdatedException(errorMsg);
        }

        coursesService.updateCourse(courseId, courseDTO);
        log.info("Данные курса с id: {} успешно обновлены", courseId);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable Integer courseId) {
        log.info("Удаление курса с id: {}", courseId);
        coursesService.deleteCourse(courseId);
        log.info("Курс с id: {} успешно удален", courseId);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
