package com.niiazov.coursemanagement.controllers;

import com.niiazov.coursemanagement.dto.LessonMaterialDTO;
import com.niiazov.coursemanagement.exceptions.ResourceNotCreatedException;
import com.niiazov.coursemanagement.exceptions.ResourceNotUpdatedException;
import com.niiazov.coursemanagement.services.LessonMaterialsService;
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
public class LessonMaterialsController {

    private final LessonMaterialsService lessonMaterialsService;

    @PostMapping("/{courseId}/lessons/{lessonId}/materials")
    public ResponseEntity<HttpStatus> addLessonMaterial(@PathVariable Integer lessonId,
                                                        @PathVariable Integer courseId,
                                                        @Valid @RequestBody LessonMaterialDTO lessonMaterialDTO,
                                                        BindingResult bindingResult) {

        log.info("Попытка добавления нового материала к уроку с id: {}", lessonId);
        if (bindingResult.hasErrors()) {
            String errorMsg = ErrorsUtil.getErrorMessage(bindingResult);
            log.error("Ошибка валидации при добавлении материала к уроку с id: {}: {}", lessonId, errorMsg);
            throw new ResourceNotCreatedException(errorMsg);
        }

        lessonMaterialsService.addLessonMaterial(courseId, lessonId, lessonMaterialDTO);
        log.info("Новый материал к уроку с id: {} успешно добавлен", lessonId);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{courseId}/lessons/{lessonId}/materials")
    public ResponseEntity<Set<LessonMaterialDTO>> getLessonMaterials(@PathVariable Integer lessonId,
                                                                     @PathVariable Integer courseId) {

        log.info("Запрос списка материалов к уроку с id: {} в курсе с id: {}", lessonId, courseId);
        Set<LessonMaterialDTO> lessonMaterialDTOs = lessonMaterialsService.getLessonMaterials(lessonId, courseId);

        log.info("Список материалов к уроку с id: {} в курсе с id: {} успешно получен", lessonId, courseId);
        return ResponseEntity.ok(lessonMaterialDTOs);
    }

    @GetMapping("/{courseId}/lessons/{lessonId}/materials/{lessonMaterialId}")
    public ResponseEntity<LessonMaterialDTO> getLessonMaterial(@PathVariable Integer lessonId,
                                                               @PathVariable Integer courseId,
                                                               @PathVariable Integer lessonMaterialId) {

        log.info("Запрос данных о материале к уроку с id: {} в курсе с id: {}", lessonId, courseId);
        LessonMaterialDTO lessonMaterialDTO = lessonMaterialsService
                .getLessonMaterialDTO(lessonId, courseId, lessonMaterialId);

        log.info("Материал к уроку с id: {} в курсе с id: {} успешно получен", lessonId, courseId);
        return ResponseEntity.ok(lessonMaterialDTO);
    }

    @PutMapping("/{courseId}/lessons/{lessonId}/materials/{lessonMaterialId}")
    public ResponseEntity<HttpStatus> updateLessonMaterial(@PathVariable Integer lessonId,
                                                           @PathVariable Integer courseId,
                                                           @PathVariable Integer lessonMaterialId,
                                                           @Valid @RequestBody LessonMaterialDTO lessonMaterialDTO,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = ErrorsUtil.getErrorMessage(bindingResult);
            log.error("Ошибка валидации при обновлении материала к уроку с id: {}: {}", lessonId, errorMsg);
            throw new ResourceNotUpdatedException(errorMsg);
        }

        log.info("Попытка обновления материала к уроку с id: {}", lessonId);
        lessonMaterialsService.updateLessonMaterial(lessonId, courseId, lessonMaterialId, lessonMaterialDTO);

        log.info("Материал к уроку с id: {} успешно обновлен", lessonId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}/lessons/{lessonId}/materials/{lessonMaterialId}")
    public ResponseEntity<HttpStatus> deleteLessonMaterial(@PathVariable Integer lessonId,
                                                           @PathVariable Integer courseId,
                                                           @PathVariable Integer lessonMaterialId) {

        log.info("Попытка удаления материала к уроку с id: {}", lessonId);
        lessonMaterialsService.deleteLessonMaterial(lessonId, courseId, lessonMaterialId);

        log.info("Материал к уроку с id: {} успешно удален", lessonId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
