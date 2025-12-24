package com.irithir.mapper;

import com.irithir.dto.TaskDto;
import com.irithir.model.Task;

import java.util.stream.Collectors;

import static com.irithir.mapper.SubTaskMapper.mapToSubTaskDto;

public class TaskMapper {
    public static Task mapToTask(TaskDto taskDto){
        Task task = Task.builder()
                .id(taskDto.getId())
                .taskTitle(taskDto.getTaskTitle())
                .taskPriority(taskDto.getTaskPriority())
                .taskStatus(taskDto.getTaskStatus())
                .deadline(taskDto.getDeadline())
                .taskDescription(taskDto.getTaskDescription())
                .createdOn(taskDto.getCreatedOn())
                .updatedOn(taskDto.getUpdatedOn())
                .createdBy(taskDto.getCreatedBy())
                .build();

        return task;
    }

    public static TaskDto mapToTaskDto(Task task){
        TaskDto taskDto = TaskDto.builder()
                .id(task.getId())
                .taskTitle(task.getTaskTitle())
                .taskPriority(task.getTaskPriority())
                .taskStatus(task.getTaskStatus())
                .deadline(task.getDeadline())
                .taskDescription(task.getTaskDescription())
                .createdOn(task.getCreatedOn())
                .updatedOn(task.getUpdatedOn())
                .subTasks(task.getSubTasks().stream().map((subTask) -> mapToSubTaskDto(subTask)).collect(Collectors.toList()))
                .createdBy(task.getCreatedBy())
                .build();

        return taskDto;
    }
}
