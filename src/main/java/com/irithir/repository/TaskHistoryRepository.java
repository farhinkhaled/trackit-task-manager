package com.irithir.repository;

import com.irithir.model.Task;
import com.irithir.model.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
    List<TaskHistory> findTop9ByTaskOrderByCreatedOnDesc(Task task);
    List<TaskHistory> findByTaskOrderByCreatedOnDesc(Task task);
}
