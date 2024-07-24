package com.niiazov.coursemanagement.controllers;

import com.niiazov.coursemanagement.dto.LessonDTO;
import com.niiazov.coursemanagement.exceptions.ResourceNotCreatedException;
import com.niiazov.coursemanagement.exceptions.ResourceNotUpdatedException;
import com.niiazov.coursemanagement.services.LessonsService;
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
public class LessonsController {

    private final LessonsService lessonsService;

    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<HttpStatus> createLesson(@PathVariable Integer courseId,
                                                   @RequestBody @Valid LessonDTO lessonDTO,
                                                   BindingResult bindingResult) {

        log.info("Попытка создания нового урока в курсе с id: {}", courseId);
        if (bindingResult.hasErrors()) {
            String errorMsg = ErrorsUtil.getErrorMessage(bindingResult);
            log.error("Ошибка валидации при создании урока: {}", errorMsg);
            throw new ResourceNotCreatedException(errorMsg);
        }

        lessonsService.createLesson(lessonDTO, courseId);
        log.info("Урок в курсе с id: {} успешно создан: {}", courseId, lessonDTO);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{courseId}/lessons")
    public ResponseEntity<Set<LessonDTO>> getLessons(@PathVariable Integer courseId) {

        log.info("Запрос списка уроков в курсе с id: {}", courseId);
        Set<LessonDTO> lessonDTOs = lessonsService.getAllLessons(courseId);
        log.info("Список уроков в курсе с id: {} успешно получен", courseId);

        return ResponseEntity.ok(lessonDTOs);
    }

    @GetMapping("/{courseId}/lessons/{lessonId}")
    public ResponseEntity<LessonDTO> getLesson(@PathVariable Integer courseId,
                                               @PathVariable Integer lessonId) {
        log.info("Запрос данных о уроке с id: {} в курсе с id: {}", lessonId, courseId);
        LessonDTO lessonDTO = lessonsService.getLessonDTO(courseId, lessonId);
        log.info("Урок с id: {} в курсе с id: {} успешно найден", lessonId, courseId);

        return ResponseEntity.ok(lessonDTO);
    }

    @PutMapping("/{courseId}/lessons/{lessonId}")
    public ResponseEntity<HttpStatus> updateLesson(@PathVariable Integer courseId,
                                                   @PathVariable Integer lessonId,
                                                   @RequestBody @Valid LessonDTO lessonDTO,
                                                   BindingResult bindingResult) {
        log.info("Обновление данных об уроке с id: {} в курсе с id: {}", lessonId, courseId);
        if (bindingResult.hasErrors()) {
            String errorMsg = ErrorsUtil.getErrorMessage(bindingResult);
            log.error("Ошибка валидации при обновлении урока с id: {}: {}", lessonId, errorMsg);
            throw new ResourceNotUpdatedException(errorMsg);
        }

        lessonsService.updateLesson(courseId, lessonId, lessonDTO);
        log.info("Данные урока с id: {} в курсе с id: {} успешно обновлены", lessonId, courseId);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}/lessons/{lessonId}")
    public ResponseEntity<HttpStatus> deleteLesson(@PathVariable Integer courseId,
                                                   @PathVariable Integer lessonId) {
        log.info("Удаление урока с id: {} в курсе с id: {}", lessonId, courseId);
        lessonsService.deleteLesson(courseId, lessonId);
        log.info("Урок с id: {} в курсе с id: {} успешно удален", lessonId, courseId);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
