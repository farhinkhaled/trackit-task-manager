package com.irithir.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubTaskDto {
    private Long id;
    private String subTaskTitle;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
