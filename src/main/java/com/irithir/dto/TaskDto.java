package com.irithir.dto;

import com.irithir.model.UserEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String taskTitle;
    private String taskPriority;
    private String taskStatus;
    private LocalDate deadline;
    private String taskDescription;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private List<SubTaskDto> subTasks;
    private UserEntity createdBy;
}
