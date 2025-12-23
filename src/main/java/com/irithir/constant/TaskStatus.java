package com.irithir.constant;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class TaskStatus {
    public static final String IN_PROGRESS = "in-progress";
    public static final String COMPLETED = "completed";
    public static final String OVERDUE = "overdue";
}
