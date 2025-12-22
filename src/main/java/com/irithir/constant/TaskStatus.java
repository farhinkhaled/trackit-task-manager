package com.irithir.constant;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class TaskStatus {
    public static final String IN_PROGRESS = "IN_PROGRESS";
    public static final String COMPLETE = "COMPLETE";
    public static final String OVERDUE = "OVERDUE";
}
