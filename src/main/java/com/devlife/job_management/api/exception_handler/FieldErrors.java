package com.devlife.job_management.api.exception_handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FieldErrors {
    private String name;
    private String message;
}
