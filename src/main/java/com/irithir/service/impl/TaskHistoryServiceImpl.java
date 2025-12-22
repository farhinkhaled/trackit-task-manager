package com.irithir.service.impl;

import com.irithir.model.Task;
import com.irithir.model.TaskHistory;
import com.irithir.repository.TaskHistoryRepository;
import com.irithir.repository.TaskRepository;
import com.irithir.service.TaskHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskHistoryServiceImpl implements TaskHistoryService {
    private TaskHistoryRepository taskHistoryRepository;
    private TaskRepository taskRepository;

    public TaskHistoryServiceImpl(TaskHistoryRepository taskHistoryRepository,
                                  TaskRepository taskRepository) {
        this.taskHistoryRepository = taskHistoryRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskHistory> findLatestHistories(Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        List<TaskHistory> latestHistories = taskHistoryRepository.findTop9ByTaskOrderByCreatedOnDesc(task);
        return latestHistories;
    }

    @Override
    public List<TaskHistory> getAllTaskHistories(Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        List<TaskHistory> taskHistories = taskHistoryRepository.findByTaskOrderByCreatedOnDesc(task);
        return taskHistories;
    }
}
