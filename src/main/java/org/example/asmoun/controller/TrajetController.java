package org.example.asmoun.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.asmoun.dto.request.TrajetCreateRequest;
import org.example.asmoun.dto.request.TrajetSearchRequest;
import org.example.asmoun.dto.response.TrajetResponse;
import org.example.asmoun.service.TrajetService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class TrajetController {
    private final TrajetService trajetService;
    @PostMapping("/conducteur/{conducteurId}/trajets")
    public ResponseEntity<TrajetResponse> createTrajet(
            @PathVariable Long conducteurId,
            @Valid @RequestBody TrajetCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(trajetService.createTrajet(conducteurId, request));
    }
    @GetMapping("/trajets/search")
    public ResponseEntity<Page<TrajetResponse>> searchTrajet(
            @Valid @ModelAttribute @ParameterObject
            TrajetSearchRequest request,

            @ParameterObject @PageableDefault(size = 10)
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                trajetService.searchTrajet(request, pageable)
        );
    }

}
