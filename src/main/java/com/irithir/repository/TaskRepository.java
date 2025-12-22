package com.irithir.repository;

import com.irithir.constant.TaskStatus;
import com.irithir.model.Task;
import com.irithir.repository.projection.TaskDeadlineCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Modifying
    @Query("""
        UPDATE Task t
        SET t.taskStatus = :overdueStatus
        WHERE t.deadline < :today
          AND t.taskStatus IN (:activeStatuses)
    """)
    int markTasksAsOverdue(@Param("overdueStatus") String overdueStatus,
                           @Param("activeStatuses") List<String> activeStatuses,
                           @Param("today") LocalDate today);

    @Query("SELECT t FROM Task t WHERE t.deadline = :tomorrow")
    List<Task> findDueTomorrowTasks(@Param("tomorrow") LocalDate tomorrow);

    @Query("SELECT t FROM Task t WHERE t.deadline < CURRENT_DATE AND t.taskStatus <> '" + TaskStatus.COMPLETE + "'")
    List<Task> findOverdueTasks();
}
