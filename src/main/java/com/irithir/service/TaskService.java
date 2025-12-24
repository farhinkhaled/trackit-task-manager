package com.irithir.service;

import com.irithir.dto.TaskDto;
import com.irithir.model.Task;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    List<TaskDto> findAllTasks();
    void saveTask(TaskDto taskDto);
    Page<Task> getTasks(int page, int size);
    TaskDto findTaskById(Long taskId);
    void updateTask(TaskDto taskDto);
    void deleteTask(Long taskId);
//    List<TaskDto> searchTasks(String query);
    List<TaskDto> upcomingTasks();
    void markTaskAsComplete(Long taskId);
    long countTasksByStatus(String status);
    List<TaskDto> allUpcomingTasks();

    List<TaskDto> findTasksByDeadline(LocalDate deadline);
}
