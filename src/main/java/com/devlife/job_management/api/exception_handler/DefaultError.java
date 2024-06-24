package com.devlife.job_management.api.exception_handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
public class DefaultError {
    private Integer status;
    private OffsetDateTime dateTime;
    private String title;

    private List<FieldErrors> fieldErrorsList;
}
