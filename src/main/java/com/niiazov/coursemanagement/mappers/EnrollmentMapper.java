package com.niiazov.coursemanagement.mappers;

import com.niiazov.coursemanagement.dto.EnrollmentDTO;
import com.niiazov.coursemanagement.models.Enrollment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    Enrollment toEntity(EnrollmentDTO enrollmentDTO);

    EnrollmentDTO toDTO(Enrollment enrollment);

}
