package com.irithir.service;

import com.irithir.dto.TaskDto;
import com.irithir.model.TaskNotification;

import java.util.List;

public interface TaskNotificationService {
    List<TaskNotification> findAllNotifications();
}
