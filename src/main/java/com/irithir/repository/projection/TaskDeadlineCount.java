package com.irithir.repository.projection;

import java.time.LocalDate;

public interface TaskDeadlineCount {
    LocalDate getDeadline();
    Long getCount();
}
