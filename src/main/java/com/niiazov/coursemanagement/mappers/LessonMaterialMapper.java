package com.niiazov.coursemanagement.mappers;

import com.niiazov.coursemanagement.dto.LessonMaterialDTO;
import com.niiazov.coursemanagement.models.LessonMaterial;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonMaterialMapper {

    LessonMaterial toEntity(LessonMaterialDTO lessonMaterialDTO);

    LessonMaterialDTO toDTO(LessonMaterial lessonMaterial);
}
