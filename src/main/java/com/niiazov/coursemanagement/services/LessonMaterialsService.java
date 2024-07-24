package com.niiazov.coursemanagement.services;

import com.niiazov.coursemanagement.dto.LessonMaterialDTO;
import com.niiazov.coursemanagement.exceptions.ResourceNotFoundException;
import com.niiazov.coursemanagement.mappers.LessonMaterialMapper;
import com.niiazov.coursemanagement.models.Lesson;
import com.niiazov.coursemanagement.models.LessonMaterial;
import com.niiazov.coursemanagement.repositories.LessonMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonMaterialsService {

    private final LessonsService lessonsService;
    private final LessonMaterialMapper lessonMaterialMapper;
    private final LessonMaterialRepository lessonMaterialRepository;

    @Transactional
    public void addLessonMaterial(Integer courseId,
                                  Integer lessonId,
                                  LessonMaterialDTO lessonMaterialDTO) {

        Lesson lesson = lessonsService.getLesson(courseId, lessonId);
        LessonMaterial lessonMaterial = lessonMaterialMapper.toEntity(lessonMaterialDTO);

        lesson.getLessonMaterials().add(lessonMaterial);
        lesson.setUpdatedAt(LocalDateTime.now());

        lessonMaterial.setLesson(lesson);

        lessonMaterialRepository.save(lessonMaterial);
    }

    public Set<LessonMaterialDTO> getLessonMaterials(Integer lessonId,
                                                     Integer courseId) {

        Lesson lesson = lessonsService.getLesson(courseId, lessonId);
        return lesson.getLessonMaterials().stream()
                .map(lessonMaterialMapper::toDTO)
                .collect(Collectors.toSet());
    }

    public LessonMaterialDTO getLessonMaterialDTO(Integer lessonId,
                                                  Integer courseId,
                                                  Integer lessonMaterialId) {

        Lesson lesson = lessonsService.getLesson(courseId, lessonId);

        return lesson.getLessonMaterials().stream()
                .filter(lessonMaterial -> lessonMaterial.getId().equals(lessonMaterialId))
                .findFirst()
                .map(lessonMaterialMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Lesson material with id " + lessonMaterialId + " not found."));
    }

    public LessonMaterial getLessonMaterial(Integer lessonId,
                                            Integer courseId,
                                            Integer lessonMaterialId) {

        Lesson lesson = lessonsService.getLesson(courseId, lessonId);

        return lesson.getLessonMaterials().stream()
                .filter(lessonMaterial -> lessonMaterial.getId().equals(lessonMaterialId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Lesson material with id " + lessonMaterialId + " not found."));
    }

    @Transactional
    public void updateLessonMaterial(Integer lessonId,
                                     Integer courseId,
                                     Integer lessonMaterialId,
                                     LessonMaterialDTO lessonMaterialDTO) {

        LessonMaterial lessonMaterialToUpdate = getLessonMaterial(lessonId, courseId, lessonMaterialId);

        lessonMaterialToUpdate.setTitle(lessonMaterialDTO.getTitle());
        lessonMaterialToUpdate.setLessonMaterialType(lessonMaterialDTO.getLessonMaterialType());
        lessonMaterialToUpdate.setDescription(lessonMaterialDTO.getDescription());
        lessonMaterialToUpdate.setFileUrl(lessonMaterialDTO.getFileUrl());
        lessonMaterialToUpdate.setUpdatedAt(LocalDateTime.now());

        lessonMaterialRepository.save(lessonMaterialToUpdate);
    }

    @Transactional
    public void deleteLessonMaterial(Integer lessonId,
                                     Integer courseId,
                                     Integer lessonMaterialId) {

        LessonMaterial lessonMaterialToDelete = getLessonMaterial(lessonId, courseId, lessonMaterialId);
        Lesson lesson = lessonMaterialToDelete.getLesson();

        lesson.getLessonMaterials().remove(lessonMaterialToDelete);
        lesson.setUpdatedAt(LocalDateTime.now());

        lessonMaterialRepository.delete(lessonMaterialToDelete);
    }
}
