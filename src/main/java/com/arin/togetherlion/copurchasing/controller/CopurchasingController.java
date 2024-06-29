package com.arin.togetherlion.copurchasing.controller;

import com.arin.togetherlion.copurchasing.domain.dto.CopurchasingCreateRequest;
import com.arin.togetherlion.copurchasing.service.CopurchasingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
@Validated
public class CopurchasingController {

    @Autowired
    private final CopurchasingService copurchasingService;

    @PostMapping("/copurchasings")
    public ResponseEntity<Void> create(@RequestBody @Valid CopurchasingCreateRequest request) {
        final Long copurchasingId = copurchasingService.create(request);

        return ResponseEntity.created(URI.create("/copurchasings/" + copurchasingId)).build();
    }

    @DeleteMapping("/copurchasings/{copurchasingId}")
    public ResponseEntity<String> delete(@PathVariable Long copurchasingId, @RequestBody Long userId) {
        try {
            copurchasingService.delete(userId, copurchasingId);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}

