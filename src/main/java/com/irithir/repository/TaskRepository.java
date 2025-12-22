package com.irithir.repository;

import com.irithir.model.Task;
import com.irithir.repository.projection.TaskDeadlineCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE LOWER(t.taskTitle) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Task> searchTasks(String query);

    @Query("""
        SELECT t.deadline AS deadline, COUNT(t) AS count
        FROM Task t
        WHERE t.deadline BETWEEN :startDate AND :endDate
        GROUP BY t.deadline
    """)
    List<TaskDeadlineCount> countTasksByDeadlineBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    List<Task> findByDeadline(LocalDate deadline);
}
