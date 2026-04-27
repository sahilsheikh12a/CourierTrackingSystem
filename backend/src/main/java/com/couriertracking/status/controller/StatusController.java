package com.couriertracking.status.controller;

import com.couriertracking.status.dto.StatusUpdateRequest;
import com.couriertracking.status.dto.StatusUpdateResponse;
import com.couriertracking.status.service.StatusUpdateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
public class StatusController {
    private final StatusUpdateService statusUpdateService;

    public StatusController(StatusUpdateService statusUpdateService) {
        this.statusUpdateService = statusUpdateService;
    }

    @PutMapping
    public ResponseEntity<StatusUpdateResponse> updateStatus(@RequestBody StatusUpdateRequest request) {
        return ResponseEntity.ok(statusUpdateService.updateStatus(request));
    }
}
