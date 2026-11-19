package org.example.asmoun.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PointArretCreateRequest(
        @NotBlank String ville ,
        @NotNull @Min(0) Integer ordre ,
        @NotNull @Future LocalDateTime heurePassage ,
        @NotNull @DecimalMin("0.00")
        @Digits(integer = 8,fraction = 2)
        BigDecimal prixDepuisDepart
) {

}
