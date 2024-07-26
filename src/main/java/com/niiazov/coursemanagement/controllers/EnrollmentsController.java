package com.niiazov.coursemanagement.controllers;

import com.niiazov.coursemanagement.dto.EnrollmentDTO;
import com.niiazov.coursemanagement.exceptions.ResourceNotCreatedException;
import com.niiazov.coursemanagement.exceptions.ResourceNotUpdatedException;
import com.niiazov.coursemanagement.services.EnrollmentsService;
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
@RequestMapping("/enrollments")
public class EnrollmentsController {
    private final EnrollmentsService enrollmentsService;

    @PostMapping
    public ResponseEntity<HttpStatus> createEnrollment(@RequestBody @Valid EnrollmentDTO enrollmentDTO,
                                                       BindingResult bindingResult) {

        log.info("Попытка создания новой записи на курс: {}", enrollmentDTO);
        if (bindingResult.hasErrors()) {
            String errorMsg = ErrorsUtil.getErrorMessage(bindingResult);
            log.error("Ошибка валидации при создании записи на курс: {}: {}", enrollmentDTO, errorMsg);
            throw new ResourceNotCreatedException(errorMsg);
        }

        enrollmentsService.createEnrollment(enrollmentDTO);
        log.info("Запись на курс успешно создана: {}", enrollmentDTO);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Set<EnrollmentDTO>> getEnrollmentsByUserId(@PathVariable Integer userId) {

        log.info("Запрос списка всех записей для пользователя с id: {}", userId);
        Set<EnrollmentDTO> enrollmentDTOs = enrollmentsService.getEnrollmentsByUserId(userId);
        log.info("Список всех записей для пользователя с id: {} успешно получен", userId);

        return ResponseEntity.ok(enrollmentDTOs);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Set<EnrollmentDTO>> getEnrollmentsByCourseId(@PathVariable Integer courseId) {

        log.info("Запрос списка всех пользователей на курсе с id: {}", courseId);
        Set<EnrollmentDTO> enrollmentDTOs = enrollmentsService.getEnrollmentsByCourseId(courseId);
        log.info("Список всех пользователей на курсе с id: {} успешно получен", courseId);

        return ResponseEntity.ok(enrollmentDTOs);
    }

    @PutMapping("/{enrollmentId}")
    public ResponseEntity<HttpStatus> updateEnrollment(@PathVariable Integer enrollmentId,
                                                       @RequestBody @Valid EnrollmentDTO enrollmentDTO,
                                                       BindingResult bindingResult) {
        log.info("Обновление данных о записи с id: {}", enrollmentId);
        if (bindingResult.hasErrors()) {
            String errorMsg = ErrorsUtil.getErrorMessage(bindingResult);
            log.error("Ошибка валидации при обновлении записи с id: {}: {}", enrollmentId, errorMsg);
            throw new ResourceNotUpdatedException(errorMsg);
        }

        enrollmentsService.updateEnrollment(enrollmentId, enrollmentDTO);
        log.info("Данные о записи с id: {} успешно обновлены", enrollmentId);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{enrollmentId}")
    public ResponseEntity<HttpStatus> deleteEnrollment(@PathVariable Integer enrollmentId) {
        log.info("Удаление записи с id: {}", enrollmentId);
        enrollmentsService.deleteEnrollment(enrollmentId);
        log.info("Запись с id: {} успешно удалена", enrollmentId);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
