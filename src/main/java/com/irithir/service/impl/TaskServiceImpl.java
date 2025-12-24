package com.irithir.service.impl;

import com.irithir.constant.TaskStatus;
import com.irithir.dto.TaskDto;
import com.irithir.model.Task;
import com.irithir.model.TaskHistory;
import com.irithir.model.UserEntity;
import com.irithir.repository.TaskHistoryRepository;
import com.irithir.repository.TaskRepository;
import com.irithir.repository.UserRepository;
import com.irithir.security.SecurityUtil;
import com.irithir.service.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.irithir.mapper.TaskMapper.mapToTask;
import static com.irithir.mapper.TaskMapper.mapToTaskDto;

@Service
@EnableScheduling
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private TaskHistoryRepository taskHistoryRepository;
    private UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository,
                           TaskHistoryRepository taskHistoryRepository,
                           UserRepository userRepository){
        this.taskRepository = taskRepository;
        this.taskHistoryRepository = taskHistoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TaskDto> findAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map((task) -> mapToTaskDto(task)).collect(Collectors.toList());
    }

    @Override
    public void saveTask(TaskDto taskDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);

        System.out.println(username);

        Task task = mapToTask(taskDto);
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        task.setCreatedBy(user);

        TaskHistory taskHistory = TaskHistory.builder()
                .eventType("New Task " +task.getTaskTitle()+ " was created on ")
                .task(task)
                .subTask(null)
                .build();

        taskRepository.save(task);
        taskHistoryRepository.save(taskHistory);
    }

    @Override
    public Page<Task> getTasks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findAll(pageable);
    }

    @Override
    public TaskDto findTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        return mapToTaskDto(task);
    }

    @Override
    public void updateTask(TaskDto taskDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);

        Task task = mapToTask(taskDto);

        LocalDate today = LocalDate.now();

        if(today.isAfter(task.getDeadline())){
            task.setTaskStatus(TaskStatus.OVERDUE);
        }
        else{
            task.setTaskStatus(TaskStatus.IN_PROGRESS);
        }

        task.setCreatedBy(user);

        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public List<TaskDto> searchTasks(String query) {
        List<Task> tasks = taskRepository.searchTasks(query);
        return tasks.stream().map((task) -> mapToTaskDto(task)).collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> upcomingTasks() {
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(10);

        List<Task> upcomingTasks = taskRepository.findUpcomingTasks(startDate, endDate)
                                    .stream()
                                    .sorted(Comparator.comparing(Task::getDeadline))
                                    .limit(5)
                                    .collect(Collectors.toList());

        return upcomingTasks.stream().map((upcomingTask) -> mapToTaskDto(upcomingTask)).collect(Collectors.toList());
    }

    @Override
    public void markTaskAsComplete(Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        task.setTaskStatus(TaskStatus.COMPLETED);
        taskRepository.save(task);
    }

    @Override
    public long countTasksByStatus(String status) {
        return taskRepository.countByTaskStatus(status);
    }

    @Override
    public List<TaskDto> allUpcomingTasks() {
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(10);

        List<Task> upcomingTasks = taskRepository.findUpcomingTasks(startDate, endDate);

        return upcomingTasks.stream().map((upcomingTask) -> mapToTaskDto(upcomingTask)).collect(Collectors.toList());
    }

    @Scheduled(cron = "0 5 0 * * ?")
    @Transactional
    public void updateOverdueTasks() {
        List<String> activeStatuses = List.of(
                TaskStatus.IN_PROGRESS
        );

        int updated = taskRepository.markTasksAsOverdue(
                TaskStatus.OVERDUE,
                activeStatuses,
                LocalDate.now()
        );
    }
}
