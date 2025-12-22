package com.irithir.service;

import com.irithir.model.TaskHistory;

import java.util.List;

public interface TaskHistoryService {
    List<TaskHistory> findLatestHistories(Long taskId);
    List<TaskHistory> getAllTaskHistories(Long taskId);
}
