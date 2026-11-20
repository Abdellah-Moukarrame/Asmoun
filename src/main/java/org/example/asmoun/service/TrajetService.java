package org.example.asmoun.service;

import org.example.asmoun.dto.request.TrajetCreateRequest;
import org.example.asmoun.dto.request.TrajetSearchRequest;
import org.example.asmoun.dto.response.TrajetResponse;
import org.example.asmoun.repository.TrajetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrajetService {
    TrajetResponse createTrajet (Long conducteurId,TrajetCreateRequest request  );
    Page<TrajetResponse> searchTrajet (TrajetSearchRequest request, Pageable pageable);

}
