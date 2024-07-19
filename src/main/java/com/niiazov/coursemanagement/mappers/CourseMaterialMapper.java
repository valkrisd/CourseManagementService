package com.niiazov.coursemanagement.mappers;

import com.niiazov.coursemanagement.dto.CourseMaterialDTO;
import com.niiazov.coursemanagement.models.CourseMaterial;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMaterialMapper {

    CourseMaterial toEntity(CourseMaterialDTO courseMaterialDTO);

    CourseMaterialDTO toDTO(CourseMaterial courseMaterial);
}
