package com.niiazov.coursemanagement.controllers;

import com.niiazov.coursemanagement.dto.EnrollmentDTO;
import com.niiazov.coursemanagement.services.EnrollmentsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/enrollments")
public class EnrollmentsController {
    private final EnrollmentsService enrollmentsService;

    @PostMapping
    public ResponseEntity<HttpStatus> createEnrollment(@RequestBody @Valid EnrollmentDTO enrollmentDTO) {

        log.info("Попытка создания новой записи на курс: {}", enrollmentDTO);
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
                                                       @RequestBody @Valid EnrollmentDTO enrollmentDTO) {
        log.info("Обновление данных о записи с id: {}", enrollmentId);
        enrollmentsService.updateEnrollment(enrollmentId, enrollmentDTO);
        log.info("Данные о записи с id: {} успешно обновлены", enrollmentId);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{enrollmentId}/user/{userId}")
    public ResponseEntity<HttpStatus> deleteEnrollment(@PathVariable Integer enrollmentId, @PathVariable Integer userId) {
        log.info("Удаление записи на курс с id: {} для пользователя  с id: {}", enrollmentId, userId);
        enrollmentsService.deleteEnrollment(enrollmentId, userId);
        log.info("Запись на курс с id: {} для пользователя с id: {} успешно удалена", enrollmentId, userId);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
