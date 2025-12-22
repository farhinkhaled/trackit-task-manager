package com.irithir.mapper;

import com.irithir.dto.SubTaskDto;
import com.irithir.model.SubTask;

public class SubTaskMapper {
    public static SubTask mapToSubTask(SubTaskDto subTaskDto){
        SubTask subTask = SubTask.builder()
                .id(subTaskDto.getId())
                .subTaskTitle(subTaskDto.getSubTaskTitle())
                .createdOn(subTaskDto.getCreatedOn())
                .updatedOn(subTaskDto.getUpdatedOn())
                .build();

        return subTask;
    }

    public static SubTaskDto mapToSubTaskDto(SubTask subTask){
        SubTaskDto subTaskDto = SubTaskDto.builder()
                .id(subTask.getId())
                .subTaskTitle(subTask.getSubTaskTitle())
                .createdOn(subTask.getCreatedOn())
                .updatedOn(subTask.getUpdatedOn())
                .build();

        return subTaskDto;
    }
}
