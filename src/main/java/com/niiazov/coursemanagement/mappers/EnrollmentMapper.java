package com.niiazov.coursemanagement.mappers;

import com.niiazov.coursemanagement.dto.EnrollmentDTO;
import com.niiazov.coursemanagement.models.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    Enrollment toEntity(EnrollmentDTO enrollmentDTO);

    @Mapping(target = "courseDTO", source = "course")
    EnrollmentDTO toDTO(Enrollment enrollment);

}
