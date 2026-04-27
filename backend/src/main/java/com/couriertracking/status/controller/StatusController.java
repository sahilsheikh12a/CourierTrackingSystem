package com.couriertracking.status.controller;

import com.couriertracking.status.dto.StatusUpdateRequest;
import com.couriertracking.status.dto.StatusUpdateResponse;
import com.couriertracking.status.service.StatusUpdateService;

public class StatusController {
    private final StatusUpdateService statusUpdateService;

    public StatusController(StatusUpdateService statusUpdateService) {
        this.statusUpdateService = statusUpdateService;
    }

    public StatusUpdateResponse updateStatus(StatusUpdateRequest request) {
        return statusUpdateService.updateStatus(request);
    }
}
