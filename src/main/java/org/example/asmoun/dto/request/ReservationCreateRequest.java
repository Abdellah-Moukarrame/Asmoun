package org.example.asmoun.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReservationCreateRequest(
        @NotNull @Positive Long trajetId,
        @NotNull @Positive Long pointMonteeId,
        @NotNull @Positive Long pointDescenteId,
        @NotNull @Positive Integer nombrePlaces
) {
}