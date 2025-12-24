package com.irithir.repository;

import com.irithir.model.Task;
import com.irithir.model.UserEntity;
import com.irithir.repository.projection.TaskDeadlineCount;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
        WHERE t.deadline BETWEEN :startDate AND :endDate AND t.createdBy = :user
        GROUP BY t.deadline
    """)
    List<TaskDeadlineCount> countTasksByDeadlineBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("user") UserEntity user);

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

    @Query("SELECT t FROM Task t WHERE t.deadline = :tomorrow AND t.taskStatus = :status AND t.createdBy = :user")
    List<Task> findDueTomorrowTasks(@Param("tomorrow") LocalDate tomorrow,
                                    @Param("status") String status,
                                    @Param("user") UserEntity user);

    @Query("SELECT t FROM Task t WHERE t.deadline BETWEEN :startDate AND :endDate AND t.createdBy = :user")
    List<Task> findUpcomingTasks(LocalDate startDate,
                                       LocalDate endDate,
                                       UserEntity user);

    List<Task> findByCreatedBy(UserEntity user);
    Page<Task> findByCreatedBy(UserEntity user, Pageable pageable);

    long countByCreatedByAndTaskStatus(UserEntity user, String status);
    List<Task> findByDeadlineAndCreatedBy(LocalDate deadline, UserEntity user);
}
