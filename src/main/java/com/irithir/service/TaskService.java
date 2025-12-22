package com.irithir.service;

import com.irithir.dto.TaskDto;
import com.irithir.model.Task;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {
    List<TaskDto> findAllTasks();
    void saveTask(TaskDto taskDto);
    Page<Task> getTasks(int page, int size);
    TaskDto findTaskById(Long taskId);
    void updateTask(TaskDto taskDto);
    void deleteTask(Long taskId);
    List<TaskDto> searchTasks(String query);
}
