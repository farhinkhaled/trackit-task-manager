package com.irithir.service;

import com.irithir.dto.SubTaskDto;
import com.irithir.model.SubTask;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubTaskService {
    void saveSubTask(Long taskId, SubTaskDto subTaskDto);
    List<SubTaskDto> findAllSubTasks();
    void deleteSubTask(Long subTaskId);
    SubTask findSubTaskById(Long subTaskId);
    Page<SubTask> getSubTasks(Long tasId, int page, int size);
}
