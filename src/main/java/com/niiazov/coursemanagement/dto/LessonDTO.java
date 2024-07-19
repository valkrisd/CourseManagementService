package com.niiazov.coursemanagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LessonDTO {

    @Size(max = 255, message = "Title cannot be longer than 255 characters")
    @NotNull
    private String title;

    private String description;

    private Integer orderIndex;

    // TODO: find out how it should be implemented
    private String materials;


}
