package com.irithir.service.impl;

import com.irithir.dto.SubTaskDto;
import com.irithir.model.SubTask;
import com.irithir.model.Task;
import com.irithir.model.TaskHistory;
import com.irithir.repository.SubTaskRepository;
import com.irithir.repository.TaskHistoryRepository;
import com.irithir.repository.TaskRepository;
import com.irithir.service.SubTaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.irithir.mapper.SubTaskMapper.mapToSubTask;
import static com.irithir.mapper.SubTaskMapper.mapToSubTaskDto;
import static com.irithir.mapper.TaskMapper.mapToTaskDto;

@Service
public class SubTaskServiceImpl implements SubTaskService {
    private SubTaskRepository subTaskRepository;
    private TaskRepository taskRepository;
    private TaskHistoryRepository taskHistoryRepository;

    public SubTaskServiceImpl(SubTaskRepository subTaskRepository,
                              TaskRepository taskRepository,
                              TaskHistoryRepository taskHistoryRepository){
        this.taskRepository = taskRepository;
        this.subTaskRepository = subTaskRepository;
        this.taskHistoryRepository = taskHistoryRepository;
    }

    @Override
    public void saveSubTask(Long taskId, SubTaskDto subTaskDto) {
        Task task = taskRepository.findById(taskId).get();
        SubTask subTask = mapToSubTask(subTaskDto);
        List<SubTask> subtasks = task.getSubTasks();

        subtasks.add(subTask);
        task.setSubTasks(subtasks);
        subTask.setTask(task);

        TaskHistory taskHistory;

        if(subTask.getId() == null){
            taskHistory = TaskHistory.builder()
                    .eventType("New Subtask " +subTask.getSubTaskTitle()+ " added on ")
                    .task(task)
                    .subTask(subTask)
                    .build();
        }
        else{
            taskHistory = TaskHistory.builder()
                    .eventType("Subtask " +subTask.getSubTaskTitle()+ " was edited on ")
                    .task(task)
                    .subTask(subTask)
                    .build();
        }

        subTaskRepository.save(subTask);
        taskHistoryRepository.save(taskHistory);
    }

    @Override
    public List<SubTaskDto> findAllSubTasks() {
        List<SubTask> subTasks = subTaskRepository.findAll();
        return subTasks.stream().map((subTask) -> mapToSubTaskDto(subTask)).collect(Collectors.toList());
    }

    @Override
    public void deleteSubTask(Long subTaskId) {
        SubTask subTask = subTaskRepository.findById(subTaskId).get();

        TaskHistory taskHistory = TaskHistory.builder()
                .eventType("Subtask " +subTask.getSubTaskTitle()+ " was deleted on")
                .task(subTask.getTask())
                .subTask(null)
                .build();

        taskHistoryRepository.save(taskHistory);
        subTaskRepository.deleteById(subTaskId);
    }

    @Override
    public SubTask findSubTaskById(Long subTaskId) {
        SubTask subTask = subTaskRepository.findById(subTaskId).get();
        return subTask;
    }

    @Override
    public Page<SubTask> getSubTasks(Long tasId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return subTaskRepository.findByTask_Id(tasId, pageable);
    }
}
