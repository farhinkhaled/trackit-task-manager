package com.irithir.service.impl;

import com.irithir.constant.TaskStatus;
import com.irithir.dto.TaskDto;
import com.irithir.model.Task;
import com.irithir.model.TaskNotification;
import com.irithir.model.UserEntity;
import com.irithir.repository.TaskRepository;
import com.irithir.repository.UserRepository;
import com.irithir.security.SecurityUtil;
import com.irithir.service.TaskNotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskNotificationServiceImpl implements TaskNotificationService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    public TaskNotificationServiceImpl(TaskRepository taskRepository,
                                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TaskNotification> findAllNotifications() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String status = TaskStatus.IN_PROGRESS;

        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);

        List<Task> dueTomorrowTasks = taskRepository.findDueTomorrowTasks(tomorrow, status, user);

        return dueTomorrowTasks.stream()
                .map(task -> TaskNotification.builder()
                        .taskId(task.getId())
                        .notificationMessage("\"" + task.getTaskTitle() + "\" is due tomorrow.")
                        .build())
                .toList();
    }
}
