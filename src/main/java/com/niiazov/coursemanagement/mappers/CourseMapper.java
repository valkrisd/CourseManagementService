package com.niiazov.coursemanagement.mappers;

import com.niiazov.coursemanagement.dto.CourseDTO;
import com.niiazov.coursemanagement.models.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDTO toDTO(Course course);

    Course toEntity(CourseDTO courseDTO);
}
