package com.irithir.repository;

import com.irithir.constant.TaskStatus;
import com.irithir.model.Task;
import com.irithir.repository.projection.TaskDeadlineCount;
import jakarta.transaction.Transactional;
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
    @Transactional
    @Query("""
        UPDATE Task t
        SET t.taskStatus = :overdueStatus
        WHERE t.deadline < :today
          AND t.taskStatus IN (:activeStatuses)
    """)
    int markTasksAsOverdue(@Param("overdueStatus") String overdueStatus,
                           @Param("activeStatuses") List<String> activeStatuses,
                           @Param("today") LocalDate today);

    @Query("SELECT t FROM Task t WHERE t.deadline = :tomorrow AND t.taskStatus = :status")
    List<Task> findDueTomorrowTasks(@Param("tomorrow") LocalDate tomorrow,
                                    @Param("status") String status);

    @Query("SELECT t FROM Task t WHERE t.deadline BETWEEN :startDate AND :endDate")
    List<Task> findUpcomingTasks(LocalDate startDate, LocalDate endDate);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.taskStatus = :status")
    long countByTaskStatus(@Param("status") String status);
}
