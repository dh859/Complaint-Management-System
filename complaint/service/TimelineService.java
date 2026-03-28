package com.cms.cmsapp.complaint.service;

import org.springframework.stereotype.Service;

import com.cms.cmsapp.common.Enums.Status;
import com.cms.cmsapp.complaint.entity.Complaint;
import com.cms.cmsapp.complaint.entity.Timeline;
import com.cms.cmsapp.complaint.repository.TimelineRepo;
import com.cms.cmsapp.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimelineService {

    private final TimelineRepo timelineRepo;

    public void createTimelineEntry(
            Complaint complaint,
            User performedBy,
            Status before,
            Status after,
            String remarks
    ) {

        Timeline timeline = new Timeline();
        timeline.setComplaint(complaint);
        timeline.setUser(performedBy);
        timeline.setStatusBefore(before);
        timeline.setStatusAfter(after);
        timeline.setRemarks(remarks);

        timelineRepo.save(timeline);
    }
}
