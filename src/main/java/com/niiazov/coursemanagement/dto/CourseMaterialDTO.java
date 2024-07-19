package com.niiazov.coursemanagement.dto;

import com.niiazov.coursemanagement.enums.Type;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CourseMaterialDTO {

    @Size(max = 50, message = "Type cannot be longer than 50 characters")
    private Type type;

    @Size(max = 255, message = "Title cannot be longer than 255 characters")
    private String title;

    private String description;

    @Size(max = 255, message = "File URL cannot be longer than 255 characters")
    private String fileUrl;
}
