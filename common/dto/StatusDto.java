package com.cms.cmsapp.common.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cms.cmsapp.common.Enums.Status;

@Component
public class StatusDto {
    List<String> statuses=List.of(Status.values()).stream().map(status->status.name()).toList();

    public List<String> getStatuses() {
        return statuses;
    }
}
