package com.shoppingcart.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class APIError {

    private HttpStatus status;

    private List<String> errors;
    private LocalDateTime timestamp;
    private String pathUri;
}
