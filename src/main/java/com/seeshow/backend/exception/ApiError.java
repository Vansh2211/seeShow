package com.seeshow.backend.exception;

import lombok.*;

import java.time.LocalDateTime;
import lombok.*;
import java.util.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiError {

    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;
    
    private String path;

    private Map<String, String> validationErrors;
}
