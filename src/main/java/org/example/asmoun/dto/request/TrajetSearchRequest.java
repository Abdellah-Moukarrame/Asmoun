package org.example.asmoun.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TrajetSearchRequest(
        String villDeDepart ,
        String villeDarrive,
        LocalDateTime dateDepart ,
        Integer nombrePlaces ,
        BigDecimal prixMax


        ) {
}
