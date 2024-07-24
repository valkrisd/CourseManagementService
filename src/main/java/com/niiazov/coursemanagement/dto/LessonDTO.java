package com.niiazov.coursemanagement.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LessonDTO {

    @Size(max = 255, message = "Title cannot be longer than 255 characters")
    @NotEmpty(message = "Title cannot be empty")
    private String title;

    private String description;

    private Integer orderIndex;

}
