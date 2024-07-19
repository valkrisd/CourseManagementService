package com.niiazov.coursemanagement.dto;

import com.niiazov.coursemanagement.enums.Status;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CourseDTO {
    @Size(max = 255, message = "Title cannot be longer than 255 characters")
    @NotNull
    private String title;

    private String description;

    @Size(max = 100, message = "Author name cannot be longer than 100 characters")
    private String author;

    private Integer duration;

    private Status status;

    @Digits(integer = 10, fraction = 2, message = "Price must be a number of up to 10 integer digits with 2 fractional digits")
    private BigDecimal price;

    private LocalDate startDate;

    private LocalDate endDate;
}
