package com.niiazov.coursemanagement.mappers;

import com.niiazov.coursemanagement.dto.LessonDTO;
import com.niiazov.coursemanagement.models.Lesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    Lesson toEntity(LessonDTO lessonDTO);

    LessonDTO toDTO(Lesson lesson);
}
