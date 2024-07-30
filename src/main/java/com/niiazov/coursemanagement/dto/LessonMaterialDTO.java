package com.niiazov.coursemanagement.dto;

import com.niiazov.coursemanagement.enums.LessonMaterialType;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LessonMaterialDTO {

    private LessonMaterialType lessonMaterialType;

    @Size(max = 255, message = "Title cannot be longer than 255 characters")
    private String title;

    private String description;

    @Size(max = 255, message = "File URL cannot be longer than 255 characters")
    private String fileUrl;
}
