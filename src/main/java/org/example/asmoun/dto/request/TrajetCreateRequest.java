package org.example.asmoun.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;


public record TrajetCreateRequest<PointArretCreateRequest>(
        @NotNull @Positive
        Long vehiculeId,

        @NotNull @Min(1) @Max(8)
        Integer nombrePlaces,

        @NotNull @Size(min = 2)
        List<@NotNull @Valid PointArretCreateRequest> pointsArret
) {
}
