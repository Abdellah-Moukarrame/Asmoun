package org.example.asmoun.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record TrajetCreateRequest(
        @NotNull @Positive Long vehiculeId,
        @NotNull @Size(min = 2)
        List<@NotNull @Valid PointArretCreateRequest> pointsArret
) {}