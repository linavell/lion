package com.arin.togetherlion.copurchasing.controller;

import com.arin.togetherlion.copurchasing.domain.dto.CopurchasingCreateRequest;
import com.arin.togetherlion.copurchasing.service.CopurchasingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class CopurchasingController {

    private final CopurchasingService copurchasingService;

    @PostMapping("/copurchasings")
    public ResponseEntity<Void> create(@RequestBody @Valid CopurchasingCreateRequest request) {
        final Long copurchasingId = copurchasingService.create(request);

        return ResponseEntity.created(URI.create("/copurchasings/" + copurchasingId)).build();
    }
}

